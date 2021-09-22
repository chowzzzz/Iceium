/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SizeEntity;
import util.exception.DeleteSizeException;
import util.exception.InputDataValidationException;
import util.exception.SizeExistException;
import util.exception.SizeNotFoundException;
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
import util.enumeration.SizeTypeEnum;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSizeException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class SizeEntitySessionBean implements SizeEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SizeEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public SizeEntity createNewSize(SizeEntity newSizeEntity) throws SizeExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            if (newSizeEntity.getCode() == null)
            {
                SizeEntity lastSizeEntity = retrieveLastEntrySize();
                String code;
                if (lastSizeEntity == null)
                {
                    code = "600";
                }
                else
                {
                    code = String.valueOf(Integer.parseInt(lastSizeEntity.getCode()) + 1);
                }
                newSizeEntity.setCode(code);
            }

            Set<ConstraintViolation<SizeEntity>> constraintViolations = validator.validate(newSizeEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newSizeEntity);
                em.flush();

                return newSizeEntity;
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
                    throw new SizeExistException();
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
    public List<SizeEntity> retrieveAllSizes()
    {
        Query query = em.createQuery("SELECT s FROM SizeEntity s ORDER BY s.sizeTypeEnum, s.size ASC");
        List<SizeEntity> sizeEntities = query.getResultList();

        for (SizeEntity sizeEntity : sizeEntities)
        {
            sizeEntity.getSpecificationEntities();
        }

        return sizeEntities;
    }

    @Override
    public List<SizeEntity> retrieveAllSizesWithProducts()
    {
        Query query = em.createQuery("SELECT DISTINCT s FROM SizeEntity s WHERE SIZE(s.specificationEntities) > 0 ORDER BY s.size ASC");
        List<SizeEntity> sizes = query.getResultList();

        return sizes;
    }

    @Override
    public SizeEntity retrieveSizeBySizeId(Long sizeId) throws SizeNotFoundException
    {
        SizeEntity sizeEntity = em.find(SizeEntity.class, sizeId);

        if (sizeEntity != null)
        {
            sizeEntity.getSpecificationEntities();

            return sizeEntity;
        }
        else
        {
            throw new SizeNotFoundException("Size ID " + sizeId + " does not exist!");
        }
    }

    @Override
    public SizeEntity retrieveSizeBySize(Double size, SizeTypeEnum sizeTypeEnum) throws SizeNotFoundException
    {
        Query query = em.createQuery("SELECT s FROM SizeEntity s WHERE s.size = :inSize AND s.sizeTypeEnum = :inSizeTypeEnum");
        query.setParameter("inSize", size);
        query.setParameter("inSizeTypeEnum", sizeTypeEnum);

        try
        {
            SizeEntity sizeEntity = (SizeEntity) query.getSingleResult();
            sizeEntity.getSpecificationEntities();

            return sizeEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new SizeNotFoundException("Size " + size + " does not exist!");
        }
    }

    @Override
    public void updateSize(SizeEntity sizeEntity) throws UpdateSizeException, InputDataValidationException, SizeExistException, NoChangesMadeException
    {
        if (sizeEntity != null)
        {
            Set<ConstraintViolation<SizeEntity>> constraintViolations = validator.validate(sizeEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    SizeEntity sizeEntityToUpdate = retrieveSizeBySizeId(sizeEntity.getSizeId());

                    if (!isSizeExist(sizeEntity.getSize(), sizeEntity.getSizeTypeEnum()))
                    {
                        sizeEntityToUpdate.setSize(sizeEntity.getSize());
                        sizeEntityToUpdate.setSizeTypeEnum(sizeEntity.getSizeTypeEnum());
                    }
                    else
                    {
                        if (sizeEntityToUpdate.getSize().equals(sizeEntity.getSize()) && sizeEntityToUpdate.getSizeTypeEnum().equals(sizeEntity.getSizeTypeEnum()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new SizeExistException();
                        }
                    }
                }
                catch (SizeNotFoundException ex)
                {
                    throw new UpdateSizeException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateSizeException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteSize(Long sizeId) throws DeleteSizeException
    {
        try
        {
            SizeEntity sizeEntityToRemove = retrieveSizeBySizeId(sizeId);

            if (sizeEntityToRemove.getSpecificationEntities().isEmpty())
            {
                em.remove(sizeEntityToRemove);
            }
            else
            {
                throw new DeleteSizeException("Size ID " + sizeId + " is associated with existing products(s) and cannot be deleted!");
            }

        }
        catch (SizeNotFoundException ex)
        {
            throw new DeleteSizeException(ex.getMessage());
        }
    }

    private boolean isSizeExist(double size, SizeTypeEnum sizeTypeEnum)
    {
        Query query = em.createQuery("SELECT COUNT(s) FROM SizeEntity s WHERE s.size = :inSize AND s.sizeTypeEnum = :inSizeTypeEnum");
        query.setParameter("inSize", size);
        query.setParameter("inSizeTypeEnum", sizeTypeEnum);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private SizeEntity retrieveLastEntrySize()
    {
        Query query = em.createQuery("SELECT s FROM SizeEntity s ORDER BY s.sizeId DESC");

        try
        {
            List<SizeEntity> sizeEntities = ((List<SizeEntity>) query.setMaxResults(1).getResultList());
            return sizeEntities.get(0);
        }
        catch (ArrayIndexOutOfBoundsException | IllegalStateException ex)
        {
            return null;
        }
    }
}
