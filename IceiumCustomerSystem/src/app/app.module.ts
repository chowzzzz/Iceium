import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { NgxCleaveDirectiveModule } from 'ngx-cleave-directive';
import { CreditCardDirectivesModule } from 'angular-cc-library';

import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { CarouselModule } from 'primeng/carousel';
import { CheckboxModule } from 'primeng/checkbox';
import { ColorPickerModule } from 'primeng/colorpicker';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DataViewModule } from 'primeng/dataview';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MultiSelectModule } from 'primeng/multiselect';
import { PasswordModule } from 'primeng/password';
import { RatingModule } from 'primeng/rating';
import { SelectButtonModule } from 'primeng/selectbutton';
import { SharedModule } from 'primeng/api';
import { SliderModule } from 'primeng/slider';
import { StepsModule } from 'primeng/steps';
import { TabViewModule } from 'primeng/tabview';
import { TableModule } from 'primeng/table';
import { Toast, ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { DialogModule } from 'primeng/dialog';

import { AccessRightErrorComponent } from './admin/access-right-error/access-right-error.component';
import { AccountMenuComponent } from './components/account-menu/account-menu.component';
import { AddNewAddressComponent } from './account/add-new-address/add-new-address.component';
import { AddNewCreditCardComponent } from './account/add-new-credit-card/add-new-credit-card.component';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { CarouselProductComponent } from './components/carousel-product/carousel-product.component';
import { CartComponent } from './cart/cart/cart.component';
import { ChangePasswordComponent } from './account/change-password/change-password.component';
import { CheckoutComponent } from './cart/checkout/checkout.component';
import { CheckoutConfirmationComponent } from './cart/checkout-confirmation/checkout-confirmation.component';
import { ColorPickerComponent } from './components/color-picker/color-picker.component';
import { ColorsComponent } from './components/colors/colors.component';
import { CustomizeProductComponent } from './product/customize-product/customize-product.component';
import { DeliveryAddressComponent } from './components/delivery-address/delivery-address.component';
import { FilterBarComponent } from './components/filter-bar/filter-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ForgetPasswordComponent } from './admin/forget-password/forget-password.component';
import { HeaderComponent } from './components/header/header.component';
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

@NgModule({
    declarations: [
        AccessRightErrorComponent,
        AccountMenuComponent,
        AddNewAddressComponent,
        AddNewCreditCardComponent,
        AppComponent,
        BreadcrumbComponent,
        CarouselProductComponent,
        CartComponent,
        ChangePasswordComponent,
        CheckoutComponent,
        CheckoutConfirmationComponent,
        ColorPickerComponent,
        ColorsComponent,
        CustomizeProductComponent,
        DeliveryAddressComponent,
        FilterBarComponent,
        FooterComponent,
        ForgetPasswordComponent,
        HeaderComponent,
        IndexComponent,
        LoginComponent,
        PaymentMethodComponent,
        ProfileComponent,
        ReviewComponent,
        SignupComponent,
        ViewAllAddressesComponent,
        ViewAllCreditCardsComponent,
        ViewAllOrdersComponent,
        ViewAllProductsComponent,
        ViewOrderItemDetailsComponent,
        ViewProductDetailsComponent,
    ],
    imports: [
        AppRoutingModule,
        BreadcrumbModule,
        BrowserAnimationsModule,
        BrowserModule,
        ButtonModule,
        CalendarModule,
        CardModule,
        CarouselModule,
        CheckboxModule,
        ColorPickerModule,
        CommonModule,
        ConfirmDialogModule,
        DataViewModule,
        DividerModule,
        DropdownModule,
        FormsModule,
        HttpClientModule,
        InputNumberModule,
        InputTextModule,
        MenuModule,
        MenubarModule,
        MultiSelectModule,
        NgxCleaveDirectiveModule,
        PasswordModule,
        RatingModule,
        SelectButtonModule,
        SharedModule,
        SliderModule,
        StepsModule,
        TabViewModule,
        TableModule,
        ToastModule,
        ToolbarModule,
        TooltipModule,
        CreditCardDirectivesModule,
        DialogModule,
    ],
    bootstrap: [AppComponent],
})
export class AppModule {}
