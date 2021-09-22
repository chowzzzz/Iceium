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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.SizeTypeEnum;

/**
 *
 * @author MOK
 */
@Entity
@Table(uniqueConstraints =
{
    @UniqueConstraint(columnNames =
    {
        "size", "sizeTypeEnum"
    })
})
public class SizeEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    @Column(nullable = false)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private Double size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private SizeTypeEnum sizeTypeEnum;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String code;

    @OneToMany(mappedBy = "sizeEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<SpecificationEntity> specificationEntities;

    public SizeEntity()
    {
        specificationEntities = new ArrayList<>();
    }

    public SizeEntity(Double size, SizeTypeEnum sizeTypeEnum)
    {
        this();

        this.size = size;
        this.sizeTypeEnum = sizeTypeEnum;
    }

    public SizeEntity(Double size, SizeTypeEnum sizeTypeEnum, String code)
    {
        this();

        this.size = size;
        this.sizeTypeEnum = sizeTypeEnum;
        this.code = code;
    }

    public Long getSizeId()
    {
        return sizeId;
    }

    public void setSizeId(Long sizeId)
    {
        this.sizeId = sizeId;
    }

    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    public SizeTypeEnum getSizeTypeEnum()
    {
        return sizeTypeEnum;
    }

    public void setSizeTypeEnum(SizeTypeEnum sizeTypeEnum)
    {
        this.sizeTypeEnum = sizeTypeEnum;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public List<SpecificationEntity> getSpecificationEntities()
    {
        return specificationEntities;
    }

    public void setSpecificationEntities(List<SpecificationEntity> specificationEntities)
    {
        this.specificationEntities = specificationEntities;
    }

}
