/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AddressEntitySessionBeanLocal;
import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import ejb.session.stateless.ReviewEntitySessionBeanLocal;
import ejb.session.stateless.SpecificationEntitySessionBeanLocal;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.exception.OrderNotFoundException;
import util.formatter.ConsoleLogger;
import util.initialization.CreateOrders;
import util.initialization.CreateReviews;

/**
 *
 * @author Theodoric
 */
@Startup
@LocalBean
@Singleton
@DependsOn("OrderInitializationSessionBean5")
public class OrderInitializationSessionBean6
{
    @EJB(name = "CouponEntitySessionBeanLocal")
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;    

    @EJB(name = "AddressEntitySessionBeanLocal")
    private AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;

    @EJB(name = "orderItemEntitySessionBeanLocal")
    private OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    @EJB(name = "ReviewEntitySessionBeanLocal")
    private ReviewEntitySessionBeanLocal reviewEntitySessionBeanLocal;

    @EJB(name = "SpecificationEntitySessionBeanLocal")
    private SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;

    @EJB(name = "orderEntitySessionBeanLocal")
    private OrderEntitySessionBeanLocal orderEntitySessionBeanLocal;

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    private final int listStart = 500;
    private final int listEnd = 600;
    private final int orders = 2;
    
    public OrderInitializationSessionBean6()
    {
    }

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            ConsoleLogger.log("Started");
            orderEntitySessionBeanLocal.retrieveOrderByOrderId(CreateOrders.limit);
            ConsoleLogger.log("No Generation");
        }
        catch (OrderNotFoundException ex)
        {
            initializeData();
            ConsoleLogger.log("Completed");
        }
    }

    /**
     * Order Creation Method Start
     */
    private void initializeData()
    {
        try
        {
            createOrders();
            createReviews();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void createOrders() throws Exception
    {
        new CreateOrders(couponEntitySessionBeanLocal, specificationEntitySessionBeanLocal, orderEntitySessionBeanLocal, customerEntitySessionBeanLocal, addressEntitySessionBeanLocal, listStart, listEnd, orders).createOrders();
    }

    private void createReviews() throws Exception
    {
        new CreateReviews(orderItemEntitySessionBeanLocal, reviewEntitySessionBeanLocal).createReviews();
    }

    /**
     * Order Creation Method End
     */
}
