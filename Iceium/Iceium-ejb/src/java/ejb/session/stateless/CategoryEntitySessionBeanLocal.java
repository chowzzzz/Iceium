/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CategoryEntity;
import util.exception.CategoryNotFoundException;
import util.exception.DeleteCategoryException;
import util.exception.InputDataValidationException;
import java.util.List;
import javax.ejb.Local;
import util.exception.CategoryExistException;
import util.exception.CreateNewCategoryException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCategoryException;

/**
 *
 * @author Theodoric
 */
@Local
public interface CategoryEntitySessionBeanLocal
{

    public CategoryEntity createNewCategory(CategoryEntity newCategoryEntity) throws CreateNewCategoryException, CategoryExistException, UnknownPersistenceException, InputDataValidationException;

    public List<CategoryEntity> retrieveAllCategories();

    public List<CategoryEntity> retrieveAllRootCategories();

    public List<CategoryEntity> retrieveAllLeafCategories();

    public CategoryEntity retrieveCategoryByCategoryId(Long categoryId) throws CategoryNotFoundException;

    public CategoryEntity retrieveCategoryByName(String name) throws CategoryNotFoundException;
    
    public void updateCategory(CategoryEntity categoryEntity) throws UpdateCategoryException, InputDataValidationException, CategoryExistException, NoChangesMadeException;
    
    public void deleteCategory(Long categoryId) throws DeleteCategoryException;    
}
