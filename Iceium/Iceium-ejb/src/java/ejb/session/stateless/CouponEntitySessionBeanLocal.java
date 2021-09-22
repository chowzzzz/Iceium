/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CouponEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.CouponCodeExistException;
import util.exception.CouponNotFoundException;
import util.exception.CreateNewCouponException;
import util.exception.DeleteCouponException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCouponException;

/**
 *
 * @author Theodoric
 */
@Local
public interface CouponEntitySessionBeanLocal
{

    public CouponEntity createNewCoupon(CouponEntity newCouponEntity) throws CreateNewCouponException, CouponCodeExistException, InputDataValidationException, UnknownPersistenceException;

    public List<CouponEntity> retrieveAllCoupons();

    public CouponEntity retrieveCouponByCouponId(Long couponId) throws CouponNotFoundException;

    public CouponEntity retrieveCouponByCouponCode(String couponCode) throws CouponNotFoundException;
    
    public void updateCoupon(CouponEntity couponEntity) throws UpdateCouponException, InputDataValidationException;

    public void deleteCoupon(Long couponId) throws CouponNotFoundException, DeleteCouponException;
    
    public boolean checkCouponExistByCouponCode(String couponCode);
}
