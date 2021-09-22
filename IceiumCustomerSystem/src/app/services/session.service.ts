import { Injectable } from '@angular/core';
import { Customer } from '../models/customer';
import { OrderItem } from '../models/order-item';
import { Coupon } from '../models/coupon';
import { Address } from '../models/address';
import { CreditCard } from '../models/credit-card';
import { PaymentMethodEnum } from '../models/enum/payment-method-enum.enum';

@Injectable({
    providedIn: 'root',
})
export class SessionService {
    constructor() {}

    getIsLogin(): boolean {
        if (sessionStorage.isLogin == 'true') {
            return true;
        } else {
            return false;
        }
    }

    setIsLogin(isLogin: boolean): void {
        sessionStorage.isLogin = isLogin;
    }

    getCurrentCustomer(): Customer {
        return JSON.parse(sessionStorage.currentCustomer);
    }

    setCurrentCustomer(currentCustomer: Customer | null): void {
        sessionStorage.currentCustomer = JSON.stringify(currentCustomer);
    }

    getUsername(): string {
        return sessionStorage.username;
    }

    setUsername(username: string | undefined): void {
        sessionStorage.username = username;
    }

    getPassword(): string {
        return sessionStorage.password;
    }

    setPassword(password: string | undefined): void {
        sessionStorage.password = password;
    }

    getCart(): OrderItem[] | undefined {
        const cart = sessionStorage.getItem('cart');

        if (typeof cart === 'string') {
            return JSON.parse(cart);
        } else {
            return [];
        }
    }

    setCart(orderItems: OrderItem[]): void {
        sessionStorage.setItem('cart', JSON.stringify(orderItems));
    }

    getPaymentMethod(): String | undefined {
        const paymentMethod = sessionStorage.getItem('paymentMethod');

        if (typeof paymentMethod === 'string') {
            return JSON.parse(paymentMethod);
        } else {
            return undefined;
        }
    }

    setPaymentMethod(paymentMethod: String): void {
        sessionStorage.setItem('paymentMethod', JSON.stringify(paymentMethod));
    }
    
    getCreditCard(): CreditCard | undefined {
        const creditCard = sessionStorage.getItem('creditCard');

        if (typeof creditCard === 'string') {
            return JSON.parse(creditCard);
        } else {
            return undefined;
        }
    }

    setCreditCard(creditCard: CreditCard): void {
        sessionStorage.setItem('creditCard', JSON.stringify(creditCard));
    }

    getDeliveryAddress(): Address | undefined {
        const deliveryAddress = sessionStorage.getItem('deliveryAddress');

        if (typeof deliveryAddress === 'string') {
            return JSON.parse(deliveryAddress);
        } else {
            return undefined;
        }
    }

    setDeliveryAddress(deliveryAddress: Address): void {
        sessionStorage.setItem('deliveryAddress', JSON.stringify(deliveryAddress));
    }

    getCoupon(): Coupon | undefined {
        const coupon = sessionStorage.getItem('coupon');
        if (typeof coupon === 'string') {
            return JSON.parse(coupon);
        } else {
            return undefined;
        }        
    }

    setCheckoutOrderId(checkoutOrderId: number | undefined) {
        sessionStorage.setItem('checkoutOrderId', JSON.stringify(checkoutOrderId));
    }

    getCheckoutOrderId(): number | undefined {
        const checkoutOrderId = sessionStorage.getItem('checkoutOrderId');
        if (typeof checkoutOrderId === 'string') {
            return JSON.parse(checkoutOrderId);
        } else {
            return undefined;
        }        
    }

    setCoupon(coupon: Coupon | undefined) {
        sessionStorage.setItem('coupon', JSON.stringify(coupon));
    }

    clearCheckout()
    {
        sessionStorage.setItem('cart', JSON.stringify(new Array()));
        sessionStorage.removeItem('paymentMethod');
        sessionStorage.removeItem('creditCard');
        sessionStorage.removeItem('deliveryAddress');
        sessionStorage.removeItem('coupon');
    }

    checkAccessRight(path: string): boolean {
        if (!this.getIsLogin()) {
            if (
                path == '/profile' ||
                path == '/viewAllCreditCards' ||
                path == '/changePassword' ||
                path == '/createNewCreditCard' ||
                path == '/changePassword' ||
                path == '/checkout' ||
                path == '/checkoutConfirmation' ||
                path == '/viewAllOrders' ||
                path.startsWith('/viewOrderItemDetails')
            ) {
                return false;
            } else {
                return true;
            }
        } else {
            if (
                path == 'accessRightError' ||
                path == 'login' ||
                path == 'signup' ||
                path == 'forgetPassword'
            ) {
                return false;
            } else {
                return true;
            }
        }
    }
}
