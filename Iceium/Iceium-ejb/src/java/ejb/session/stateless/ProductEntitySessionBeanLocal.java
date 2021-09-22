/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ColorEntity;
import entity.ProductEntity;
import entity.SizeEntity;
import entity.SpecificationEntity;
import util.exception.DeleteProductException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.UpdateProductException;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewProductException;
import util.exception.ProductSkuCodeExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Theodoric & hilary
 */
@Local
public interface ProductEntitySessionBeanLocal {

    public ProductEntity createNewProduct(ProductEntity newProductEntity, Long modelId, Long categoryId, Long saleId, List<Long> tagList, List<List<Object>> specificationList) throws CreateNewProductException, ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException;

    public List<ProductEntity> retrieveAllProducts();

    public List<ProductEntity> retrieveAllProductsByRootCategory(String categoryName) throws CategoryNotFoundException;

    public Map<Long, Double> retrieveAllProductRatings();

    public ProductEntity retrieveProductByProductId(Long productId) throws ProductNotFoundException;

    public Double retrieveProductRatingByProductId(Long productId) throws ProductNotFoundException;

    public ProductEntity retrieveProductBySpecificationId(Long specificationId) throws ProductNotFoundException;

    public ProductEntity retrieveProductByProductSkuCode(String skuCode) throws ProductNotFoundException;

    public void deleteProduct(Long productId) throws ProductNotFoundException, DeleteProductException;

    public ProductEntity updateProduct(ProductEntity productEntity, Long modelId, Long categoryId, Long saleId, List<Long> tagList, List<List<Object>> specificationList) throws UpdateProductException, InputDataValidationException;

    public List<ProductEntity> searchProductsByName(String searchString);

    public Long reorderProducts();

    public ProductEntity createNewCustomizeProduct(ProductEntity newProductEntity, Long modelId, SpecificationEntity specification, ColorEntity color, SizeEntity size) throws CreateNewProductException, ProductSkuCodeExistException, UnknownPersistenceException, InputDataValidationException;

}
