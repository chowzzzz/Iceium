/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CustomerEntity;

/**
 *
 * @author hilary
 */
public class UpdateProfileReq {

    private CustomerEntity currCustomer;
    private Long dateOfBirth;

    public UpdateProfileReq() {
    }

    public UpdateProfileReq(CustomerEntity currCustomer, Long dateOfBirth) {
        this.currCustomer = currCustomer;
        this.dateOfBirth = dateOfBirth;
    }

    public CustomerEntity getCurrCustomer() {
        return currCustomer;
    }

    public void setCurrCustomer(CustomerEntity currCustomer) {
        this.currCustomer = currCustomer;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
