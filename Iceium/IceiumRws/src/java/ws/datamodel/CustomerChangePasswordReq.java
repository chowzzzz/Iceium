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
public class CustomerChangePasswordReq {
    private String username;
    private String oldPwd;
    private String newPwd;

    public CustomerChangePasswordReq() {
    }

    public CustomerChangePasswordReq(String username, String oldPwd, String newPwd) {
        this.username = username;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
    
    
}
