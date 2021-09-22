/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
public class BrandEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String code;

    @OneToMany(mappedBy = "brandEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    List<ModelEntity> modelEntities;

    public BrandEntity()
    {
        modelEntities = new ArrayList<>();
    }

    public BrandEntity(String name)
    {
        this();

        this.name = name;
    }

    public BrandEntity(String name, String code)
    {
        this();

        this.name = name;
        this.code = code;
    }

    public Long getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Long brandId)
    {
        this.brandId = brandId;
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

    public List<ModelEntity> getModelEntities()
    {
        return modelEntities;
    }

    public void setModelEntities(List<ModelEntity> modelEntities)
    {
        this.modelEntities = modelEntities;
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
        final BrandEntity other = (BrandEntity) obj;
        if (!Objects.equals(this.brandId, other.brandId))
        {
            return false;
        }
        return true;
    }

}
