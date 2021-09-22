/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderItemEntity;
import java.time.LocalDateTime;
import util.exception.CreateNewOrderItemException;
import util.exception.OrderItemNotFoundException;
import util.exception.OrderItemRefundExchangeException;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.OrderItemStatusEnum;

/**
 *
 * @author Theodoric & hilary
 */
@Local
public interface OrderItemEntitySessionBeanLocal
{

    public OrderItemEntity createNewOrderItem(OrderItemEntity newOrderItemEntity, Long productId) throws CreateNewOrderItemException;

    public List<OrderItemEntity> retrieveAllOrderItems();

    public OrderItemEntity retrieveOrderItemByOrderItemId(Long orderItemId) throws OrderItemNotFoundException;

    public List<OrderItemEntity> retrieveAllRefundExchangeOrderItems();

    public List<OrderItemEntity> retrieveOrderItemsByCustomer(Long customerId);

    public List<OrderItemEntity> retrieveOrderItemsByOrder(Long orderId);
    
    public Long checkAllOrderItemsNotClosed(LocalDateTime now);

    public void approveRefund(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException;

    public void approveExchange(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException;

    public void cancelRefund(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException;

    public void cancelExchange(Long orderItemId) throws OrderItemNotFoundException, OrderItemRefundExchangeException;

    public void updateOrderItem(Long orderItemId, OrderItemStatusEnum orderItemStatusEnum) throws OrderItemNotFoundException;

}
