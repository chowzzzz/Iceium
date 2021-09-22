/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ProductEntity;
import entity.SaleEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewSaleException;
import util.exception.DeleteSaleException;
import util.exception.InputDataValidationException;
import util.exception.SaleExistException;
import util.exception.SaleNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSaleException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class SaleEntitySessionBean implements SaleEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SaleEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public SaleEntity createNewSale(SaleEntity newSaleEntity) throws CreateNewSaleException, SaleExistException, InputDataValidationException, UnknownPersistenceException
    {
        try
        {
            if (newSaleEntity.getStartDateTime().isBefore(newSaleEntity.getEndDateTime()))
            {
                Set<ConstraintViolation<SaleEntity>> constraintViolations = validator.validate(newSaleEntity);

                if (constraintViolations.isEmpty())
                {
                    em.persist(newSaleEntity);
                    em.flush();

                    return newSaleEntity;
                }
                else
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            else
            {
                throw new CreateNewSaleException("Start date has to be before end date");
            }
        }
        catch (PersistenceException ex)
        {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new SaleExistException();
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

    @Override
    public List<SaleEntity> retrieveAllSales()
    {
        Query query = em.createQuery("SELECT s FROM SaleEntity s ORDER BY s.startDateTime ASC");
        List<SaleEntity> promotionEntities = query.getResultList();

        for (SaleEntity saleEntity : promotionEntities)
        {
            saleEntity.getProductEntities().size();
        }

        return promotionEntities;
    }

    @Override
    public SaleEntity retrieveSaleBySaleId(Long saleId) throws SaleNotFoundException
    {
        SaleEntity saleEntity = em.find(SaleEntity.class, saleId);

        if (saleEntity != null)
        {
            saleEntity.getProductEntities().size();
            return saleEntity;
        }
        else
        {
            throw new SaleNotFoundException("Sale ID " + saleId + " does not exist!");
        }
    }

    @Override
    public void updateSale(SaleEntity saleEntity) throws UpdateSaleException, InputDataValidationException
    {
        if (saleEntity != null)
        {
            try
            {
                SaleEntity saleEntityToUpdate = retrieveSaleBySaleId(saleEntity.getSaleId());

                saleEntityToUpdate.setDescription(saleEntity.getDescription());

                Set<ConstraintViolation<SaleEntity>> constraintViolations = validator.validate(saleEntityToUpdate);

                if (!constraintViolations.isEmpty())
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            catch (SaleNotFoundException ex)
            {
                throw new UpdateSaleException(ex.getMessage());
            }
        }
        else
        {
            throw new UpdateSaleException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteSale(Long saleId) throws SaleNotFoundException, DeleteSaleException
    {
        SaleEntity saleEntityToRemove = retrieveSaleBySaleId(saleId);

        if (saleEntityToRemove.getProductEntities() != null)
        {
            if (saleEntityToRemove.getProductEntities().isEmpty())
            {
                em.remove(saleEntityToRemove);
            }
            else
            {
                throw new DeleteSaleException("Sale ID " + saleId + " is associated with existing product(s) and cannot be deleted!");
            }
        }
    }
    
    @Override
    public Long checkAllSalesNotClosed(LocalDateTime now)
    {
        Query query = em.createQuery("SELECT s FROM SaleEntity s WHERE s.endDateTime <= :inDate");
        query.setParameter("inDate", now);

        List<SaleEntity> saleEntities = query.getResultList();

        long salesClosed = 0;

        for (SaleEntity saleEntity : saleEntities)
        {
            for (ProductEntity productEntity : saleEntity.getProductEntities())
            {
                productEntity.removeSaleEntity();
                salesClosed++;
            }
        }

        return salesClosed;
    }

}
