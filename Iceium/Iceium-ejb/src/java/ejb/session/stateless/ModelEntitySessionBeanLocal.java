/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ModelEntity;
import util.exception.BrandNotFoundException;
import util.exception.DeleteModelException;
import util.exception.InputDataValidationException;
import util.exception.ModelExistException;
import util.exception.ModelNotFoundException;
import java.util.List;
import javax.ejb.Local;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateModelException;

/**
 *
 * @author Theodoric
 */
@Local
public interface ModelEntitySessionBeanLocal
{

    public ModelEntity createNewModel(ModelEntity newModelEntity, Long brandId) throws BrandNotFoundException, ModelExistException, UnknownPersistenceException, InputDataValidationException;

    public List<ModelEntity> retrieveAllModels();

    public List<String> retrieveAllModelNamesWithProducts();

    public ModelEntity retrieveModelByModelId(Long modelId) throws ModelNotFoundException;

    public ModelEntity retrieveModelByModelNameAndBrandName(String modelName, String brandName) throws ModelNotFoundException;

    public void updateModel(ModelEntity modelEntity) throws UpdateModelException, InputDataValidationException, ModelExistException, NoChangesMadeException;

    public void deleteModel(Long modelId) throws DeleteModelException;
}
