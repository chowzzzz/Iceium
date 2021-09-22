/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import entity.CategoryEntity;
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
import util.exception.CategoryNotFoundException;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Category")
public class CategoryResource {

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    public CategoryResource() {
        sessionBeanLookup = new SessionBeanLookup();
        categoryEntitySessionBeanLocal = sessionBeanLookup.lookupCategoryEntitySessionBeanLocal();
    }

    @Path("retrieveAllCategories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCategories() {
        try {
            List<CategoryEntity> categoryEntities = categoryEntitySessionBeanLocal.retrieveAllCategories();

            for (CategoryEntity categoryEntity : categoryEntities) {
                categoryEntity.getProductEntities().clear();
                categoryEntity.getSubCategoryEntities().clear();
                if (categoryEntity.getParentCategoryEntity() != null) {
                    categoryEntity.getParentCategoryEntity().getSubCategoryEntities().clear();
                }

            }
            GenericEntity<List<CategoryEntity>> genericEntity = new GenericEntity<List<CategoryEntity>>(categoryEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveCategory/{categoryId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCategory(@PathParam("categoryId") Long categoryId) {
        try {
            CategoryEntity categoryEntity = categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

            categoryEntity.getProductEntities().clear();
            categoryEntity.getSubCategoryEntities().clear();
            if (categoryEntity.getParentCategoryEntity() != null) {
                categoryEntity.getParentCategoryEntity().getSubCategoryEntities().clear();
            }

            return Response.status(Response.Status.OK).entity(categoryEntity).build();

        } catch (CategoryNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
