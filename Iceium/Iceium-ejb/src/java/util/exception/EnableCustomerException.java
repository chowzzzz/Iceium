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
public class EnableCustomerException extends Exception {

    /**
     * Creates a new instance of <code>EnableCustomerException</code> without
     * detail message.
     */
    public EnableCustomerException() {
    }

    /**
     * Constructs an instance of <code>EnableCustomerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EnableCustomerException(String msg) {
        super(msg);
    }
}
