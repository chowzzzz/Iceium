/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.validation;

import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author Theodoric
 */
public class InputDataValidation
{
    public static <E> String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<E>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation<E> constraintViolation : constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
