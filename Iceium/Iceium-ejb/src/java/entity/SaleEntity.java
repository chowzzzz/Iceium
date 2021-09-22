/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Theodoric
 */
@Entity
public class SaleEntity extends PromotionEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "saleEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<ProductEntity> productEntities;

    public SaleEntity()
    {
        super();
        this.productEntities = new ArrayList<>();
    }

    public SaleEntity(BigDecimal discountRate, LocalDateTime startDateTime, LocalDateTime endDateTime, String description)
    {
        super(discountRate, startDateTime, endDateTime, description);
        this.productEntities = new ArrayList<>();
    }

    public Long getSaleId()
    {
        return super.getPromotionId();
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
