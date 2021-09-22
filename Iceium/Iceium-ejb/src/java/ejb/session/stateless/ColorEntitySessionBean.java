/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ColorEntity;
import util.exception.ColorExistException;
import util.exception.ColorNotFoundException;
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
import util.exception.DeleteColorException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateColorException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class ColorEntitySessionBean implements ColorEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ColorEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public ColorEntity createNewColor(ColorEntity newColorEntity) throws ColorExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            if (newColorEntity.getCode() == null)
            {
                ColorEntity lastColorEntity = retrieveLastEntryColor();
                String code;
                if (lastColorEntity == null)
                {
                    code = "1000";
                }
                else
                {
                    code = String.valueOf(Integer.parseInt(lastColorEntity.getCode()) + 1);
                }
                newColorEntity.setCode(code);
            }

            newColorEntity.setHex(newColorEntity.getHex().toUpperCase());
            if (!newColorEntity.getHex().contains("#"))
            {
                newColorEntity.setHex("#" + newColorEntity.getHex());
            }

            Set<ConstraintViolation<ColorEntity>> constraintViolations = validator.validate(newColorEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newColorEntity);
                em.flush();

                return newColorEntity;
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
                    throw new ColorExistException();
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
    public List<ColorEntity> retrieveAllColors()
    {
        Query query = em.createQuery("SELECT c FROM ColorEntity c ORDER BY c.colorId ASC");
        List<ColorEntity> colorEntities = query.getResultList();

        for (ColorEntity colorEntity : colorEntities)
        {
            colorEntity.getSpecificationEntities();
        }

        return colorEntities;
    }

    @Override
    public List<ColorEntity> retrieveAllColorNamesWithProducts()
    {
        Query query = em.createQuery("SELECT DISTINCT c FROM ColorEntity c WHERE SIZE(c.specificationEntities) > 0 ORDER BY c.name ASC");
        List<ColorEntity> colorEntities = query.getResultList();

        return colorEntities;
    }

    @Override
    public ColorEntity retrieveColorByColorId(Long colorId) throws ColorNotFoundException
    {
        ColorEntity colorEntity = em.find(ColorEntity.class, colorId);

        if (colorEntity != null)
        {
            colorEntity.getSpecificationEntities();

            return colorEntity;
        }
        else
        {
            throw new ColorNotFoundException("Color ID " + colorId + " does not exist!");
        }
    }

    @Override
    public ColorEntity retrieveColorByName(String name) throws ColorNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM ColorEntity c WHERE c.name = :inName");
        query.setParameter("inName", name);

        try
        {
            ColorEntity colorEntity = (ColorEntity) query.getSingleResult();
            colorEntity.getSpecificationEntities();

            return colorEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new ColorNotFoundException("Color " + name + " does not exist!");
        }
    }

    @Override
    public void updateColor(ColorEntity colorEntity) throws UpdateColorException, InputDataValidationException, ColorExistException, NoChangesMadeException
    {
        if (colorEntity != null)
        {
            Set<ConstraintViolation<ColorEntity>> constraintViolations = validator.validate(colorEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    ColorEntity colorEntityToUpdate = retrieveColorByColorId(colorEntity.getColorId());

                    if (!isColorHexExist(colorEntity.getHex()) && !isColorNameExist(colorEntity.getName()))
                    {
                        colorEntityToUpdate.setName(colorEntity.getName());
                        colorEntityToUpdate.setHex(colorEntity.getHex());
                    }
                    else
                    {
                        if (colorEntityToUpdate.getHex().equals(colorEntity.getHex()) && colorEntityToUpdate.getName().equals(colorEntity.getName()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new ColorExistException();
                        }
                    }
                }
                catch (ColorNotFoundException ex)
                {
                    throw new UpdateColorException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateColorException("An unknown error has occurred due to null elements");
        }

    }

    @Override
    public void deleteColor(Long colorId) throws DeleteColorException
    {
        try
        {
            ColorEntity colorEntityToRemove = retrieveColorByColorId(colorId);

            if (colorEntityToRemove.getSpecificationEntities().isEmpty())
            {
                em.remove(colorEntityToRemove);
            }
            else
            {
                throw new DeleteColorException("Color ID " + colorId + " is associated with existing products(s) and cannot be deleted!");
            }

        }
        catch (ColorNotFoundException ex)
        {
            throw new DeleteColorException(ex.getMessage());
        }
    }

    private boolean isColorHexExist(String hex)
    {
        Query query = em.createQuery("SELECT COUNT(c) FROM ColorEntity c WHERE c.hex = :inHex");
        query.setParameter("inHex", hex);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private boolean isColorNameExist(String name)
    {
        Query query = em.createQuery("SELECT COUNT(c) FROM ColorEntity c WHERE c.name = :inName");
        query.setParameter("inName", name);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private ColorEntity retrieveLastEntryColor()
    {
        Query query = em.createQuery("SELECT c FROM ColorEntity c ORDER BY c.colorId DESC");

        try
        {
            List<ColorEntity> colorEntities = ((List<ColorEntity>) query.setMaxResults(1).getResultList());
            return colorEntities.get(0);
        }
        catch (ArrayIndexOutOfBoundsException | IllegalStateException ex)
        {
            return null;
        }
    }

}
