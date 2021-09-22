/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CouponEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CouponCodeExistException;
import util.exception.CouponNotFoundException;
import util.exception.CreateNewCouponException;
import util.exception.DeleteCouponException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCouponException;
import util.generator.RandomStringGenerator;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class CouponEntitySessionBean implements CouponEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CouponEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public CouponEntity createNewCoupon(CouponEntity newCouponEntity) throws CreateNewCouponException, CouponCodeExistException, InputDataValidationException, UnknownPersistenceException
    {
        try
        {
            if (newCouponEntity.getStartDateTime().isBefore(newCouponEntity.getEndDateTime()))
            {
                RandomStringGenerator randomStringGenerator = new RandomStringGenerator(12);
                List<String> couponCodes = retrieveAllCouponCodes();

                String couponCode;

                do
                {
                    couponCode = randomStringGenerator.getString().toUpperCase();
                }
                while (couponCodes.contains(couponCode));

                newCouponEntity.setCouponCode(couponCode);

                Set<ConstraintViolation<CouponEntity>> constraintViolations = validator.validate(newCouponEntity);

                if (constraintViolations.isEmpty())
                {
                    em.persist(newCouponEntity);
                    em.flush();

                    return newCouponEntity;
                }
                else
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            else
            {
                throw new CreateNewCouponException("Start date has to be before end date");
            }
        }
        catch (PersistenceException ex)
        {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CouponCodeExistException();
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<CouponEntity> retrieveAllCoupons()
    {
        Query query = em.createQuery("SELECT c FROM CouponEntity c ORDER BY c.startDateTime ASC");
        List<CouponEntity> promotionEntities = query.getResultList();

        for (CouponEntity couponEntity : promotionEntities)
        {
            couponEntity.getOrderEntities().size();
        }

        return promotionEntities;
    }

    @Override
    public CouponEntity retrieveCouponByCouponId(Long couponId) throws CouponNotFoundException
    {
        CouponEntity couponEntity = em.find(CouponEntity.class, couponId);

        if (couponEntity != null)
        {
            couponEntity.getOrderEntities().size();
            return couponEntity;
        }
        else
        {
            throw new CouponNotFoundException("Coupon ID " + couponId + " does not exist!");
        }
    }

    @Override
    public CouponEntity retrieveCouponByCouponCode(String couponCode) throws CouponNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CouponEntity c WHERE c.couponCode = :inCouponCode");
        query.setParameter("inCouponCode", couponCode);

        try
        {
            CouponEntity couponEntity = (CouponEntity) query.getSingleResult();
            couponEntity.getOrderEntities().size();
            
            return couponEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CouponNotFoundException("Coupon Code " + couponCode + " does not exist!");
        }
    }
    
    @Override
    public void updateCoupon(CouponEntity couponEntity) throws UpdateCouponException, InputDataValidationException
    {
        if (couponEntity != null)
        {
            try
            {
                CouponEntity couponEntityToUpdate = retrieveCouponByCouponId(couponEntity.getCouponId());

                couponEntityToUpdate.setDescription(couponEntity.getDescription());
                couponEntityToUpdate.setMaximumRedemptions(couponEntity.getMaximumRedemptions());
                couponEntityToUpdate.setMinimumSpend(couponEntity.getMinimumSpend());

                Set<ConstraintViolation<CouponEntity>> constraintViolations = validator.validate(couponEntityToUpdate);

                if (!constraintViolations.isEmpty())
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            catch (CouponNotFoundException ex)
            {
                throw new UpdateCouponException(ex.getMessage());
            }
        }
        else
        {
            throw new UpdateCouponException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteCoupon(Long couponId) throws CouponNotFoundException, DeleteCouponException
    {
        CouponEntity couponEntityToRemove = retrieveCouponByCouponId(couponId);

        if (couponEntityToRemove.getOrderEntities() != null)
        {
            if (couponEntityToRemove.getOrderEntities().isEmpty())
            {
                em.remove(couponEntityToRemove);
            }
            else
            {
                throw new DeleteCouponException("Coupon ID " + couponId + " is associated with existing orders(s) and cannot be deleted!");
            }
        }
    }
    
    @Override
    public boolean checkCouponExistByCouponCode(String couponCode)
    {
        Query query = em.createQuery("SELECT COUNT(c) FROM CouponEntity c WHERE c.couponCode = :inCouponCode");
        query.setParameter("inCouponCode", couponCode);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private List<String> retrieveAllCouponCodes()
    {
        Query query = em.createQuery("SELECT c.couponCode FROM CouponEntity c");

        return query.getResultList();
    }

}
