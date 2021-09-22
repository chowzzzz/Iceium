/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.ColorEntitySessionBeanLocal;
import entity.ColorEntity;
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
@Path("Color")
public class ColorResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    public ColorResource() {
        sessionBeanLookup = new SessionBeanLookup();
        colorEntitySessionBeanLocal = sessionBeanLookup.lookupColorEntitySessionBeanLocal();
    }

    @Path("retrieveAllColors")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllColors() {
        try {
            List<ColorEntity> colorEntities = colorEntitySessionBeanLocal.retrieveAllColors();

            for (ColorEntity colorEntity : colorEntities) {
                colorEntity.getSpecificationEntities().clear();
            }
            GenericEntity<List<ColorEntity>> genericEntity = new GenericEntity<List<ColorEntity>>(colorEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("retrieveAllColorsWithProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllColorsWithProducts() {
        try {
            List<ColorEntity> colorEntities = colorEntitySessionBeanLocal.retrieveAllColorNamesWithProducts();

            for (ColorEntity colorEntity : colorEntities) {
                colorEntity.getSpecificationEntities().clear();
            }
            GenericEntity<List<ColorEntity>> genericEntity = new GenericEntity<List<ColorEntity>>(colorEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
