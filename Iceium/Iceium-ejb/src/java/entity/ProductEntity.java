/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author hilary
 */
@Entity
public class ProductEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 24)
    @NotNull
    @Size(max = 24)
    private String skuCode;

    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String name;

    @Column(nullable = false, length = 4096)
    @NotNull
    @Size(max = 4096)
    private String description;

    @Column(nullable = false, precision = 11, scale = 2)
    @DecimalMin("0.00")
    @NotNull
    @Digits(integer = 9, fraction = 2)
    private BigDecimal unitPrice;

    @Column(nullable = true, length = 256)
    @Size(max = 256)
    private String imageLink;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ModelEntity modelEntity;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CategoryEntity categoryEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<TagEntity> tagEntities;

    @OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
    private List<SpecificationEntity> specificationEntities;

    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private SaleEntity saleEntity;

    public ProductEntity()
    {
        tagEntities = new ArrayList<>();
        specificationEntities = new ArrayList<>();
    }

    public ProductEntity(String name, String description, BigDecimal unitPrice)
    {
        this();

        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public ProductEntity(String name, String description, BigDecimal unitPrice, String imageLink)
    {
        this();

        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.imageLink = imageLink;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getSkuCode()
    {
        return skuCode;
    }

    public void setSkuCode(String skuCode)
    {
        this.skuCode = skuCode;
    }

    public void generateSkuCode()
    {
        if (this.modelEntity.getBrandEntity() != null && this.modelEntity != null && this.categoryEntity != null)
        {
            this.skuCode = "ICEIUM" + "-" + this.modelEntity.getBrandEntity().getCode() + "-" + this.modelEntity.getCode() + "-" + this.categoryEntity.getDescription();
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public String getImageLink()
    {
        return imageLink;
    }

    public void setImageLink(String imageLink)
    {
        this.imageLink = imageLink;
    }

    public ModelEntity getModelEntity()
    {
        return modelEntity;
    }

    public void setModelEntity(ModelEntity modelEntity)
    {
        if (this.modelEntity != null)
        {
            this.modelEntity.getProductEntities().remove(this);
        }

        this.modelEntity = modelEntity;

        if (this.modelEntity != null)
        {
            if (!this.modelEntity.getProductEntities().contains(this))
            {
                this.modelEntity.getProductEntities().add(this);
            }
        }
    }

    public void removeModelEntity()
    {
        if (this.modelEntity != null)
        {
            this.modelEntity.getProductEntities().remove(this);
            this.modelEntity = null;
        }
    }

    public CategoryEntity getCategoryEntity()
    {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity)
    {
        if (this.categoryEntity != null)
        {
            this.categoryEntity.getProductEntities().remove(this);
        }

        this.categoryEntity = categoryEntity;

        if (this.categoryEntity != null)
        {
            if (!this.categoryEntity.getProductEntities().contains(this))
            {
                this.categoryEntity.getProductEntities().add(this);
            }
        }
    }

    public void removeCategoryEntity()
    {
        if (this.categoryEntity != null)
        {
            this.categoryEntity.getProductEntities().remove(this);
            this.categoryEntity = null;
        }
    }

    public List<TagEntity> getTagEntities()
    {
        return tagEntities;
    }

    public void setTagEntities(List<TagEntity> tagEntities)
    {
        this.tagEntities = tagEntities;
    }

    public void addTagEntity(TagEntity tagEntity)
    {
        if (tagEntity != null)
        {
            if (!this.tagEntities.contains(tagEntity))
            {
                this.tagEntities.add(tagEntity);

                if (!tagEntity.getProductEntities().contains(this))
                {
                    tagEntity.getProductEntities().add(this);
                }
            }
        }
    }

    public void removeTagEntity(TagEntity tagEntity)
    {
        if (tagEntity != null)
        {
            if (this.tagEntities.contains(tagEntity))
            {
                this.tagEntities.remove(tagEntity);

                if (tagEntity.getProductEntities().contains(this))
                {
                    tagEntity.getProductEntities().remove(this);
                }
            }
        }
    }

    public List<SpecificationEntity> getSpecificationEntities()
    {
        return specificationEntities;
    }

    public void setSpecificationEntities(List<SpecificationEntity> specificationEntities)
    {
        this.specificationEntities = specificationEntities;
    }

    public SaleEntity getSaleEntity()
    {
        return saleEntity;
    }

    public void setSaleEntity(SaleEntity saleEntity)
    {
        if (this.saleEntity != null)
        {
            this.saleEntity.getProductEntities().remove(this);
        }

        this.saleEntity = saleEntity;

        if (this.saleEntity != null)
        {
            if (!this.saleEntity.getProductEntities().contains(this))
            {
                this.saleEntity.getProductEntities().add(this);
            }
        }
    }

    public void removeSaleEntity()
    {
        if (this.saleEntity != null)
        {
            this.saleEntity.getProductEntities().remove(this);
            this.saleEntity = null;
        }
    }

    public Integer getTotalQuantityOnHand()
    {
        Integer total = 0;

        for (SpecificationEntity specificationEntity : specificationEntities)
        {
            total += specificationEntity.getQuantityOnHand();
        }

        return total;
    }

    public BigDecimal getDiscountedPrice()
    {
        if (this.saleEntity != null)
        {
            return this.unitPrice.subtract(this.unitPrice.multiply(this.saleEntity.getDiscountRate()));
        }
        else
        {
            return null;
        }
    }
    
}
