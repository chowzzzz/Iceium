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
public enum SizeTypeEnum
{
    NORMAL("N"),
    YOUTH("Y"),
    NONE("N/A");

    private final String printName;

    SizeTypeEnum(String printName)
    {
        this.printName = printName;
    }

    public String getPrintName()
    {
        return this.printName;
    }
}
