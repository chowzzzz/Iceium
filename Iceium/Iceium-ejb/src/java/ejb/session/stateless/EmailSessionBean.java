/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderEntity;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import util.email.EmailManager;

/**
 *
 * @author Theodoric
 */
@Stateless
public class EmailSessionBean implements EmailSessionBeanLocal
{
    private final String FROM_EMAIL_ADDRESS = "iceium <no.reply.iceium@gmail.com>";
    private final String EMAIL_USERNAME = "no.reply.iceium@gmail.com";
    private final String EMAIL_PASSWORD = "iceium123456";
    
    @Override
    public Boolean emailCheckoutNotificationSync(OrderEntity orderEntity, String toEmailAddress)
    {
        EmailManager emailManager = new EmailManager(EMAIL_USERNAME, EMAIL_PASSWORD);
        Boolean result = emailManager.emailCheckoutNotification(orderEntity, FROM_EMAIL_ADDRESS, toEmailAddress);
        
        return result;
    } 
    
    
    
    @Asynchronous
    @Override
    public Future<Boolean> emailCheckoutNotificationAsync(OrderEntity orderEntity, String toEmailAddress) throws InterruptedException
    {        
        EmailManager emailManager = new EmailManager(EMAIL_USERNAME, EMAIL_PASSWORD);
        Boolean result = emailManager.emailCheckoutNotification(orderEntity, FROM_EMAIL_ADDRESS, toEmailAddress);
        
        return new AsyncResult<>(result);
    }
    
}
