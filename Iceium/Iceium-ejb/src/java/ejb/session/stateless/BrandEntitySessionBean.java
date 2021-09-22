/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BrandEntity;
import util.exception.BrandExistException;
import util.exception.BrandNotFoundException;
import util.exception.DeleteBrandException;
import util.exception.InputDataValidationException;
import java.util.List;
import java.util.Set;
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
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateBrandException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class BrandEntitySessionBean implements BrandEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BrandEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public BrandEntity createNewBrand(BrandEntity newBrandEntity) throws BrandExistException, UnknownPersistenceException, InputDataValidationException
    {

        try
        {
            if (newBrandEntity.getCode() == null)
            {
                BrandEntity lastBrandEntity = retrieveLastEntryBrand();
                String code;
                if (lastBrandEntity == null)
                {
                    code = "000";
                }
                else
                {
                    code = String.format("%1$" + 3 + "s", String.valueOf(Integer.parseInt(lastBrandEntity.getCode()) + 1)).replace(' ', '0');
                }
                newBrandEntity.setCode(code);
            }

            Set<ConstraintViolation<BrandEntity>> constraintViolations = validator.validate(newBrandEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newBrandEntity);
                em.flush();

                return newBrandEntity;
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
                    throw new BrandExistException();
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
    public List<BrandEntity> retrieveAllBrands()
    {
        Query query = em.createQuery("SELECT b FROM BrandEntity b ORDER BY b.name ASC");
        List<BrandEntity> brandEntities = query.getResultList();

        for (BrandEntity brandEntity : brandEntities)
        {
            brandEntity.getModelEntities().size();
        }

        return brandEntities;
    }

    @Override
    public List<String> retrieveAllBrandNamesWithProducts()
    {
        Query query = em.createQuery("SELECT DISTINCT m.brandEntity.name FROM ModelEntity m WHERE SIZE(m.productEntities) > 0 ORDER BY m.brandEntity.name ASC");
        List<String> brandNames = query.getResultList();

        return brandNames;
    }

    @Override
    public BrandEntity retrieveBrandByBrandId(Long brandId) throws BrandNotFoundException
    {
        BrandEntity brandEntity = em.find(BrandEntity.class, brandId);

        if (brandEntity != null)
        {
            brandEntity.getModelEntities();

            return brandEntity;
        }
        else
        {
            throw new BrandNotFoundException("Brand ID " + brandId + " does not exist!");
        }
    }

    @Override
    public BrandEntity retrieveBrandByName(String name) throws BrandNotFoundException
    {
        Query query = em.createQuery("SELECT b FROM BrandEntity b WHERE b.name = :inName");
        query.setParameter("inName", name);

        try
        {
            BrandEntity brandEntity = (BrandEntity) query.getSingleResult();
            brandEntity.getModelEntities();

            return brandEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new BrandNotFoundException("Brand " + name + " does not exist!");
        }
    }

    @Override
    public void updateBrand(BrandEntity brandEntity) throws UpdateBrandException, InputDataValidationException, BrandExistException, NoChangesMadeException
    {
        if (brandEntity != null)
        {
            Set<ConstraintViolation<BrandEntity>> constraintViolations = validator.validate(brandEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    BrandEntity brandEntityToUpdate = retrieveBrandByBrandId(brandEntity.getBrandId());

                    if (!isBrandExist(brandEntity.getName()))
                    {
                        brandEntityToUpdate.setName(brandEntity.getName());
                    }
                    else
                    {
                        if (brandEntityToUpdate.getName().equals(brandEntity.getName()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new BrandExistException();
                        }
                    }
                }
                catch (BrandNotFoundException ex)
                {
                    throw new UpdateBrandException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateBrandException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteBrand(Long brandId) throws DeleteBrandException
    {
        try
        {
            BrandEntity brandEntityToRemove = retrieveBrandByBrandId(brandId);

            if (brandEntityToRemove.getModelEntities().isEmpty())
            {
                em.remove(brandEntityToRemove);
            }
            else
            {
                throw new DeleteBrandException("Brand ID " + brandId + " is associated with existing model(s) and cannot be deleted!");
            }
        }
        catch (BrandNotFoundException ex)
        {
            throw new DeleteBrandException(ex.getMessage());
        }
    }

    private boolean isBrandExist(String name)
    {
        Query query = em.createQuery("SELECT COUNT(b) FROM BrandEntity b WHERE b.name = :inName");
        query.setParameter("inName", name);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private BrandEntity retrieveLastEntryBrand()
    {
        Query query = em.createQuery("SELECT b FROM BrandEntity b ORDER BY b.brandId DESC");

        try
        {
            List<BrandEntity> brandEntities = ((List<BrandEntity>) query.setMaxResults(1).getResultList());
            return brandEntities.get(0);
        }
        catch (ArrayIndexOutOfBoundsException | IllegalStateException ex)
        {
            return null;
        }
    }
}
