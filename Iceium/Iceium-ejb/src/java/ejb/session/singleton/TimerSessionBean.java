/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmailSessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import util.exception.OrderNotFoundException;
import util.formatter.ConsoleLogger;

/**
 *
 * @author Theodoric
 */
@Singleton
@DependsOn("OrderInitializationSessionBean6")
public class TimerSessionBean
{
    @EJB(name = "SaleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "OrderItemEntitySessionBeanLocal")
    private OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss");

    @Schedule(dayOfMonth = "1", info = "productsReorderQuantityCheckTimer")
//    @Schedule(hour = "*", minute = "*", second = "*/5", info = "productsReorderQuantityCheckTimer")
    public void productsReorderQuantityCheckTimer()
    {
        LocalDateTime now = LocalDateTime.now();
        ConsoleLogger.log("Products Reorder Quantity at: " + dtf.format(now));

        long productsReordered = productEntitySessionBeanLocal.reorderProducts();

        ConsoleLogger.log(productsReordered + " Products Ordered");
    }

    @Schedule(hour = "*", minute = "*", second = "*/15", info = "orderCheckTimer")
    public void orderCheckTimer()
    {
        LocalDateTime now = LocalDateTime.now();
        ConsoleLogger.log("Order Check at: " + dtf.format(now));

        long ordersClosed = orderItemEntitySessionBeanLocal.checkAllOrderItemsNotClosed(now);

        ConsoleLogger.log("Closed " + ordersClosed + " Orders");
    }
    
    @Schedule(hour = "*", minute = "*", second = "*/15", info = "saleCheckTimer")
    public void saleCheckTimer()
    {
        LocalDateTime now = LocalDateTime.now();
        ConsoleLogger.log("Sale Check at: " + dtf.format(now));

        long salesClosed = saleEntitySessionBeanLocal.checkAllSalesNotClosed(now);

        ConsoleLogger.log("Closed " + salesClosed + " Sales");
    }

}
