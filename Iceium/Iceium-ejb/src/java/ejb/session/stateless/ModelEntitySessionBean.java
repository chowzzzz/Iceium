/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BrandEntity;
import entity.ModelEntity;
import util.exception.BrandNotFoundException;
import util.exception.DeleteModelException;
import util.exception.InputDataValidationException;
import util.exception.ModelExistException;
import util.exception.ModelNotFoundException;
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
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateModelException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric
 */
@Stateless
public class ModelEntitySessionBean implements ModelEntitySessionBeanLocal
{

    @EJB(name = "brandEntitySessionBeanLocal")
    private BrandEntitySessionBeanLocal brandEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ModelEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public ModelEntity createNewModel(ModelEntity newModelEntity, Long brandId) throws BrandNotFoundException, ModelExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {

            if (newModelEntity.getCode() == null)
            {
                ModelEntity lastModelEntity = retrieveLastEntryModel();
                String code;
                if (lastModelEntity == null)
                {
                    code = "200";
                }
                else
                {
                    code = String.valueOf(Integer.parseInt(lastModelEntity.getCode()) + 1);
                }
                newModelEntity.setCode(code);
            }

            BrandEntity brandEntity = brandEntitySessionBeanLocal.retrieveBrandByBrandId(brandId);
            newModelEntity.setBrandEntity(brandEntity);

            Set<ConstraintViolation<ModelEntity>> constraintViolations = validator.validate(newModelEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newModelEntity);
                em.flush();

                return newModelEntity;
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
                    throw new ModelExistException();
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
    public List<ModelEntity> retrieveAllModels()
    {
        Query query = em.createQuery("SELECT m FROM ModelEntity m ORDER BY m.name ASC");
        List<ModelEntity> modelEntities = query.getResultList();

        for (ModelEntity modelEntity : modelEntities)
        {
            modelEntity.getBrandEntity();
            modelEntity.getProductEntities().size();
        }

        return modelEntities;
    }

    @Override
    public List<String> retrieveAllModelNamesWithProducts()
    {
        Query query = em.createQuery("SELECT DISTINCT m.name FROM ModelEntity m WHERE SIZE(m.productEntities) > 0 ORDER BY m.name ASC");
        List<String> modelNames = query.getResultList();

        return modelNames;
    }

    @Override
    public ModelEntity retrieveModelByModelId(Long modelId) throws ModelNotFoundException
    {
        ModelEntity modelEntity = em.find(ModelEntity.class, modelId);

        if (modelEntity != null)
        {
            modelEntity.getBrandEntity();
            modelEntity.getProductEntities().size();

            return modelEntity;
        }
        else
        {
            throw new ModelNotFoundException("Model ID " + modelId + " does not exist!");
        }
    }

    @Override
    public ModelEntity retrieveModelByModelNameAndBrandName(String modelName, String brandName) throws ModelNotFoundException
    {
        Query query = em.createQuery("SELECT m FROM ModelEntity m WHERE m.name = :inModelName AND m.brandEntity.name = :inBrandName");
        query.setParameter("inModelName", modelName);
        query.setParameter("inBrandName", brandName);

        try
        {
            ModelEntity modelEntity = (ModelEntity) query.getSingleResult();
            modelEntity.getBrandEntity();
            modelEntity.getProductEntities().size();

            return modelEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new ModelNotFoundException("Model " + modelName + " under Brand " + brandName + " does not exist!");
        }
    }

    @Override
    public void updateModel(ModelEntity modelEntity) throws UpdateModelException, InputDataValidationException, ModelExistException, NoChangesMadeException
    {
        if (modelEntity != null)
        {
            Set<ConstraintViolation<ModelEntity>> constraintViolations = validator.validate(modelEntity);

            if (constraintViolations.isEmpty())
            {
                try
                {
                    ModelEntity modelEntityToUpdate = retrieveModelByModelId(modelEntity.getModelId());

                    if (!isModelExist(modelEntity.getName(), modelEntity.getBrandEntity().getBrandId()))
                    {
                        modelEntityToUpdate.setName(modelEntity.getName());

                        if (modelEntity.getBrandEntity() != null)
                        {
                            modelEntityToUpdate.removeBrandEntity();
                            BrandEntity brandEntityToUpdate = brandEntitySessionBeanLocal.retrieveBrandByBrandId(modelEntity.getBrandEntity().getBrandId());
                            modelEntityToUpdate.setBrandEntity(brandEntityToUpdate);
                        }
                    }
                    else
                    {
                        if (modelEntityToUpdate.getName().equals(modelEntity.getName()) && modelEntityToUpdate.getBrandEntity().getBrandId().equals(modelEntity.getBrandEntity().getBrandId()))
                        {
                            throw new NoChangesMadeException();
                        }
                        else
                        {
                            throw new ModelExistException();
                        }
                    }
                }
                catch (ModelNotFoundException | BrandNotFoundException ex)
                {
                    throw new UpdateModelException(ex.getMessage());
                }
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new UpdateModelException("An unknown error has occurred due to null elements");
        }
    }

    @Override
    public void deleteModel(Long modelId) throws DeleteModelException
    {
        try
        {
            ModelEntity modelEntityToRemove = retrieveModelByModelId(modelId);

            if (modelEntityToRemove.getProductEntities().isEmpty())
            {
                List<ModelEntity> modelEntities = modelEntityToRemove.getBrandEntity().getModelEntities();
                for (int i = 0; i < modelEntities.size(); i++)
                {
                    if (modelEntities.get(i).getModelId().equals(modelEntityToRemove.getModelId()))
                    {
                        modelEntities.remove(i);
                        break;
                    }
                }
                em.remove(modelEntityToRemove);
            }
            else
            {
                throw new DeleteModelException("Model ID " + modelId + " is associated with existing products(s) and cannot be deleted!");
            }
        }
        catch (ModelNotFoundException ex)
        {
            throw new DeleteModelException(ex.getMessage());
        }
    }

    private boolean isModelExist(String name, Long brandId)
    {
        Query query = em.createQuery("SELECT COUNT(m) FROM ModelEntity m WHERE m.name = :inName AND m.brandEntity.brandId = :inBrandId");
        query.setParameter("inName", name);
        query.setParameter("inBrandId", brandId);

        Long count = (Long) query.getSingleResult();

        return count.equals(0L) ? false : true;
    }

    private ModelEntity retrieveLastEntryModel()
    {
        Query query = em.createQuery("SELECT m FROM ModelEntity m ORDER BY m.modelId DESC");

        try
        {
            List<ModelEntity> modelEntities = ((List<ModelEntity>) query.setMaxResults(1).getResultList());
            return modelEntities.get(0);

        }
        catch (ArrayIndexOutOfBoundsException | IllegalStateException ex)
        {
            return null;
        }
    }

}
