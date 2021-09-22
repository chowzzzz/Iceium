/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author Theodoric
 */
public enum OrderItemStatusEnum
{
    PENDING("Pending"),
    SHIPPED("Shipped"),
    IN_DELIVERY("In Delivery"),
    DELIVERED("Delivered"),
    DELIVERY_FAILED("Delivery Failed"),
    CANCELLED("Order Cancelled"),
    EXCHANGE_PENDING("Exchange Pending"),
    REFUND_PENDING("Refund Pending"),
    EXCHANGE_APPROVED("Exchange Approved"),
    REFUND_APPROVED("Refund Pending"),
    EXCHANGE_COMPLETED("Exchange Completed"),
    REFUND_COMPLETED("Refund Completed"),
    EXCHANGE_REFUND_CANCELLED("Exchange Refund Cancelled"),
    ITEM_NOT_RETURNED("Item Not Returned"),
    ORDER_CLOSED("Order Closed");

    private final String printName;

    OrderItemStatusEnum(String printName)
    {
        this.printName = printName;
    }
    
    public String getPrintName()
    {
        return this.printName;
    }

}
