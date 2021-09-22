/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Theodoric
 */
@Entity
public class SpecificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specificationId;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantityOnHand;
  
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer reorderQuantity;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ProductEntity productEntity;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ColorEntity colorEntity;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private SizeEntity sizeEntity;

    @OneToMany(mappedBy = "specificationEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<OrderItemEntity> orderItemEntities;

    public SpecificationEntity() {
        this.orderItemEntities = new ArrayList<>();
    }

    public SpecificationEntity(Integer quantityOnHand, Integer reorderQuantity)
    {
        this();

        this.quantityOnHand = quantityOnHand;
        this.reorderQuantity = reorderQuantity;
    }

    public Long getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Long specificationId) {
        this.specificationId = specificationId;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public Integer getReorderQuantity()
    {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity)
    {
        this.reorderQuantity = reorderQuantity;
    }

    public ProductEntity getProductEntity()
    {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        if (this.productEntity != null) {
            this.productEntity.getSpecificationEntities().remove(this);
        }

        this.productEntity = productEntity;

        if (this.productEntity != null) {
            if (!this.productEntity.getSpecificationEntities().contains(this)) {
                this.productEntity.getSpecificationEntities().add(this);
            }
        }
    }

    public void setProductEntityToNull() {
        this.productEntity = null;
    }

    public void removeProductEntity() {
        if (this.productEntity != null) {
            this.productEntity.getSpecificationEntities().remove(this);
            this.productEntity = null;
        }
    }

    public ColorEntity getColorEntity() {
        return colorEntity;
    }

    public void setColorEntity(ColorEntity colorEntity) {
        if (this.colorEntity != null) {
            this.colorEntity.getSpecificationEntities().remove(this);
        }

        this.colorEntity = colorEntity;

        if (this.colorEntity != null) {
            if (!this.colorEntity.getSpecificationEntities().contains(this)) {
                this.colorEntity.getSpecificationEntities().add(this);
            }
        }
    }

    public void removeColorEntity() {
        if (this.colorEntity != null) {
            this.colorEntity.getSpecificationEntities().remove(this);
            this.colorEntity = null;
        }
    }

    public SizeEntity getSizeEntity() {
        return sizeEntity;
    }

    public void setSizeEntity(SizeEntity sizeEntity) {
        if (this.sizeEntity != null) {
            this.sizeEntity.getSpecificationEntities().remove(this);
        }

        this.sizeEntity = sizeEntity;

        if (this.sizeEntity != null) {
            if (!this.sizeEntity.getSpecificationEntities().contains(this)) {
                this.sizeEntity.getSpecificationEntities().add(this);
            }
        }
    }

    public void removeSizeEntity() {
        if (this.sizeEntity != null) {
            this.sizeEntity.getSpecificationEntities().remove(this);
            this.sizeEntity = null;
        }
    }

    public List<OrderItemEntity> getOrderItemEntities() {
        return orderItemEntities;
    }

    public void setOrderItemEntities(List<OrderItemEntity> orderItemEntities) {
        this.orderItemEntities = orderItemEntities;
    }

}
