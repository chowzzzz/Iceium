/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Theodoric
 */
@Entity
public class CreditCardEntity implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCardId;

    @Column(nullable = false, unique = true, length = 19)
    @NotNull
    @Size(min = 16, max = 19)
    private String creditCardNumber;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDateTime expiryDate;

    @Column(nullable = false, length = 4)
    @NotNull
    @Size(min = 3, max = 4)
    private String securityCode;

    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String nameOnCard;

    public CreditCardEntity()
    {
    }

    public CreditCardEntity(String creditCardNumber, LocalDateTime expiryDate, String securityCode, String nameOnCard)
    {
        this();

        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.securityCode = securityCode;
        this.nameOnCard = nameOnCard;
    }

    public Long getCreditCardId()
    {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId)
    {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber)
    {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public String getSecurityCode()
    {
        return securityCode;
    }

    public void setSecurityCode(String securityCode)
    {
        this.securityCode = securityCode;
    }

    public String getNameOnCard()
    {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard)
    {
        this.nameOnCard = nameOnCard;
    }
    
}
