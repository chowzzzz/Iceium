/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enumeration.OrderItemStatusEnum;

/**
 *
 * @author hilary
 */
@Entity
public class OrderItemEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer orderItemNumber;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantity;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal subTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private OrderItemStatusEnum orderItemStatusEnum;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SpecificationEntity specificationEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private OrderEntity orderEntity;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ReviewEntity reviewEntity;

    public OrderItemEntity()
    {
        this.subTotal = BigDecimal.ZERO;
        this.orderItemStatusEnum = OrderItemStatusEnum.PENDING;
    }

    public OrderItemEntity(Integer serialNumber, SpecificationEntity specificationEntity, Integer quantity)
    {
        this();
        
        this.orderItemNumber = serialNumber;
        this.specificationEntity = specificationEntity;
        this.quantity = quantity;
    }

    public Long getOrderItemId()
    {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId)
    {
        this.orderItemId = orderItemId;
    }

    public Integer getOrderItemNumber()
    {
        return orderItemNumber;
    }

    public void setOrderItemNumber(Integer orderItemNumber)
    {
        this.orderItemNumber = orderItemNumber;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal()
    {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal)
    {
        this.subTotal = subTotal;
    }

    public OrderItemStatusEnum getOrderItemStatusEnum()
    {
        return orderItemStatusEnum;
    }

    public void setOrderItemStatusEnum(OrderItemStatusEnum orderItemStatusEnum)
    {
        this.orderItemStatusEnum = orderItemStatusEnum;
    }

    public SpecificationEntity getSpecificationEntity()
    {
        return specificationEntity;
    }

    public void setSpecificationEntity(SpecificationEntity specificationEntity)
    {
        if (this.specificationEntity != null)
        {
            this.specificationEntity.getOrderItemEntities().remove(this);
        }

        this.specificationEntity = specificationEntity;

        if (this.specificationEntity != null)
        {
            if (!this.specificationEntity.getOrderItemEntities().contains(this))
            {
                this.specificationEntity.getOrderItemEntities().add(this);
            }
        }
    }
    
    public void setSpecificationEntityToNull() {
        this.specificationEntity = null;
    }

    public void removeSpecificationEntity()
    {
        if (this.specificationEntity != null)
        {
            this.specificationEntity.getOrderItemEntities().remove(this);
            this.specificationEntity = null;
        }
    }

    public OrderEntity getOrderEntity()
    {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity)
    {
        if (this.orderEntity != null)
        {
            this.orderEntity.getOrderItemEntities().remove(this);
        }

        this.orderEntity = orderEntity;

        if (this.orderEntity != null)
        {
            if (!this.orderEntity.getOrderItemEntities().contains(this))
            {
                this.orderEntity.getOrderItemEntities().add(this);
            }
        }
    }
    
    
    public void setOrderEntityToNull() {
        this.orderEntity = null;
    }

    public void removeOrderEntity()
    {
        if (this.orderEntity != null)
        {
            this.orderEntity.getOrderItemEntities().remove(this);
            this.orderEntity = null;
        }
    }

    public ReviewEntity getReviewEntity()
    {
        return reviewEntity;
    }

    public void setReviewEntity(ReviewEntity reviewEntity)
    {
        this.reviewEntity = reviewEntity;
    }

    public void removeOrderItemEntity()
    {
        if (this.reviewEntity != null)
        {
            this.reviewEntity.setOrderItemEntity(null);
            this.reviewEntity = null;
        }
    }

}
