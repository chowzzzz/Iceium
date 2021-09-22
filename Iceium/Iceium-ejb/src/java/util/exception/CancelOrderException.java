/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author hilary
 */
public class CancelOrderException extends Exception {

    public CancelOrderException() {
    }

    public CancelOrderException(String msg) {
        super(msg);
    }
}
