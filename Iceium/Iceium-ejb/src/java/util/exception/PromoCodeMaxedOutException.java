/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author gohle
 */
public class PromoCodeMaxedOutException extends Exception {

    /**
     * Creates a new instance of <code>PromoCodeMaxedOutException</code> without
     * detail message.
     */
    public PromoCodeMaxedOutException() {
    }

    /**
     * Constructs an instance of <code>PromoCodeMaxedOutException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PromoCodeMaxedOutException(String msg) {
        super(msg);
    }
}
