/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.AddressEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.AddressEntity;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.exception.AddressExistException;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteAddressException;
import util.exception.InputDataValidationException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAddressException;
import ws.datamodel.UpdateAddressReq;

/**
 *
 * @author Theodoric
 */
@Path("Address")
public class AddressResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;
    private final CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    public AddressResource() {
        sessionBeanLookup = new SessionBeanLookup();
        addressEntitySessionBeanLocal = sessionBeanLookup.lookupAddressEntitySessionBeanLocal();
        customerEntitySessionBeanLocal = sessionBeanLookup.lookupCustomerEntitySessionBeanLocal();
    }

    @Path("createAddress")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(AddressEntity newAddressEntity) {
        if (newAddressEntity != null) {
            try {
                newAddressEntity = addressEntitySessionBeanLocal.createNewAddress(newAddressEntity);

                return Response.status(Response.Status.OK).entity(newAddressEntity.getAddressId()).build();
            } catch (AddressExistException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new address request").build();
        }
    }

    @Path("createAddress/{customerId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(AddressEntity newAddressEntity, @PathParam("customerId") Long customerId) {
        if (newAddressEntity != null) {
            try {
                newAddressEntity = addressEntitySessionBeanLocal.createNewAddress(newAddressEntity);

                addressEntitySessionBeanLocal.addAddressToCustomer(newAddressEntity.getAddressId(), customerId);

                return Response.status(Response.Status.OK).entity(newAddressEntity.getAddressId()).build();
            } catch (AddressExistException ex) {
                try {
                    AddressEntity addressEntity = addressEntitySessionBeanLocal.retrieveAddressByAddressAndPostalCode(newAddressEntity.getAddress(), newAddressEntity.getPostalCode());
                    addressEntitySessionBeanLocal.addAddressToCustomer(addressEntity.getAddressId(), customerId);
                    return Response.status(Response.Status.OK).entity(addressEntity.getAddressId()).build();
                } catch (AddressNotFoundException | CustomerNotFoundException e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
                }
            } catch (AddressNotFoundException | CustomerNotFoundException | InputDataValidationException | UnknownPersistenceException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new address request").build();
        }
    }

    @Path("retrieveAddressByAddressAndPostalCode/{address}/{postalCode}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAddressByAddressAndPostalCode(@PathParam("address") String address, @PathParam("postalCode") String postalCode) {
        try {
            AddressEntity addressEntity = addressEntitySessionBeanLocal.retrieveAddressByAddressAndPostalCode(address, postalCode);

            addressEntity.getCustomerEntities().clear();
            addressEntity.getOrderEntities().clear();

            System.out.println("address: " + addressEntity);
            return Response.status(Response.Status.OK).entity(addressEntity).build();

        } catch (AddressNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAddress(UpdateAddressReq updateAddressReq) {
        if (updateAddressReq != null) {
            try {
                addressEntitySessionBeanLocal.updateAddress(updateAddressReq.getAddressEntity(), updateAddressReq.getCustomerId());

                return Response.status(Response.Status.OK).build();
            } catch (NoChangesMadeException ex) {
                return Response.status(Response.Status.NOT_MODIFIED).build();
            } catch (UpdateAddressException | AddressExistException | InputDataValidationException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password change request").build();
        }
    }
    
    @Path("{customerId}/{addressId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@PathParam("customerId") Long customerId, @PathParam("addressId") Long addressId) {
        try {
            addressEntitySessionBeanLocal.deleteCustomerAddress(addressId, customerId);

            return Response.status(Response.Status.OK).build();
        } catch (DeleteAddressException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }



}
