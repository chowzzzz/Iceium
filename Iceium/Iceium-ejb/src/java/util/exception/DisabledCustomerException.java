/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author MOK
 */
public class DisabledCustomerException extends Exception {

    /**
     * Creates a new instance of <code>DisabledCustomerException</code> without
     * detail message.
     */
    public DisabledCustomerException() {
    }

    /**
     * Constructs an instance of <code>DisabledCustomerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DisabledCustomerException(String msg) {
        super(msg);
    }
}
