/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
@Table(uniqueConstraints =
{
    @UniqueConstraint(columnNames =
    {
        "address", "postalCode"
    })
})
public class AddressEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false, length = 256)
    @NotNull
    @Size(max = 256)
    private String address;

    @Column(nullable = false, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String postalCode;

    @ManyToMany
    private List<CustomerEntity> customerEntities;

    @OneToMany(mappedBy = "addressEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<OrderEntity> orderEntities;

    public AddressEntity()
    {
        this.customerEntities = new ArrayList<>();
    }

    public AddressEntity(String address, String postalCode)
    {
        this();

        this.address = address;
        this.postalCode = postalCode;
    }

    public Long getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public List<CustomerEntity> getCustomerEntities()
    {
        return customerEntities;
    }

    public void setCustomerEntities(List<CustomerEntity> customerEntities)
    {
        this.customerEntities = customerEntities;
    }

    public List<OrderEntity> getOrderEntities()
    {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities)
    {
        this.orderEntities = orderEntities;
    }
    
    public String getFullAddress()
    {
        return this.address + ", " + this.postalCode;
    }

}
