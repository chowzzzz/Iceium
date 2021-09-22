/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CategoryEntity;
import entity.ProductEntity;
import util.exception.CategoryNotFoundException;
import util.exception.DeleteCategoryException;
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
import util.exception.CategoryExistException;
import util.exception.CreateNewCategoryException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCategoryException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class CategoryEntitySessionBean implements CategoryEntitySessionBeanLocal
{

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CategoryEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public CategoryEntity createNewCategory(CategoryEntity newCategoryEntity) throws CreateNewCategoryException, CategoryExistException, UnknownPersistenceException, InputDataValidationException
    {
        Set<ConstraintViolation<CategoryEntity>> constraintViolations = validator.validate(newCategoryEntity);

        if (constraintViolations.isEmpty())
        {
            try
            {
                if (newCategoryEntity.getParentCategoryEntity() != null)
                {
                    CategoryEntity parentCategoryEntity = retrieveCategoryByCategoryId(newCategoryEntity.getParentCategoryEntity().getCategoryId());
                    newCategoryEntity.setParentCategoryEntity(parentCategoryEntity);
                }
                em.persist(newCategoryEntity);
                em.flush();

                return newCategoryEntity;
            }
            catch (PersistenceException ex)
            {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
                {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                    {
                        throw new CategoryExistException();
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
            catch (CategoryNotFoundException ex)
            {
                throw new CreateNewCategoryException(ex.getMessage());
            }
        }
        else
        {
            throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<CategoryEntity> retrieveAllCategories()
    {
        Query query = em.createQuery("SELECT c FROM CategoryEntity c");
        List<CategoryEntity> categoryEntities = query.getResultList();

        for (CategoryEntity categoryEntity : categoryEntities)
        {
            categoryEntity.getParentCategoryEntity();
            categoryEntity.getSubCategoryEntities().size();
            categoryEntity.getProductEntities().size();
        }

        return categoryEntities;
    }

    @Override
    public List<CategoryEntity> retrieveAllRootCategories()
    {
        Query query = em.createQuery("SELECT c FROM CategoryEntity c WHERE c.parentCategoryEntity IS NULL ORDER BY c.name ASC");
        List<CategoryEntity> rootCategoryEntities = query.getResultList();

        for (CategoryEntity rootCategoryEntity : rootCategoryEntities)
        {
            lazilyLoadSubCategories(rootCategoryEntity);

            rootCategoryEntity.getProductEntities().size();
        }

        return rootCategoryEntities;
    }

    private void lazilyLoadSubCategories(CategoryEntity categoryEntity)
    {
        for (CategoryEntity ce : categoryEntity.getSubCategoryEntities())
        {
            lazilyLoadSubCategories(ce);
        }
    }

    @Override
    public List<CategoryEntity> retrieveAllLeafCategories()
    {
        Query query = em.createQuery("SELECT c FROM CategoryEntity c WHERE c.subCategoryEntities IS EMPTY ORDER BY c.name ASC");
        List<CategoryEntity> leafCategoryEntities = query.getResultList();

        for (CategoryEntity leafCategoryEntity : leafCategoryEntities)
        {
            leafCategoryEntity.getParentCategoryEntity();
            leafCategoryEntity.getProductEntities().size();
        }

        return leafCategoryEntities;
    }

    @Override
    public CategoryEntity retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException
    {
        CategoryEntity categoryEntity = em.find(CategoryEntity.class, categoryId);

        if (categoryEntity != null)
        {
            return categoryEntity;
        }
        else
        {
            throw new CategoryNotFoundException("Category ID " + categoryId + " does not exist!");
        }
    }

    @Override
    public CategoryEntity retrieveCategoryByName(String name) throws CategoryNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM CategoryEntity c WHERE c.name = :inName");
        query.setParameter("inName", name);

        try
        {
            return (CategoryEntity) query.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CategoryNotFoundException("Category Name " + name + " does not exist!");
        }
    }

    @Override
    public void updateCategory(CategoryEntity categoryEntity) throws UpdateCategoryException, InputDataValidationException, CategoryExistException, NoChangesMadeException
    {
        if (categoryEntity != null)
        {
            Set<ConstraintViolation<CategoryEntity>> constraintViolations = validator.validate(categoryEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    CategoryEntity categoryEntityToUpdate = retrieveCategoryByCategoryId(categoryEntity.getCategoryId());

                    if (!isCategoryExist(categoryEntity.getName()))
                    {
                        categoryEntityToUpdate.setName(categoryEntity.getName());
                        categoryEntityToUpdate.setDescription(categoryEntity.getDescription());

                        if (categoryEntity.getParentCategoryEntity() != null)
                        {
                            categoryEntityToUpdate.removeParentCategoryEntity();
                            CategoryEntity parentCategoryEntityToUpdate = retrieveCategoryByCategoryId(categoryEntity.getParentCategoryEntity().getCategoryId());
                            categoryEntityToUpdate.setParentCategoryEntity(parentCategoryEntityToUpdate);
                        }

                        for (ProductEntity productEntity : categoryEntityToUpdate.getProductEntities())
                        {
                            productEntity.generateSkuCode();
                        }
                    }
                    else
                    {
                        if (categoryEntityToUpdate.getName().equals(categoryEntity.getName()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new CategoryExistException();
                        }
                    }
                }
                catch (CategoryNotFoundException ex)
                {
                    throw new UpdateCategoryException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateCategoryException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteCategory(Long categoryId) throws DeleteCategoryException
    {
        try
        {
            CategoryEntity categoryEntityToRemove = retrieveCategoryByCategoryId(categoryId);

            if (categoryEntityToRemove.getProductEntities().isEmpty())
            {
                if (categoryEntityToRemove.getSubCategoryEntities().isEmpty())
                {
                    categoryEntityToRemove.setParentCategoryEntity(null);
                    em.remove(categoryEntityToRemove);
                }
                else
                {
                    throw new DeleteCategoryException("Category ID " + categoryId + " is a parent category and cannot be deleted!");
                }
            }
            else
            {
                throw new DeleteCategoryException("Category ID " + categoryId + " is associated with existing products(s) and cannot be deleted!");
            }
        }
        catch (CategoryNotFoundException ex)
        {
            throw new DeleteCategoryException(ex.getMessage());
        }
    }
    
    private boolean isCategoryExist(String name)
    {
        Query query = em.createQuery("SELECT COUNT(c) FROM CategoryEntity c WHERE c.name = :inName");
        query.setParameter("inName", name);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

}
