/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.SpecificationEntitySessionBeanLocal;
import entity.OrderItemEntity;
import entity.SpecificationEntity;
import entity.SpecificationEntity;
import entity.TagEntity;
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
import util.exception.SpecificationNotFoundException;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Specification")
public class SpecificationResource
{

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;

    public SpecificationResource()
    {
        sessionBeanLookup = new SessionBeanLookup();
        specificationEntitySessionBeanLocal = sessionBeanLookup.lookupSpecificationEntitySessionBeanLocal();
    }

    @Path("retrieveSpecification/{specificationId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSpecification(@PathParam("specificationId") Long specificationId)
    {
        try
        {
            SpecificationEntity specificationEntity = specificationEntitySessionBeanLocal.retrieveSpecificationBySpecificationId(specificationId);

            specificationEntity.getColorEntity().getSpecificationEntities().clear();
            specificationEntity.getSizeEntity().getSpecificationEntities().clear();
            specificationEntity.getOrderItemEntities().clear();
            specificationEntity.setProductEntity(null);
            
            return Response.status(Response.Status.OK).entity(specificationEntity).build();

        }
        catch (SpecificationNotFoundException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }


}
