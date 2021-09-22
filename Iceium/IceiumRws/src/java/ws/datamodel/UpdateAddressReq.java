/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.AddressEntity;


/**
 *
 * @author MOK
 */
public class UpdateAddressReq {
    private Long customerId;
    private AddressEntity addressEntity;

    public UpdateAddressReq() {
    }

    public UpdateAddressReq(Long customerId, AddressEntity addressEntity) {
        this.customerId = customerId;
        this.addressEntity = addressEntity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    
}
