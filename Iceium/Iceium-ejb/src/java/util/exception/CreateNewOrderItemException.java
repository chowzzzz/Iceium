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
public class CreateNewOrderItemException extends Exception {

    
    public CreateNewOrderItemException() {
    }

    public CreateNewOrderItemException(String msg) {
        super(msg);
    }
}
