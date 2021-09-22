/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SaleEntity;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSaleException;
import util.exception.DeleteSaleException;
import util.exception.InputDataValidationException;
import util.exception.SaleExistException;
import util.exception.SaleNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSaleException;

/**
 *
 * @author Theodoric
 */
@Local
public interface SaleEntitySessionBeanLocal
{

    public SaleEntity createNewSale(SaleEntity newSaleEntity) throws CreateNewSaleException, SaleExistException, InputDataValidationException, UnknownPersistenceException;

    public List<SaleEntity> retrieveAllSales();

    public SaleEntity retrieveSaleBySaleId(Long saleId) throws SaleNotFoundException;

    public void updateSale(SaleEntity saleEntity) throws UpdateSaleException, InputDataValidationException;

    public void deleteSale(Long saleId) throws SaleNotFoundException, DeleteSaleException;
    
    public Long checkAllSalesNotClosed(LocalDateTime now);
}
