/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PromotionEntity;
import util.exception.PromotionNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gohle
 */
@Stateless
public class PromotionEntitySessionBean implements PromotionEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    public PromotionEntitySessionBean()
    {
    }

    @Override
    public List<PromotionEntity> retrieveAllPromotions()
    {
        Query query = em.createQuery("SELECT p FROM PromotionEntity p ORDER BY p.startDateTime ASC");
        List<PromotionEntity> promotionEntities = query.getResultList();

        return promotionEntities;
    }

    @Override
    public PromotionEntity retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException
    {
        PromotionEntity promotionEntity = em.find(PromotionEntity.class, promotionId);

        if (promotionEntity != null)
        {
            return promotionEntity;
        }
        else
        {
            throw new PromotionNotFoundException("Promotion ID " + promotionId + " does not exist!");
        }
    }

//    @Override
//    public Long redeemPromoCode(Long promotionId) throws PromotionNotFoundException, PromoCodeMaxedOutException
//    {
//        PromotionEntity promotionEntity = retrievePromotionByPromotionId(promotionId);
//
//        if ((promotionEntity.getMaxRedemptions() - promotionEntity.getCurrentRedemptions()) > 0)
//        {
//            promotionEntity.setCurrentRedemptions(promotionEntity.getCurrentRedemptions() + 1);
//        }
//        else
//        {
//            throw new PromoCodeMaxedOutException("Code has been maxed out");
//        }
//        return promotionEntity.getPromotionId();
//    }
//
//    @Override
//    public void updatePromotion(PromotionEntity promotionEntity) throws PromotionNotFoundException, InputDataValidationException
//    {
//        if (promotionEntity != null && promotionEntity.getPromotionId() != null)
//        {
//            Set<ConstraintViolation<PromotionEntity>> constraintViolations = validator.validate(promotionEntity);
//
//            if (constraintViolations.isEmpty())
//            {
//                PromotionEntity promotionEntityToUpdate = retrievePromotionDetailsById(promotionEntity.getPromotionId());
//
//                promotionEntityToUpdate.setDiscountRate(promotionEntity.getDiscountRate());
//                promotionEntityToUpdate.setStartDateTime(promotionEntity.getStartDateTime());
//                promotionEntityToUpdate.setEndDateTime(promotionEntity.getEndDateTime());
//                promotionEntityToUpdate.setDescription(promotionEntity.getDescription());
//                promotionEntityToUpdate.setMaxRedemptions(promotionEntity.getMaxRedemptions());
//            }
//            else
//            {
//                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
//            }
//        }
//        else
//        {
//            throw new PromotionNotFoundException("Promotion ID not provided for Promotion to be updated");
//        }
//    }
//
    
}
