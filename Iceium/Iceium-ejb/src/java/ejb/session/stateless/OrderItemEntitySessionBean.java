/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderItemEntity;
import entity.ProductEntity;
import java.time.LocalDateTime;
import util.exception.CreateNewOrderItemException;
import util.exception.OrderItemNotFoundException;
import util.exception.OrderItemRefundExchangeException;
import util.exception.ProductNotFoundException;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.OrderItemStatusEnum;

/**
 *
 * @author Mok & hilary
 */
@Stateless
public class OrderItemEntitySessionBean implements OrderItemEntitySessionBeanLocal
{

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;
    @Resource
    private EJBContext eJBContext;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OrderItemEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public OrderItemEntity createNewOrderItem(OrderItemEntity newOrderItemEntity, Long productId) throws CreateNewOrderItemException
    {
        if (newOrderItemEntity != null)
        {
            try
            {
                ProductEntity productEntity = productEntitySessionBeanLocal.retrieveProductByProductId(productId);
//                newOrderItemEntity.setProductEntity(productEntity);
//                productEntity.getOrderItemEntities().add(newOrderItemEntity);
                newOrderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.PENDING);

                em.persist(newOrderItemEntity);
                em.flush();

                return newOrderItemEntity;
            }
            catch (ProductNotFoundException ex)
            {
                eJBContext.setRollbackOnly();
                throw new CreateNewOrderItemException(ex.getMessage());
            }
        }
        else
        {
            throw new CreateNewOrderItemException("Order item information not provided");
        }
    }

    @Override
    public List<OrderItemEntity> retrieveAllOrderItems()
    {
        Query query = em.createQuery("SELECT oi FROM OrderItemEntity oi");
        List<OrderItemEntity> orderItemEntities = query.getResultList();

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            orderItemEntity.getOrderEntity();
        }

        return orderItemEntities;
    }

    @Override
    public Long checkAllOrderItemsNotClosed(LocalDateTime now)
    {
        Query query = em.createQuery("SELECT oi FROM OrderItemEntity oi WHERE oi.orderItemStatusEnum <> :inOrderItemStatusEnum");
        query.setParameter("inOrderItemStatusEnum", OrderItemStatusEnum.ORDER_CLOSED);

        List<OrderItemEntity> orderItemEntities = query.getResultList();

        long ordersClosed = 0;

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            LocalDateTime closeOrderDateTime = orderItemEntity.getOrderEntity().getTransactionDateTime().plusDays(30);
            if (closeOrderDateTime.isBefore(now))
            {
                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.ORDER_CLOSED);
                ordersClosed++;
            }
        }

        return ordersClosed;
    }

    @Override
    public OrderItemEntity retrieveOrderItemByOrderItemId(Long orderItemId) throws OrderItemNotFoundException
    {
        OrderItemEntity orderItemEntity = em.find(OrderItemEntity.class, orderItemId);

        if (orderItemEntity != null)
        {
            orderItemEntity.getOrderEntity();

            return orderItemEntity;
        }
        else
        {
            throw new OrderItemNotFoundException("Order Item ID " + orderItemId + " does not exist!");
        }
    }

    @Override
    public List<OrderItemEntity> retrieveOrderItemsByCustomer(Long customerId)
    {
        Query query = em.createQuery("SELECT oi FROM OrderItemEntity oi WHERE oi.orderEntity.customerEntity.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        List<OrderItemEntity> orderItemEntities = query.getResultList();

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            orderItemEntity.getOrderEntity();
        }

        return orderItemEntities;
    }

    @Override
    public List<OrderItemEntity> retrieveOrderItemsByOrder(Long orderId)
    {
        Query query = em.createQuery("SELECT oi FROM OrderItemEntity oi WHERE oi.orderEntity.orderId = :inOrderId");
        query.setParameter("inOrderId", orderId);
        List<OrderItemEntity> orderItemEntities = query.getResultList();

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            orderItemEntity.getOrderEntity();
        }

        return orderItemEntities;
    }

    @Override
    public void updateOrderItem(Long orderItemId, OrderItemStatusEnum orderItemStatusEnum) throws OrderItemNotFoundException
    {
        OrderItemEntity orderItemEntity = retrieveOrderItemByOrderItemId(orderItemId);

        orderItemEntity.setOrderItemStatusEnum(orderItemStatusEnum);
    }

    @Override
    public List<OrderItemEntity> retrieveAllRefundExchangeOrderItems()
    {
        Query query = em.createQuery("SELECT oi FROM OrderItemEntity oi WHERE oi.orderItemStatusEnum IN :inRefundExchangeEnum");
        query.setParameter("inRefundExchangeEnum", EnumSet.of(OrderItemStatusEnum.EXCHANGE_PENDING, OrderItemStatusEnum.EXCHANGE_APPROVED, OrderItemStatusEnum.EXCHANGE_COMPLETED, OrderItemStatusEnum.REFUND_PENDING, OrderItemStatusEnum.REFUND_APPROVED, OrderItemStatusEnum.REFUND_COMPLETED, OrderItemStatusEnum.EXCHANGE_REFUND_CANCELLED));

        List<OrderItemEntity> orderItemEntities = query.getResultList();

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            orderItemEntity.getOrderEntity();
        }

        return orderItemEntities;
    }

    @Override
    public void approveRefund(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException
    {
        OrderItemEntity orderItemEntity = retrieveOrderItemByOrderItemId(orderItemId);

        if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.REFUND_PENDING)
        {
            orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.REFUND_APPROVED);
        }
        else
        {
            throw new OrderItemRefundExchangeException("Order Item is not pending for refund approval");
        }
    }

    @Override
    public void approveExchange(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException
    {
        OrderItemEntity orderItemEntity = retrieveOrderItemByOrderItemId(orderItemId);

        if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.EXCHANGE_PENDING)
        {
            orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.EXCHANGE_APPROVED);
        }
        else
        {
            throw new OrderItemRefundExchangeException("Order Item is not pending for exchange approval");
        }
    }

    @Override
    public void cancelRefund(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException
    {
        OrderItemEntity orderItemEntity = retrieveOrderItemByOrderItemId(orderItemId);

        if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.REFUND_PENDING || orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.REFUND_APPROVED)
        {
            orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.EXCHANGE_REFUND_CANCELLED);
        }
        else if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.REFUND_COMPLETED)
        {
            throw new OrderItemRefundExchangeException("Order Item is refunded already");
        }
        else
        {
            throw new OrderItemRefundExchangeException("Order Item is not applicable to cancel refund");
        }
    }

    @Override
    public void cancelExchange(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException
    {
        OrderItemEntity orderItemEntity = retrieveOrderItemByOrderItemId(orderItemId);

        if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.EXCHANGE_PENDING || orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.EXCHANGE_APPROVED)
        {
            orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.EXCHANGE_REFUND_CANCELLED);
        }
        else if (orderItemEntity.getOrderItemStatusEnum() == OrderItemStatusEnum.EXCHANGE_COMPLETED)
        {
            throw new OrderItemRefundExchangeException("Order Item is exchanged already");
        }
        else
        {
            throw new OrderItemRefundExchangeException("Order Item is not applicable to cancel exchange");
        }
    }

}
