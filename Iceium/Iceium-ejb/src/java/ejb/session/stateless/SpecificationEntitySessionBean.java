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
import util.exception.SpecificationInsufficientQuantityOnHandException;
import util.exception.SpecificationNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.ColorNotFoundException;
import util.exception.CreateNewSpecificationException;
import util.exception.ProductNotFoundException;
import util.exception.SizeNotFoundException;

/**
 *
 * @author Theodoric
 */
@Stateless
public class SpecificationEntitySessionBean implements SpecificationEntitySessionBeanLocal {

    @EJB(name = "ProductEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "SizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "ColorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "Iceium-ejbPU")
    private EntityManager em;

    public SpecificationEntitySessionBean() {
    }

    @Override
    public SpecificationEntity createNewSpecification(SpecificationEntity newSpecification, ColorEntity color, SizeEntity size, ProductEntity product) throws CreateNewSpecificationException {
        try {
            em.persist(newSpecification);
            ColorEntity colorEntity = colorEntitySessionBeanLocal.retrieveColorByColorId(color.getColorId());
            SizeEntity sizeEntity = sizeEntitySessionBeanLocal.retrieveSizeBySizeId(size.getSizeId());
            ProductEntity productEntity = productEntitySessionBeanLocal.retrieveProductByProductId(product.getProductId());
            
            newSpecification.setColorEntity(colorEntity);
            newSpecification.setSizeEntity(sizeEntity);
            newSpecification.setProductEntity(productEntity);

            colorEntity.getSpecificationEntities().add(newSpecification);
            sizeEntity.getSpecificationEntities().add(newSpecification);
            productEntity.getSpecificationEntities().add(newSpecification);

            em.flush();

            return newSpecification;

        } catch (ColorNotFoundException | SizeNotFoundException | ProductNotFoundException ex) {
            throw new CreateNewSpecificationException("An error occured while creating new specification: " + ex.getMessage());
        }
    }

    @Override
    public List<SpecificationEntity> retrieveAllSpecifications() {
        Query query = em.createQuery("SELECT s FROM SpecificationEntity s ORDER BY s.specificationId ASC");
        List<SpecificationEntity> specificationEntities = query.getResultList();

        for (SpecificationEntity specificationEntity : specificationEntities) {
            specificationEntity.getProductEntity();
        }

        return specificationEntities;
    }

    @Override
    public SpecificationEntity retrieveSpecificationBySpecificationId(Long specificationId) throws SpecificationNotFoundException {
        SpecificationEntity specificationEntity = em.find(SpecificationEntity.class, specificationId);

        if (specificationEntity != null) {
            specificationEntity.getProductEntity();

            return specificationEntity;
        } else {
            throw new SpecificationNotFoundException("Specification ID " + specificationId + " does not exist!");
        }
    }

    @Override
    public void debitQuantityOnHand(Long specificationId, Integer quantityToDebit) throws SpecificationNotFoundException, SpecificationInsufficientQuantityOnHandException {
        SpecificationEntity specificationEntity = retrieveSpecificationBySpecificationId(specificationId);

        if (specificationEntity.getQuantityOnHand() >= quantityToDebit) {
            specificationEntity.setQuantityOnHand(specificationEntity.getQuantityOnHand() - quantityToDebit);
        } else {
            throw new SpecificationInsufficientQuantityOnHandException("Product " + specificationEntity.getProductEntity().getSkuCode() + " of specification color: " + specificationEntity.getColorEntity().getName() + ", size: " + specificationEntity.getSizeEntity().getSize() + " has quantity on hand is " + specificationEntity.getQuantityOnHand() + " versus quantity to debit of " + quantityToDebit);
        }
    }

    //    @Override
//    public void creditQuantityOnHand(Long productId, Integer quantityToCredit) throws ProductNotFoundException
//    {
//        ProductEntity productEntity = retrieveProductByProductId(productId);
//        productEntity.setQuantityOnHand(productEntity.getQuantityOnHand() + quantityToCredit);
//    }
}
