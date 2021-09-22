/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CustomerEntity;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AddressExistException;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteAddressException;
import util.exception.InputDataValidationException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAddressException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class AddressEntitySessionBean implements AddressEntitySessionBeanLocal {

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public AddressEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public AddressEntity createNewAddress(AddressEntity newAddressEntity) throws AddressExistException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<AddressEntity>> constraintViolations = validator.validate(newAddressEntity);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(newAddressEntity);
                em.flush();

                return newAddressEntity;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new AddressExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public List<AddressEntity> retrieveAllAddresses() {
        Query query = em.createQuery("SELECT a FROM AddressEntity a");
        List<AddressEntity> addressEntities = query.getResultList();

        for (AddressEntity addressEntity : addressEntities) {
            addressEntity.getCustomerEntities().size();
            addressEntity.getOrderEntities().size();
        }

        return addressEntities;
    }

    @Override
    public AddressEntity retrieveAddressByAddressId(Long addressId) throws AddressNotFoundException {
        AddressEntity addressEntity = em.find(AddressEntity.class, addressId);

        if (addressEntity != null) {
            addressEntity.getCustomerEntities().size();
            return addressEntity;
        } else {
            throw new AddressNotFoundException("Address ID " + addressId + " does not exist!");
        }
    }

    @Override
    public AddressEntity retrieveAddressByAddressAndPostalCode(String address, String postalCode) throws AddressNotFoundException {
        Query query = em.createQuery("SELECT a FROM AddressEntity a WHERE a.address = :inAddress AND a.postalCode = :inPostalCode");
        query.setParameter("inAddress", address);
        query.setParameter("inPostalCode", postalCode);

        try {
            AddressEntity addressEntity = (AddressEntity) query.getSingleResult();
            addressEntity.getCustomerEntities().size();
            return addressEntity;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AddressNotFoundException("Address with address " + address + " and " + postalCode + " does not exist!");
        }
    }

    @Override
    public void addAddressToCustomer(long addressId, long customerId) throws AddressNotFoundException, CustomerNotFoundException {
        
        AddressEntity addressEntity = retrieveAddressByAddressId(addressId);
        CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerId);

        
        customerEntity.addAddressEntity(addressEntity);
    }

    @Override
    public void updateAddress(AddressEntity addressEntity, Long customerId) throws UpdateAddressException, InputDataValidationException, AddressExistException, NoChangesMadeException {
        if (addressEntity != null) {
            Set<ConstraintViolation<AddressEntity>> constraintViolations = validator.validate(addressEntity);

            if (constraintViolations.isEmpty()) {
                try {
                    AddressEntity addressEntityToUpdate = retrieveAddressByAddressId(addressEntity.getAddressId());
                    
                    String address = addressEntity.getAddress();
                    String postalCode = addressEntity.getPostalCode();
                    
                    if (addressEntityToUpdate.getAddress().equals(address) && addressEntityToUpdate.getPostalCode().equals(postalCode)) {
                        throw new NoChangesMadeException();
                    } else {
                        if (!isAddressExist(address, postalCode)) {
                            deleteCustomerAddress(addressEntity.getAddressId(), customerId);
                            AddressEntity newAddressEntity = createNewAddress(addressEntity);
                            addAddressToCustomer(newAddressEntity.getAddressId(), customerId);
                        } else {
                            AddressEntity newAddressEntity = retrieveAddressByAddressAndPostalCode(address, postalCode);
                            addAddressToCustomer(newAddressEntity.getAddressId(), customerId);
                        }
                    }

                } catch (AddressNotFoundException | DeleteAddressException | AddressExistException | UnknownPersistenceException | CustomerNotFoundException ex) {
                    throw new UpdateAddressException(ex.getMessage());
                }
            } else {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new UpdateAddressException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteCustomerAddress(Long addressId, Long customerId) throws DeleteAddressException {
        try {
            CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            AddressEntity addressEntityToRemove = retrieveAddressByAddressId(addressId);

            customerEntity.getAddressEntities().remove(addressEntityToRemove);
            addressEntityToRemove.getCustomerEntities().remove(customerEntity);
            
            if (addressEntityToRemove.getOrderEntities().isEmpty()) {
                if (addressEntityToRemove.getCustomerEntities().isEmpty()) {
                    em.remove(addressEntityToRemove);
                    em.flush();
                }
            } else {
                throw new DeleteAddressException("Address ID " + addressId + " is associated with existing model(s) and cannot be deleted!");
            }
        } catch (AddressNotFoundException | CustomerNotFoundException ex) {
            throw new DeleteAddressException(ex.getMessage());
        }
    }
    
    private boolean isAddressExist(String address, String postalCode) {
        Query query = em.createQuery("SELECT COUNT(a) FROM AddressEntity a WHERE a.address = :inAddress and a.postalCode = :inPostalCode");
        query.setParameter("inAddress", address);
        query.setParameter("inPostalCode", postalCode);

        Long count = (Long) query.getSingleResult();

        return !count.equals(0L);
    }
}
