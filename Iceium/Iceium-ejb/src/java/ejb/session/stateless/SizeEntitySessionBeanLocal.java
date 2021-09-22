/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SizeEntity;
import util.exception.DeleteSizeException;
import util.exception.InputDataValidationException;
import util.exception.SizeExistException;
import util.exception.SizeNotFoundException;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.SizeTypeEnum;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSizeException;

/**
 *
 * @author Theodoric
 */
@Local
public interface SizeEntitySessionBeanLocal
{
    public SizeEntity createNewSize(SizeEntity newSizeEntity) throws SizeExistException, UnknownPersistenceException, InputDataValidationException;
    
    public List<SizeEntity> retrieveAllSizes();
    
    public List<SizeEntity> retrieveAllSizesWithProducts();
    
    public SizeEntity retrieveSizeBySizeId(Long sizeId) throws SizeNotFoundException;
    
    public SizeEntity retrieveSizeBySize(Double size, SizeTypeEnum sizeTypeEnum) throws SizeNotFoundException;
    
    public void updateSize(SizeEntity sizeEntity) throws UpdateSizeException, InputDataValidationException, SizeExistException, NoChangesMadeException;
    
    public void deleteSize(Long sizeId) throws DeleteSizeException;
}
