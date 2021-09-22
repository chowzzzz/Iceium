/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.BrandEntitySessionBeanLocal;
import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.ColorEntitySessionBeanLocal;
import ejb.session.stateless.ModelEntitySessionBeanLocal;
import ejb.session.stateless.SizeEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.BrandEntity;
import entity.CategoryEntity;
import entity.ColorEntity;
import entity.ModelEntity;
import entity.SizeEntity;
import entity.TagEntity;
import util.exception.DeleteBrandException;
import util.exception.DeleteCategoryException;
import util.exception.DeleteModelException;
import util.exception.DeleteSizeException;
import util.exception.DeleteTagException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.enumeration.SizeTypeEnum;
import util.exception.BrandExistException;
import util.exception.BrandNotFoundException;
import util.exception.CategoryExistException;
import util.exception.CategoryNotFoundException;
import util.exception.ColorExistException;
import util.exception.ColorNotFoundException;
import util.exception.CreateNewCategoryException;
import util.exception.DeleteColorException;
import util.exception.InputDataValidationException;
import util.exception.ModelExistException;
import util.exception.ModelNotFoundException;
import util.exception.NoChangesMadeException;
import util.exception.SizeExistException;
import util.exception.SizeNotFoundException;
import util.exception.TagExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateBrandException;
import util.exception.UpdateCategoryException;
import util.exception.UpdateColorException;
import util.exception.UpdateModelException;
import util.exception.UpdateSizeException;
import util.exception.UpdateTagException;

/**
 *
 * @author Theodoric
 */
@Named(value = "miscellaneousManagedBean")
@ViewScoped
public class MiscellaneousManagedBean implements Serializable
{

    @EJB(name = "colorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    @EJB(name = "sizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "modelEntitySessionBeanLocal")
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    @EJB(name = "brandEntitySessionBeanLocal")
    private BrandEntitySessionBeanLocal brandEntitySessionBeanLocal;

