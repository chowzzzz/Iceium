/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;
import util.enumeration.SizeTypeEnum;

/**
 *
 * @author Theodoric
 */
@FacesConverter(value="sizeTypeEnumConverter", forClass = SizeTypeEnum.class)
public class SizeTypeEnumConverter extends EnumConverter
{

    public SizeTypeEnumConverter()
    {
        super(SizeTypeEnum.class);
    }
}
