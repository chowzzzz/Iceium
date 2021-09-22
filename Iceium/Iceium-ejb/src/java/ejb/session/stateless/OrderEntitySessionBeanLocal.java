/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderEntity;
import util.exception.CancelOrderException;
import util.exception.CreateNewOrderException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.OrderNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hilary
 */
@Local
public interface OrderEntitySessionBeanLocal
{

    public OrderEntity createNewOrder(OrderEntity newOrderEntity, Long customerId, Long addressId, Long couponId) throws CustomerNotFoundException, CreateNewOrderException, InputDataValidationException;

    public void cancelOrder(Long orderId) throws CancelOrderException;

    public List<OrderEntity> retrieveAllOrders();

    public List<List<OrderEntity>> retrieveAllOrdersFromPastTwelveMonths();
    
    public OrderEntity retrieveOrderByOrderId(Long orderId) throws OrderNotFoundException;

    public List<OrderEntity> retrieveOrdersByCustomer(Long customerId);
    
}