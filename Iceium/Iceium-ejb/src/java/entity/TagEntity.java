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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author MOK
 */
@Entity
public class TagEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String description;

    @ManyToMany
    private List<ProductEntity> productEntities;

    public TagEntity()
    {
        this.productEntities = new ArrayList<>();
    }

    public TagEntity(String name, String description)
    {
        this();

        this.name = name;
        this.description = description;
    }

    public Long getTagId()
    {
        return tagId;
    }

    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
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

    public List<ProductEntity> getProductEntities()
    {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities)
    {
        this.productEntities = productEntities;
    }

    public void addProductEntity(ProductEntity productEntity)
    {
        if (productEntity != null)
        {
            if(!this.productEntities.contains(productEntity))
            {
                this.productEntities.add(productEntity);
                
                if(!productEntity.getTagEntities().contains(this))
                {                    
                    productEntity.getTagEntities().add(this);
                }
            }
        }
    }

    public void removeProductEntity(ProductEntity productEntity)
    {
        if (productEntity != null)
        {
            if(!this.productEntities.contains(productEntity))
            {
                this.productEntities.remove(productEntity);
                
                if(!productEntity.getTagEntities().contains(this))
                {                    
                    productEntity.getTagEntities().remove(this);
                }
            }
        }
    }
}
