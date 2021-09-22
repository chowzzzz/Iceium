/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CouponEntitySessionBeanLocal;
import entity.CouponEntity;
import entity.OrderEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.CouponNotFoundException;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Coupon")
public class CouponResource
{

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;

    public CouponResource()
    {
        sessionBeanLookup = new SessionBeanLookup();
        couponEntitySessionBeanLocal = sessionBeanLookup.lookupCouponEntitySessionBeanLocal();
    }

    @Path("retrieveAllCoupons")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCoupons()
    {
        try
        {
            List<CouponEntity> couponEntities = couponEntitySessionBeanLocal.retrieveAllCoupons();

            for (CouponEntity couponEntity : couponEntities)
            {
                couponEntity.getOrderEntities().clear();
            }
            
            GenericEntity<List<CouponEntity>> genericEntity = new GenericEntity<List<CouponEntity>>(couponEntities)
            {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveCouponByCouponCode")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCouponByCouponCode(String couponCode)
    {
        try
        {
            CouponEntity couponEntity = couponEntitySessionBeanLocal.retrieveCouponByCouponCode(couponCode);

            couponEntity.getOrderEntities().clear();
                        
            return Response.status(Response.Status.OK).entity(couponEntity).build();
        }
        catch (CouponNotFoundException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("checkCouponExistByCouponCode")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkCouponExistByCouponCode(String couponCode)
    {
        try
        {
            boolean couponExist = couponEntitySessionBeanLocal.checkCouponExistByCouponCode(couponCode);
            if (couponExist)
            {
                return Response.status(Response.Status.OK).build();
            }
            else
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
