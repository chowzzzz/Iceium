/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.SpecificationEntitySessionBeanLocal;
import entity.OrderEntity;
import entity.OrderItemEntity;
import entity.ProductEntity;
import entity.TagEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.CreateNewOrderException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import ws.datamodel.CreateOrderReq;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Order")
public class OrderResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;
    private final SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;
    private final OrderEntitySessionBeanLocal orderEntitySessionBeanLocal;

    public OrderResource() {
        sessionBeanLookup = new SessionBeanLookup();
        orderEntitySessionBeanLocal = sessionBeanLookup.lookupOrderEntitySessionBeanLocal();
        specificationEntitySessionBeanLocal = sessionBeanLookup.lookupSpecificationEntitySessionBeanLocal();
        couponEntitySessionBeanLocal = sessionBeanLookup.lookupCouponEntitySessionBeanLocal();
    }

    @Path("retrieveOrder/{orderId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOrder(@PathParam("orderId") Long orderId) {
        try {
            OrderEntity orderEntity = orderEntitySessionBeanLocal.retrieveOrderByOrderId(orderId);

            if (orderEntity.getCouponEntity() != null) {
                orderEntity.getCouponEntity().getOrderEntities().clear();
            }

            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities()) {
                orderItemEntity.setOrderEntityToNull();
                orderItemEntity.setReviewEntity(null);

                orderItemEntity.getSpecificationEntity().getOrderItemEntities().clear();
                orderItemEntity.getSpecificationEntity().getColorEntity().getSpecificationEntities().clear();
                orderItemEntity.getSpecificationEntity().getSizeEntity().getSpecificationEntities().clear();

                ProductEntity productEntity = orderItemEntity.getSpecificationEntity().getProductEntity();

                productEntity.getSpecificationEntities().clear();

                if (productEntity.getCategoryEntity().getParentCategoryEntity() != null) {
                    productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                productEntity.getCategoryEntity().getProductEntities().clear();

                for (TagEntity tagEntity : productEntity.getTagEntities()) {
                    tagEntity.getProductEntities().clear();
                }
                productEntity.getModelEntity().getProductEntities().clear();
                productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                if (productEntity.getSaleEntity() != null) {
                    productEntity.getSaleEntity().getProductEntities().clear();
                }
            }
            orderEntity.getCustomerEntity().getOrderEntities().clear();
            orderEntity.getCustomerEntity().getCreditCardEntities().clear();
            orderEntity.getCustomerEntity().getAddressEntities().clear();
            orderEntity.getAddressEntity().getOrderEntities().clear();
            orderEntity.getAddressEntity().getCustomerEntities().clear();

            return Response.status(Response.Status.OK).entity(orderEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveOrdersByCustomer/{customerId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOrdersByCustomer(@PathParam("customerId") Long customerId) {
        try {
            List<OrderEntity> orderEntities = orderEntitySessionBeanLocal.retrieveOrdersByCustomer(customerId);

            for (OrderEntity orderEntity : orderEntities) {
                if (orderEntity.getCouponEntity() != null) {
                    orderEntity.getCouponEntity().getOrderEntities().clear();
                }

                for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities()) {
                    orderItemEntity.setOrderEntityToNull();
                    orderItemEntity.setReviewEntity(null);

                    orderItemEntity.getSpecificationEntity().getOrderItemEntities().clear();
                    orderItemEntity.getSpecificationEntity().getColorEntity().getSpecificationEntities().clear();
                    orderItemEntity.getSpecificationEntity().getSizeEntity().getSpecificationEntities().clear();

                    ProductEntity productEntity = orderItemEntity.getSpecificationEntity().getProductEntity();

                    productEntity.getSpecificationEntities().clear();

                    if (productEntity.getCategoryEntity().getParentCategoryEntity() != null) {
                        productEntity.getCategoryEntity().getParentCategoryEntity().getSubCategoryEntities().clear();
                    }

                    productEntity.getCategoryEntity().getProductEntities().clear();

                    for (TagEntity tagEntity : productEntity.getTagEntities()) {
                        tagEntity.getProductEntities().clear();
                    }
                    productEntity.getModelEntity().getProductEntities().clear();
                    productEntity.getModelEntity().getBrandEntity().getModelEntities().clear();

                    if (productEntity.getSaleEntity() != null) {
                        productEntity.getSaleEntity().getProductEntities().clear();
                    }
                }
                orderEntity.getCustomerEntity().getOrderEntities().clear();
                orderEntity.getCustomerEntity().getCreditCardEntities().clear();
                orderEntity.getCustomerEntity().getAddressEntities().clear();
                orderEntity.getAddressEntity().getOrderEntities().clear();
                orderEntity.getAddressEntity().getCustomerEntities().clear();
            }

            GenericEntity<List<OrderEntity>> genericEntity = new GenericEntity<List<OrderEntity>>(orderEntities) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("createOrder")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(CreateOrderReq createOrderReq) {
        if (createOrderReq != null) {
            try {
                OrderEntity newOrderEntity = createOrderReq.getNewOrderEntity();
                Long customerId = createOrderReq.getCustomerId();
                Long addressId = createOrderReq.getAddressId();
                Long couponId = createOrderReq.getCouponId();

                newOrderEntity.setTransactionDateTime(LocalDateTime.now());

                newOrderEntity = orderEntitySessionBeanLocal.createNewOrder(newOrderEntity, customerId, addressId, couponId);

                if (newOrderEntity.getCouponEntity() != null) {
                    newOrderEntity.getCouponEntity().getOrderEntities().clear();
                }
                newOrderEntity.getOrderItemEntities().clear();
                newOrderEntity.getCustomerEntity().getOrderEntities().clear();
                newOrderEntity.getCustomerEntity().getCreditCardEntities().clear();
                newOrderEntity.getCustomerEntity().getAddressEntities().clear();
                newOrderEntity.getAddressEntity().getOrderEntities().clear();
                newOrderEntity.getAddressEntity().getCustomerEntities().clear();

                return Response.status(Response.Status.OK).entity(newOrderEntity.getOrderId()).build();
            } catch (CreateNewOrderException | CustomerNotFoundException | InputDataValidationException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new order request").build();
        }
    }

}
