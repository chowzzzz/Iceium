/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CategoryEntity;
import entity.ColorEntity;
import entity.ModelEntity;
import entity.OrderItemEntity;
import entity.ProductEntity;
import entity.SaleEntity;
import entity.SizeEntity;
import entity.SpecificationEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import util.exception.CategoryNotFoundException;
import util.exception.ColorNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ModelNotFoundException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SizeNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UpdateProductException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import util.exception.CreateNewSpecificationException;
import util.exception.SaleNotFoundException;
import util.exception.UnknownPersistenceException;
import util.validation.InputDataValidation;

/**
 *
 * @author Theodoric & hilary
 */
@Stateless
public class ProductEntitySessionBean implements ProductEntitySessionBeanLocal
{

    @EJB(name = "SpecificationEntitySessionBeanLocal")
    private SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;

    @EJB(name = "saleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "tagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "modelEntitySessionBeanLocal")
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    @EJB(name = "colorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    @EJB(name = "sizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "categoryEntitySessionBeanLocal")
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProductEntitySessionBean()
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public ProductEntity createNewProduct(ProductEntity newProductEntity, Long modelId, Long categoryId, Long saleId, List<Long> tagList, List<List<Object>> specificationList) throws CreateNewProductException, ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            ModelEntity modelEntity = modelEntitySessionBeanLocal.retrieveModelByModelId(modelId);
            newProductEntity.setModelEntity(modelEntity);

            if (saleId != null)
            {
                SaleEntity saleEntity = saleEntitySessionBeanLocal.retrieveSaleBySaleId(saleId);
                newProductEntity.setSaleEntity(saleEntity);
            }

            for (Long tagId : tagList)
            {
                TagEntity tagEntity = tagEntitySessionBeanLocal.retrieveTagByTagId(tagId);
                newProductEntity.addTagEntity(tagEntity);
            }

            createSpecificationEntities(newProductEntity, specificationList);

