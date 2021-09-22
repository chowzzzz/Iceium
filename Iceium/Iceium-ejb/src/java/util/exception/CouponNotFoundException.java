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
public class CouponNotFoundException extends Exception
{

    public CouponNotFoundException()
    {
    }

    public CouponNotFoundException(String msg)
    {
        super(msg);
    }
    
}
