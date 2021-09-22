/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import entity.CouponEntity;
import entity.SaleEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import util.exception.CouponCodeExistException;
import util.exception.CouponNotFoundException;
import util.exception.CreateNewCouponException;
import util.exception.CreateNewSaleException;
import util.exception.DeleteCouponException;
import util.exception.DeleteSaleException;
import util.exception.InputDataValidationException;
import util.exception.SaleExistException;
import util.exception.SaleNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCouponException;
import util.exception.UpdateSaleException;

/**
 *
 * @author gohle
 */
@Named(value = "promotionManagementManagedBean")
@ViewScoped
public class PromotionManagementManagedBean implements Serializable
{

    @EJB(name = "saleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "couponEntitySessionBeanLocal")
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;

    @Inject
    private ViewPromotionManagedBean viewPromotionManagedBean;

    private List<SaleEntity> saleEntities;
    private List<SaleEntity> filteredSaleEntities;
    private SaleEntity selectedSaleEntityToUpdate;

    private List<CouponEntity> couponEntities;
    private List<CouponEntity> filteredCouponEntities;
    private CouponEntity selectedCouponEntityToUpdate;

    private SaleEntity newSaleEntity;
    private CouponEntity newCouponEntity;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

    public PromotionManagementManagedBean()
    {
        saleEntities = new ArrayList<>();
        couponEntities = new ArrayList<>();
        initializeCreate();
    }

    private void initializeCreate()
    {
        newSaleEntity = new SaleEntity();
        newCouponEntity = new CouponEntity();
    }

    @PostConstruct
    public void postConstruct()
    {
        saleEntities = saleEntitySessionBeanLocal.retrieveAllSales();
        couponEntities = couponEntitySessionBeanLocal.retrieveAllCoupons();
    }

    public void createNewSale(ActionEvent event)
    {
        try
        {
            newSaleEntity.setDiscountRate(newSaleEntity.getDiscountRate().divide(BigDecimal.valueOf(100)));

            newSaleEntity = saleEntitySessionBeanLocal.createNewSale(newSaleEntity);

            saleEntities.add(newSaleEntity);

            if (filteredSaleEntities != null)
            {
                filteredSaleEntities.remove(newSaleEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New sale created successfully Sale ID: " + newSaleEntity.getSaleId() + ", (Sale Description: " + newSaleEntity.getDescription() + ")", null));

            initializeCreate();
        }
        catch (CreateNewSaleException | SaleExistException | InputDataValidationException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new sale: " + ex.getMessage(), null));
        }
    }

    public void createNewCoupon(ActionEvent event)
    {
        try
        {
            newCouponEntity.setDiscountRate(newCouponEntity.getDiscountRate().divide(BigDecimal.valueOf(100)));

            newCouponEntity = couponEntitySessionBeanLocal.createNewCoupon(newCouponEntity);

            couponEntities.add(newCouponEntity);

            if (filteredCouponEntities != null)
            {
                filteredCouponEntities.remove(newCouponEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New coupon created successfully Coupon ID: " + newCouponEntity.getCouponId() + ", (Coupon Code: " + newCouponEntity.getCouponCode() + ")", null));

            initializeCreate();
        }
        catch (CreateNewCouponException | CouponCodeExistException | InputDataValidationException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new coupon: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateSale(ActionEvent event)
    {
        selectedSaleEntityToUpdate = (SaleEntity) event.getComponent().getAttributes().get("saleEntityToUpdate");
    }

    public void updateSale(ActionEvent event)
    {
        try
        {
            saleEntitySessionBeanLocal.updateSale(selectedSaleEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sale updated successfully", null));
        }
        catch (UpdateSaleException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating sale: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateCoupon(ActionEvent event)
    {
        selectedCouponEntityToUpdate = (CouponEntity) event.getComponent().getAttributes().get("couponEntityToUpdate");
    }

    public void updateCoupon(ActionEvent event)
    {
        try
        {
            couponEntitySessionBeanLocal.updateCoupon(selectedCouponEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Coupon updated successfully", null));
        }
        catch (UpdateCouponException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating coupon: " + ex.getMessage(), null));
        }
    }

    public void deleteSale(ActionEvent event)
    {
        try
        {
            SaleEntity saleEntityToDelete = (SaleEntity) event.getComponent().getAttributes().get("saleEntityToDelete");
            saleEntitySessionBeanLocal.deleteSale(saleEntityToDelete.getSaleId());

            saleEntities.remove(saleEntityToDelete);

            if (filteredSaleEntities != null)
            {
                filteredSaleEntities.remove(saleEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sale ID: " + saleEntityToDelete.getSaleId() + ", Description: " + saleEntityToDelete.getDescription() + " deleted successfully", null));
        }
        catch (SaleNotFoundException | DeleteSaleException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting sale: " + ex.getMessage(), null));
        }
    }

    public void deleteCoupon(ActionEvent event)
    {
        try
        {
            CouponEntity couponEntityToDelete = (CouponEntity) event.getComponent().getAttributes().get("couponEntityToDelete");
            couponEntitySessionBeanLocal.deleteCoupon(couponEntityToDelete.getCouponId());

            couponEntities.remove(couponEntityToDelete);

            if (filteredCouponEntities != null)
            {
                filteredCouponEntities.remove(couponEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Coupon ID: " + couponEntityToDelete.getCouponId() + ", Code: " + couponEntityToDelete.getCouponCode() + ", Description: " + couponEntityToDelete.getDescription() + " deleted successfully", null));
        }
        catch (CouponNotFoundException | DeleteCouponException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting coupon: " + ex.getMessage(), null));
        }
    }

    public LocalDate getMinDate()
    {
        return LocalDateTime.now().toLocalDate();
    }

    public String getMinDateString()
    {
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public ViewPromotionManagedBean getViewPromotionManagedBean()
    {
        return viewPromotionManagedBean;
    }

    public void setViewPromotionManagedBean(ViewPromotionManagedBean viewPromotionManagedBean)
    {
        this.viewPromotionManagedBean = viewPromotionManagedBean;
    }

    public List<SaleEntity> getSaleEntities()
    {
        return saleEntities;
    }

    public void setSaleEntities(List<SaleEntity> saleEntities)
    {
        this.saleEntities = saleEntities;
    }

    public List<SaleEntity> getFilteredSaleEntities()
    {
        return filteredSaleEntities;
    }

    public void setFilteredSaleEntities(List<SaleEntity> filteredSaleEntities)
    {
        this.filteredSaleEntities = filteredSaleEntities;
    }

    public SaleEntity getSelectedSaleEntityToUpdate()
    {
        return selectedSaleEntityToUpdate;
    }

    public void setSelectedSaleEntityToUpdate(SaleEntity selectedSaleEntityToUpdate)
    {
        this.selectedSaleEntityToUpdate = selectedSaleEntityToUpdate;
    }

    public List<CouponEntity> getCouponEntities()
    {
        return couponEntities;
    }

    public void setCouponEntities(List<CouponEntity> couponEntities)
    {
        this.couponEntities = couponEntities;
    }

    public List<CouponEntity> getFilteredCouponEntities()
    {
        return filteredCouponEntities;
    }

    public void setFilteredCouponEntities(List<CouponEntity> filteredCouponEntities)
    {
        this.filteredCouponEntities = filteredCouponEntities;
    }

    public CouponEntity getSelectedCouponEntityToUpdate()
    {
        return selectedCouponEntityToUpdate;
    }

    public void setSelectedCouponEntityToUpdate(CouponEntity selectedCouponEntityToUpdate)
    {
        this.selectedCouponEntityToUpdate = selectedCouponEntityToUpdate;
    }

    public SaleEntity getNewSaleEntity()
    {
        return newSaleEntity;
    }

    public void setNewSaleEntity(SaleEntity newSaleEntity)
    {
        this.newSaleEntity = newSaleEntity;
    }

    public CouponEntity getNewCouponEntity()
    {
        return newCouponEntity;
    }

    public void setNewCouponEntity(CouponEntity newCouponEntity)
    {
        this.newCouponEntity = newCouponEntity;
    }

}
