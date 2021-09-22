/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.BrandEntity;
import util.exception.BrandExistException;
import util.exception.BrandNotFoundException;
import util.exception.DeleteBrandException;
import util.exception.InputDataValidationException;
import java.util.List;
import javax.ejb.Local;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateBrandException;

/**
 *
 * @author Theodoric
 */
@Local
public interface BrandEntitySessionBeanLocal
{

    public BrandEntity createNewBrand(BrandEntity newBrandEntity) throws BrandExistException, UnknownPersistenceException, InputDataValidationException;

    public List<BrandEntity> retrieveAllBrands();

    public List<String> retrieveAllBrandNamesWithProducts();

    public BrandEntity retrieveBrandByBrandId(Long brandId) throws BrandNotFoundException;

    public BrandEntity retrieveBrandByName(String name) throws BrandNotFoundException;

    public void updateBrand(BrandEntity brandEntity) throws UpdateBrandException, InputDataValidationException, BrandExistException, NoChangesMadeException;

    public void deleteBrand(Long brandId) throws DeleteBrandException;
}
