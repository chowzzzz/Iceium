/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.CustomerEntity;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author MOK
 */
@Named(value = "viewCustomerManagedBean")
@ViewScoped
public class ViewCustomerManagedBean implements Serializable {

    private CustomerEntity customerEntityToView;
    
    public ViewCustomerManagedBean() {
    }

    public CustomerEntity getCustomerEntityToView() {
        return customerEntityToView;
    }

    public void setCustomerEntityToView(CustomerEntity customerEntityToView) {
        this.customerEntityToView = customerEntityToView;
    }
    
}
