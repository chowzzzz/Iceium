/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CreditCardEntitySessionBeanLocal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CreateNewCreditCardException;
import util.exception.CreditCardNotFoundException;
import util.exception.CreditCardNumberExistException;
import ws.datamodel.CreateCreditCardReq;

/**
 * REST Web Service
 *
 * @author MOK
 */
@Path("CreditCard")
public class CreditCardResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    private final CreditCardEntitySessionBeanLocal creditCardEntitySessionBeanLocal;

    public CreditCardResource() {
        sessionBeanLookup = new SessionBeanLookup();
        creditCardEntitySessionBeanLocal = sessionBeanLookup.lookupCreditCardEntitySessionBeanLocal();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCreditCard(CreateCreditCardReq createCreditCardReq) {
        if (createCreditCardReq != null) {
            try {
                LocalDateTime date = Instant.ofEpochMilli(createCreditCardReq.getExpiryDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                createCreditCardReq.getNewCreditCardEntity().setExpiryDate(date);

                Long creditCardId = creditCardEntitySessionBeanLocal.createNewCreditCard(createCreditCardReq.getNewCreditCardEntity(), createCreditCardReq.getCustomerEntity());

                return Response.status(Response.Status.OK).entity(creditCardId).build();
            } catch (CreateNewCreditCardException | CreditCardNumberExistException ex) {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new credit card request").build();
        }
    }

    @Path("{customerId}/{creditCardId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCard(@PathParam("customerId") Long customerId, @PathParam("creditCardId") Long creditCardId) {
        try {
            creditCardEntitySessionBeanLocal.deleteCreditCard(creditCardId, customerId);

            return Response.status(Status.OK).build();
        } catch (CreditCardNotFoundException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

}
