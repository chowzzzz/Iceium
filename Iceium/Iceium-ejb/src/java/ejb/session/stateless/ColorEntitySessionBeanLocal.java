/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ColorEntity;
import util.exception.ColorExistException;
import util.exception.ColorNotFoundException;
import util.exception.InputDataValidationException;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteColorException;
import util.exception.NoChangesMadeException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateColorException;

/**
 *
 * @author Theodoric
 */
@Local
public interface ColorEntitySessionBeanLocal
{

    public ColorEntity createNewColor(ColorEntity newColorEntity) throws ColorExistException, UnknownPersistenceException, InputDataValidationException;

    public List<ColorEntity> retrieveAllColors();

    public List<ColorEntity> retrieveAllColorNamesWithProducts();

    public ColorEntity retrieveColorByColorId(Long colorId) throws ColorNotFoundException;

    public ColorEntity retrieveColorByName(String name) throws ColorNotFoundException;
    
    public void updateColor(ColorEntity colorEntity) throws UpdateColorException, InputDataValidationException, ColorExistException, NoChangesMadeException;
    
    public void deleteColor(Long colorId) throws DeleteColorException;
}