    @EJB(name = "tagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "categoryEntitySessionBeanLocal")
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    private List<CategoryEntity> categoryEntities;
    private List<TagEntity> tagEntities;
    private List<BrandEntity> brandEntities;
    private List<ModelEntity> modelEntities;
    private List<SizeEntity> sizeEntities;
    private List<ColorEntity> colorEntities;

    private List<CategoryEntity> filteredCategoryEntities;
    private List<TagEntity> filteredTagEntities;
    private List<BrandEntity> filteredBrandEntities;
    private List<ModelEntity> filteredModelEntities;
    private List<SizeEntity> filteredSizeEntities;
    private List<ColorEntity> filteredColorEntities;

    private CategoryEntity newCategoryEntity;
    private TagEntity newTagEntity;
    private BrandEntity newBrandEntity;
    private ModelEntity newModelEntity;
    private SizeEntity newSizeEntity;
    private ColorEntity newColorEntity;

    private CategoryEntity selectedCategoryEntityToUpdate;
    private CategoryEntity selectedParentCategoryEntityToUpdate;
    private TagEntity selectedTagEntityToUpdate;
    private BrandEntity selectedBrandEntityToUpdate;
    private ModelEntity selectedModelEntityToUpdate;
    private SizeEntity selectedSizeEntityToUpdate;
    private ColorEntity selectedColorEntityToUpdate;

    public MiscellaneousManagedBean()
    {
        newCategoryEntity = new CategoryEntity();
        newTagEntity = new TagEntity();
        newBrandEntity = new BrandEntity();
        newSizeEntity = new SizeEntity();
        newColorEntity = new ColorEntity();
        newModelEntity = new ModelEntity();

        categoryEntities = new ArrayList<>();
        tagEntities = new ArrayList<>();
        brandEntities = new ArrayList<>();
        modelEntities = new ArrayList<>();
        sizeEntities = new ArrayList<>();
        colorEntities = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct()
    {
        categoryEntities = categoryEntitySessionBeanLocal.retrieveAllCategories();
        tagEntities = tagEntitySessionBeanLocal.retrieveAllTags();
        brandEntities = brandEntitySessionBeanLocal.retrieveAllBrands();
        modelEntities = modelEntitySessionBeanLocal.retrieveAllModels();
        sizeEntities = sizeEntitySessionBeanLocal.retrieveAllSizes();
        colorEntities = colorEntitySessionBeanLocal.retrieveAllColors();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("CategoryEntityConverter_categoryEntities", categoryEntities);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BrandEntityConverter_brandEntities", brandEntities);
    }

    /**
     * CREATE METHODS START
     */
    public void createNewCategory(ActionEvent event)
    {
        try
        {
            newCategoryEntity = categoryEntitySessionBeanLocal.createNewCategory(newCategoryEntity);

            categoryEntities.add(newCategoryEntity);

            if (filteredCategoryEntities != null)
            {
                filteredCategoryEntities.add(newCategoryEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New category created successfully (Category ID: " + newCategoryEntity.getCategoryId() + ")", null));

            newCategoryEntity = new CategoryEntity();
        }
        catch (CategoryExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Category name already exist", null));
        }
        catch (CreateNewCategoryException | UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new category: " + ex.getMessage(), null));
        }
    }

    public void createNewTag(ActionEvent event)
    {
        try
        {
            newTagEntity = tagEntitySessionBeanLocal.createNewTag(newTagEntity);

            tagEntities.add(newTagEntity);

            if (filteredTagEntities != null)
            {
                filteredTagEntities.add(newTagEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag created successfully (Tag ID: " + newTagEntity.getTagId() + ")", null));

            newTagEntity = new TagEntity();
        }
        catch (TagExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tag name already exist", null));
        }
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new tag: " + ex.getMessage(), null));
        }
    }

    public void createNewBrand(ActionEvent event)
    {
        try
        {
            newBrandEntity = brandEntitySessionBeanLocal.createNewBrand(newBrandEntity);

            brandEntities.add(newBrandEntity);

            if (filteredBrandEntities != null)
            {
                filteredBrandEntities.add(newBrandEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New brand created successfully (Brand ID: " + newBrandEntity.getBrandId() + ")", null));

            newBrandEntity = new BrandEntity();
        }
        catch (BrandExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Brand name already exist", null));
        }
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new brand: " + ex.getMessage(), null));
        }
    }

    public void createNewModel(ActionEvent event)
    {
        try
        {
            newModelEntity = modelEntitySessionBeanLocal.createNewModel(newModelEntity, newModelEntity.getBrandEntity().getBrandId());

            modelEntities.add(newModelEntity);

            if (filteredModelEntities != null)
            {
                filteredModelEntities.add(newModelEntity);
            }

            for (BrandEntity brandEntity : brandEntities)
            {
                if (brandEntity.getBrandId().equals(newModelEntity.getBrandEntity().getBrandId()))
                {
                    brandEntity.getModelEntities().add(newModelEntity);
                    break;
                }
            }

            if (filteredBrandEntities != null)
            {
                for (BrandEntity brandEntity : filteredBrandEntities)
                {
                    if (brandEntity.getBrandId().equals(newModelEntity.getBrandEntity().getBrandId()))
                    {
                        brandEntity.getModelEntities().add(newModelEntity);
                        break;
                    }
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New model created successfully (Model ID: " + newModelEntity.getModelId() + ")", null));

            newModelEntity = new ModelEntity();
        }
        catch (ModelExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Model name already exist", null));
        }
        catch (BrandNotFoundException | UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new model: " + ex.getMessage(), null));
        }
    }

    public void createNewSize(ActionEvent event)
    {
        try
        {
            newSizeEntity = sizeEntitySessionBeanLocal.createNewSize(newSizeEntity);

            sizeEntities.add(newSizeEntity);

            if (filteredSizeEntities != null)
            {
                filteredSizeEntities.add(newSizeEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New size created successfully (Size ID: " + newSizeEntity.getSizeId() + ")", null));

            newSizeEntity = new SizeEntity();
        }
        catch (SizeExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Size name already exist", null));
        }
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new size: " + ex.getMessage(), null));
        }
    }

    public void createNewColor(ActionEvent event)
    {
        try
        {
            newColorEntity = colorEntitySessionBeanLocal.createNewColor(newColorEntity);

            colorEntities.add(newColorEntity);

            if (filteredColorEntities != null)
            {
                filteredColorEntities.add(newColorEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New color created successfully (Color ID: " + newColorEntity.getColorId() + ")", null));

            newColorEntity = new ColorEntity();
        }
        catch (ColorExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Color name already exist", null));
        }
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new color: " + ex.getMessage(), null));
        }
    }

    /**
     * CREATE METHODS END
     */
    /**
     * UPDATE METHODS START
     */
    public void doUpdateCategory(ActionEvent event)
    {
        selectedCategoryEntityToUpdate = (CategoryEntity) event.getComponent().getAttributes().get("categoryEntityToUpdate");
        selectedParentCategoryEntityToUpdate = selectedCategoryEntityToUpdate.getParentCategoryEntity();
    }

    public void updateCategory(ActionEvent event)
    {
        try
        {
            selectedCategoryEntityToUpdate.setParentCategoryEntity(selectedParentCategoryEntityToUpdate);
            categoryEntitySessionBeanLocal.updateCategory(selectedCategoryEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (CategoryExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The name " + selectedCategoryEntityToUpdate.getName() + " already exists", null));
            try
            {
                selectedCategoryEntityToUpdate = categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(selectedCategoryEntityToUpdate.getCategoryId());
                for (int i = 0; i < categoryEntities.size(); i++)
                {
                    if (categoryEntities.get(i).getCategoryId().equals(selectedCategoryEntityToUpdate.getCategoryId()))
                    {
                        categoryEntities.set(i, selectedCategoryEntityToUpdate);
                        break;
                    }
                }
            }
            catch (CategoryNotFoundException ex2)
            {

            }
        }
        catch (UpdateCategoryException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating category: " + ex.getMessage(), null));
        }
    }

    public void doUpdateTag(ActionEvent event)
    {
        selectedTagEntityToUpdate = (TagEntity) event.getComponent().getAttributes().get("tagEntityToUpdate");
    }

    public void updateTag(ActionEvent event)
    {
        try
        {
            tagEntitySessionBeanLocal.updateTag(selectedTagEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (TagExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The name " + selectedTagEntityToUpdate.getName() + " already exists", null));
            try
            {
                selectedTagEntityToUpdate = tagEntitySessionBeanLocal.retrieveTagByTagId(selectedTagEntityToUpdate.getTagId());
                for (int i = 0; i < tagEntities.size(); i++)
                {
                    if (tagEntities.get(i).getTagId().equals(selectedTagEntityToUpdate.getTagId()))
                    {
                        tagEntities.set(i, selectedTagEntityToUpdate);
                        break;
                    }
                }
            }
            catch (TagNotFoundException ex2)
            {

            }
        }
        catch (UpdateTagException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating tag: " + ex.getMessage(), null));
        }
    }

    public void doUpdateBrand(ActionEvent event)
    {
        selectedBrandEntityToUpdate = (BrandEntity) event.getComponent().getAttributes().get("brandEntityToUpdate");
    }

    public void updateBrand(ActionEvent event)
    {
        try
        {
            brandEntitySessionBeanLocal.updateBrand(selectedBrandEntityToUpdate);

            for (int i = 0; i < modelEntities.size(); i++)
            {
                if (modelEntities.get(i).getBrandEntity().getBrandId().equals(selectedBrandEntityToUpdate.getBrandId()))
                {
                    modelEntities.get(i).setBrandEntity(selectedBrandEntityToUpdate);
                }
            }

            if (filteredModelEntities != null)
            {
                for (int i = 0; i < filteredModelEntities.size(); i++)
                {
                    if (filteredModelEntities.get(i).getBrandEntity().getBrandId().equals(selectedBrandEntityToUpdate.getBrandId()))
                    {
                        filteredModelEntities.get(i).setBrandEntity(selectedBrandEntityToUpdate);
                    }
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Brand updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (BrandExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The name " + selectedBrandEntityToUpdate.getName() + " already exists", null));
            try
            {
                selectedBrandEntityToUpdate = brandEntitySessionBeanLocal.retrieveBrandByBrandId(selectedBrandEntityToUpdate.getBrandId());
                for (int i = 0; i < brandEntities.size(); i++)
                {
                    if (brandEntities.get(i).getBrandId().equals(selectedBrandEntityToUpdate.getBrandId()))
                    {
                        brandEntities.set(i, selectedBrandEntityToUpdate);
                        break;
                    }
                }
            }
            catch (BrandNotFoundException ex2)
            {

            }
        }
        catch (UpdateBrandException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating brand: " + ex.getMessage(), null));
        }
    }

    public void doUpdateModel(ActionEvent event)
    {
        selectedModelEntityToUpdate = (ModelEntity) event.getComponent().getAttributes().get("modelEntityToUpdate");
    }

    public void updateModel(ActionEvent event)
    {
        try
        {
            modelEntitySessionBeanLocal.updateModel(selectedModelEntityToUpdate);

            boolean found = false;

            for (int i = 0; i < brandEntities.size(); i++)
            {
                if (brandEntities.get(i).getBrandId().equals(selectedModelEntityToUpdate.getBrandEntity().getBrandId()))
                {
                    for (int j = 0; j < brandEntities.get(i).getModelEntities().size(); j++)
                    {
                        if (brandEntities.get(i).getModelEntities().get(j).getModelId().equals(selectedModelEntityToUpdate.getModelId()))
                        {
                            brandEntities.get(i).getModelEntities().set(j, selectedModelEntityToUpdate);
                            found = true;
                            break;
                        }
                    }
                }
                if (found)
                {
                    break;
                }
            }

            if (filteredBrandEntities != null)
            {
                found = false;
                for (int i = 0; i < filteredBrandEntities.size(); i++)
                {
                    if (filteredBrandEntities.get(i).getBrandId().equals(selectedModelEntityToUpdate.getBrandEntity().getBrandId()))
                    {
                        for (int j = 0; j < filteredBrandEntities.get(i).getModelEntities().size(); j++)
                        {
                            if (filteredBrandEntities.get(i).getModelEntities().get(j).getModelId().equals(selectedModelEntityToUpdate.getModelId()))
                            {
                                filteredBrandEntities.get(i).getModelEntities().set(j, selectedModelEntityToUpdate);
                                found = true;
                                break;
                            }
                        }
                    }
                    if (found)
                    {
                        break;
                    }
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Model updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (ModelExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The name " + selectedModelEntityToUpdate.getName() + " for " + selectedModelEntityToUpdate.getBrandEntity().getName() + " already exists", null));
            try
            {
                selectedModelEntityToUpdate = modelEntitySessionBeanLocal.retrieveModelByModelId(selectedModelEntityToUpdate.getModelId());
                for (int i = 0; i < modelEntities.size(); i++)
                {
                    if (modelEntities.get(i).getModelId().equals(selectedModelEntityToUpdate.getModelId()))
                    {
                        modelEntities.set(i, selectedModelEntityToUpdate);
                        break;
                    }
                }
            }
            catch (ModelNotFoundException ex2)
            {

            }
        }
        catch (UpdateModelException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating model: " + ex.getMessage(), null));
        }
    }

    public void doUpdateSize(ActionEvent event)
    {
        selectedSizeEntityToUpdate = (SizeEntity) event.getComponent().getAttributes().get("sizeEntityToUpdate");
    }

    public void updateSize(ActionEvent event)
    {
        try
        {
            sizeEntitySessionBeanLocal.updateSize(selectedSizeEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Size updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (SizeExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The size " + selectedSizeEntityToUpdate.getSize() + " for " + selectedSizeEntityToUpdate.getSizeTypeEnum() + " already exists", null));
            try
            {
                selectedSizeEntityToUpdate = sizeEntitySessionBeanLocal.retrieveSizeBySizeId(selectedSizeEntityToUpdate.getSizeId());
                for (int i = 0; i < sizeEntities.size(); i++)
                {
                    if (sizeEntities.get(i).getSizeId().equals(selectedSizeEntityToUpdate.getSizeId()))
                    {
                        sizeEntities.set(i, selectedSizeEntityToUpdate);
                        break;
                    }
                }
            }
            catch (SizeNotFoundException ex2)
            {

            }
        }
        catch (UpdateSizeException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating size: " + ex.getMessage(), null));
        }
    }

    public void doUpdateColor(ActionEvent event)
    {
        selectedColorEntityToUpdate = (ColorEntity) event.getComponent().getAttributes().get("colorEntityToUpdate");
    }

    public void updateColor(ActionEvent event)
    {
        try
        {
            if (!selectedColorEntityToUpdate.getHex().startsWith("#"))
            {
                selectedColorEntityToUpdate.setHex("#" + selectedColorEntityToUpdate.getHex().toUpperCase());
            }
            colorEntitySessionBeanLocal.updateColor(selectedColorEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Color updated successfully", null));
        }
        catch (NoChangesMadeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No changes were made", null));
        }
        catch (ColorExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "The hex code " + selectedColorEntityToUpdate.getHex() + " or name " + selectedColorEntityToUpdate.getName() + " already exists", null));
            try
            {
                selectedColorEntityToUpdate = colorEntitySessionBeanLocal.retrieveColorByColorId(selectedColorEntityToUpdate.getColorId());
                for (int i = 0; i < colorEntities.size(); i++)
                {
                    if (colorEntities.get(i).getColorId().equals(selectedColorEntityToUpdate.getColorId()))
                    {
                        colorEntities.set(i, selectedColorEntityToUpdate);
                        break;
                    }
                }
            }
            catch (ColorNotFoundException ex2)
            {

            }
        }
        catch (UpdateColorException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating color: " + ex.getMessage(), null));
        }
    }

    /**
     * UPDATE METHODS END
     */
    /**
     * DELETE METHODS START
     */
    public void deleteCategory(ActionEvent event)
    {
        try
        {
            CategoryEntity categoryEntityToDelete = (CategoryEntity) event.getComponent().getAttributes().get("categoryEntityToDelete");
            categoryEntitySessionBeanLocal.deleteCategory(categoryEntityToDelete.getCategoryId());

            categoryEntities.remove(categoryEntityToDelete);

            if (filteredCategoryEntities != null)
            {
                filteredCategoryEntities.remove(categoryEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, categoryEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (DeleteCategoryException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting category: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteTag(ActionEvent event)
    {
        try
        {
            TagEntity tagEntityToDelete = (TagEntity) event.getComponent().getAttributes().get("tagEntityToDelete");
            tagEntitySessionBeanLocal.deleteTag(tagEntityToDelete.getTagId());

            tagEntities.remove(tagEntityToDelete);

            if (filteredTagEntities != null)
            {
                filteredTagEntities.remove(tagEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, tagEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (DeleteTagException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting tag: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteBrand(ActionEvent event)
    {
        try
        {
            BrandEntity brandEntityToDelete = (BrandEntity) event.getComponent().getAttributes().get("brandEntityToDelete");
            brandEntitySessionBeanLocal.deleteBrand(brandEntityToDelete.getBrandId());

            brandEntities.remove(brandEntityToDelete);

            if (filteredBrandEntities != null)
            {
                filteredBrandEntities.remove(brandEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, brandEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (DeleteBrandException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting brand: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteModel(ActionEvent event)
    {
        try
        {
            ModelEntity modelEntityToDelete = (ModelEntity) event.getComponent().getAttributes().get("modelEntityToDelete");
            modelEntitySessionBeanLocal.deleteModel(modelEntityToDelete.getModelId());

            modelEntities.remove(modelEntityToDelete);

            if (filteredModelEntities != null)
            {
                filteredModelEntities.remove(modelEntityToDelete);
            }

            removeModelFromBrand(getBrandFromBrandEntities(modelEntityToDelete), modelEntityToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, modelEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (DeleteModelException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting model: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    private BrandEntity getBrandFromBrandEntities(ModelEntity modelEntityToDelete)
    {
        for (BrandEntity brandEntity : brandEntities)
        {
            if (brandEntity.getBrandId().equals(modelEntityToDelete.getBrandEntity().getBrandId()))
            {
                return brandEntity;
            }
        }
        return null;
    }

    private void removeModelFromBrand(BrandEntity brandEntity, ModelEntity modelEntityToDelete)
    {
        if (brandEntity != null)
        {
            List<ModelEntity> modelEntities = brandEntity.getModelEntities();
            for (int i = 0; i < modelEntities.size(); i++)
            {
                if (modelEntities.get(i).getModelId().equals(modelEntityToDelete.getModelId()))
                {
                    modelEntities.remove(i);
                    break;
                }
            }
        }
    }

    public void deleteSize(ActionEvent event)
    {
        try
        {
            SizeEntity sizeEntityToDelete = (SizeEntity) event.getComponent().getAttributes().get("sizeEntityToDelete");
            sizeEntitySessionBeanLocal.deleteSize(sizeEntityToDelete.getSizeId());

            sizeEntities.remove(sizeEntityToDelete);

            if (filteredSizeEntities != null)
            {
                filteredSizeEntities.remove(sizeEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, sizeEntityToDelete.getSize() + " " + sizeEntityToDelete.getSizeTypeEnum().getPrintName() + " deleted successfully", null));
        }
        catch (DeleteSizeException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting size: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteColor(ActionEvent event)
    {
        try
        {
            ColorEntity colorEntityToDelete = (ColorEntity) event.getComponent().getAttributes().get("colorEntityToDelete");
            colorEntitySessionBeanLocal.deleteColor(colorEntityToDelete.getColorId());

            colorEntities.remove(colorEntityToDelete);

            if (filteredColorEntities != null)
            {
                filteredColorEntities.remove(colorEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, colorEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (DeleteColorException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting size: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    /**
     * DELETE METHODS END
     */
    /**
     * SPECIAL GETTERS START
     */
    public String getModelsString(BrandEntity brandEntity)
    {
        return brandEntity.getModelEntities().isEmpty() ?
                "No Models Available" :
                brandEntity.getModelEntities()
                        .stream()
                        .map(m -> m.getName())
                        .distinct()
                        .sorted()
                        .reduce((m1, m2) -> m1 + ", " + m2)
                        .get();
    }

    public SizeTypeEnum[] getSizeTypeEnums()
    {
        return SizeTypeEnum.values();
    }

    public List<CategoryEntity> getCategoryEntities()
    {
        return categoryEntities;
    }

    public void setCategoryEntities(List<CategoryEntity> categoryEntities)
    {
        this.categoryEntities = categoryEntities;
    }

    public List<TagEntity> getTagEntities()
    {
        return tagEntities;
    }

    public void setTagEntities(List<TagEntity> tagEntities)
    {
        this.tagEntities = tagEntities;
    }

    public List<BrandEntity> getBrandEntities()
    {
        return brandEntities;
    }

    public void setBrandEntities(List<BrandEntity> brandEntities)
    {
        this.brandEntities = brandEntities;
    }

    public List<ModelEntity> getModelEntities()
    {
        return modelEntities;
    }

    public void setModelEntities(List<ModelEntity> modelEntities)
    {
        this.modelEntities = modelEntities;
    }

    public List<SizeEntity> getSizeEntities()
    {
        return sizeEntities;
    }

    public void setSizeEntities(List<SizeEntity> sizeEntities)
    {
        this.sizeEntities = sizeEntities;
    }

    public List<ColorEntity> getColorEntities()
    {
        return colorEntities;
    }

    public void setColorEntities(List<ColorEntity> colorEntities)
    {
        this.colorEntities = colorEntities;
    }

    public List<CategoryEntity> getFilteredCategoryEntities()
    {
        return filteredCategoryEntities;
    }

    public void setFilteredCategoryEntities(List<CategoryEntity> filteredCategoryEntities)
    {
        this.filteredCategoryEntities = filteredCategoryEntities;
    }

    public List<TagEntity> getFilteredTagEntities()
    {
        return filteredTagEntities;
    }

    public void setFilteredTagEntities(List<TagEntity> filteredTagEntities)
    {
        this.filteredTagEntities = filteredTagEntities;
    }

    public List<BrandEntity> getFilteredBrandEntities()
    {
        return filteredBrandEntities;
    }

    public void setFilteredBrandEntities(List<BrandEntity> filteredBrandEntities)
    {
        this.filteredBrandEntities = filteredBrandEntities;
    }

    public List<ModelEntity> getFilteredModelEntities()
    {
        return filteredModelEntities;
    }

    public void setFilteredModelEntities(List<ModelEntity> filteredModelEntities)
    {
        this.filteredModelEntities = filteredModelEntities;
    }

    public List<SizeEntity> getFilteredSizeEntities()
    {
        return filteredSizeEntities;
    }

    public void setFilteredSizeEntities(List<SizeEntity> filteredSizeEntities)
    {
        this.filteredSizeEntities = filteredSizeEntities;
    }

    public List<ColorEntity> getFilteredColorEntities()
    {
        return filteredColorEntities;
    }

    public void setFilteredColorEntities(List<ColorEntity> filteredColorEntities)
    {
        this.filteredColorEntities = filteredColorEntities;
    }

    public CategoryEntity getNewCategoryEntity()
    {
        return newCategoryEntity;
    }

    public void setNewCategoryEntity(CategoryEntity newCategoryEntity)
    {
        this.newCategoryEntity = newCategoryEntity;
    }

    public TagEntity getNewTagEntity()
    {
        return newTagEntity;
    }

    public void setNewTagEntity(TagEntity newTagEntity)
    {
        this.newTagEntity = newTagEntity;
    }

    public BrandEntity getNewBrandEntity()
    {
        return newBrandEntity;
    }

    public void setNewBrandEntity(BrandEntity newBrandEntity)
    {
        this.newBrandEntity = newBrandEntity;
    }

    public ModelEntity getNewModelEntity()
    {
        return newModelEntity;
    }

    public void setNewModelEntity(ModelEntity newModelEntity)
    {
        this.newModelEntity = newModelEntity;
    }

    public SizeEntity getNewSizeEntity()
    {
        return newSizeEntity;
    }

    public void setNewSizeEntity(SizeEntity newSizeEntity)
    {
        this.newSizeEntity = newSizeEntity;
    }

    public ColorEntity getNewColorEntity()
    {
        return newColorEntity;
    }

    public void setNewColorEntity(ColorEntity newColorEntity)
    {
        this.newColorEntity = newColorEntity;
    }

    public CategoryEntity getSelectedCategoryEntityToUpdate()
    {
        return selectedCategoryEntityToUpdate;
    }

    public void setSelectedCategoryEntityToUpdate(CategoryEntity selectedCategoryEntityToUpdate)
    {
        this.selectedCategoryEntityToUpdate = selectedCategoryEntityToUpdate;
    }

    public CategoryEntity getSelectedParentCategoryEntityToUpdate()
    {
        return selectedParentCategoryEntityToUpdate;
    }

    public void setSelectedParentCategoryEntityToUpdate(CategoryEntity selectedParentCategoryEntityToUpdate)
    {
        this.selectedParentCategoryEntityToUpdate = selectedParentCategoryEntityToUpdate;
    }

    public TagEntity getSelectedTagEntityToUpdate()
    {
        return selectedTagEntityToUpdate;
    }

    public void setSelectedTagEntityToUpdate(TagEntity selectedTagEntityToUpdate)
    {
        this.selectedTagEntityToUpdate = selectedTagEntityToUpdate;
    }

    public BrandEntity getSelectedBrandEntityToUpdate()
    {
        return selectedBrandEntityToUpdate;
    }

    public void setSelectedBrandEntityToUpdate(BrandEntity selectedBrandEntityToUpdate)
    {
        this.selectedBrandEntityToUpdate = selectedBrandEntityToUpdate;
    }

    public ModelEntity getSelectedModelEntityToUpdate()
    {
        return selectedModelEntityToUpdate;
    }

    public void setSelectedModelEntityToUpdate(ModelEntity selectedModelEntityToUpdate)
    {
        this.selectedModelEntityToUpdate = selectedModelEntityToUpdate;
    }

    public SizeEntity getSelectedSizeEntityToUpdate()
    {
        return selectedSizeEntityToUpdate;
    }

    public void setSelectedSizeEntityToUpdate(SizeEntity selectedSizeEntityToUpdate)
    {
        this.selectedSizeEntityToUpdate = selectedSizeEntityToUpdate;
    }

    public ColorEntity getSelectedColorEntityToUpdate()
    {
        return selectedColorEntityToUpdate;
    }

    public void setSelectedColorEntityToUpdate(ColorEntity selectedColorEntityToUpdate)
    {
        this.selectedColorEntityToUpdate = selectedColorEntityToUpdate;
    }

    /**
     * SPECIAL GETTERS END
     */
    /**
     * GETTER/SETTERS START
     */
    /**
     * GETTER/SETTERS END
     */
}
