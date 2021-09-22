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
public class CreateNewSpecificationException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewSpecificationException</code>
     * without detail message.
     */
    public CreateNewSpecificationException() {
    }

    /**
     * Constructs an instance of <code>CreateNewSpecificationException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewSpecificationException(String msg) {
        super(msg);
    }
}
