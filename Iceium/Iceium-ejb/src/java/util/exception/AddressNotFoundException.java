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
public class AddressNotFoundException extends Exception
{

    public AddressNotFoundException()
    {
    }

    public AddressNotFoundException(String msg)
    {
        super(msg);
    }

}
