/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.BrandEntitySessionBeanLocal;
import entity.BrandEntity;
import entity.BrandEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.BrandNotFoundException;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Brand")
public class BrandResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private BrandEntitySessionBeanLocal brandEntitySessionBeanLocal;

    public BrandResource() {
        sessionBeanLookup = new SessionBeanLookup();
        brandEntitySessionBeanLocal = sessionBeanLookup.lookupBrandEntitySessionBeanLocal();
    }

    @Path("retrieveAllBrands")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBrands() {
        try {
            List<BrandEntity> brandEntities = brandEntitySessionBeanLocal.retrieveAllBrands();

            for (BrandEntity brandEntity : brandEntities) {
                brandEntity.getModelEntities().clear();
                
            }
            GenericEntity<List<BrandEntity>> genericEntity = new GenericEntity<List<BrandEntity>>(brandEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    
    @Path("retrieveBrand/{brandId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBrand(@PathParam("brandId") Long brandId) {
        try {
            BrandEntity brandEntity = brandEntitySessionBeanLocal.retrieveBrandByBrandId(brandId);

            brandEntity.getModelEntities().clear();

            return Response.status(Response.Status.OK).entity(brandEntity).build();

        } catch (BrandNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
