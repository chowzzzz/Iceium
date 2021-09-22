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
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import ejb.session.stateless.SizeEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.BrandEntity;
import entity.CategoryEntity;
import entity.ColorEntity;
import entity.ModelEntity;
import entity.ProductEntity;
import entity.SaleEntity;
import entity.SizeEntity;
import entity.SpecificationEntity;
import entity.TagEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import util.enumeration.SizeTypeEnum;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author Theodoric
 */
@Named(value = "productManagementManagedBean")
@ViewScoped
public class ProductManagementManagedBean implements Serializable
{

    @EJB(name = "SaleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "tagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "categoryEntitySessionBeanLocal")
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    @EJB(name = "sizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "colorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    @EJB(name = "modelEntitySessionBeanLocal")
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    @EJB(name = "brandEntitySessionBeanLocal")
    private BrandEntitySessionBeanLocal brandEntitySessionBeanLocal;

    @EJB(name = "productEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @Inject
    private ViewProductManagedBean viewProductManagedBean;

    private List<ProductEntity> productEntities;
    private List<ProductEntity> filteredProductEntities;
    private Map<Long, Double> productRatings;

    private List<String> brandNamesWithProducts;
    private List<String> modelNamesWithProducts;
    private List<ColorEntity> colorNamesWithProducts;
    private List<SizeEntity> sizesWithProducts;
    private List<BrandEntity> brandEntities;
    private List<ModelEntity> modelEntities;
    private List<ColorEntity> colorEntities;
    private List<SizeEntity> sizeEntities;
    private List<CategoryEntity> categoryEntities;
    private List<TagEntity> tagEntities;
    private List<SaleEntity> saleEntities;

    private ProductEntity newProductEntity;
    private Long brandIdNew;
    private Long modelIdNew;
    private Long categoryIdNew;
    private SaleEntity saleEntityNew;
    private BigDecimal discountedPriceNew;
    private List<Long> tagIdsNew;
    private Integer quantityOnHandNew;
    private Integer reorderQuantityNew;
    private ColorEntity colorNew;
    private SizeEntity sizeNew;
    private List<List<Object>> specificationList;

    private List<Object> specification;

    private ProductEntity selectedProductEntityToUpdate;
    private Long brandIdToUpdate;
    private Long modelIdToUpdate;
    private Long categoryIdToUpdate;
    private SaleEntity saleEntityToUpdate;
    private BigDecimal discountedPriceToUpdate;
    private List<Long> tagIdsToUpdate;

    public ProductManagementManagedBean()
    {
        newProductEntity = new ProductEntity();

        productEntities = new ArrayList<>();
        productRatings = new HashMap<>();

        brandNamesWithProducts = new ArrayList<>();
        modelNamesWithProducts = new ArrayList<>();
        colorNamesWithProducts = new ArrayList<>();
        sizesWithProducts = new ArrayList<>();
        brandEntities = new ArrayList<>();
        modelEntities = new ArrayList<>();
        colorEntities = new ArrayList<>();
        sizeEntities = new ArrayList<>();
        categoryEntities = new ArrayList<>();
        tagEntities = new ArrayList<>();

        initializeCreate();
    }

    public void initializeCreate()
    {
        newProductEntity = new ProductEntity();
        brandIdNew = null;
        modelIdNew = null;
        categoryIdNew = null;
        saleEntityNew = null;
        discountedPriceNew = null;
        tagIdsNew = new ArrayList<>();
        quantityOnHandNew = null;
        colorNew = null;
        sizeNew = null;
        specificationList = new ArrayList<>();

        modelEntities = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct()
    {
        productEntities = productEntitySessionBeanLocal.retrieveAllProducts();
        productRatings = productEntitySessionBeanLocal.retrieveAllProductRatings();

        brandNamesWithProducts = brandEntitySessionBeanLocal.retrieveAllBrandNamesWithProducts();
        modelNamesWithProducts = modelEntitySessionBeanLocal.retrieveAllModelNamesWithProducts();
        colorNamesWithProducts = colorEntitySessionBeanLocal.retrieveAllColorNamesWithProducts();
        sizesWithProducts = sizeEntitySessionBeanLocal.retrieveAllSizesWithProducts();
        brandEntities = brandEntitySessionBeanLocal.retrieveAllBrands();
        modelEntities = null;
        colorEntities = colorEntitySessionBeanLocal.retrieveAllColors();
        sizeEntities = sizeEntitySessionBeanLocal.retrieveAllSizes();
        categoryEntities = categoryEntitySessionBeanLocal.retrieveAllCategories();
        saleEntities = saleEntitySessionBeanLocal.retrieveAllSales();
        tagEntities = tagEntitySessionBeanLocal.retrieveAllTags();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BrandEntityConverter_brandEntities", brandEntities);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ModelEntityConverter_modelEntities", modelEntities);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ColorEntityConverter_colorEntities", colorEntities);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("SizeEntityConverter_sizeEntities", sizeEntities);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("SaleEntityConverter_saleEntities", saleEntities);
    }

    public void brandSelectionChange(SelectEvent event)
    {
        Long selectedBrandEntityId = (Long) event.getObject();

        if (!isBrandSelectionChange(selectedBrandEntityId))
        {
            modelEntities = null;
        }
    }

    private boolean isBrandSelectionChange(Long selectedBrandEntityId)
    {
        for (BrandEntity brandEntity : brandEntities)
        {
            if (brandEntity.getBrandId().equals(selectedBrandEntityId))
            {
                modelEntities = brandEntity.getModelEntities();
                return true;
            }
        }
        return false;
    }

    public void createProductUnitPriceValueChange(AjaxBehaviorEvent event)
    {
        discountedPriceNew = calculateDiscountPrice(newProductEntity, saleEntityNew);
    }

    public void createProductSaleSelectionChange(SelectEvent event)
    {
        discountedPriceNew = calculateDiscountPrice(newProductEntity, saleEntityNew);
    }

    public void updateProductUnitPriceValueChange(AjaxBehaviorEvent event)
    {
        discountedPriceToUpdate = calculateDiscountPrice(selectedProductEntityToUpdate, saleEntityToUpdate);
    }

    public void updateProductSaleSelectionChange(SelectEvent event)
    {
        discountedPriceToUpdate = calculateDiscountPrice(selectedProductEntityToUpdate, saleEntityToUpdate);
    }

    private BigDecimal calculateDiscountPrice(ProductEntity productEntity, SaleEntity saleEntity)
    {
        BigDecimal unitPrice = productEntity.getUnitPrice();
        if (saleEntity != null || unitPrice == null)
        {
            return unitPrice.subtract(unitPrice.multiply(saleEntity.getDiscountRate()));
        }
        else
        {
            return null;
        }
    }

    public void categorySelectItemFilterChange(SelectEvent event)
    {
        if (event.getObject() != null)
        {
            String categoryName = (String) event.getObject();

            try
            {
                productEntities = productEntitySessionBeanLocal.retrieveAllProductsByRootCategory(categoryName);
            }
            catch (CategoryNotFoundException ex)
            {

            }
        }
        else
        {
            productEntities = productEntitySessionBeanLocal.retrieveAllProducts();
        }
    }

    public void addSpecification(ActionEvent event)
    {
        if (!checkSpecificationExist())
        {
            List<Object> specification = new ArrayList<>();

            specification.add(quantityOnHandNew);
            specification.add(reorderQuantityNew);
            specification.add(colorNew);
            specification.add(sizeNew);

            specificationList.add(specification);
        }

        String colorName = ((ColorEntity) specificationList.get(specificationList.size() - 1).get(2)).getName();
        double size = ((SizeEntity) specificationList.get(specificationList.size() - 1).get(3)).getSize();
        int quantity = (Integer) specificationList.get(specificationList.size() - 1).get(0);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Specification of Color: " + colorName + " and Size: " + size + " of Quantity: " + quantity + " has been added", null));

        quantityOnHandNew = null;
        reorderQuantityNew = null;
        colorNew = null;
        sizeNew = null;
    }

    private boolean checkSpecificationExist()
    {
        for (List<Object> storedSpecification : specificationList)
        {
            ColorEntity colorEntityStored = (ColorEntity) storedSpecification.get(2);
            SizeEntity sizeEntityStored = (SizeEntity) storedSpecification.get(3);
            Integer quantityOnHandStored = (Integer) storedSpecification.get(0);

            if (colorNew.getColorId().equals(colorEntityStored.getColorId()) && sizeNew.getSizeId().equals(sizeEntityStored.getSizeId()))
            {
                storedSpecification.set(0, quantityOnHandStored + quantityOnHandNew);
                storedSpecification.set(1, reorderQuantityNew);
                return true;
            }
        }
        return false;
    }

    public void createNewProduct(ActionEvent event)
    {
        try
        {
            newProductEntity = productEntitySessionBeanLocal.createNewProduct(newProductEntity, modelIdNew, categoryIdNew, saleEntityNew == null ? null : saleEntityNew.getSaleId(), tagIdsNew, specificationList);
            Long newProductId = newProductEntity.getProductId();

            productEntities.add(newProductEntity);

            if (filteredProductEntities != null)
            {
                filteredProductEntities.add(newProductEntity);
            }

            productRatings = productEntitySessionBeanLocal.retrieveAllProductRatings();

            initializeCreate();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product created successfully (Product ID: " + newProductId + ")", null));
        }
        catch (CreateNewProductException | ProductSkuCodeExistException | UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new product: " + ex.getMessage(), null));
        }
    }

    public void doUpdateProduct(ActionEvent event)
    {
        selectedProductEntityToUpdate = (ProductEntity) event.getComponent().getAttributes().get("productEntityToUpdate");
        brandIdToUpdate = selectedProductEntityToUpdate.getModelEntity().getBrandEntity().getBrandId();
        modelIdToUpdate = selectedProductEntityToUpdate.getModelEntity().getModelId();
        categoryIdToUpdate = selectedProductEntityToUpdate.getCategoryEntity().getCategoryId();
        saleEntityToUpdate = selectedProductEntityToUpdate.getSaleEntity();

//        if (selectedProductEntityToUpdate.getSaleEntity() != null)
//        {
//            saleIdToUpdate = selectedProductEntityToUpdate.getSaleEntity().getSaleId();
//        }
//        else
//        {
//            saleIdToUpdate = null;
//        }
        discountedPriceToUpdate = selectedProductEntityToUpdate.getDiscountedPrice();

        tagIdsToUpdate = new ArrayList<>();
        for (TagEntity tagEntity : selectedProductEntityToUpdate.getTagEntities())
        {
            tagIdsToUpdate.add(tagEntity.getTagId());
        }

        specificationList = new ArrayList<>();

        modelEntities = selectedProductEntityToUpdate.getModelEntity().getBrandEntity().getModelEntities();
    }

    public void updateProduct()
    {
        try
        {
            selectedProductEntityToUpdate = productEntitySessionBeanLocal.updateProduct(selectedProductEntityToUpdate, modelIdToUpdate, categoryIdToUpdate, saleEntityToUpdate == null ? null : saleEntityToUpdate.getSaleId(), tagIdsToUpdate, specificationList);

            for (int i = 0; i < productEntities.size(); i++)
            {
                if (productEntities.get(i).getProductId().equals(selectedProductEntityToUpdate.getProductId()))
                {
                    productEntities.set(i, selectedProductEntityToUpdate);
                    break;
                }
            }

            if (filteredProductEntities != null)
            {
                for (int i = 0; i < filteredProductEntities.size(); i++)
                {
                    if (filteredProductEntities.get(i).getProductId().equals(selectedProductEntityToUpdate.getProductId()))
                    {
                        filteredProductEntities.set(i, selectedProductEntityToUpdate);
                        break;
                    }
                }
            }

            productRatings = productEntitySessionBeanLocal.retrieveAllProductRatings();

            specificationList = new ArrayList<>();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product updated successfully", null));
        }
        catch (UpdateProductException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteProduct(ActionEvent event)
    {
        try
        {
            ProductEntity productEntityToDelete = (ProductEntity) event.getComponent().getAttributes().get("productEntityToDelete");
            productEntitySessionBeanLocal.deleteProduct(productEntityToDelete.getProductId());

            productEntities.remove(productEntityToDelete);

            if (filteredProductEntities != null)
            {
                filteredProductEntities.remove(productEntityToDelete);
            }

            productRatings = productEntitySessionBeanLocal.retrieveAllProductRatings();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, productEntityToDelete.getName() + " deleted successfully", null));
        }
        catch (ProductNotFoundException | DeleteProductException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void updateSpecificationQuantity(AjaxBehaviorEvent event)
    {
        Integer quantityOnHand = ((Integer) event.getComponent().getAttributes().get("quantityOnHand"));

        if (quantityOnHand == 0)
        {
            deleteSpecification(((Integer) event.getComponent().getAttributes().get("index")).intValue());
        }
    }

    public void deleteSpecification(ActionEvent event)
    {
        deleteSpecification(((Integer) event.getComponent().getAttributes().get("index")).intValue());
    }

    public void deleteSpecification(int index)
    {
        SpecificationEntity specficiationEntityDeleted = selectedProductEntityToUpdate.getSpecificationEntities().remove(index);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Specification ID: " + specficiationEntityDeleted.getSpecificationId() + " deleted successfully", null));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Product to confirm deletion", null));
    }

    public void handleFileUpload(FileUploadEvent event)
    {
        try
        {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            newProductEntity.setImageLink(event.getFile().getFileName());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        }
        catch (IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public String getSizesString(ProductEntity productEntity)
    {
        return productEntity.getSpecificationEntities().isEmpty() ?
                "No Sizes Available" :
                productEntity.getSpecificationEntities()
                        .stream()
                        .map(s -> s.getSizeEntity())
                        .distinct()
                        .sorted((s1, s2) -> Double.valueOf(s1.getSize()).compareTo(Double.valueOf(s2.getSize())))
                        .map(s -> s.getSize() + (s.equals(SizeTypeEnum.YOUTH) ? SizeTypeEnum.YOUTH.getPrintName() : ""))
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .get();
    }

    public String getColorsString(ProductEntity productEntity)
    {
        return productEntity.getSpecificationEntities().isEmpty() ?
                "No Colors Available" :
                productEntity.getSpecificationEntities()
                        .stream()
                        .map(s -> s.getColorEntity().getName())
                        .distinct()
                        .sorted()
                        .reduce((c1, c2) -> c1 + ", " + c2)
                        .get();
    }

    public String getProductRating(ProductEntity productEntity)
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        Double productRating = this.productRatings.get(productEntity.getProductId());

        return productRating.equals(0.0) ? "No Product Ratings" : decimalFormat.format(productRating);
    }

    public ViewProductManagedBean getViewProductManagedBean()
    {
        return viewProductManagedBean;
    }

    public void setViewProductManagedBean(ViewProductManagedBean viewProductManagedBean)
    {
        this.viewProductManagedBean = viewProductManagedBean;
    }

    public List<ProductEntity> getProductEntities()
    {
        return productEntities;
    }

    public void setProductEntities(List<ProductEntity> productEntities)
    {
        this.productEntities = productEntities;
    }

    public List<ProductEntity> getFilteredProductEntities()
    {
        return filteredProductEntities;
    }

    public void setFilteredProductEntities(List<ProductEntity> filteredProductEntities)
    {
        this.filteredProductEntities = filteredProductEntities;
    }

    public Map<Long, Double> getProductRatings()
    {
        return productRatings;
    }

    public void setProductRatings(Map<Long, Double> productRatings)
    {
        this.productRatings = productRatings;
    }

    public List<String> getBrandNamesWithProducts()
    {
        return brandNamesWithProducts;
    }

    public void setBrandNamesWithProducts(List<String> brandNamesWithProducts)
    {
        this.brandNamesWithProducts = brandNamesWithProducts;
    }

    public List<String> getModelNamesWithProducts()
    {
        return modelNamesWithProducts;
    }

    public void setModelNamesWithProducts(List<String> modelNamesWithProducts)
    {
        this.modelNamesWithProducts = modelNamesWithProducts;
    }

    public List<ColorEntity> getColorNamesWithProducts()
    {
        return colorNamesWithProducts;
    }

    public void setColorNamesWithProducts(List<ColorEntity> colorNamesWithProducts)
    {
        this.colorNamesWithProducts = colorNamesWithProducts;
    }

    public List<SizeEntity> getSizesWithProducts()
    {
        return sizesWithProducts;
    }

    public void setSizesWithProducts(List<SizeEntity> sizesWithProducts)
    {
        this.sizesWithProducts = sizesWithProducts;
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

    public List<ColorEntity> getColorEntities()
    {
        return colorEntities;
    }

    public void setColorEntities(List<ColorEntity> colorEntities)
    {
        this.colorEntities = colorEntities;
    }

    public List<SizeEntity> getSizeEntities()
    {
        return sizeEntities;
    }

    public void setSizeEntities(List<SizeEntity> sizeEntities)
    {
        this.sizeEntities = sizeEntities;
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

    public List<SaleEntity> getSaleEntities()
    {
        return saleEntities;
    }

    public void setSaleEntities(List<SaleEntity> saleEntities)
    {
        this.saleEntities = saleEntities;
    }

    public ProductEntity getNewProductEntity()
    {
        return newProductEntity;
    }

    public void setNewProductEntity(ProductEntity newProductEntity)
    {
        this.newProductEntity = newProductEntity;
    }

    public Long getBrandIdNew()
    {
        return brandIdNew;
    }

    public void setBrandIdNew(Long brandIdNew)
    {
        this.brandIdNew = brandIdNew;
    }

    public Long getModelIdNew()
    {
        return modelIdNew;
    }

    public void setModelIdNew(Long modelIdNew)
    {
        this.modelIdNew = modelIdNew;
    }

    public Long getCategoryIdNew()
    {
        return categoryIdNew;
    }

    public void setCategoryIdNew(Long categoryIdNew)
    {
        this.categoryIdNew = categoryIdNew;
    }

    public SaleEntity getSaleEntityNew()
    {
        return saleEntityNew;
    }

    public void setSaleEntityNew(SaleEntity saleEntityNew)
    {
        this.saleEntityNew = saleEntityNew;
    }

    public BigDecimal getDiscountedPriceNew()
    {
        return discountedPriceNew;
    }

    public void setDiscountedPriceNew(BigDecimal discountedPriceNew)
    {
        this.discountedPriceNew = discountedPriceNew;
    }

    public List<Long> getTagIdsNew()
    {
        return tagIdsNew;
    }

    public void setTagIdsNew(List<Long> tagIdsNew)
    {
        this.tagIdsNew = tagIdsNew;
    }

    public Integer getQuantityOnHandNew()
    {
        return quantityOnHandNew;
    }

    public void setQuantityOnHandNew(Integer quantityOnHandNew)
    {
        this.quantityOnHandNew = quantityOnHandNew;
    }

    public Integer getReorderQuantityNew()
    {
        return reorderQuantityNew;
    }

    public void setReorderQuantityNew(Integer reorderQuantityNew)
    {
        this.reorderQuantityNew = reorderQuantityNew;
    }

    public ColorEntity getColorNew()
    {
        return colorNew;
    }

    public void setColorNew(ColorEntity colorNew)
    {
        this.colorNew = colorNew;
    }

    public SizeEntity getSizeNew()
    {
        return sizeNew;
    }

    public void setSizeNew(SizeEntity sizeNew)
    {
        this.sizeNew = sizeNew;
    }

    public List<List<Object>> getSpecificationList()
    {
        return specificationList;
    }

    public void setSpecificationList(List<List<Object>> specificationList)
    {
        this.specificationList = specificationList;
    }

    public List<Object> getSpecification()
    {
        return specification;
    }

    public void setSpecification(List<Object> specification)
    {
        this.specification = specification;
    }

    public ProductEntity getSelectedProductEntityToUpdate()
    {
        return selectedProductEntityToUpdate;
    }

    public void setSelectedProductEntityToUpdate(ProductEntity selectedProductEntityToUpdate)
    {
        this.selectedProductEntityToUpdate = selectedProductEntityToUpdate;
    }

    public Long getBrandIdToUpdate()
    {
        return brandIdToUpdate;
    }

    public void setBrandIdToUpdate(Long brandIdToUpdate)
    {
        this.brandIdToUpdate = brandIdToUpdate;
    }

    public Long getModelIdToUpdate()
    {
        return modelIdToUpdate;
    }

    public void setModelIdToUpdate(Long modelIdToUpdate)
    {
        this.modelIdToUpdate = modelIdToUpdate;
    }

    public Long getCategoryIdToUpdate()
    {
        return categoryIdToUpdate;
    }

    public void setCategoryIdToUpdate(Long categoryIdToUpdate)
    {
        this.categoryIdToUpdate = categoryIdToUpdate;
    }

    public SaleEntity getSaleEntityToUpdate()
    {
        return saleEntityToUpdate;
    }

    public void setSaleEntityToUpdate(SaleEntity saleEntityToUpdate)
    {
        this.saleEntityToUpdate = saleEntityToUpdate;
    }
//    public Long getSaleIdToUpdate()
//    {
//        return saleIdToUpdate;
//    }
//
//    public void setSaleIdToUpdate(Long saleIdToUpdate)
//    {
//        this.saleIdToUpdate = saleIdToUpdate;
//    }

    public BigDecimal getDiscountedPriceToUpdate()
    {
        return discountedPriceToUpdate;
    }

    public void setDiscountedPriceToUpdate(BigDecimal discountedPriceToUpdate)
    {
        this.discountedPriceToUpdate = discountedPriceToUpdate;
    }

    public List<Long> getTagIdsToUpdate()
    {
        return tagIdsToUpdate;
    }

    public void setTagIdsToUpdate(List<Long> tagIdsToUpdate)
    {
        this.tagIdsToUpdate = tagIdsToUpdate;
    }

}
