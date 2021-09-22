/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.initialization;

import ejb.session.stateless.OrderEntitySessionBeanLocal;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.SpecificationEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.enumeration.PaymentMethodEnum;
import util.exception.CreateNewOrderException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;

/**
 *
 * @author Theodoric
 */
public class CheckoutCart
{

    private List<OrderItemEntity> orderItemEntities;
    private Integer totalOrderItem;
    private Integer totalQuantity;

    public CheckoutCart()
    {
        initialiseCart();
    }

    public void initialiseCart()
    {
        orderItemEntities = new ArrayList<>();
        totalOrderItem = 0;
        totalQuantity = 0;
    }

    public BigDecimal addItem(SpecificationEntity specificationEntity, Integer quantity)
    {
        BigDecimal unitPrice = specificationEntity.getProductEntity().getUnitPrice();
        if (specificationEntity.getProductEntity().getSaleEntity() != null)
        {
            unitPrice = unitPrice.subtract(unitPrice.multiply(specificationEntity.getProductEntity().getSaleEntity().getDiscountRate()));
        }

        BigDecimal subTotal = unitPrice.multiply(new BigDecimal(quantity)).setScale(2, BigDecimal.ROUND_UP);

        ++totalOrderItem;
        orderItemEntities.add(new OrderItemEntity(totalOrderItem, specificationEntity, quantity));
        totalQuantity += quantity;

        return subTotal;
    }

    public OrderEntity doCheckout(OrderEntitySessionBeanLocal orderEntitySessionBeanLocal, Long customerId, Long addressId, Long couponId, PaymentMethodEnum paymentMethodEnum, LocalDateTime transactionDateTime) throws CustomerNotFoundException, CreateNewOrderException, InputDataValidationException
    {
        OrderEntity newOrderEntity = new OrderEntity(totalOrderItem, totalQuantity, transactionDateTime, orderItemEntities, paymentMethodEnum);

        newOrderEntity = orderEntitySessionBeanLocal.createNewOrder(newOrderEntity, customerId, addressId, couponId);
        initialiseCart();

        return newOrderEntity;
    }

    public List<OrderItemEntity> getOrderItemEntities()
    {
        return orderItemEntities;
    }

    public void setOrderItemEntities(List<OrderItemEntity> orderItemEntities)
    {
        this.orderItemEntities = orderItemEntities;
    }

    public Integer getTotalOrderItem()
    {
        return totalOrderItem;
    }

    public void setTotalOrderItem(Integer totalOrderItem)
    {
        this.totalOrderItem = totalOrderItem;
    }

    public Integer getTotalQuantity()
    {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity)
    {
        this.totalQuantity = totalQuantity;
    }

}
