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
public enum AccessRightEnum
{
    HEAD_MANAGER("Head Manager"),
    PRODUCT_MANAGER("Product Manager"),
    CUSTOMER_MANAGER("Customer Manager"),
    TRANSACTION_MANAGER("Transaction Manager");
    
    private final String printName;
    
    AccessRightEnum(String printName)
    {
        this.printName = printName;
    }
    
    public String getPrintName()
    {
        return this.printName;
    }
    
}
