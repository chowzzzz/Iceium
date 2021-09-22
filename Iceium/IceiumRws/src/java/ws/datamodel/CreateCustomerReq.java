/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CustomerEntity;

/**
 *
 * @author MOK
 */
public class CreateCustomerReq {
    
    private CustomerEntity newCustomer;
    private Long dateOfBirth;

    public CreateCustomerReq() {
    }

    public CreateCustomerReq(CustomerEntity newCustomer, Long dateOfBirth) {
        this.newCustomer = newCustomer;
        this.dateOfBirth = dateOfBirth;
    }

    public CustomerEntity getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(CustomerEntity newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
}
