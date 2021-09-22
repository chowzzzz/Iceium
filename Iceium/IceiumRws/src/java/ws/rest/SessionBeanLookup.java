/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.AddressEntitySessionBeanLocal;
import ejb.session.stateless.BrandEntitySessionBeanLocal;
import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.ColorEntitySessionBeanLocal;
import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.CreditCardEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.ModelEntitySessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import ejb.session.stateless.SizeEntitySessionBeanLocal;
import ejb.session.stateless.SpecificationEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author MOK
 */
public class SessionBeanLookup
{

    private final String ejbModuleJndiPath;

    public SessionBeanLookup()
    {
        ejbModuleJndiPath = "java:global/Iceium/Iceium-ejb/";
    }

    public AddressEntitySessionBeanLocal lookupAddressEntitySessionBeanLocal()
    {
        try
        {
            javax.naming.Context c = new InitialContext();
            return (AddressEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "AddressEntitySessionBean!ejb.session.stateless.AddressEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CustomerEntitySessionBeanLocal lookupCustomerEntitySessionBeanLocal()
    {
        try
        {
            javax.naming.Context c = new InitialContext();
            return (CustomerEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "CustomerEntitySessionBean!ejb.session.stateless.CustomerEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CategoryEntitySessionBeanLocal lookupCategoryEntitySessionBeanLocal()
    {
        try
        {
            javax.naming.Context c = new InitialContext();
            return (CategoryEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "CategoryEntitySessionBean!ejb.session.stateless.CategoryEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public TagEntitySessionBeanLocal lookupTagEntitySessionBeanLocal()
    {
        try
        {
            javax.naming.Context c = new InitialContext();
            return (TagEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "TagEntitySessionBean!ejb.session.stateless.TagEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ProductEntitySessionBeanLocal lookupProductEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (ProductEntitySessionBeanLocal) c.lookup(ejbModuleJndiPath + "ProductEntitySessionBean!ejb.session.stateless.ProductEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public BrandEntitySessionBeanLocal lookupBrandEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (BrandEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/BrandEntitySessionBean!ejb.session.stateless.BrandEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ModelEntitySessionBeanLocal lookupModelEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (ModelEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/ModelEntitySessionBean!ejb.session.stateless.ModelEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public SizeEntitySessionBeanLocal lookupSizeEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (SizeEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/SizeEntitySessionBean!ejb.session.stateless.SizeEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ColorEntitySessionBeanLocal lookupColorEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (ColorEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/ColorEntitySessionBean!ejb.session.stateless.ColorEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CreditCardEntitySessionBeanLocal lookupCreditCardEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (CreditCardEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/CreditCardEntitySessionBean!ejb.session.stateless.CreditCardEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public OrderItemEntitySessionBeanLocal lookupOrderItemEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (OrderItemEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/OrderItemEntitySessionBean!ejb.session.stateless.OrderItemEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public OrderEntitySessionBeanLocal lookupOrderEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (OrderEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/OrderEntitySessionBean!ejb.session.stateless.OrderEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CouponEntitySessionBeanLocal lookupCouponEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (CouponEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/CouponEntitySessionBean!ejb.session.stateless.CouponEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public SaleEntitySessionBeanLocal lookupSaleEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (SaleEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/SaleEntitySessionBean!ejb.session.stateless.SaleEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public SpecificationEntitySessionBeanLocal lookupSpecificationEntitySessionBeanLocal()
    {
        try
        {
            Context c = new InitialContext();
            return (SpecificationEntitySessionBeanLocal) c.lookup("java:global/Iceium/Iceium-ejb/SpecificationEntitySessionBean!ejb.session.stateless.SpecificationEntitySessionBeanLocal");
        }
        catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
