/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.initialization;

import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import entity.OrderItemEntity;
import entity.ReviewEntity;
import java.util.List;
import util.enumeration.OrderItemStatusEnum;
import util.generator.RandomDateGenerator;
import util.generator.RandomNumberGenerator;
import util.generator.RandomStringGenerator;

/**
 *
 * @author Theodoric
 */
public class CreateReviews
{

    private OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    private ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal;
    
    public CreateReviews(OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal, ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal)
    {
        this.orderItemEntitySessionBeanLocal = orderItemEntitySessionBeanLocal;
        this.reviewEntitySessionBeanLocal = reviewEntitySessionBeanLocal;
    }
    
    public void createReviews() throws Exception
    {
        List<OrderItemEntity> orderItemEntities = orderItemEntitySessionBeanLocal.retrieveAllOrderItems();

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator(50);
        RandomNumberGenerator randomNumberGenerator1 = new RandomNumberGenerator(3.5, 5.0);
        RandomNumberGenerator randomNumberGenerator2 = new RandomNumberGenerator(1, 100);
        RandomDateGenerator randomDateGenerator = new RandomDateGenerator(1, 1, 2020, 18, 4, 2021);
        
        ReviewEntity reviewEntity;

        for (OrderItemEntity orderItemEntity : orderItemEntities)
        {
            int orderItemStatusRandom = randomNumberGenerator2.getRandomInteger();

            if (1 <= orderItemStatusRandom && orderItemStatusRandom <= 75)
            {
                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.DELIVERED);
            }
            else if (75 < orderItemStatusRandom && orderItemStatusRandom <= 85)
            {
                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.CANCELLED);
            }
            else if (85 < orderItemStatusRandom && orderItemStatusRandom <= 95)
            {
                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.REFUND_COMPLETED);
            }
            else if (95 < orderItemStatusRandom && orderItemStatusRandom <= 100)
            {
                orderItemEntity.setOrderItemStatusEnum(OrderItemStatusEnum.EXCHANGE_COMPLETED);
            }

            reviewEntity = new ReviewEntity(Math.round(randomNumberGenerator1.getRandomDouble(1) * 2) / 2.0, "What an amazing product!");
            reviewEntity.setDateOfReview(randomDateGenerator.getDate());
            
            reviewEntitySessionBeanLocal.createNewReview(orderItemEntity.getOrderItemId(), reviewEntity);
        }
    }
}
