import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/LoginView.vue'),
        meta: {
            showTitle: false,
            showFooter: false,
            showCarousel: false,
            showHeader: false,
            showNav: false,
        }
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/RegisterView.vue'),
        meta: {
            showTitle: false,
            showFooter: false,
            showCarousel: false,
            showHeader: false,
            showNav: false,
        }
    },
    {
        path: '/profile',
        name: 'Profile',
        component: () => import('../views/ProfileView.vue'),
        meta: {
            showTitle: false,
            showFooter: false,
            showCarousel: false,
            showHeader: false,
            showNav: false,
            requiresAuth: true
        }
    },
    {
        path: '/product',
        name: 'Product',
        component: () => import('../views/ProductView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: true,
            showNav: true,
        }
    },
    {
        path: '/product/:id',
        name: 'ProductDetail',
        component: () => import('../views/ProductDetailView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: true,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/forgot-password',
        name: 'ForgotPassword',
        component: () => import('../views/ForgotPasswordView.vue'),
        meta: {
            showTitle: false,
            showFooter: true,
            showCarousel: false,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/reset-password/:email/:code',
        name: 'ResetPassword',
        component: () => import('../views/ResetPasswordView.vue'),
        meta: {
            showTitle: false,
            showFooter: true,
            showCarousel: false,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/cart',
        name: 'Cart',
        component: () => import('../views/CartView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/cart/checkout/:cartIdList',
        name: 'Checkout',
        component: () => import('../views/OrderView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/order',
        name: 'OrderList',
        component: () => import('../views/OrderListView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/order/:id',
        name: 'OrderDetail',
        component: () => import('../views/OrderDetailComponent.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/contact',
        name: 'Contact',
        component: () => import('../views/ContactView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/HomeView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
    {
        path: '/voucher',
        name: 'VoucherComponent',
        component: () => import('../views/VoucherView.vue'),
        meta: {
            showTitle: true,
            showFooter: true,
            showCarousel: true,
            showHeader: false,
            showNav: true,
            requiresAuth: true
        }
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: routes
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/', '/home', '/cart', '/product', '/product/:id', '/forgot-password','/reset-password/:email/:code', '/contact', '/home'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('access_token');
    if (to.matched.some(record => record.path === '/reset-password/:email/:code')) {
        // Allow access without authentication
        return next();
    }
    if (to.matched.some(record => record.path === '/product/:id')) {
        // Allow access without authentication
        return next();
    }

    // trying to access a restricted page + not logged in
    // redirect to login page
    if (authRequired && !loggedIn) {
        return next('/login');
    }
    next();
});
export default router
