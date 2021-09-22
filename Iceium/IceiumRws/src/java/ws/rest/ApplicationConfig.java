/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import java.util.Set;

/**
 *
 * @author MOK
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.rest.AddressResource.class);
        resources.add(ws.rest.BrandResource.class);
        resources.add(ws.rest.CategoryResource.class);
        resources.add(ws.rest.ColorResource.class);
        resources.add(ws.rest.CorsFilter.class);
        resources.add(ws.rest.CouponResource.class);
        resources.add(ws.rest.CreditCardResource.class);
        resources.add(ws.rest.CustomerResource.class);
        resources.add(ws.rest.ModelResource.class);
        resources.add(ws.rest.OrderItemResource.class);
        resources.add(ws.rest.OrderResource.class);
        resources.add(ws.rest.ProductResource.class);
        resources.add(ws.rest.SaleResource.class);
        resources.add(ws.rest.SizeResource.class);
        resources.add(ws.rest.SpecificationResource.class);
        resources.add(ws.rest.TagResource.class);
    }
    
}
