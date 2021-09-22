/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CustomerEntity;
import java.util.HashMap;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.EnableCustomerException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateCustomerException;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCustomerException;
import util.exception.DisabledCustomerException;
import util.exception.UnknownPersistenceException;
import util.exception.changePasswordException;

/**
 *
 * @author Theodoric
 */
@Local
public interface CustomerEntitySessionBeanLocal
{

    public Long createNewCustomer(CustomerEntity newCustomerEntity) throws CreateNewCustomerException, CustomerUsernameExistException, UnknownPersistenceException, InputDataValidationException;

    public List<CustomerEntity> retrieveAllCustomers();

    public HashMap<Integer, Integer> retrieveAllCustomerAges();
    
    public CustomerEntity retrieveCustomerByCustomerId(Long customerId) throws CustomerNotFoundException;

    public CustomerEntity retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public void updateCustomer(CustomerEntity customerEntity) throws CustomerNotFoundException, UpdateCustomerException, InputDataValidationException;

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    public CustomerEntity customerLogin(String username, String password) throws DisabledCustomerException, InvalidLoginCredentialException;

    public void enableCustomerAccount(Long customerId) throws CustomerNotFoundException, EnableCustomerException;

    public void disableCustomerAccount(Long customerId) throws CustomerNotFoundException, EnableCustomerException;

    public void customerChangePassword(String username, String oldPwd, String newPwd) throws changePasswordException, DisabledCustomerException, InvalidLoginCredentialException;

}
