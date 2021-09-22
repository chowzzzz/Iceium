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
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCreditCardException;
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
import util.exception.UnknownPersistenceException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric & hilary
 */
@Stateless
public class CreditCardEntitySessionBean implements CreditCardEntitySessionBeanLocal
{

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CreditCardEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewCreditCard(CreditCardEntity newCreditCardEntity, CustomerEntity customerEntity) throws CreateNewCreditCardException, CreditCardNumberExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerEntity.getCustomerId());
            customerEntity.getCreditCardEntities().add(newCreditCardEntity);

            Set<ConstraintViolation<CreditCardEntity>> constraintViolations = validator.validate(newCreditCardEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newCreditCardEntity);
                em.flush();

                return newCreditCardEntity.getCreditCardId();
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        catch (PersistenceException ex)
        {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CreditCardNumberExistException();
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
        catch (CustomerNotFoundException ex)
        {
            throw new CreateNewCreditCardException(ex.getMessage());
        }

    }

    @Override
    public List<CreditCardEntity> retrieveAllCreditCards()
    {
        Query query = em.createQuery("SELECT cc FROM CreditCardEntity cc");

        return query.getResultList();
    }

    @Override
    public CreditCardEntity retrieveCreditCardByCreditCardId(Long staffId) throws CreditCardNotFoundException
    {
        CreditCardEntity creditCardEntity = em.find(CreditCardEntity.class, staffId);

        if (creditCardEntity != null)
        {
            return creditCardEntity;
        }
        else
        {
            throw new CreditCardNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public CreditCardEntity retrieveCreditCardByCreditCardNumber(String creditCardNumber) throws CreditCardNotFoundException
    {
        Query query = em.createQuery("SELECT cc FROM CreditCardEntity cc WHERE cc.creditCardNumber = :inCreditCardNumber");
        query.setParameter("inCreditCardNumber", creditCardNumber);

        try
        {
            return (CreditCardEntity) query.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CreditCardNotFoundException("Credit card number " + creditCardNumber + " does not exist!");
        }
    }

    @Override
    public void deleteCreditCard(Long creditCardId, Long customerId) throws CreditCardNotFoundException
    {
        try {
            CustomerEntity customerEntity = customerEntitySessionBeanLocal.retrieveCustomerByCustomerId(customerId);
            CreditCardEntity creditCardEntityToRemove = retrieveCreditCardByCreditCardId(creditCardId);

            customerEntity.getCreditCardEntities().remove(creditCardEntityToRemove);

            em.remove(creditCardEntityToRemove);
            em.flush();
            
        } catch (CustomerNotFoundException ex) {
            throw new CreditCardNotFoundException(ex.getMessage());
        }
    }

    @Override
    public void updateCreditCard(CreditCardEntity creditCardEntity) throws CreditCardNotFoundException, InputDataValidationException, UpdateCreditCardException
    {
        if (creditCardEntity != null && creditCardEntity.getCreditCardId() != null)
        {
            Set<ConstraintViolation<CreditCardEntity>> constraintViolations = validator.validate(creditCardEntity);

            if (constraintViolations.isEmpty())
            {
                CreditCardEntity creditCardEntityToUpdate = retrieveCreditCardByCreditCardId(creditCardEntity.getCreditCardId());

                if (creditCardEntityToUpdate.getCreditCardNumber().equals(creditCardEntity.getCreditCardNumber()))
                {
                    creditCardEntityToUpdate.setExpiryDate(creditCardEntity.getExpiryDate());
                    creditCardEntityToUpdate.setNameOnCard(creditCardEntity.getNameOnCard());
                    creditCardEntityToUpdate.setSecurityCode(creditCardEntity.getSecurityCode());
                }
                else
                {
                    throw new UpdateCreditCardException("Number of credit card record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new CreditCardNotFoundException("Credit Card ID not provided for credit card to be updated");
        }
    }

}
