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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author MOK
 */
@Entity
public class CategoryEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String description;

    @OneToMany(mappedBy = "parentCategoryEntity", fetch = FetchType.LAZY)
    private List<CategoryEntity> subCategoryEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity parentCategoryEntity;

    @OneToMany(mappedBy = "categoryEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<ProductEntity> productEntities;

    public CategoryEntity()
    {
        this.subCategoryEntities = new ArrayList<>();
        this.parentCategoryEntity = null;
        this.productEntities = new ArrayList<>();        
    }

    public CategoryEntity(String name, String description)
    {
        this();

        this.name = name;
        this.description = description;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
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

    public List<CategoryEntity> getSubCategoryEntities()
    {
        return subCategoryEntities;
    }

    public void setSubCategoryEntities(List<CategoryEntity> subCategoryEntities)
    {
        this.subCategoryEntities = subCategoryEntities;
    }

    public CategoryEntity getParentCategoryEntity()
    {
        return parentCategoryEntity;
    }

    public void setParentCategoryEntity(CategoryEntity parentCategoryEntity)
    {
        if (this.parentCategoryEntity != null)
        {
            this.parentCategoryEntity.getSubCategoryEntities().remove(this);
        }

        this.parentCategoryEntity = parentCategoryEntity;

        if (this.parentCategoryEntity != null)
        {
            if (!this.parentCategoryEntity.getSubCategoryEntities().contains(this))
            {
                this.parentCategoryEntity.getSubCategoryEntities().add(this);
            }
        }
    }

    public void removeParentCategoryEntity()
    {
        if (this.parentCategoryEntity != null)
        {
            this.parentCategoryEntity.getSubCategoryEntities().remove(this);
            this.parentCategoryEntity = null;
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
