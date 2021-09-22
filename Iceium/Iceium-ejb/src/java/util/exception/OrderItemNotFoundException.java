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
public class OrderItemNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>OrderItemNotFoundException</code> without
     * detail message.
     */
    public OrderItemNotFoundException() {
    }

    /**
     * Constructs an instance of <code>OrderItemNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderItemNotFoundException(String msg) {
        super(msg);
    }
}
