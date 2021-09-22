/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.CustomerEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author MOK
 */
@Named(value = "customerManagementManagedBean")
@ViewScoped
public class CustomerManagementManagedBean implements Serializable
{

    @EJB
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    private List<CustomerEntity> customerEntities;
    private List<CustomerEntity> filteredCustomerEntities;

    private CustomerEntity customerEntityToUpdate;
    private List<AddressEntity> addressEntitiesToUpdate;

    private AddressEntity newAddressEntity;

    @Inject
    private ViewCustomerManagedBean viewCustomerManagedBean;

    public CustomerManagementManagedBean()
    {
        customerEntities = new ArrayList<>();
        newAddressEntity = new AddressEntity();
    }

    @PostConstruct
    public void postConstruct()
    {
        customerEntities = customerEntitySessionBeanLocal.retrieveAllCustomers();
    }

    public void doUpdateCustomer(ActionEvent event)
    {
        customerEntityToUpdate = (CustomerEntity) event.getComponent().getAttributes().get("customerEntityToUpdate");
        addressEntitiesToUpdate = new ArrayList<>(customerEntityToUpdate.getAddressEntities());
    }

    public void addAddress(ActionEvent event)
    {
        addressEntitiesToUpdate.add(newAddressEntity);
        newAddressEntity = new AddressEntity();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Address added", null));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Customer to save changes", null));
    }

    public void deleteAddress(ActionEvent event)
    {
        Integer index = (Integer) event.getComponent().getAttributes().get("addressRow");
        if (addressEntitiesToUpdate.remove(index.intValue()) != null)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Address S/N " + (index + 1) + " removed", null));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update Customer to save changes", null));
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: Address cannot be removed", null));
        }
    }

    public void updateCustomer(ActionEvent event)
    {
        try
        {
            if (addressEntitiesToUpdate != null)
            {
                customerEntityToUpdate.setAddressEntities(addressEntitiesToUpdate);
            }
            customerEntityToUpdate = (CustomerEntity) event.getComponent().getAttributes().get("customerEntityToUpdate");
            customerEntitySessionBeanLocal.updateCustomer(customerEntityToUpdate);

            addressEntitiesToUpdate = customerEntityToUpdate.getAddressEntities();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer updated successfully", null));
        }
        catch (CustomerNotFoundException | InputDataValidationException | UpdateCustomerException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating customer: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteCustomer(ActionEvent event)
    {
        try
        {
            CustomerEntity customerEntityToDelete = (CustomerEntity) event.getComponent().getAttributes().get("customerEntityToDelete");
            customerEntitySessionBeanLocal.deleteCustomer(customerEntityToDelete.getCustomerId());

            customerEntities.remove(customerEntityToDelete);

            if (filteredCustomerEntities != null)
            {
                filteredCustomerEntities.remove(customerEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, customerEntityToDelete.getFullName() + " deleted successfully", null));
        }
        catch (CustomerNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting customer: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<CustomerEntity> getCustomerEntities()
    {
        return customerEntities;
    }

    public void setCustomerEntities(List<CustomerEntity> customerEntities)
    {
        this.customerEntities = customerEntities;
    }

    public List<CustomerEntity> getFilteredCustomerEntities()
    {
        return filteredCustomerEntities;
    }

    public void setFilteredCustomerEntities(List<CustomerEntity> filteredCustomerEntities)
    {
        this.filteredCustomerEntities = filteredCustomerEntities;
    }

    public CustomerEntity getCustomerEntityToUpdate()
    {
        return customerEntityToUpdate;
    }

    public void setCustomerEntityToUpdate(CustomerEntity customerEntityToUpdate)
    {
        this.customerEntityToUpdate = customerEntityToUpdate;
    }

    public List<AddressEntity> getAddressEntitiesToUpdate()
    {
        return addressEntitiesToUpdate;
    }

    public void setAddressEntitiesToUpdate(List<AddressEntity> addressEntitiesToUpdate)
    {
        this.addressEntitiesToUpdate = addressEntitiesToUpdate;
    }

    public AddressEntity getNewAddressEntity()
    {
        return newAddressEntity;
    }

    public void setNewAddressEntity(AddressEntity newAddressEntity)
    {
        this.newAddressEntity = newAddressEntity;
    }

    public ViewCustomerManagedBean getViewCustomerManagedBean()
    {
        return viewCustomerManagedBean;
    }

    public void setViewCustomerManagedBean(ViewCustomerManagedBean viewCustomerManagedBean)
    {
        this.viewCustomerManagedBean = viewCustomerManagedBean;
    }

}
