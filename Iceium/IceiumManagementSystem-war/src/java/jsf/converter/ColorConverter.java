/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import entity.ColorEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Theodoric
 */
@FacesConverter(forClass = ColorEntity.class)
public class ColorConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.length() == 0 || value.equals("null")) 
        {
            return null;
        }

        try
        {            
            Long objLong = Long.parseLong(value);
            
            List<ColorEntity> colorEntities = (List<ColorEntity>)context.getExternalContext().getSessionMap().get("ColorEntityConverter_colorEntities");
            
            for(ColorEntity colorEntity : colorEntities)
            {
                if(colorEntity.getColorId().equals(objLong))
                {
                    return colorEntity;
                }
            }
        }
        catch(Exception ex)
        {
            throw new IllegalArgumentException("Please select a valid value");
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null)
        {
            return "";
        }

        if (value instanceof String)
        {
            return "";
        }

        if (value instanceof ColorEntity)
        {
            ColorEntity colorEntity = (ColorEntity) value;

            try
            {
                return colorEntity.getColorId().toString();
            }
            catch (Exception ex)
            {
                throw new IllegalArgumentException("Invalid value");
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid value");
        }
    }

}
