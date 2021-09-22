package ejb.session.stateless;

import entity.OrderItemEntity;
import entity.ReviewEntity;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.OrderItemNotFoundException;
import util.exception.ReviewNotFoundException;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.validation.InputDataValidation;

/**
 *
 * @author Mok
 */
@Stateless
public class ReviewEntitySessionBean implements ReviewEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;
    @Resource
    private EJBContext eJBContext;

    @EJB
    private OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewReview(Long orderItemId, ReviewEntity newReviewEntity) throws CreateNewReviewException, InputDataValidationException
    {
        if (newReviewEntity != null)
        {
            try
            {
                OrderItemEntity orderItemEntity = orderItemEntitySessionBeanLocal.retrieveOrderItemByOrderItemId(orderItemId);
                newReviewEntity.setOrderItemEntity(orderItemEntity);
                orderItemEntity.setReviewEntity(newReviewEntity);
                
                Set<ConstraintViolation<ReviewEntity>> constraintViolations = validator.validate(newReviewEntity);

                if (constraintViolations.isEmpty())
                {
                    em.persist(newReviewEntity);
                    em.flush();

                    return newReviewEntity.getReviewId();
                }
                else
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            catch (OrderItemNotFoundException ex)
            {
                eJBContext.setRollbackOnly();
                throw new CreateNewReviewException(ex.getMessage());
            }
        }
        else
        {
            throw new CreateNewReviewException("Review information not provided");
        }
    }

    @Override
    public List<ReviewEntity> retrieveAllReviewsOfProduct(Long productId)
    {
        Query query = em.createQuery("SELECT r FROM ReviewEntity r WHERE r.orderItemEntity.specificationEntity.productEntity.productId = :inProductId");
        query.setParameter("inProductId", productId);

        return (List<ReviewEntity>) query.getResultList();
    }

    @Override
    public List<ReviewEntity> retrieveAllReviewsOfOrderItem(Long orderItemId)
    {
        Query query = em.createQuery("SELECT r FROM ReviewEntity r WHERE r.orderItemEntity.orderItemId = :inOrderItemId");
        query.setParameter("inOrderItemId", orderItemId);

        List<ReviewEntity> reviewEntities = query.getResultList();

        for (ReviewEntity reviewEntity : reviewEntities)
        {
            reviewEntity.getOrderItemEntity().getSpecificationEntity().getProductEntity();
        }

        return (List<ReviewEntity>) reviewEntities;
    }

    public ReviewEntity retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException
    {
        ReviewEntity reviewEntity = em.find(ReviewEntity.class, reviewId);

        if (reviewEntity != null)
        {
            reviewEntity.getOrderItemEntity().getSpecificationEntity().getProductEntity();
            return reviewEntity;
        }
        else
        {
            throw new ReviewNotFoundException("Review ID " + reviewId + " does not exist!");
        }
    }

//    @Override
//    public void updateReview(ReviewEntity reviewEntity) throws ReviewNotFoundException, InputDataValidationException
//    {
//        if (reviewEntity != null && reviewEntity.getReviewId() != null)
//        {
//            Set<ConstraintViolation<ReviewEntity>> constraintViolations = validator.validate(reviewEntity);
//
//            if (constraintViolations.isEmpty())
//            {
//                ReviewEntity reviewEntityToUpdate = retrieveReviewByReviewId(reviewEntity.getReviewId());
//
//                reviewEntityToUpdate.setRating(reviewEntity.getRating());
//                reviewEntityToUpdate.setReview(reviewEntity.getReview());
//            }
//            else
//            {
//                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
//            }
//        }
//        else
//        {
//            throw new ReviewNotFoundException("Review ID not provided for review to be updated");
//        }
//    }
//
//    public void deleteReview(Long reviewId) throws ReviewNotFoundException
//    {
//        ReviewEntity reviewEntityToRemove = retrieveReviewByReviewId(reviewId);
//        em.remove(reviewEntityToRemove);
//    }

}
