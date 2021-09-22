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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
public class CouponEntity extends PromotionEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 12, unique = true)
    @NotNull
    @Size(min = 12, max = 12)
    private String couponCode;

    @Column(nullable = false, precision = 6, scale = 2)
    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal minimumSpend;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer maximumRedemptions;

    @OneToMany(mappedBy = "couponEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<OrderEntity> orderEntities;

    public CouponEntity()
    {
        super();
        this.orderEntities = new ArrayList<>();
    }

    public CouponEntity(BigDecimal minimumSpend, Integer maximumRedemptions, BigDecimal discountRate, LocalDateTime startDateTime, LocalDateTime endDateTime, String description)
    {
        super(discountRate, startDateTime, endDateTime, description);
        this.minimumSpend = minimumSpend;
        this.maximumRedemptions = maximumRedemptions;
        this.orderEntities = new ArrayList<>();
    }
    
    public Long getCouponId()
    {
        return super.getPromotionId();
    }

    public String getCouponCode()
    {
        return couponCode;
    }

    public void setCouponCode(String couponCode)
    {
        this.couponCode = couponCode;
    }

    public BigDecimal getMinimumSpend()
    {
        return minimumSpend;
    }

    public void setMinimumSpend(BigDecimal minimumSpend)
    {
        this.minimumSpend = minimumSpend;
    }

    public Integer getMaximumRedemptions()
    {
        return maximumRedemptions;
    }

    public void setMaximumRedemptions(Integer maximumRedemptions)
    {
        this.maximumRedemptions = maximumRedemptions;
    }

    public List<OrderEntity> getOrderEntities()
    {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities)
    {
        this.orderEntities = orderEntities;
    }

}
