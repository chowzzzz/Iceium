/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AddressEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.AddressExistException;
import util.exception.AddressNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteAddressException;
import util.exception.InputDataValidationException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAddressException;

/**
 *
 * @author Theodoric
 */
@Local
public interface AddressEntitySessionBeanLocal
{

    public AddressEntity createNewAddress(AddressEntity newAddressEntity) throws AddressExistException, UnknownPersistenceException, InputDataValidationException;

    public List<AddressEntity> retrieveAllAddresses();
    
    public AddressEntity retrieveAddressByAddressId(Long addressId) throws AddressNotFoundException;

    public AddressEntity retrieveAddressByAddressAndPostalCode(String address, String postalCode) throws AddressNotFoundException;
    
    public void addAddressToCustomer(long addressId, long customerId) throws AddressNotFoundException, CustomerNotFoundException;

    public void deleteCustomerAddress(Long addressId, Long customerId) throws DeleteAddressException;

    public void updateAddress(AddressEntity addressEntity, Long customerId) throws UpdateAddressException, InputDataValidationException, AddressExistException, NoChangesMadeException;
}
