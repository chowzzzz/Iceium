/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.ModelEntitySessionBeanLocal;
import entity.ModelEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Model")
public class ModelResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    public ModelResource() {
        sessionBeanLookup = new SessionBeanLookup();
        modelEntitySessionBeanLocal = sessionBeanLookup.lookupModelEntitySessionBeanLocal();
    }

    @Path("retrieveAllModels")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModels() {
        try {
            List<ModelEntity> modelEntities = modelEntitySessionBeanLocal.retrieveAllModels();

            for (ModelEntity modelEntity : modelEntities) {
                modelEntity.getBrandEntity().getModelEntities().clear();
                modelEntity.getProductEntities().clear();
            }
            GenericEntity<List<ModelEntity>> genericEntity = new GenericEntity<List<ModelEntity>>(modelEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveModelByName")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveModelByName(@QueryParam("brandName") String brandName,
            @QueryParam("modelName") String modelName) {
        try {
            ModelEntity modelEntity = modelEntitySessionBeanLocal.retrieveModelByModelNameAndBrandName(modelName, brandName);

            modelEntity.getBrandEntity().getModelEntities().clear();
            modelEntity.getProductEntities().clear();
            return Response.status(Response.Status.OK).entity(modelEntity).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
