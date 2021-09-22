/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.initialization;

import ejb.session.stateless.AddressEntitySessionBeanLocal;
import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.CustomerEntitySessionBeanLocal;
import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.SpecificationEntitySessionBeanLocal;
import entity.AddressEntity;
import entity.CouponEntity;
import entity.CustomerEntity;
import entity.SpecificationEntity;
import java.time.LocalDateTime;
import java.util.List;
import util.enumeration.PaymentMethodEnum;
import util.generator.RandomDateGenerator;
import util.generator.RandomNumberGenerator;

/**
 *
 * @author Theodoric
 */
public class CreateOrders
{

    public static final Long limit = 1400L;
    
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;
    
    private SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal;

    private OrderEntitySessionBeanLocal orderEntitySessionBeanLocal;

    private CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal;

    private AddressEntitySessionBeanLocal addressEntitySessionBeanLocal;

    private CheckoutCart checkoutCart;

    private final int listStart;
    private final int listEnd;
    private final int orders;

    public CreateOrders(CouponEntitySessionBeanLocal couponEntitySessionBeanLocal, SpecificationEntitySessionBeanLocal specificationEntitySessionBeanLocal, OrderEntitySessionBeanLocal orderEntitySessionBeanLocal, CustomerEntitySessionBeanLocal customerEntitySessionBeanLocal, AddressEntitySessionBeanLocal addressEntitySessionBeanLocal, int listStart, int listEnd, int orders)
    {
        this.couponEntitySessionBeanLocal = couponEntitySessionBeanLocal;
        this.specificationEntitySessionBeanLocal = specificationEntitySessionBeanLocal;
        this.orderEntitySessionBeanLocal = orderEntitySessionBeanLocal;
        this.customerEntitySessionBeanLocal = customerEntitySessionBeanLocal;
        this.addressEntitySessionBeanLocal = addressEntitySessionBeanLocal;

        this.checkoutCart = new CheckoutCart();

        this.listStart = listStart;
        this.listEnd = listEnd;
        this.orders = orders;
    }

    public void createOrders() throws Exception
    {
        List<CustomerEntity> customerEntities = customerEntitySessionBeanLocal.retrieveAllCustomers();
        customerEntities = customerEntities.subList(listStart, listEnd);
        List<AddressEntity> addressEntities = addressEntitySessionBeanLocal.retrieveAllAddresses();        
        List<SpecificationEntity> specificationEntities = specificationEntitySessionBeanLocal.retrieveAllSpecifications();
        List<CouponEntity> couponEntities = couponEntitySessionBeanLocal.retrieveAllCoupons();

        RandomNumberGenerator randomNumberGenerator1 = new RandomNumberGenerator(0, specificationEntities.size() - 1);        
        RandomNumberGenerator randomNumberGenerator2 = new RandomNumberGenerator(0, addressEntities.size() - 1);
        RandomNumberGenerator randomNumberGenerator3 = new RandomNumberGenerator(0, couponEntities.size() - 1);
        RandomNumberGenerator randomNumberGenerator4 = new RandomNumberGenerator(1, 3);
        RandomDateGenerator randomDateGenerator = new RandomDateGenerator(1, 1, 2020, 1, 6, 2021);

        for (CustomerEntity customerEntity : customerEntities)
        {
            for (int i = 0; i < orders; i++)
            {
                PaymentMethodEnum paymentMethodEnum = randomNumberGenerator4.getRandomInteger() % 2 == 0 ? PaymentMethodEnum.CASH_ON_DELIVERY : PaymentMethodEnum.CREDIT_CARD;
                LocalDateTime transactionDateTime = randomDateGenerator.getDate();
                AddressEntity addressEntity = addressEntities.get(randomNumberGenerator2.getRandomInteger());
                CouponEntity couponEntity = (i + randomNumberGenerator3.getRandomInteger()) % 2 == 0 ? couponEntities.get(randomNumberGenerator3.getRandomInteger()) : null;
                                
                createOrder(specificationEntities, transactionDateTime, customerEntity, paymentMethodEnum, addressEntity, couponEntity, randomNumberGenerator1, randomNumberGenerator4);
            }
        }
    }

    private void createOrder(List<SpecificationEntity> specificationEntities, LocalDateTime transactionDateTime, CustomerEntity customerEntity, PaymentMethodEnum paymentMethodEnum, AddressEntity addressEntity, CouponEntity couponEntity, RandomNumberGenerator randomNumberGenerator1, RandomNumberGenerator randomNumberGenerator4) throws Exception
    {
        for (int i = 0; i <= randomNumberGenerator4.getRandomInteger(); i++)
        {
            SpecificationEntity specificationEntity = specificationEntities.get(randomNumberGenerator1.getRandomInteger());
            int quantity = randomNumberGenerator4.getRandomInteger();

            this.checkoutCart.addItem(specificationEntity, quantity);
        }

        this.checkoutCart.doCheckout(orderEntitySessionBeanLocal, customerEntity.getCustomerId(), addressEntity.getAddressId(), couponEntity == null ? null : couponEntity.getCouponId(), paymentMethodEnum, transactionDateTime);
    }

}
