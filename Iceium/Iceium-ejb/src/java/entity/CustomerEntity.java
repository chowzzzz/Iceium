/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import util.security.CryptographicHelper;

/**
 *
 * @author Theodoric
 */
@Entity
@XmlRootElement
public class CustomerEntity implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;

    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime dateOfBirth;

    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;

    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    private String username;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    @Column(nullable = false)
    @NotNull
    private Boolean isEnabled;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<CreditCardEntity> creditCardEntities;

    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private List<OrderEntity> orderEntities;

    @ManyToMany(mappedBy="customerEntities")
    private List<AddressEntity> addressEntities;

    public CustomerEntity()
    {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);

        this.creditCardEntities = new ArrayList<>();
        this.orderEntities = new ArrayList<>();
        this.addressEntities = new ArrayList<>();
        this.isEnabled = true;
    }

    public CustomerEntity(String firstName, String lastName, LocalDateTime dateOfBirth, String email, String username, String password)
    {
        this();

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.username = username;
        this.isEnabled = true;

        setPassword(password);
    }

    public Long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public LocalDateTime getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getAge()
    {
        return ChronoUnit.YEARS.between(dateOfBirth, LocalDateTime.now());
    }

    public String getFullName()
    {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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

    public void setUnhashedPassword(String password)
    {
        this.password = password;
    }

    public void setPassword(String password)
    {
        if (password != null)
        {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else
        {
            this.password = null;
        }
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public List<CreditCardEntity> getCreditCardEntities()
    {
        return creditCardEntities;
    }

    public void setCreditCardEntities(List<CreditCardEntity> creditCardEntities)
    {
        this.creditCardEntities = creditCardEntities;
    }

    public List<OrderEntity> getOrderEntities()
    {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities)
    {
        this.orderEntities = orderEntities;
    }

    public List<AddressEntity> getAddressEntities()
    {
        return addressEntities;
    }

    public void setAddressEntities(List<AddressEntity> addressEntities)
    {
        this.addressEntities = addressEntities;
    }

    public void addAddressEntity(AddressEntity addressEntity)
    {
        if (addressEntity != null)
        {
            if (!this.addressEntities.contains(addressEntity))
            {
                this.addressEntities.add(addressEntity);

                if (!addressEntity.getCustomerEntities().contains(this))
                {
                    addressEntity.getCustomerEntities().add(this);
                }
            }
        }
    }

    public void removeAddressEntity(AddressEntity addressEntity)
    {
        if (addressEntity != null)
        {
            if (this.addressEntities.contains(addressEntity))
            {
                this.addressEntities.remove(addressEntity);

                if (addressEntity.getCustomerEntities().contains(this))
                {
                    addressEntity.getCustomerEntities().remove(this);
                }
            }
        }
    }

    public Boolean getIsEnabled()
    {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

}
