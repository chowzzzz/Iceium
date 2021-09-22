/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ReviewEntity;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Theodoric
 */
@Local
public interface ReviewEntitySessionBeanLocal
{

    public Long createNewReview(Long orderItemId, ReviewEntity newReviewEntity) throws CreateNewReviewException, InputDataValidationException;

    public List<ReviewEntity> retrieveAllReviewsOfProduct(Long productId);

    public List<ReviewEntity> retrieveAllReviewsOfOrderItem(Long orderItemId);

//    public void updateReview(ReviewEntity reviewEntity) throws ReviewNotFoundException, InputDataValidationException;
    
}
