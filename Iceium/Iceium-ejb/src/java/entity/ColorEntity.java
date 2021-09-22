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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
public class ColorEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String code;

    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(max = 7)
    private String hex;

    @OneToMany(mappedBy = "colorEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<SpecificationEntity> specificationEntities;

    public ColorEntity()
    {
        specificationEntities = new ArrayList<>();
    }

    public ColorEntity(String name, String hex)
    {
        this.name = name;
        this.hex = hex;
    }

    
    public ColorEntity(String name, String code, String hex)
    {
        this.name = name;
        this.code = code;
        this.hex = hex;
    }

    public Long getColorId()
    {
        return colorId;
    }

    public void setColorId(Long colorId)
    {
        this.colorId = colorId;
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

    public String getHex()
    {
        return hex;
    }

    public void setHex(String hex)
    {
        this.hex = hex;
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
