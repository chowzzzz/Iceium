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
public class CreateMessageOfTheDayException extends Exception {

    /**
     * Creates a new instance of <code>CreateMessageOfTheDayException</code>
     * without detail message.
     */
    public CreateMessageOfTheDayException() {
    }

    /**
     * Constructs an instance of <code>CreateMessageOfTheDayException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateMessageOfTheDayException(String msg) {
        super(msg);
    }
}
