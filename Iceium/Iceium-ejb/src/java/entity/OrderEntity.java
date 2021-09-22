/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.enumeration.PaymentMethodEnum;

/**
 *
 * @author Theodoric
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true, length = 24)
    @NotNull
    @Size(min = 24, max = 24)
    private String serialNumber;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer totalOrderItem;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer totalQuantity;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime transactionDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private PaymentMethodEnum paymentMethodEnum;

    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CustomerEntity customerEntity;

    @XmlTransient
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private AddressEntity addressEntity;

    @OneToMany(mappedBy = "orderEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<OrderItemEntity> orderItemEntities;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private CouponEntity couponEntity;

    public OrderEntity()
    {
        this.orderItemEntities = new ArrayList<>();
        this.totalOrderItem = 0;
        this.totalQuantity = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

    public OrderEntity(Integer totalOrderItem, Integer totalQuantity, LocalDateTime transactionDateTime, List<OrderItemEntity> orderItemEntities, PaymentMethodEnum paymentMethodEnum)
    {
        this();

        this.totalOrderItem = totalOrderItem;
        this.totalQuantity = totalQuantity;
        this.transactionDateTime = transactionDateTime;
        this.orderItemEntities = orderItemEntities;
        this.paymentMethodEnum = paymentMethodEnum;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
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

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }
    
    public void addTotalAmount(BigDecimal addTotalAmount)
    {
        this.totalAmount = this.totalAmount.add(addTotalAmount);
    }

    public LocalDateTime getTransactionDateTime()
    {
        return transactionDateTime;
    }

    public String getMonthYear()
    {
        return transactionDateTime.getMonth() + " " + transactionDateTime.getYear();
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime)
    {
        this.transactionDateTime = transactionDateTime;
    }

    public PaymentMethodEnum getPaymentMethodEnum()
    {
        return paymentMethodEnum;
    }

    public void setPaymentMethodEnum(PaymentMethodEnum paymentMethodEnum)
    {
        this.paymentMethodEnum = paymentMethodEnum;
    }

    @XmlTransient
    public CustomerEntity getCustomerEntity()
    {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity)
    {
        if (this.customerEntity != null)
        {
            this.customerEntity.getOrderEntities().remove(this);
        }

        this.customerEntity = customerEntity;

        if (this.customerEntity != null)
        {
            if (!this.customerEntity.getOrderEntities().contains(this))
            {
                this.customerEntity.getOrderEntities().add(this);
            }
        }
    }
    
    public void removeCustomerEntity()
    {
        if (this.customerEntity != null)
        {
            this.customerEntity.getOrderEntities().remove(this);
            this.customerEntity = null;
        }
    }

    @XmlTransient
    public AddressEntity getAddressEntity()
    {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity)
    {
        if (this.addressEntity != null)
        {
            this.addressEntity.getOrderEntities().remove(this);
        }

        this.addressEntity = addressEntity;

        if (this.addressEntity != null)
        {
            if (!this.addressEntity.getOrderEntities().contains(this))
            {
                this.addressEntity.getOrderEntities().add(this);
            }
        }
    }

    public void removeAddressEntity()
    {
        if (this.addressEntity != null)
        {
            this.addressEntity.getOrderEntities().remove(this);
            this.addressEntity = null;
        }
    }

    public List<OrderItemEntity> getOrderItemEntities()
    {
        return orderItemEntities;
    }

    public void setOrderItemEntities(List<OrderItemEntity> orderItemEntities)
    {
        this.orderItemEntities = orderItemEntities;
    }

    public void addOrderItemEntity(OrderItemEntity orderItemEntity)
    {
        this.orderItemEntities.add(orderItemEntity);
    }

    public CouponEntity getCouponEntity()
    {
        return couponEntity;
    }

    public void setCouponEntity(CouponEntity couponEntity)
    {
        if (this.couponEntity != null)
        {
            this.couponEntity.getOrderEntities().remove(this);
        }

        this.couponEntity = couponEntity;

        if (this.couponEntity != null)
        {
            if (!this.couponEntity.getOrderEntities().contains(this))
            {
                this.couponEntity.getOrderEntities().add(this);
            }
        }
    }

    public void removeCouponEntity()
    {
        if (this.couponEntity != null)
        {
            this.couponEntity.getOrderEntities().remove(this);
            this.couponEntity = null;
        }
    }

}
