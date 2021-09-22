/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.StaffEntity;
import util.exception.InvalidLoginCredentialException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Theodoric
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean
{

    @EJB
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;

    private String username;
    private String password;

    public LoginManagedBean()
    {
    }

    @PostConstruct
    public void init()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public void login(ActionEvent event) throws IOException
    {
        try
        {
            StaffEntity currentStaffEntity = staffEntitySessionBeanLocal.staffLogin(username, password);

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            ec.getSession(true);
            ec.getSessionMap().put("isLogin", true);
            ec.getSessionMap().put("currentStaffEntity", currentStaffEntity);
            ec.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        }
        catch (InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential: " + ex.getMessage(), null));
        }
    }

    public void cancelLogin(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            ec.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void logout(ActionEvent event) throws IOException
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        ((HttpSession) ec.getSession(true)).invalidate();
        ec.redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml?faces-redirect=true");
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
