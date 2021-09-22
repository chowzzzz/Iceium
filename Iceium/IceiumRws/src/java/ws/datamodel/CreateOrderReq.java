/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.OrderEntity;
import java.util.List;

/**
 *
 * @author MOK
 */
public class CreateOrderReq
{

    private OrderEntity newOrderEntity;
    private Long customerId;
    private Long addressId;
    private Long couponId;

    public CreateOrderReq()
    {
    }

    public CreateOrderReq(OrderEntity newOrderEntity, Long customerId, Long addressId, Long couponId)
    {
        this.newOrderEntity = newOrderEntity;
        this.customerId = customerId;
        this.addressId = addressId;
        this.couponId = couponId;
    }

    public OrderEntity getNewOrderEntity()
    {
        return newOrderEntity;
    }

    public void setNewOrderEntity(OrderEntity newOrderEntity)
    {
        this.newOrderEntity = newOrderEntity;
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public Long getCouponId()
    {
        return couponId;
    }

    public void setCouponId(Long couponId)
    {
        this.couponId = couponId;
    }

}
