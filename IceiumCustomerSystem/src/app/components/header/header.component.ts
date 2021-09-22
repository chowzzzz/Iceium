import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { SessionService } from 'src/app/services/session.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
    items: MenuItem[];
    searchValue: string = '';
    loginItems: MenuItem[];
    login: boolean;
    userButton: string = '';

    constructor(
        private router: Router,
        public sessionService: SessionService,
        private activatedRoute: ActivatedRoute
    ) {
        this.items = [];
        this.activatedRoute.queryParams.subscribe((params) => {
            this.searchValue = params['keyword'];
        });
        this.loginItems = [];
        this.login = false;
    }

    ngOnInit(): void {
        // console.log(this.sessionService.getIsLogin());

        if (this.sessionService.getIsLogin()) {
            this.userButton = this.sessionService.getCurrentCustomer().fullName;
        } else {
            this.userButton = '';
        }
        this.items = [
            {
                label: 'Products',
                items: [
                    {
                        label: 'All Products',
                        routerLink: '/viewAllProducts',
                        queryParams: {},
                    },
                    {
                        label: 'Ice Skates',
                        items: [
                            {
                                label: 'All Skates',
                                routerLink: ['/viewAllProducts'],
                                queryParams: { category: 1 },
                            },
                            {
                                label: 'Junior Ice Hockey Skates',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 2,
                                },
                            },
                            {
                                label: 'Youth Ice Hockey Skates',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 3,
                                },
                            },
                            {
                                label: 'Senior Ice Hockey Skates',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 2,
                                },
                            },
                            {
                                label: 'Figure Skates',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 5,
                                },
                            },
                        ],
                    },
                    {
                        label: 'Accessories',
                        items: [
                            {
                                label: 'All Accessories',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 6,
                                },
                            },
                            {
                                label: 'Puck',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 7,
                                },
                            },
                            {
                                label: 'Tape',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 8,
                                },
                            },
                            {
                                label: 'Net',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 9,
                                },
                            },
                            {
                                label: 'Laces',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 10,
                                },
                            },
                            {
                                label: 'Stick',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 11,
                                },
                            },
                            {
                                label: 'Runner',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 12,
                                },
                            },
                            {
                                label: 'Soaker',
                                routerLink: ['/viewAllProducts'],
                                queryParams: {
                                    category: 13,
                                },
                            },
                        ],
                    },
                    {
                        label: 'Customize Product',
                        routerLink: '/customizeProduct',
                        queryParams: {},
                    },
                ],
            },
        ];

        if (this.sessionService.getIsLogin()) {
            this.login = true;
        } else {
            this.login = false;
        }

        this.loginItems = [
            {
                visible: !this.login,
                items: [
                    {
                        label: 'Login',
                        icon: 'pi pi-sign-in',
                        routerLink: '/login',
                    },
                    {
                        label: 'Sign up',
                        icon: 'pi pi-user',
                        routerLink: '/signup',
                    },
                ],
            },
            {
                visible: this.login,
                items: [
                    {
                        label: 'My Account',
                        icon: 'pi pi-user',
                        routerLink: '/profile',
                    },
                    {
                        label: 'My Orders',
                        icon: 'pi pi-copy',
                        routerLink: '/viewAllOrders',
                    },
                    {
                        label: 'Logout',
                        icon: 'pi pi-sign-out',
                        command: (event) => {
                            this.customerLogout();
                        },
                    },
                ],
            },
        ];
    }

    doSearch() {
        this.router.navigate(['/viewAllProducts'], {
            queryParams: { keyword: this.searchValue },
        });
    }

    customerLogout(): void {
        this.sessionService.setIsLogin(false);
        this.sessionService.setCurrentCustomer(null);
        this.router.navigate(['/index']).then(() => {
            window.location.reload();
        });
    }
}
