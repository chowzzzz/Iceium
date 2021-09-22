/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author hilary
 */
public class SpecificationInsufficientQuantityOnHandException extends Exception
{

    public SpecificationInsufficientQuantityOnHandException()
    {
    }

    public SpecificationInsufficientQuantityOnHandException(String msg)
    {
        super(msg);
    }
}
