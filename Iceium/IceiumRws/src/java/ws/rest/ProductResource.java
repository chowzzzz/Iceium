/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.ProductEntitySessionBeanLocal;
import entity.OrderItemEntity;
import entity.ProductEntity;
import entity.SpecificationEntity;
import entity.TagEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.CreateNewProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;
import ws.datamodel.CreateCustomizeProductReq;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Product")
public class ProductResource
{

    @Context
    private UriInfo context;

    private SessionBeanLookup sessionBeanLookup;
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    public ProductResource()
    {
        sessionBeanLookup = new SessionBeanLookup();
        productEntitySessionBeanLocal = sessionBeanLookup.lookupProductEntitySessionBeanLocal();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomizeProduct(CreateCustomizeProductReq createCustomizeProductReq)
    {
        if (createCustomizeProductReq != null)
        {
            try
            {
                ProductEntity productEntity = productEntitySessionBeanLocal.createNewCustomizeProduct(createCustomizeProductReq.getNewProductEntity(), createCustomizeProductReq.getModelId(), createCustomizeProductReq.getSpecificationEntity(), createCustomizeProductReq.getColorEntity(), createCustomizeProductReq.getSizeEntity());

                if (productEntity.getCategoryEntity().getParentCategoryEntity() != null)
                {
                    productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                productEntity.getCategoryEntity().getProductEntities().clear();

                for (TagEntity tagEntity : productEntity.getTagEntities())
                {
                    tagEntity.getProductEntities().clear();
                }
                productEntity.getModelEntity().getProductEntities().clear();
                productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                if (productEntity.getSaleEntity() != null)
                {
                    productEntity.getSaleEntity().getProductEntities().clear();
                }

                for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
                {

                    specificationEntity.setProductEntityToNull();

                    specificationEntity.getColorEntity().getSpecificationEntities().clear();

                    specificationEntity.getSizeEntity().getSpecificationEntities().clear();

                    for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                    {
                        orderItemEntity.setSpecificationEntityToNull();
                        if (orderItemEntity.getReviewEntity() != null)
                        {
                            orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                        }

                        orderItemEntity.setOrderEntityToNull();
                    }
                }
                return Response.status(Response.Status.OK).entity(productEntity).build();
            }
            catch (CreateNewProductException | ProductSkuCodeExistException | InputDataValidationException | UnknownPersistenceException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch (Exception ex)
            {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new credit card request").build();
        }
    }

    @Path("retrieveProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProducts()
    {
        try
        {
            List<ProductEntity> productEntities = productEntitySessionBeanLocal.retrieveAllProducts();

            for (ProductEntity productEntity : productEntities)
            {

                if (productEntity.getCategoryEntity().getParentCategoryEntity() != null)
                {
                    productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                productEntity.getCategoryEntity().getProductEntities().clear();

                for (TagEntity tagEntity : productEntity.getTagEntities())
                {
                    tagEntity.getProductEntities().clear();
                }
                productEntity.getModelEntity().getProductEntities().clear();
                productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                if (productEntity.getSaleEntity() != null)
                {
                    productEntity.getSaleEntity().getProductEntities().clear();
                }

                for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
                {

                    specificationEntity.setProductEntityToNull();

                    specificationEntity.getColorEntity().getSpecificationEntities().clear();

                    specificationEntity.getSizeEntity().getSpecificationEntities().clear();

                    for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                    {
                        orderItemEntity.setSpecificationEntityToNull();
                        if (orderItemEntity.getReviewEntity() != null)
                        {
                            orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                        }

                        orderItemEntity.setOrderEntityToNull();
                    }
                }

            }

            GenericEntity<List<ProductEntity>> genericEntity = new GenericEntity<List<ProductEntity>>(productEntities)
            {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveProduct/{productId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProduct(@PathParam("productId") Long productId)
    {
        try
        {
            ProductEntity productEntity = productEntitySessionBeanLocal.retrieveProductByProductId(productId);

            if (productEntity.getCategoryEntity().getParentCategoryEntity() != null)
            {
                productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
            }

            productEntity.getCategoryEntity().getProductEntities().clear();

            for (TagEntity tagEntity : productEntity.getTagEntities())
            {
                tagEntity.getProductEntities().clear();
            }
            productEntity.getModelEntity().getProductEntities().clear();
            productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

            if (productEntity.getSaleEntity() != null)
            {
                productEntity.getSaleEntity().getProductEntities().clear();
            }

            for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
            {

                specificationEntity.setProductEntityToNull();

                specificationEntity.getColorEntity().getSpecificationEntities().clear();

                specificationEntity.getSizeEntity().getSpecificationEntities().clear();

                for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                {
                    orderItemEntity.setSpecificationEntityToNull();
                    if (orderItemEntity.getReviewEntity() != null)
                    {
                        orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                    }

                    orderItemEntity.setOrderEntityToNull();
                }
            }

            return Response.status(Response.Status.OK).entity(productEntity).build();

        }
        catch (ProductNotFoundException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveProductBySpecificationId/{specificationId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveProductBySpecificationId(@PathParam("specificationId") Long specificationId)
    {
        try
        {
            ProductEntity productEntity = productEntitySessionBeanLocal.retrieveProductBySpecificationId(specificationId);

            if (productEntity.getCategoryEntity().getParentCategoryEntity() != null)
            {
                productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
            }

            productEntity.getCategoryEntity().getProductEntities().clear();

            for (TagEntity tagEntity : productEntity.getTagEntities())
            {
                tagEntity.getProductEntities().clear();
            }
            productEntity.getModelEntity().getProductEntities().clear();
            productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

            if (productEntity.getSaleEntity() != null)
            {
                productEntity.getSaleEntity().getProductEntities().clear();
            }

            for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
            {

                specificationEntity.setProductEntityToNull();

                specificationEntity.getColorEntity().getSpecificationEntities().clear();

                specificationEntity.getSizeEntity().getSpecificationEntities().clear();

                for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                {
                    orderItemEntity.setSpecificationEntityToNull();
                    if (orderItemEntity.getReviewEntity() != null)
                    {
                        orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                    }

                    orderItemEntity.setOrderEntityToNull();
                }
            }

            return Response.status(Response.Status.OK).entity(productEntity).build();

        }
        catch (ProductNotFoundException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveProducts/{brandName}/{modelName}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchProducts(@PathParam("brandName") String productName)
    {
        try
        {
            List<ProductEntity> productEntities = productEntitySessionBeanLocal.searchProductsByName(productName);

            for (ProductEntity productEntity : productEntities)
            {

                if (productEntity.getCategoryEntity().getParentCategoryEntity() != null)
                {
                    productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                productEntity.getCategoryEntity().getProductEntities().clear();

                for (TagEntity tagEntity : productEntity.getTagEntities())
                {
                    tagEntity.getProductEntities().clear();
                }
                productEntity.getModelEntity().getProductEntities().clear();
                productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                if (productEntity.getSaleEntity() != null)
                {
                    productEntity.getSaleEntity().getProductEntities().clear();
                }

                for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
                {

                    specificationEntity.setProductEntityToNull();

                    specificationEntity.getColorEntity().getSpecificationEntities().clear();

                    specificationEntity.getSizeEntity().getSpecificationEntities().clear();

                    for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                    {
                        orderItemEntity.setSpecificationEntityToNull();
                        if (orderItemEntity.getReviewEntity() != null)
                        {
                            orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                        }

                        orderItemEntity.setOrderEntityToNull();
                    }
                }

            }
            GenericEntity<List<ProductEntity>> genericEntity = new GenericEntity<List<ProductEntity>>(productEntities)
            {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();

        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
