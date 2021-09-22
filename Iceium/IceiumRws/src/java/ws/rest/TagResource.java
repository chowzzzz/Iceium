/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.TagEntitySessionBeanLocal;
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
@Path("Tag")
public class TagResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    public TagResource() {
        sessionBeanLookup = new SessionBeanLookup();
        tagEntitySessionBeanLocal = sessionBeanLookup.lookupTagEntitySessionBeanLocal();
    }

    @Path("retrieveAllTags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags() {
        try {
            List<TagEntity> tagEntities = tagEntitySessionBeanLocal.retrieveAllTags();

            for (TagEntity tagEntity : tagEntities) {
                tagEntity.getProductEntities().clear();
            }
            GenericEntity<List<TagEntity>> genericEntity = new GenericEntity<List<TagEntity>>(tagEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
