/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OrderEntity;
import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author Theodoric
 */
@Local
public interface EmailSessionBeanLocal
{
    public Boolean emailCheckoutNotificationSync(OrderEntity orderEntity, String toEmailAddress);
    
    public Future<Boolean> emailCheckoutNotificationAsync(OrderEntity orderEntity, String toEmailAddress) throws InterruptedException;
}
