/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.SaleEntitySessionBeanLocal;
import entity.SaleEntity;
import entity.OrderEntity;
import entity.ProductEntity;
import entity.TagEntity;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Sale")
public class SaleResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    public SaleResource() {
        sessionBeanLookup = new SessionBeanLookup();
        saleEntitySessionBeanLocal = sessionBeanLookup.lookupSaleEntitySessionBeanLocal();
    }

    @Path("retrieveAllSales")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSales() {
        try {
            List<SaleEntity> saleEntities = saleEntitySessionBeanLocal.retrieveAllSales();

            for (SaleEntity saleEntity : saleEntities) {
                saleEntity.getProductEntities().clear(); 
                for (ProductEntity productEntity : saleEntity.getProductEntities()) {
                    productEntity.setSaleEntity(null);

                    productEntity.getTagEntities().clear();
                    productEntity.getSpecificationEntities().clear();

                    if (productEntity.getCategoryEntity().getParentCategoryEntity() != null) {
                        productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                    }

                    productEntity.getCategoryEntity().getProductEntities().clear();

                    productEntity.getModelEntity().getProductEntities().clear();
                    productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                }
            }
            GenericEntity<List<SaleEntity>> genericEntity = new GenericEntity<List<SaleEntity>>(saleEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
