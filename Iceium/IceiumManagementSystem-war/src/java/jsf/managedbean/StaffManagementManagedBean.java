/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.StaffEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import util.enumeration.AccessRightEnum;
import util.exception.InputDataValidationException;
import util.exception.StaffNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Theodoric
 */
@Named(value = "staffManagementManagedBean")
@ViewScoped
public class StaffManagementManagedBean implements Serializable
{

    @EJB(name = "staffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;

    @Inject
    private ViewStaffManagedBean viewStaffManagedBean;

    private List<StaffEntity> staffEntities;
    private List<StaffEntity> filteredStaffEntities;

    private StaffEntity newStaffEntity;

    private StaffEntity selectedStaffEntityToUpdate;

    public StaffManagementManagedBean()
    {
        newStaffEntity = new StaffEntity();
        staffEntities = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct()
    {
        staffEntities = staffEntitySessionBeanLocal.retrieveAllStaffs();
    }

    public void createNewStaff(ActionEvent event)
    {
        try
        {
            newStaffEntity = staffEntitySessionBeanLocal.createNewStaff(newStaffEntity);

            staffEntities.add(newStaffEntity);

            if (filteredStaffEntities != null)
            {
                filteredStaffEntities.add(newStaffEntity);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New staff created successfully (Staff ID: " + newStaffEntity.getStaffId() + ")", null));

            newStaffEntity = new StaffEntity();
        }
        catch (StaffUsernameExistException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new staff: The staff username already exist", null));
        }
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new staff: " + ex.getMessage(), null));
        }
    }

    public void doUpdateStaff(ActionEvent event)
    {
        selectedStaffEntityToUpdate = (StaffEntity) event.getComponent().getAttributes().get("staffEntityToUpdate");
    }

    public void updateStaff(ActionEvent event)
    {
        try
        {
            staffEntitySessionBeanLocal.updateStaff(selectedStaffEntityToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Staff updated successfully", null));
        }
        catch (StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating staff: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteStaff(ActionEvent event)
    {
        try
        {
            StaffEntity staffEntityToDelete = (StaffEntity) event.getComponent().getAttributes().get("staffEntityToDelete");
            staffEntitySessionBeanLocal.deleteStaff(staffEntityToDelete.getStaffId());

            staffEntities.remove(staffEntityToDelete);

            if (filteredStaffEntities != null)
            {
                filteredStaffEntities.remove(staffEntityToDelete);
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, staffEntityToDelete.getFirstName() + " " + staffEntityToDelete.getLastName() + " deleted successfully", null));
        }
        catch (StaffNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting staff: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public ViewStaffManagedBean getViewStaffManagedBean()
    {
        return viewStaffManagedBean;
    }

    public void setViewStaffManagedBean(ViewStaffManagedBean viewStaffManagedBean)
    {
        this.viewStaffManagedBean = viewStaffManagedBean;
    }

    public List<StaffEntity> getStaffEntities()
    {
        return staffEntities;
    }

    public void setStaffEntities(List<StaffEntity> staffEntities)
    {
        this.staffEntities = staffEntities;
    }

    public List<StaffEntity> getFilteredStaffEntities()
    {
        return filteredStaffEntities;
    }

    public void setFilteredStaffEntities(List<StaffEntity> filteredStaffEntities)
    {
        this.filteredStaffEntities = filteredStaffEntities;
    }

    public StaffEntity getNewStaffEntity()
    {
        return newStaffEntity;
    }

    public void setNewStaffEntity(StaffEntity newStaffEntity)
    {
        this.newStaffEntity = newStaffEntity;
    }

    public StaffEntity getSelectedStaffEntityToUpdate()
    {
        return selectedStaffEntityToUpdate;
    }

    public void setSelectedStaffEntityToUpdate(StaffEntity selectedStaffEntityToUpdate)
    {
        this.selectedStaffEntityToUpdate = selectedStaffEntityToUpdate;
    }

    public AccessRightEnum[] getAccessRightEnums()
    {
        return AccessRightEnum.values();
    }

}
