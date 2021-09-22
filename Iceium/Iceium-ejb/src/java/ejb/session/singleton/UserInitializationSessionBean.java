/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AddressEntitySessionBeanLocal;
import ejb.session.stateless.CreditCardEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.CreditCardEntity;
import entity.CustomerEntity;
import entity.StaffEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.AccessRightEnum;
import util.exception.StaffNotFoundException;
import util.formatter.ConsoleLogger;
import util.generator.RandomDateGenerator;
import util.generator.RandomNumberGenerator;

/**
 *
 * @author Theodoric
 */
@Startup
@LocalBean
@Singleton
public class UserInitializationSessionBean
{

    @EJB(name = "AddressEntitySessionBeanLocal")
    private AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;

    @EJB(name = "CreditCardEntitySessionBeanLocal")
    private CreditCardEntitySessionBeanLocal creditCardEntitySessionBeanLocal;

    @EJB(name = "CustomerEntitySessionBeanLocal")
    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    @EJB(name = "StaffEntitySessionBeanLocal")
    private StaffEntitySessionBeanLocal staffEntitySessionBeanLocal;

    public UserInitializationSessionBean()
    {
    }

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            ConsoleLogger.log("Started");
            staffEntitySessionBeanLocal.retrieveStaffByUsername("headmanager");
            ConsoleLogger.log("No Generation");
        }
        catch (StaffNotFoundException ex)
        {
            initializeData();
            ConsoleLogger.log("Completed");
        }
    }

    /**
     * User Creation Method Start
     */
    private void initializeData()
    {
        try
        {
            createStaffs();
            createAddresses();
            createCustomers();
            createCreditCards();            
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void createStaffs() throws Exception
    {
        List<StaffEntity> newStaffEntities = new ArrayList<>();

        newStaffEntities.add(new StaffEntity("Head", "Manager", AccessRightEnum.HEAD_MANAGER, "headmanager", "password"));
        newStaffEntities.add(new StaffEntity("Product", "Manager", AccessRightEnum.PRODUCT_MANAGER, "productmanager", "password"));
        newStaffEntities.add(new StaffEntity("Customer", "Manager", AccessRightEnum.CUSTOMER_MANAGER, "customermanager", "password"));
        newStaffEntities.add(new StaffEntity("Transaction", "Manager", AccessRightEnum.TRANSACTION_MANAGER, "transactionmanager", "password"));

        newStaffEntities.add(new StaffEntity("Lawrence", "Fletcher", AccessRightEnum.HEAD_MANAGER, "lawrence", "password"));
        newStaffEntities.add(new StaffEntity("Linda", "Flynn", AccessRightEnum.PRODUCT_MANAGER, "linda", "password"));
        newStaffEntities.add(new StaffEntity("Phineas", "Flynn", AccessRightEnum.CUSTOMER_MANAGER, "phineas", "password"));
        newStaffEntities.add(new StaffEntity("Ferb", "Fletcher", AccessRightEnum.TRANSACTION_MANAGER, "ferb", "password"));

        newStaffEntities.add(new StaffEntity("Carl", "Tennyson", AccessRightEnum.HEAD_MANAGER, "carl", "password"));
        newStaffEntities.add(new StaffEntity("Sandra", "Tennyson", AccessRightEnum.PRODUCT_MANAGER, "sandra", "password"));
        newStaffEntities.add(new StaffEntity("Benjamin", "Tennyson", AccessRightEnum.CUSTOMER_MANAGER, "benjamin", "password"));
        newStaffEntities.add(new StaffEntity("Gwen", "Tennyson", AccessRightEnum.TRANSACTION_MANAGER, "gwen", "password"));

        newStaffEntities.add(new StaffEntity("Clark", "Kent", AccessRightEnum.HEAD_MANAGER, "clark", "password"));
        newStaffEntities.add(new StaffEntity("Diana", "Prince", AccessRightEnum.PRODUCT_MANAGER, "diana", "password"));
        newStaffEntities.add(new StaffEntity("Bruce", "Wayne", AccessRightEnum.CUSTOMER_MANAGER, "bruce", "password"));
        newStaffEntities.add(new StaffEntity("J'onn", "J'onzz", AccessRightEnum.TRANSACTION_MANAGER, "jonn", "password"));

        newStaffEntities.add(new StaffEntity("Elsa", "Martin", AccessRightEnum.HEAD_MANAGER, "elsa", "password"));
        newStaffEntities.add(new StaffEntity("Anna", "Martin", AccessRightEnum.PRODUCT_MANAGER, "anna", "password"));
        newStaffEntities.add(new StaffEntity("Kristoff", "Bjorgman", AccessRightEnum.CUSTOMER_MANAGER, "kristoff", "password"));
        newStaffEntities.add(new StaffEntity("Olaf", "Martin", AccessRightEnum.TRANSACTION_MANAGER, "olaf", "password"));

        for (StaffEntity newStaffEntity : newStaffEntities)
        {
            staffEntitySessionBeanLocal.createNewStaff(newStaffEntity);
        }
    }

    private void createAddresses() throws Exception
    {
        List<AddressEntity> addressEntities = new ArrayList<>();
        addressEntities.add(new AddressEntity("7 Boon Keng Rd City View #13-140", "S330007"));
        addressEntities.add(new AddressEntity("Kinetics Climbing Pte. Ltd., 511 Serangoon Road", "S218153"));
        addressEntities.add(new AddressEntity("5 Stadium Walk, Level 3", "S397693"));
        addressEntities.add(new AddressEntity("301 Lor 6 Toa Payoh", "S319392"));
        addressEntities.add(new AddressEntity("21 Lower Kent Ridge Rd", "S119077"));

        for (int i = 0; i < addressEntities.size(); i++)
        {
            addressEntities.set(i, addressEntitySessionBeanLocal.createNewAddress(addressEntities.get(i)));
        }
    }

    private void createCustomers() throws Exception
    {
        List<AddressEntity> addressEntities = addressEntitySessionBeanLocal.retrieveAllAddresses();

        RandomDateGenerator randomDateGenerator = new RandomDateGenerator(1, 1, 1970, 1, 1, 2005);
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator(0, addressEntities.size() - 1);

        for (int i = 1; i <= 1000; i++)
        {
            CustomerEntity customerEntity = new CustomerEntity("customer", "customer" + i, randomDateGenerator.getDate(), "customer" + i + "@email.com", "customer" + i, "password");

            if (i % 2 == 0)
            {
                customerEntity.addAddressEntity(addressEntities.get(randomNumberGenerator.getRandomInteger()));
            }

            if (i >= 800)
            {
                customerEntity.setIsEnabled(Boolean.FALSE);
            }
            customerEntitySessionBeanLocal.createNewCustomer(customerEntity);
        }
    }

    private void createCreditCards() throws Exception
    {
        RandomNumberGenerator creditCardCount = new RandomNumberGenerator(1, 3);
        RandomNumberGenerator creditCardNumberGenerator = new RandomNumberGenerator(1999_9999_9999_9999L, 99_9999_9999_9999_9999L);
        RandomDateGenerator expiryDateGenerator = new RandomDateGenerator(LocalDateTime.now().plusMonths(6), LocalDateTime.now().plusYears(3));
        RandomNumberGenerator securityCardGenerator = new RandomNumberGenerator(100, 9999);

        List<CustomerEntity> customerEntities = customerEntitySessionBeanLocal.retrieveAllCustomers();

        for (CustomerEntity customerEntity : customerEntities)
        {
            for (int i = 0; i < creditCardCount.getRandomInteger(); i++)
            {
                String creditCardNumber = creditCardNumberGenerator.getRandomLong().toString();
                LocalDateTime expiryDate = expiryDateGenerator.getDate();
                String securityCode = securityCardGenerator.getRandomInteger().toString();
                creditCardEntitySessionBeanLocal.createNewCreditCard(new CreditCardEntity(creditCardNumber, expiryDate, securityCode, customerEntity.getFullName()), customerEntity);
            }
        }
    }

    /**
     * User Creation Method End
     */
}
