/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CreditCardEntity;
import entity.CustomerEntity;

/**
 *
 * @author MOK
 */
public class CreateCreditCardReq {
    private CreditCardEntity newCreditCardEntity;
    private CustomerEntity customerEntity;
    private Long expiryDate;

    public CreateCreditCardReq() {
    }

    public CreateCreditCardReq(CreditCardEntity newCreditCardEntity, CustomerEntity customerEntity, Long expiryDate) {
        this.newCreditCardEntity = newCreditCardEntity;
        this.customerEntity = customerEntity;
        this.expiryDate = expiryDate;
    }

    public CreditCardEntity getNewCreditCardEntity() {
        return newCreditCardEntity;
    }

    public void setNewCreditCardEntity(CreditCardEntity newCreditCardEntity) {
        this.newCreditCardEntity = newCreditCardEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Long getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    
    
}