            CategoryEntity categoryEntity = categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);
            newProductEntity.setCategoryEntity(categoryEntity);

            newProductEntity.generateSkuCode();

            Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newProductEntity);
                em.flush();
            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }

            return newProductEntity;
        }
        catch (SaleNotFoundException | TagNotFoundException | ModelNotFoundException | ColorNotFoundException | SizeNotFoundException | CategoryNotFoundException ex)
        {
            throw new CreateNewProductException(ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new ProductSkuCodeExistException();
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
    public ProductEntity createNewCustomizeProduct(ProductEntity newProductEntity, Long modelId, SpecificationEntity specification, ColorEntity color, SizeEntity size) throws CreateNewProductException, ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {

            CategoryEntity categoryEntity = categoryEntitySessionBeanLocal.retrieveCategoryByName("Customize");
            Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(newProductEntity);

            if (constraintViolations.isEmpty())
            {
                em.persist(newProductEntity);

                ModelEntity modelEntity = modelEntitySessionBeanLocal.retrieveModelByModelId(modelId);
                newProductEntity.setModelEntity(modelEntity);
                modelEntity.getProductEntities().add(newProductEntity);

//                System.out.println("CAT " + categoryEntity);
//                System.out.println("CAT ID " + categoryEntity.getCategoryId());
                newProductEntity.setCategoryEntity(categoryEntity);
                categoryEntity.getProductEntities().add(newProductEntity);

                newProductEntity.generateSkuCode();
                em.flush();

                specificationEntitySessionBeanLocal.createNewSpecification(specification, color, size, newProductEntity);

            }
            else
            {
                throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
            }

            return newProductEntity;
        }
        catch (ModelNotFoundException | CategoryNotFoundException | CreateNewSpecificationException ex)
        {
            throw new CreateNewProductException(ex.getMessage());
        }
        catch (PersistenceException ex)
        {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new ProductSkuCodeExistException();
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
    public List<ProductEntity> retrieveAllProducts()
    {
        Query query = em.createQuery("SELECT p FROM ProductEntity p ORDER BY p.skuCode ASC");
        List<ProductEntity> productEntities = query.getResultList();

        for (ProductEntity productEntity : productEntities)
        {
            productEntity.getCategoryEntity();
            productEntity.getTagEntities().size();
            productEntity.getModelEntity().getBrandEntity();
            productEntity.getSpecificationEntities().size();
            for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
            {
                specificationEntity.getProductEntity();
                specificationEntity.getColorEntity();
                specificationEntity.getSizeEntity();
                specificationEntity.getOrderItemEntities().size();

                for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                {
                    orderItemEntity.getOrderEntity();
                    orderItemEntity.getOrderEntity().getCouponEntity();
                    orderItemEntity.getOrderEntity().getCustomerEntity();
                    orderItemEntity.getOrderEntity().getCustomerEntity().getCreditCardEntities().size();
                    orderItemEntity.getOrderEntity().getCustomerEntity().getOrderEntities().size();

                    orderItemEntity.getReviewEntity();
                }
            }
        }

        return productEntities;
    }

    @Override
    public List<ProductEntity> retrieveAllProductsByRootCategory(String categoryName) throws CategoryNotFoundException
    {
        List<ProductEntity> productEntities = new ArrayList<>();
        CategoryEntity categoryEntity = categoryEntitySessionBeanLocal.retrieveCategoryByName(categoryName);

        if (categoryEntity.getSubCategoryEntities().isEmpty())
        {
            productEntities.addAll(categoryEntity.getProductEntities());
        }
        else
        {
            for (CategoryEntity subCategoryEntity : categoryEntity.getSubCategoryEntities())
            {
                addLeafCategoryProducts(productEntities, subCategoryEntity);
            }
        }

        return productEntities;
    }

    private void addLeafCategoryProducts(List<ProductEntity> productEntities, CategoryEntity categoryEntity)
    {
        productEntities.addAll(categoryEntity.getProductEntities());
        for (CategoryEntity subCategoryEntity : categoryEntity.getSubCategoryEntities())
        {
            addLeafCategoryProducts(productEntities, subCategoryEntity);
        }
    }

    @Override
    public List<ProductEntity> searchProductsByName(String searchString)
    {
        Query query = em.createQuery("SELECT p FROM ProductEntity p WHERE p.name LIKE :inSearchString ORDER BY p.skuCode ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<ProductEntity> productEntities = query.getResultList();

        for (ProductEntity productEntity : productEntities)
        {
            productEntity.getCategoryEntity();
            productEntity.getTagEntities().size();
        }

        return productEntities;
    }

    @Override
    public Map<Long, Double> retrieveAllProductRatings()
    {
        Map<Long, Double> productRatings = new HashMap<>();

        Query query = em.createQuery("SELECT p FROM ProductEntity p");
        List<ProductEntity> productEntities = query.getResultList();

        for (ProductEntity productEntity : productEntities)
        {
            Double productRating = retrieveProductRatingByProduct(productEntity);
            productRatings.put(productEntity.getProductId(), productRating);
        }

        return productRatings;
    }

    @Override
    public ProductEntity retrieveProductByProductId(Long productId) throws ProductNotFoundException
    {
        ProductEntity productEntity = em.find(ProductEntity.class, productId);

        if (productEntity != null)
        {
            productEntity.getCategoryEntity();
            productEntity.getTagEntities().size();

            return productEntity;
        }
        else
        {
            throw new ProductNotFoundException("Product ID " + productId + " does not exist!");
        }
    }

    @Override
    public ProductEntity retrieveProductBySpecificationId(Long specificationId) throws ProductNotFoundException
    {
        Query query = em.createQuery("SELECT s.productEntity FROM SpecificationEntity s WHERE s.specificationId = :inSpecificationId");
        query.setParameter("inSpecificationId", specificationId);

        ProductEntity productEntity = (ProductEntity) query.getSingleResult();

        if (productEntity != null)
        {
            productEntity.getCategoryEntity();
            productEntity.getTagEntities().size();

            return productEntity;
        }
        else
        {
            throw new ProductNotFoundException("Product for specification ID " + specificationId + " does not exist!");
        }
    }

    @Override
    public Double retrieveProductRatingByProductId(Long productId) throws ProductNotFoundException
    {
        ProductEntity productEntity = retrieveProductByProductId(productId);

        if (productEntity != null)
        {
            Double rating = 0.0;
            int count = 0;

            for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
            {
                for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
                {
                    rating += orderItemEntity.getReviewEntity().getRating();
                    count++;
                }
            }

            return count == 0 ? 0.0 : BigDecimal.valueOf(rating / count).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        }
        else
        {
            throw new ProductNotFoundException("Product ID " + productId + " does not exist!");
        }
    }

    @Override
    public ProductEntity retrieveProductByProductSkuCode(String skuCode) throws ProductNotFoundException
    {
        Query query = em.createQuery("SELECT p FROM ProductEntity p WHERE p.skuCode = :inSkuCode");
        query.setParameter("inSkuCode", skuCode);

        try
        {
            ProductEntity productEntity = (ProductEntity) query.getSingleResult();
            productEntity.getCategoryEntity();
            productEntity.getTagEntities().size();

            return productEntity;
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new ProductNotFoundException("Sku Code " + skuCode + " does not exist!");
        }
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException
    {
        ProductEntity productEntityToRemove = retrieveProductByProductId(productId);

        if (productEntityToRemove.getSpecificationEntities() != null)
        {
            for (SpecificationEntity specificationEntity : productEntityToRemove.getSpecificationEntities())
            {
                if (specificationEntity.getOrderItemEntities().isEmpty())
                {
                    em.remove(specificationEntity);
                }
                else
                {
                    throw new DeleteProductException("Product ID " + productId + " is associated with existing order item(s) and cannot be deleted!");
                }
            }
        }
    }

    @Override
    public ProductEntity updateProduct(ProductEntity productEntity, Long modelId, Long categoryId, Long saleId, List<Long> tagList, List<List<Object>> specificationList) throws UpdateProductException, InputDataValidationException
    {
        if (productEntity != null)
        {
            try
            {
                ProductEntity productEntityToUpdate = retrieveProductByProductId(productEntity.getProductId());

                productEntityToUpdate.setName(productEntity.getName());
                productEntityToUpdate.setDescription(productEntity.getDescription());

                if (modelId != null && !modelId.equals(productEntityToUpdate.getModelEntity().getModelId()))
                {
                    ModelEntity modelEntityToUpdate = modelEntitySessionBeanLocal.retrieveModelByModelId(modelId);
                    productEntityToUpdate.removeModelEntity();
                    productEntityToUpdate.setModelEntity(modelEntityToUpdate);
                }

                if (categoryId != null && !categoryId.equals(productEntityToUpdate.getCategoryEntity().getCategoryId()))
                {
                    CategoryEntity categoryEntityToUpdate = categoryEntitySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);
                    productEntityToUpdate.removeCategoryEntity();
                    productEntityToUpdate.setCategoryEntity(categoryEntityToUpdate);
                }

                if (tagList != null)
                {
                    for (int i = 0; i < productEntityToUpdate.getTagEntities().size(); i++)
                    {
                        TagEntity tagEntityToRemove = productEntityToUpdate.getTagEntities().get(i);
                        if (!tagList.contains(tagEntityToRemove.getTagId()))
                        {
                            productEntityToUpdate.removeTagEntity(tagEntityToRemove);
                            i--;
                        }
                    }
                    for (Long tagId : tagList)
                    {
                        TagEntity tagEntityToUpdate = tagEntitySessionBeanLocal.retrieveTagByTagId(tagId);
                        productEntityToUpdate.addTagEntity(tagEntityToUpdate);
                    }
                }

                if (saleId != null && (productEntityToUpdate.getSaleEntity() == null || !saleId.equals(productEntityToUpdate.getSaleEntity().getSaleId())))
                {
                    SaleEntity saleEntityToUpdate = saleEntitySessionBeanLocal.retrieveSaleBySaleId(saleId);
                    productEntityToUpdate.removeSaleEntity();
                    productEntityToUpdate.setSaleEntity(saleEntityToUpdate);
                }
                else
                {
                    productEntityToUpdate.removeSaleEntity();
                }

                boolean remove;
                for (int i = 0; i < productEntityToUpdate.getSpecificationEntities().size(); i++)
                {
                    remove = true;
                    for (int j = 0; j < productEntity.getSpecificationEntities().size(); j++)
                    {
                        SpecificationEntity specificationEntityToUpdate = productEntityToUpdate.getSpecificationEntities().get(i);
                        SpecificationEntity specificationEntity = productEntity.getSpecificationEntities().get(j);

                        if (specificationEntityToUpdate.getSpecificationId().equals(specificationEntity.getSpecificationId()))
                        {
                            specificationEntityToUpdate.setQuantityOnHand(specificationEntity.getQuantityOnHand());
                            specificationEntityToUpdate.setReorderQuantity(specificationEntity.getReorderQuantity());
                            remove = false;
                            break;
                        }
                    }

                    if (remove)
                    {
                        productEntityToUpdate.getSpecificationEntities().remove(i);
                        i--;
                    }
                }

                if (specificationList != null && !specificationList.isEmpty())
                {
                    createSpecificationEntities(productEntityToUpdate, specificationList);
                }

                productEntityToUpdate.generateSkuCode();

                Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(productEntityToUpdate);

                if (constraintViolations.isEmpty())
                {
                    return productEntityToUpdate;
                }
                else
                {
                    throw new InputDataValidationException(InputDataValidation.prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            }
            catch (ProductNotFoundException | ModelNotFoundException | CategoryNotFoundException | SaleNotFoundException | TagNotFoundException | ColorNotFoundException | SizeNotFoundException ex)
            {
                throw new UpdateProductException(ex.getMessage());
            }
        }
        else
        {
            throw new UpdateProductException("An unknown error has occurred due to null elements");
        }
    }

    private void createSpecificationEntities(ProductEntity productEntity, List<List<Object>> specificationList) throws ColorNotFoundException, SizeNotFoundException
    {
        for (int i = 0; i < specificationList.size(); i++)
        {
            Integer quantityOnHand = (Integer) specificationList.get(i).get(0);
            Integer reorderQuantity = (Integer) specificationList.get(i).get(1);
            Long colorId = ((ColorEntity) specificationList.get(i).get(2)).getColorId();
            Long sizeId = ((SizeEntity) specificationList.get(i).get(3)).getSizeId();

            SpecificationEntity specificationEntity = new SpecificationEntity(quantityOnHand, reorderQuantity);

            ColorEntity colorEntity = colorEntitySessionBeanLocal.retrieveColorByColorId(colorId);
            specificationEntity.setColorEntity(colorEntity);

            SizeEntity sizeEntity = sizeEntitySessionBeanLocal.retrieveSizeBySizeId(sizeId);
            specificationEntity.setSizeEntity(sizeEntity);

            specificationEntity.setProductEntity(productEntity);

            em.persist(specificationEntity);
        }
    }

    public Long reorderProducts()
    {
        Long totalReorderedProducts = 0L;

        Query query = em.createQuery("SELECT p FROM ProductEntity p");

        List<ProductEntity> productEntities = (List<ProductEntity>) query.getResultList();

        for (ProductEntity productEntity : productEntities)
        {
            for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
            {
                specificationEntity.setQuantityOnHand(specificationEntity.getQuantityOnHand() + specificationEntity.getReorderQuantity());
                totalReorderedProducts += specificationEntity.getReorderQuantity();
            }
        }

        return totalReorderedProducts;
    }

    private Double retrieveProductRatingByProduct(ProductEntity productEntity)
    {
        Double rating = 0.0;
        int count = 0;

        for (SpecificationEntity specificationEntity : productEntity.getSpecificationEntities())
        {
            for (OrderItemEntity orderItemEntity : specificationEntity.getOrderItemEntities())
            {
                if (orderItemEntity.getReviewEntity() != null) {
                    rating += orderItemEntity.getReviewEntity().getRating();
                    count++;
                }
            }
        }

        return count == 0 ? 0.0 : BigDecimal.valueOf(rating / count).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }
}
