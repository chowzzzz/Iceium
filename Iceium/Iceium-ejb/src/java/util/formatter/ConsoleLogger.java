/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.formatter;

/**
 *
 * @author Theodoric
 */
public class ConsoleLogger
{

    public static void log(String message)
    {
        String prefix = "**********";

        String callerClass = Thread.currentThread().getStackTrace()[2].getClassName();

        String completeMessage = String.format("%10s %62s | %40s ", prefix, callerClass, message);

        completeMessage = padRightStars(completeMessage, 137);

        System.out.println(completeMessage);
    }

    private static String padRightStars(String inputString, int length)
    {
        if (inputString.length() >= length)
        {
            return inputString;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(inputString);
        while (sb.length() < length)
        {
            sb.append('*');
        }

        return sb.toString();
    }
}
