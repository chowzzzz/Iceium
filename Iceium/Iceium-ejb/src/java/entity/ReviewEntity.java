/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hilary
 */
@Entity
public class ReviewEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Max(5)
    private Double rating;

    @Column(nullable = false, length = 400)
    @NotNull
    @Size(max = 400)
    private String review;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime dateOfReview;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public OrderItemEntity orderItemEntity;

    public ReviewEntity()
    {
    }

    public ReviewEntity(Double rating, String review)
    {
        this();

        this.rating = rating;
        this.review = review;
        this.dateOfReview = LocalDateTime.now();
    }

    public Long getReviewId()
    {
        return reviewId;
    }

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public String getReview()
    {
        return review;
    }

    public void setReview(String review)
    {
        this.review = review;
    }

    public LocalDateTime getDateOfReview()
    {
        return dateOfReview;
    }

    public void setDateOfReview(LocalDateTime dateOfReview)
    {
        this.dateOfReview = dateOfReview;
    }

    public OrderItemEntity getOrderItemEntity()
    {
        return orderItemEntity;
    }

    public void setOrderItemEntity(OrderItemEntity orderItemEntity)
    {
        this.orderItemEntity = orderItemEntity;     
    }
    
    public void removeOrderItemEntity()
    {
        if (this.orderItemEntity != null)
        {
            this.orderItemEntity.setReviewEntity(null);
            this.orderItemEntity = null;
        }
    }

}
