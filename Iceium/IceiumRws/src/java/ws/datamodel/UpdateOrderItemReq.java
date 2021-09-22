/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import util.enumeration.OrderItemStatusEnum;

/**
 *
 * @author MOK
 */
public class UpdateOrderItemReq {
    private Long orderItemId;
    private OrderItemStatusEnum orderItemStatusEnum;

    public UpdateOrderItemReq() {
    }

    public UpdateOrderItemReq(Long orderItemId, OrderItemStatusEnum orderItemStatusEnum) {
        this.orderItemId = orderItemId;
        this.orderItemStatusEnum = orderItemStatusEnum;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderItemStatusEnum getOrderItemStatusEnum() {
        return orderItemStatusEnum;
    }

    public void setOrderItemStatusEnum(OrderItemStatusEnum orderItemStatusEnum) {
        this.orderItemStatusEnum = orderItemStatusEnum;
    }
    
    
}
