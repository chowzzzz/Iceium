/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import entity.SizeEntity;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Theodoric
 */
@FacesConverter(forClass = SizeEntity.class)
public class SizeConverter implements Converter
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
            
            List<SizeEntity> sizeEntities = (List<SizeEntity>)context.getExternalContext().getSessionMap().get("SizeEntityConverter_sizeEntities");
            
            for(SizeEntity sizeEntity : sizeEntities)
            {
                if(sizeEntity.getSizeId().equals(objLong))
                {
                    return sizeEntity;
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

        if (value instanceof SizeEntity)
        {
            SizeEntity sizeEntity = (SizeEntity) value;

            try
            {
                return sizeEntity.getSizeId().toString();
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
