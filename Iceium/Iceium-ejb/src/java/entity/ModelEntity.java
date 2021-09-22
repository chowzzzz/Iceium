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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
@Table(uniqueConstraints =
{
    @UniqueConstraint(columnNames =
    {
        "name", "brandEntity_brandId"
    })
})
public class ModelEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String code;

    @ManyToOne
    @JoinColumn(nullable = false)
    BrandEntity brandEntity;

    @OneToMany(mappedBy = "modelEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<ProductEntity> productEntities;

    public ModelEntity()
    {
        productEntities = new ArrayList<>();
    }

    public ModelEntity(String name)
    {
        this();

        this.name = name;
    }

    public ModelEntity(String name, String code)
    {
        this();

        this.name = name;
        this.code = code;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId(Long modelId)
    {
        this.modelId = modelId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public BrandEntity getBrandEntity()
    {
        return brandEntity;
    }

    public void setBrandEntity(BrandEntity brandEntity)
    {
        if (brandEntity != null)
        {
            brandEntity.getModelEntities().remove(this);
        }

        this.brandEntity = brandEntity;

        if (brandEntity != null)
        {
            if (!brandEntity.getModelEntities().contains(this))
            {
                brandEntity.getModelEntities().add(this);
            }
        }
    }
    
    public void removeBrandEntity()
    {
        if (this.brandEntity != null)
        {
            this.brandEntity.getModelEntities().remove(this);
            this.brandEntity = null;
        }
    }

    public List<ProductEntity> getProductEntities()
    {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities)
    {
        this.productEntities = productEntities;
    }

}
