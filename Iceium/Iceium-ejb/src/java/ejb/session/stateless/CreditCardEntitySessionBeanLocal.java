/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCardEntity;
import entity.CustomerEntity;
import util.exception.CreateNewCreditCardException;
import util.exception.CreditCardNotFoundException;
import util.exception.CreditCardNumberExistException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCreditCardException;
import java.util.List;
import javax.ejb.Local;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Theodoric & hilary
 */
@Local
public interface CreditCardEntitySessionBeanLocal
{
    public Long createNewCreditCard(CreditCardEntity newCreditCardEntity, CustomerEntity customerEntity) throws CreateNewCreditCardException, CreditCardNumberExistException, UnknownPersistenceException, InputDataValidationException;

    public List<CreditCardEntity> retrieveAllCreditCards();
    
    public CreditCardEntity retrieveCreditCardByCreditCardId(Long staffId) throws CreditCardNotFoundException;
    
    public CreditCardEntity retrieveCreditCardByCreditCardNumber(String creditCardNumber) throws CreditCardNotFoundException;
    
    public void deleteCreditCard(Long creditCardId, Long customerId) throws CreditCardNotFoundException;
    
    public void updateCreditCard(CreditCardEntity creditCardEntity) throws CreditCardNotFoundException, InputDataValidationException, UpdateCreditCardException;
    
}
