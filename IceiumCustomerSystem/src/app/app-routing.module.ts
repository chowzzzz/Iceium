import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// import { ForgetPasswordComponent } from './admin/forget-password/forget-password.component';
import { AccessRightErrorComponent } from './admin/access-right-error/access-right-error.component';
import { AddNewAddressComponent } from './account/add-new-address/add-new-address.component';
import { AddNewCreditCardComponent } from './account/add-new-credit-card/add-new-credit-card.component';
import { CartComponent } from './cart/cart/cart.component';
import { ChangePasswordComponent } from './account/change-password/change-password.component';
import { CheckoutComponent } from './cart/checkout/checkout.component';
import { CheckoutConfirmationComponent } from './cart/checkout-confirmation/checkout-confirmation.component';
import { CustomizeProductComponent } from './product/customize-product/customize-product.component';
import { DeliveryAddressComponent } from './components/delivery-address/delivery-address.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './admin/login/login.component';
import { PaymentMethodComponent } from './components/payment-method/payment-method.component';
import { ProfileComponent } from './account/profile/profile.component';
import { ReviewComponent } from './components/review/review.component';
import { SignupComponent } from './admin/signup/signup.component';
import { ViewAllAddressesComponent } from './account/view-all-addresses/view-all-addresses.component';
import { ViewAllCreditCardsComponent } from './account/view-all-credit-cards/view-all-credit-cards.component';
import { ViewAllOrdersComponent } from './account/view-all-orders/view-all-orders.component';
import { ViewAllProductsComponent } from './product/view-all-products/view-all-products.component';
import { ViewOrderItemDetailsComponent } from './account/view-order-item-details/view-order-item-details.component';
import { ViewProductDetailsComponent } from './product/view-product-details/view-product-details.component';

const routes: Routes = [
    { path: '', redirectTo: '/index', pathMatch: 'full' },
    // { path: 'forgetPassword', component: ForgetPasswordComponent },
    { path: 'accessRightError', component: AccessRightErrorComponent },
    { path: 'addNewAddress', component: AddNewAddressComponent },
    { path: 'addNewCreditCard', component: AddNewCreditCardComponent },
    { path: 'cart', component: CartComponent },
    { path: 'changePassword', component: ChangePasswordComponent },
    { path: 'checkoutConfirmation', component: CheckoutConfirmationComponent },
    { path: 'customizeProduct', component: CustomizeProductComponent },
    { path: 'index', component: IndexComponent },
    { path: 'login', component: LoginComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'viewAllAddresses', component: ViewAllAddressesComponent },
    { path: 'viewAllCreditCards', component: ViewAllCreditCardsComponent },
    { path: 'viewAllOrders', component: ViewAllOrdersComponent },
    { path: 'viewAllProducts', component: ViewAllProductsComponent },
    { path: 'viewOrderItemDetails', component: ViewOrderItemDetailsComponent },
    {
        path: 'viewOrderItemDetails/:orderId',
        component: ViewOrderItemDetailsComponent,
    },
    { path: 'viewProductDetails', component: ViewProductDetailsComponent },
    {
        path: 'viewProductDetails/:productId',
        component: ViewProductDetailsComponent,
    },
    {
        path: 'checkout',
        component: CheckoutComponent,
        children: [
            { path: '', redirectTo: 'deliveryAddress', pathMatch: 'full' },
            { path: 'deliveryAddress', component: DeliveryAddressComponent },
            { path: 'paymentMethod', component: PaymentMethodComponent },
            { path: 'review', component: ReviewComponent },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {}
