/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.CreditCardEntity;
import entity.CustomerEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.DisabledCustomerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
import util.exception.changePasswordException;
import ws.datamodel.CreateCustomerReq;
import ws.datamodel.CustomerChangePasswordReq;
import ws.datamodel.UpdateProfileReq;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("Customer")
public class CustomerResource
{

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    public CustomerResource()
    {
        sessionBeanLookup = new SessionBeanLookup();
        customerEntitySessionBeanLocal = sessionBeanLookup.lookupCustomerEntitySessionBeanLocal();
    }

    @Path("customerLogin")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(@QueryParam("username") String username,
            @QueryParam("password") String password)
    {
        try
        {
            CustomerEntity customerEntity = customerEntitySessionBeanLocal.customerLogin(username, password);
            System.out.println("********** CustomerResource.customerLogin(): Customer " + customerEntity.getUsername() + " login remotely via web service");

            customerEntity.setPassword(null);
            customerEntity.setSalt(null);
            customerEntity.getOrderEntities().clear();
            
            
            for (AddressEntity addressEntity : customerEntity.getAddressEntities()) {
                addressEntity.getCustomerEntities().clear();
                addressEntity.getOrderEntities().clear();
            }
            
            return Response.status(Response.Status.OK).entity(customerEntity).build();
        }
        catch (InvalidLoginCredentialException ex)
        {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("customerRegister")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(CreateCustomerReq createCustomerReq)
    {
        if (createCustomerReq != null)
        {
            try
            {

                LocalDateTime date = Instant.ofEpochMilli(createCustomerReq.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                createCustomerReq.getNewCustomer().setDateOfBirth(date);
                CustomerEntity customerEntity = createCustomerReq.getNewCustomer();

                Long customerId = customerEntitySessionBeanLocal.createNewCustomer(customerEntity);

                return Response.status(Response.Status.OK).entity(customerId).build();
            }
            catch (CustomerUsernameExistException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch (Exception ex)
            {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new customer request").build();
        }
    }

    @Path("retrieveCustomerByCustomerId/{customerId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCustomerByCustomerId(@PathParam("customerId") Long customerId)
    {
        try
        {
            CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerId);

            customerEntity.setPassword(null);
            customerEntity.setSalt(null);
            customerEntity.getOrderEntities().clear();
                        
            for (AddressEntity addressEntity : customerEntity.getAddressEntities()) {
                addressEntity.getCustomerEntities().clear();
                addressEntity.getOrderEntities().clear();
            }

            return Response.status(Response.Status.OK).entity(customerEntity).build();
        }
        catch (Exception ex)
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("changePassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerChangePassword(CustomerChangePasswordReq customerChangePasswordReq)
    {
        if (customerChangePasswordReq != null)
        {
            try
            {
                customerEntitySessionBeanLocal.customerChangePassword(customerChangePasswordReq.getUsername(), customerChangePasswordReq.getOldPwd(), customerChangePasswordReq.getNewPwd());

                return Response.status(Response.Status.OK).build();
            }
            catch (DisabledCustomerException | InvalidLoginCredentialException ex)
            {
                return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
            catch (changePasswordException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch (Exception ex)
            {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid password change request").build();
        }
    }

    @Path("updateProfile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(UpdateProfileReq updateProfileReq)
    {
        if (updateProfileReq != null)
        {
            try
            {
                LocalDateTime date = Instant.ofEpochMilli(updateProfileReq.getDateOfBirth()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                updateProfileReq.getCurrCustomer().setDateOfBirth(date);
                CustomerEntity customerEntity = updateProfileReq.getCurrCustomer();

                customerEntitySessionBeanLocal.updateCustomer(customerEntity);

                return Response.status(Response.Status.OK).build();
            }
            catch (CustomerNotFoundException | UpdateCustomerException | InputDataValidationException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch (Exception ex)
            {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid user profile update request").build();
        }
    }

}
