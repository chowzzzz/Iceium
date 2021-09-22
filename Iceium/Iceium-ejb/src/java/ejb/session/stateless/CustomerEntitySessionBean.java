/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import entity.CreditCardEntity;
import entity.CustomerEntity;
import entity.OrderEntity;
import entity.OrderItemEntity;
import java.util.HashMap;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.EnableCustomerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
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
import util.exception.CreateNewCustomerException;
import util.exception.CreditCardNotFoundException;
import util.exception.DisabledCustomerException;
import util.exception.UnknownPersistenceException;
import util.exception.changePasswordException;
import util.security.CryptographicHelper;
import util.validation.InputDataValidation;

/**
 *
 * @author Mok
 */
@Stateless
public class CustomerEntitySessionBean implements CustomerEntitySessionBeanLocal
{

    @EJB
    private AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CustomerEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewCustomer(CustomerEntity newCustomerEntity) throws CreateNewCustomerException, CustomerUsernameExistException, UnknownPersistenceException, InputDataValidationException
    {
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(newCustomerEntity);

        if (constraintViolations.isEmpty())
        {
            try
            {
                if (!newCustomerEntity.getAddressEntities().isEmpty())
                {
                    for (int i = 0; i < newCustomerEntity.getAddressEntities().size(); i++)
                    {
                        AddressEntity addressEntity = addressEntitySessionBeanLocal.retrieveAddressByAddressId(newCustomerEntity.getAddressEntities().get(i).getAddressId());
                        newCustomerEntity.removeAddressEntity(newCustomerEntity.getAddressEntities().get(i));
                        newCustomerEntity.addAddressEntity(addressEntity);
                    }
                }

                em.persist(newCustomerEntity);
                em.flush();

                return newCustomerEntity.getCustomerId();
            }
            catch (AddressNotFoundException ex)
            {
                throw new CreateNewCustomerException(ex.getMessage());
            }
            catch (PersistenceException ex)
            {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new CustomerUsernameExistException();
                    }
                    else
                    {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        }
        else
        {
            throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<CustomerEntity> retrieveAllCustomers()
    {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c");
        List<CustomerEntity> customerEntities = query.getResultList();

        for (CustomerEntity customerEntity : customerEntities)
        {
            customerEntity.getCreditCardEntities().size();
            customerEntity.getOrderEntities().size();
        }

        return customerEntities;
    }

    @Override
    public HashMap<Integer, Integer> retrieveAllCustomerAges()
    {
        List<CustomerEntity> customerEntities = retrieveAllCustomers();
        HashMap<Integer, Integer> ages = new HashMap<>();

        for (CustomerEntity customerEntity : customerEntities)
        {
            int age = customerEntity.getAge().intValue();
            ages.put(age, ages.containsKey(age) ? ages.get(age) + 1 : 1);
        }

        return ages;
    }

    @Override
    public CustomerEntity retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException
    {
        CustomerEntity customerEntity = em.find(CustomerEntity.class, customerId);

        if (customerEntity != null)
        {
            customerEntity.getCreditCardEntities().size();
            return customerEntity;
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID " + customerId + " does not exist!");
        }
    }

    @Override
    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CustomerEntity c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try
        {
            CustomerEntity customerEntity = (CustomerEntity) query.getSingleResult();
            customerEntity.getCreditCardEntities().size();
            return customerEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CustomerNotFoundException("Customer Username " + username + " does not exist!");
        }
    }

    @Override
    public void updateCustomer(CustomerEntity customerEntity) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException
    {
        if (customerEntity != null && customerEntity.getCustomerId() != null)
        {
            Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(customerEntity);

            if (constraintViolations.isEmpty())
            {
                CustomerEntity customerEntityToUpdate = retrieveCustomerByCustomerId(customerEntity.getCustomerId());

                if (customerEntityToUpdate.getUsername().equals(customerEntity.getUsername()))
                {
                    customerEntityToUpdate.setFirstName(customerEntity.getFirstName());
                    customerEntityToUpdate.setLastName(customerEntity.getLastName());
                    customerEntityToUpdate.setEmail(customerEntity.getEmail());

                    for (int i = 0; i < customerEntityToUpdate.getAddressEntities().size(); i++)
                    {
                        AddressEntity addressEntity = customerEntityToUpdate.getAddressEntities().get(i);
                        if (addressEntity.getAddressId() == null)
                        {
                            try
                            {
                                AddressEntity newAddressEntity = addressEntitySessionBeanLocal.createNewAddress(addressEntity);
                                customerEntityToUpdate.getAddressEntities().remove(i--);
                                customerEntityToUpdate.addAddressEntity(newAddressEntity);
                            }
                            catch (AddressExistException | UnknownPersistenceException ex)
                            {
                                throw new UpdateCustomerException(ex.getMessage());
                            }
                        }
                    }
                }
                else
                {
                    throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID not provided for customer to be updated");
        }
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException
    {

        CustomerEntity customerEntityToRemove = retrieveCustomerByCustomerId(customerId);

        for (CreditCardEntity creditCardEntity : customerEntityToRemove.getCreditCardEntities())
        {
            em.remove(creditCardEntity);
        }

        for (OrderEntity orderEntity : customerEntityToRemove.getOrderEntities())
        {

            for (OrderItemEntity orderItemEntity : orderEntity.getOrderItemEntities())
            {
                orderItemEntity.getSpecificationEntity().getOrderItemEntities().remove(orderItemEntity);
                em.remove(orderItemEntity.getReviewEntity());
                em.remove(orderItemEntity);
            }

            orderEntity.getOrderItemEntities().clear();
            em.remove(orderEntity);
        }

        customerEntityToRemove.getCreditCardEntities().clear();
        customerEntityToRemove.getOrderEntities().clear();

        em.remove(customerEntityToRemove);
    }

    @Override
    public CustomerEntity customerLogin(String username, String password) throws DisabledCustomerException, InvalidLoginCredentialException
    {
        try
        {
            CustomerEntity customerEntity = retrieveCustomerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + customerEntity.getSalt()));

            if (customerEntity.getPassword().equals(passwordHash))
            {
                if (customerEntity.getIsEnabled())
                {
                    return customerEntity;
                }
                else
                {
                    throw new DisabledCustomerException("Username is disabled");
                }
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch (CustomerNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void enableCustomerAccount(Long customerId) throws CustomerNotFoundException, EnableCustomerException
    {
        CustomerEntity customerEntityToEnable = retrieveCustomerByCustomerId(customerId);

        if (!customerEntityToEnable.getIsEnabled())
        {
            customerEntityToEnable.setIsEnabled(Boolean.TRUE);
        }
        else
        {
            throw new EnableCustomerException("Customer is already enabled");
        }
    }

    @Override
    public void disableCustomerAccount(Long customerId) throws CustomerNotFoundException, EnableCustomerException
    {
        CustomerEntity customerEntityToEnable = retrieveCustomerByCustomerId(customerId);

        if (customerEntityToEnable.getIsEnabled())
        {
            customerEntityToEnable.setIsEnabled(Boolean.FALSE);
        }
        else
        {
            throw new EnableCustomerException("Customer is already disabled");
        }
    }

    @Override
    public void customerChangePassword(String username, String oldPwd, String newPwd) throws changePasswordException, DisabledCustomerException, InvalidLoginCredentialException
    {

        CustomerEntity customerEntity = customerLogin(username, oldPwd);

        if (!newPwd.isEmpty() && newPwd != null)
        {
            customerEntity.setPassword(newPwd);
        }
        else
        {
            throw new changePasswordException("Password is not provided");
        }

    }
}
