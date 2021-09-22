/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.TagEntity;
import util.exception.DeleteTagException;
import util.exception.InputDataValidationException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
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
import util.exception.UpdateTagException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class TagEntitySessionBean implements TagEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public TagEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public TagEntity createNewTag(TagEntity newTagEntity) throws TagExistException, UnknownPersistenceException, InputDataValidationException
    {
        Set<ConstraintViolation<TagEntity>> constraintViolations = validator.validate(newTagEntity);

        if (constraintViolations.isEmpty())
        {
            try
            {
                em.persist(newTagEntity);
                em.flush();

                return newTagEntity;
            }
            catch (PersistenceException ex)
            {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new TagExistException();
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
    public List<TagEntity> retrieveAllTags()
    {
        Query query = em.createQuery("SELECT t FROM TagEntity t ORDER BY t.name ASC");
        List<TagEntity> tagEntities = query.getResultList();

        for (TagEntity tagEntity : tagEntities)
        {
            tagEntity.getProductEntities().size();
        }

        return tagEntities;
    }

    @Override
    public List<TagEntity> retrieveAllTagsWithProducts()
    {
        Query query = em.createQuery("SELECT t FROM TagEntity t WHERE SIZE(t.productEntities) > 0 ORDER BY t.name ASC");
        List<TagEntity> tagEntities = query.getResultList();

        for (TagEntity tagEntity : tagEntities)
        {
            tagEntity.getProductEntities().size();
        }

        return tagEntities;
    }

    @Override
    public TagEntity retrieveTagByTagId(Long tagId) throws TagNotFoundException
    {
        TagEntity tagEntity = em.find(TagEntity.class, tagId);

        if (tagEntity != null)
        {
            tagEntity.getProductEntities().size();
            return tagEntity;
        }
        else
        {
            throw new TagNotFoundException("Tag ID " + tagId + " does not exist!");
        }
    }

    @Override
    public TagEntity retrieveTagByName(String name) throws TagNotFoundException
    {
        Query query = em.createQuery("SELECT t FROM TagEntity t WHERE t.name = :inName");
        query.setParameter("inName", name);

        try
        {
            TagEntity tagEntity = (TagEntity) query.getSingleResult();
            tagEntity.getProductEntities().size();

            return tagEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new TagNotFoundException("Color " + name + " does not exist!");
        }
    }

    @Override
    public void updateTag(TagEntity tagEntity) throws UpdateTagException, InputDataValidationException, TagExistException, NoChangesMadeException
    {
        if (tagEntity != null)
        {
            Set<ConstraintViolation<TagEntity>> constraintViolations = validator.validate(tagEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    TagEntity tagEntityToUpdate = retrieveTagByTagId(tagEntity.getTagId());

                    if (!isTagExist(tagEntity.getName()))
                    {
                        tagEntityToUpdate.setName(tagEntity.getName());
                        tagEntityToUpdate.setDescription(tagEntity.getDescription());
                    }
                    else
                    {
                        if (tagEntityToUpdate.getName().equals(tagEntity.getName()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new TagExistException();
                        }
                    }
                }
                catch (TagNotFoundException ex)
                {
                    throw new UpdateTagException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateTagException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteTag(Long tagId) throws DeleteTagException
    {
        try
        {
            TagEntity tagEntityToRemove = retrieveTagByTagId(tagId);

            if (tagEntityToRemove.getProductEntities().isEmpty())
            {
                em.remove(tagEntityToRemove);
            }
            else
            {
                throw new DeleteTagException("Tag ID " + tagId + " is associated with existing products(s) and cannot be deleted!");
            }
        }
        catch (TagNotFoundException ex)
        {
            throw new DeleteTagException(ex.getMessage());
        }
    }

    private boolean isTagExist(String name)
    {
        Query query = em.createQuery("SELECT COUNT(t) FROM TagEntity t WHERE t.name = :inName");
        query.setParameter("inName", name);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }
}
