/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Theodoric
 */
public class CreditCardNumberExistException extends Exception
{

    public CreditCardNumberExistException()
    {
    }

    public CreditCardNumberExistException(String msg)
    {
        super(msg);
    }
    
}
