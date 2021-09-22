/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.CouponEntity;
import entity.SaleEntity;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author gohle
 */
@Named(value = "viewPromotionManagedBean")
@ViewScoped
public class ViewPromotionManagedBean implements Serializable
{

    private SaleEntity saleEntityToView;
    private CouponEntity couponEntityToView;

    public ViewPromotionManagedBean()
    {
        saleEntityToView = new SaleEntity();
        couponEntityToView = new CouponEntity();
    }

    public SaleEntity getSaleEntityToView()
    {
        return saleEntityToView;
    }

    public void setSaleEntityToView(SaleEntity saleEntityToView)
    {
        this.saleEntityToView = saleEntityToView;
    }

    public CouponEntity getCouponEntityToView()
    {
        return couponEntityToView;
    }

    public void setCouponEntityToView(CouponEntity couponEntityToView)
    {
        this.couponEntityToView = couponEntityToView;
    }

}
