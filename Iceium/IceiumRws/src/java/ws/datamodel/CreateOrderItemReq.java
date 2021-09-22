/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.OrderItemEntity;

/**
 *
 * @author MOK
 */
public class CreateOrderItemReq {
    private OrderItemEntity newOrderItem;
    private Long productId;

    public CreateOrderItemReq() {
    }

    public CreateOrderItemReq(OrderItemEntity newOrderItem, Long productId) {
        this.newOrderItem = newOrderItem;
        this.productId = productId;
    }

    public OrderItemEntity getNewOrderItem() {
        return newOrderItem;
    }

    public void setNewOrderItem(OrderItemEntity newOrderItem) {
        this.newOrderItem = newOrderItem;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    
}
