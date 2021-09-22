/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author Theodoric
 */
public enum PaymentMethodEnum
{
    CASH_ON_DELIVERY("Cash On Delivery"),
    CREDIT_CARD("Credit Card");

    private final String printName;

    PaymentMethodEnum(String printName)
    {
        this.printName = printName;
    }

    public String getPrintName()
    {
        return this.printName;
    }

}
