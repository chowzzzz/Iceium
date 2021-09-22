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
import javax.ejb.Local;
import util.exception.CreateNewSpecificationException;

/**
 *
 * @author Theodoric
 */
@Local
public interface SpecificationEntitySessionBeanLocal
{

    public List<SpecificationEntity> retrieveAllSpecifications();

    public SpecificationEntity retrieveSpecificationBySpecificationId(Long specificationId) throws SpecificationNotFoundException;

    public void debitQuantityOnHand(Long specificationId, Integer quantityToDebit) throws SpecificationNotFoundException, SpecificationInsufficientQuantityOnHandException;

    public SpecificationEntity createNewSpecification(SpecificationEntity newSpecification, ColorEntity color, SizeEntity size, ProductEntity product) throws CreateNewSpecificationException;
}
