/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CouponEntity;
import entity.CustomerEntity;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.SpecificationEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.OrderItemStatusEnum;
import util.exception.AddressNotFoundException;
import util.exception.CancelOrderException;
import util.exception.CouponNotFoundException;
import util.exception.CreateNewOrderException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;
import util.exception.SpecificationInsufficientQuantityOnHandException;
import util.exception.SpecificationNotFoundException;
import util.generator.RandomStringGenerator;
import util.validation.InputDataValidation;

/**
 *
 * @author hilary
 */
@Stateless
public class OrderEntitySessionBean implements OrderEntitySessionBeanLocal
{

    @EJB(name = "EmailSessionBeanLocal")
    private EmailSessionBeanLocal emailSessionBeanLocal;

    @EJB(name = "CouponEntitySessionBeanLocal")
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;
    
    @EJB(name = "SpecificationEntitySessionBeanLocal")
    private SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;

    @EJB(name = "AddressEntitySessionBeanLocal")
    private AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OrderEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public OrderEntity createNewOrder(OrderEntity newOrderEntity, Long customerId, Long addressId, Long couponId) throws CustomerNotFoundException, CreateNewOrderException, InputDataValidationException
    {
        if (newOrderEntity != null)
        {
            try
            {
                RandomStringGenerator randomStringGenerator = new RandomStringGenerator(24);
                String serialNumber;

                List<String> serialNumbers = retrieveAllSerialNumbers();

                do
                {
                    serialNumber = randomStringGenerator.getString().toUpperCase();
                }
                while (serialNumbers.contains(serialNumber));

                newOrderEntity.setSerialNumber(serialNumber);

                CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerId);
                newOrderEntity.setCustomerEntity(customerEntity);

                AddressEntity addressEntity = addressEntitySessionBeanLocal.retrieveAddressByAddressId(addressId);
                newOrderEntity.setAddressEntity(addressEntity);
                
                for (OrderItemEntity orderItemEntity : newOrderEntity.getOrderItemEntities())
                {
                    specificationEntitySessionBeanLocal.debitQuantityOnHand(orderItemEntity.getSpecificationEntity().getSpecificationId(), orderItemEntity.getQuantity());
                    orderItemEntity.setOrderEntity(newOrderEntity);

                    SpecificationEntity specificationEntity = specificationEntitySessionBeanLocal.retrieveSpecificationBySpecificationId(orderItemEntity.getSpecificationEntity().getSpecificationId());
//                    orderItemEntity.setSpecificationEntityToNull();
                    orderItemEntity.setSpecificationEntity(specificationEntity);

                    if (orderItemEntity.getSpecificationEntity().getProductEntity().getSaleEntity() != null)
                    {
                        orderItemEntity.getSpecificationEntity().getProductEntity().getSaleEntity().addCurrentRedemption();
                    }
                    
                    BigDecimal unitPrice = specificationEntity.getProductEntity().getUnitPrice();
                    if (specificationEntity.getProductEntity().getSaleEntity() != null)
                    {
                        unitPrice = unitPrice.subtract(unitPrice.multiply(specificationEntity.getProductEntity().getSaleEntity().getDiscountRate()));
                    }

                    BigDecimal subTotal = unitPrice.multiply(new BigDecimal(orderItemEntity.getQuantity())).setScale(2, BigDecimal.ROUND_UP);
                    orderItemEntity.setSubTotal(subTotal);
                    newOrderEntity.addTotalAmount(subTotal);
                    
                    em.persist(orderItemEntity);
                }
                
                if (couponId != null)
                {
                    CouponEntity couponEntity = couponEntitySessionBeanLocal.retrieveCouponByCouponId(couponId);
                    
                    if (couponEntity.getCurrentRedemptions() < couponEntity.getMaximumRedemptions())
                    {
                        couponEntity.addCurrentRedemption();
                        newOrderEntity.setTotalAmount(newOrderEntity.getTotalAmount().subtract(newOrderEntity.getTotalAmount().multiply(couponEntity.getDiscountRate())).setScale(2, BigDecimal.ROUND_UP));
                        newOrderEntity.setCouponEntity(couponEntity);
                    }                    
                }

                Set<ConstraintViolation<OrderEntity>> constraintViolations = validator.validate(newOrderEntity);

                if (constraintViolations.isEmpty())
                {
                    em.persist(newOrderEntity);
                    em.flush();
                    
                    if (newOrderEntity.getOrderId() > 1400)
                    {
                        emailSessionBeanLocal.emailCheckoutNotificationAsync(newOrderEntity, customerEntity.getEmail());
                    }
                    
                    return newOrderEntity;
                }
                else
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            catch (CustomerNotFoundException | AddressNotFoundException | SpecificationNotFoundException | SpecificationInsufficientQuantityOnHandException | CouponNotFoundException ex)
            {
                throw new CreateNewOrderException(ex.getMessage());
            }
            catch (InterruptedException ex)
            {
                throw new CreateNewOrderException(ex.getMessage());
            } catch (ConstraintViolationException e) {
                for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
                    
                    System.out.println("constraintviolatins: " + constraintViolation);
                }
                throw new CreateNewOrderException(e.getMessage());
            }
        }
        else
        {
            throw new CreateNewOrderException("Order information is not provided");
        }
    }

    @Override
    public OrderEntity retrieveOrderByOrderId(Long orderId) throws OrderNotFoundException
    {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity != null)
        {
            List<OrderItemEntity> orderItemEntities = orderEntity.getOrderItemEntities();
            orderItemEntities.size();

            orderEntity.getCustomerEntity();

            for (OrderItemEntity orderItemEntity : orderItemEntities)
            {
                if (orderItemEntity.getSpecificationEntity().getOrderItemEntities() != null)
                {
                    orderItemEntity.getSpecificationEntity().getOrderItemEntities().size();
                }
                if (orderItemEntity.getSpecificationEntity() != null)
                {
                    orderItemEntity.getSpecificationEntity().getProductEntity();
                }
            }

            return orderEntity;
        }
        else
        {
            throw new OrderNotFoundException("Order ID " + orderId + "does not exist!");
        }
    }

    @Override
    public List<OrderEntity> retrieveAllOrders()
    {
        Query query = em.createQuery("SELECT o FROM OrderEntity o ORDER BY o.transactionDateTime ASC");
        List<OrderEntity> orderEntities = query.getResultList();

        for (OrderEntity orderEntity : orderEntities)
        {
            orderEntity.getOrderItemEntities().size();
            orderEntity.getCustomerEntity();
            orderEntity.getCouponEntity();

            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
                if (orderItemEntity.getSpecificationEntity().getOrderItemEntities() != null)
                {
                    orderItemEntity.getSpecificationEntity().getOrderItemEntities().size();
                }
                if (orderItemEntity.getSpecificationEntity() != null)
                {
                    orderItemEntity.getSpecificationEntity().getProductEntity();
                }
            }
        }

        return orderEntities;
    }

    @Override
    public List<OrderEntity> retrieveOrdersByCustomer(Long customerId)
    {
        Query query = em.createQuery("SELECT o FROM OrderEntity o WHERE o.customerEntity.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        List<OrderEntity> orderEntities = query.getResultList();

        for (OrderEntity orderEntity : orderEntities)
        {
            orderEntity.getOrderItemEntities().size();
            orderEntity.getCustomerEntity();
            orderEntity.getCouponEntity();
        }

        return orderEntities;
    }

    @Override
    public List<List<OrderEntity>> retrieveAllOrdersFromPastTwelveMonths()
    {
        List<List<OrderEntity>> orderEntities = new ArrayList<>(12);
        for (int i = 0; i < 12; i++)
        {
            orderEntities.add(new ArrayList<>());
        }

        Query query;
        LocalDateTime now = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);

        for (int i = 11; i >= 0; i--)
        {
            query = em.createQuery("SELECT o FROM OrderEntity o WHERE o.transactionDateTime BETWEEN :startDate AND :endDate ORDER BY o.transactionDateTime ASC");
            query.setParameter("startDate", now);
            query.setParameter("endDate", now.plusMonths(1));
            orderEntities.set(i, query.getResultList());

            now = now.minusMonths(1);
        }

        return orderEntities;
    }

    @Override
    public void cancelOrder(Long orderId) throws CancelOrderException
    {
        OrderEntity orderEntity = em.find(OrderEntity.class, orderId);

        if (orderEntity != null)
        {
            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
                if (!orderItemEntity.getOrderItemStatusEnum().equals(OrderItemStatusEnum.PENDING))
                {
                    throw new CancelOrderException("An order item has already been shipped");
                }
            }

            Boolean allCancelled = true;
            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
                if (!orderItemEntity.getOrderItemStatusEnum().equals(OrderItemStatusEnum.CANCELLED))
                {
                    allCancelled = false;
                }
            }
            if (allCancelled == true)
            {
                throw new CancelOrderException("The order has already been cancelled");
            }

            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
//                try 
//                {
//                    productEntitySessionBeanLocal.creditQuantityOnHand(orderItemEntity.getProductEntity().getProductId(), orderItemEntity.getQuantity());
//                } 
//                catch (ProductNotFoundException ex) 
//                {
//                    ex.printStackTrace();
//                }
//                
//                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.CANCELLED);
            }
        }
        else
        {
            throw new CancelOrderException("Order ID " + orderId + " does not exist!");
        }
    }

    private List<String> retrieveAllSerialNumbers()
    {
        Query query = em.createQuery("SELECT o.serialNumber FROM OrderEntity o");

        return (List<String>) query.getResultList();
    }
}
