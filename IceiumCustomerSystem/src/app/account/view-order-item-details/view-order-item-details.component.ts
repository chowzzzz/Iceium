import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { Order } from 'src/app/models/order';
import { OrderService } from 'src/app/services/order.service';
import { OrderItem } from 'src/app/models/order-item';
import { OrderItemService } from 'src/app/services/order-item.service';
import { OrderItemStatusEnum } from 'src/app/models/enum/order-item-status-enum.enum';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-view-order-item-details',
    templateUrl: './view-order-item-details.component.html',
    styleUrls: ['./view-order-item-details.component.css'],
    providers: [MessageService],
})
export class ViewOrderItemDetailsComponent implements OnInit {
    order: Order;
    orderId: string | null;
    orderItems: OrderItem[] | undefined;

    constructor(
        public sessionService: SessionService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private messageService: MessageService,
        private orderService: OrderService,
        private orderItemService: OrderItemService
    ) {
        this.order = new Order();
        this.orderId = null;
        this.orderItems = new Array();
    }

    ngOnInit(): void {
        this.checkLogin();
        this.orderId = this.activatedRoute.snapshot.paramMap.get('orderId');

        if (this.orderId != null) {
            this.orderService
                .getOrder(parseInt(this.orderId))
                .subscribe((res) => {
                    this.order = res;
                    this.orderItems = res.orderItemEntities;
                });
        }
    }

    refund(orderItem: OrderItem) {
        this.updateOrderItemStatus(
            orderItem,
            OrderItemStatusEnum.REFUND_PENDING
        );
    }

    exchange(orderItem: OrderItem) {
        this.updateOrderItemStatus(
            orderItem,
            OrderItemStatusEnum.EXCHANGE_PENDING
        );
    }

    updateOrderItemStatus(orderItem: OrderItem, status: OrderItemStatusEnum) {
        if (orderItem.orderItemId) {
            this.orderItemService
                .updateOrderItem(orderItem.orderItemId, status)
                .subscribe(
                    (res) => {
                        let orderItemIndex = this.orderItems?.findIndex(
                            (oi) => {
                                return oi.orderItemId === orderItem.orderItemId;
                            }
                        );

                        if (orderItemIndex && this.orderItems) {
                            this.orderItems[
                                orderItemIndex
                            ].orderItemStatusEnum = status;
                        }

                        this.messageService.add({
                            severity: 'success',
                            summary: 'Success',
                            detail: 'Refund request sent successfully!',
                        });
                    },
                    (error) => {
                        console.log(error);

                        this.messageService.add({
                            severity: 'error',
                            summary: 'Error',
                            detail:
                                'An error has occurred while requesting for refund of item: ' +
                                error,
                        });
                    }
                );
        }
    }

    checkLogin() {
        if (!this.sessionService.getIsLogin()) {
            this.router.navigate(['/accessRightError']);
        }
    }
}
