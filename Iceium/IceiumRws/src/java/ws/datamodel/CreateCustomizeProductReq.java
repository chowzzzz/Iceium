/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.ColorEntity;
import entity.ProductEntity;
import entity.SizeEntity;
import entity.SpecificationEntity;

/**
 *
 * @author MOK
 */
public class CreateCustomizeProductReq {
    private ProductEntity newProductEntity;
    private Long modelId;
    private SpecificationEntity specificationEntity;
    private ColorEntity colorEntity;
    private SizeEntity sizeEntity;

    public CreateCustomizeProductReq() {
    }

    public CreateCustomizeProductReq(ProductEntity newProductEntity, Long modelId, SpecificationEntity specificationEntity, ColorEntity colorEntity, SizeEntity sizeEntity) {
        this.newProductEntity = newProductEntity;
        this.modelId = modelId;
        this.specificationEntity = specificationEntity;
        this.colorEntity = colorEntity;
        this.sizeEntity = sizeEntity;
    }

    public ProductEntity getNewProductEntity() {
        return newProductEntity;
    }

    public void setNewProductEntity(ProductEntity newProductEntity) {
        this.newProductEntity = newProductEntity;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public SpecificationEntity getSpecificationEntity() {
        return specificationEntity;
    }

    public void setSpecificationEntity(SpecificationEntity specificationEntity) {
        this.specificationEntity = specificationEntity;
    }

    public ColorEntity getColorEntity() {
        return colorEntity;
    }

    public void setColorEntity(ColorEntity colorEntity) {
        this.colorEntity = colorEntity;
    }

    public SizeEntity getSizeEntity() {
        return sizeEntity;
    }

    public void setSizeEntity(SizeEntity sizeEntity) {
        this.sizeEntity = sizeEntity;
    }
    
    
}
