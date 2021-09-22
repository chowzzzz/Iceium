/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import entity.OrderItemEntity;
import entity.TagEntity;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.CreateNewOrderItemException;
import util.exception.OrderItemNotFoundException;
import ws.datamodel.CreateOrderItemReq;
import ws.datamodel.UpdateOrderItemReq;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("OrderItem")
public class OrderItemResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    public OrderItemResource() {
        sessionBeanLookup = new SessionBeanLookup();
        orderItemEntitySessionBeanLocal = sessionBeanLookup.lookupOrderItemEntitySessionBeanLocal();
    }

    @Path("retrieveOrderItemsByOrder/{orderId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOrderItemsByOrder(@PathParam("orderId") Long orderId) {
        try {
            List<OrderItemEntity> orderItemEntities = orderItemEntitySessionBeanLocal.retrieveOrderItemsByOrder(orderId);

            for (OrderItemEntity orderItemEntity : orderItemEntities) {
                if (orderItemEntity.getReviewEntity() != null) {
                    orderItemEntity.getReviewEntity().setOrderItemEntity(null);
                }
                orderItemEntity.getOrderEntity().getOrderItemEntities().clear();
                orderItemEntity.getOrderEntity().setCouponEntity(null);
                orderItemEntity.getOrderEntity().getCustomerEntity().getOrderEntities().clear();
                orderItemEntity.getOrderEntity().getCustomerEntity().getCreditCardEntities().clear();
                orderItemEntity.getSpecificationEntity().getOrderItemEntities().clear();
                orderItemEntity.getSpecificationEntity().getColorEntity().getSpecificationEntities().clear();
                orderItemEntity.getSpecificationEntity().getSizeEntity().getSpecificationEntities().clear();
                orderItemEntity.getSpecificationEntity().getProductEntity().getSpecificationEntities().clear();

                if (orderItemEntity.getSpecificationEntity().getProductEntity().getCategoryEntity().getParentCategoryEntity() != null) {
                    orderItemEntity.getSpecificationEntity().getProductEntity().getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                orderItemEntity.getSpecificationEntity().getProductEntity().getCategoryEntity().getProductEntities().clear();

                for (TagEntity tagEntity : orderItemEntity.getSpecificationEntity().getProductEntity().getTagEntities()) {
                    tagEntity.getProductEntities().clear();
                }
                orderItemEntity.getSpecificationEntity().getProductEntity().getModelEntity().getProductEntities().clear();
                orderItemEntity.getSpecificationEntity().getProductEntity().getModelEntity().getBrandEntity().getModelEntities().clear();

                if (orderItemEntity.getSpecificationEntity().getProductEntity().getSaleEntity() != null) {
                    orderItemEntity.getSpecificationEntity().getProductEntity().getSaleEntity().getProductEntities().clear();
                }
            }

            GenericEntity<List<OrderItemEntity>> genericEntity = new GenericEntity<List<OrderItemEntity>>(orderItemEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrderItem(CreateOrderItemReq createOrderItemReq) {
        if (createOrderItemReq != null) {
            try {
                OrderItemEntity orderItemEntity = orderItemEntitySessionBeanLocal.createNewOrderItem(createOrderItemReq.getNewOrderItem(), createOrderItemReq.getProductId());

                return Response.status(Response.Status.OK).entity(orderItemEntity.getOrderItemId()).build();
            } catch (CreateNewOrderItemException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new orderItem request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderItem(UpdateOrderItemReq updateOrderItemReq) {
        if (updateOrderItemReq != null) {
            try {
                orderItemEntitySessionBeanLocal.updateOrderItem(updateOrderItemReq.getOrderItemId(), updateOrderItemReq.getOrderItemStatusEnum());

                return Response.status(Response.Status.OK).build();
            } catch (OrderItemNotFoundException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password change request").build();
        }
    }
}
