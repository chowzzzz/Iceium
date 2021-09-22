/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import util.enumeration.AccessRightEnum;

/**
 *
 * @author Theodoric
 */
@FacesConverter(value="accessRightEnumConverter", forClass = AccessRightEnum.class)
public class AccessRightEnumConverter extends EnumConverter
{

    public AccessRightEnumConverter()
    {
        super(AccessRightEnum.class);
    }
}
