import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { PrimeNGConfig, SelectItem } from 'primeng/api';
import { Order } from 'src/app/models/order';
import { SessionService } from 'src/app/services/session.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
    selector: 'app-view-all-orders',
    templateUrl: './view-all-orders.component.html',
    styleUrls: ['./view-all-orders.component.css'],
})
export class ViewAllOrdersComponent implements OnInit {
    orders: Order[];

    constructor(
        private primengConfig: PrimeNGConfig,
        private orderService: OrderService,
        public sessionService: SessionService,
        private router: Router
    ) {
        this.orders = new Array();
        this.primengConfig.ripple = true;
    }

    ngOnInit(): void {
        this.orderService.getOrdersByCustomer().subscribe((orders) => {
            this.orders = orders;
            this.orders.reverse();
            console.log(this.orders);
        });
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }
}
