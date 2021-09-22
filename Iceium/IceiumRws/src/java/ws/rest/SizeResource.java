/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.SizeEntitySessionBeanLocal;
import entity.SizeEntity;
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
@Path("Size")
public class SizeResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    public SizeResource() {
        sessionBeanLookup = new SessionBeanLookup();
        sizeEntitySessionBeanLocal = sessionBeanLookup.lookupSizeEntitySessionBeanLocal();
    }

    @Path("retrieveAllSizes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSizes() {
        try {
            List<SizeEntity> sizeEntities = sizeEntitySessionBeanLocal.retrieveAllSizesWithProducts();

            for (SizeEntity sizeEntity : sizeEntities) {
                sizeEntity.getSpecificationEntities().clear();
            }
            GenericEntity<List<SizeEntity>> genericEntity = new GenericEntity<List<SizeEntity>>(sizeEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
