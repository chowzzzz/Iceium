/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author MOK
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PromotionEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    @Column(nullable = false, precision = 3, scale = 2)
    @DecimalMin("0.00")
    @NotNull
    @Digits(integer = 3, fraction = 2)
    private BigDecimal discountRate;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDateTime endDateTime;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String description;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer currentRedemptions;

    public PromotionEntity()
    {
        this.currentRedemptions = 0;
    }

    public PromotionEntity(BigDecimal discountRate, LocalDateTime startDateTime, LocalDateTime endDateTime, String description)
    {
        this();

        this.discountRate = discountRate;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    public Long getPromotionId()
    {
        return promotionId;
    }

    public void setPromotionId(Long promotionId)
    {
        this.promotionId = promotionId;
    }

    public BigDecimal getDiscountRate()
    {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate)
    {
        this.discountRate = discountRate;
    }

    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getCurrentRedemptions()
    {
        return currentRedemptions;
    }

    public void setCurrentRedemptions(Integer currentRedemptions)
    {
        this.currentRedemptions = currentRedemptions;
    }
    
    public void addCurrentRedemption()
    {
        this.currentRedemptions++;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final PromotionEntity other = (PromotionEntity) obj;
        if (!Objects.equals(this.promotionId, other.promotionId))
        {
            return false;
        }
        return true;
    }

}
