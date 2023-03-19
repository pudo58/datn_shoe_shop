import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

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
		path: '/admin/dashboard',
		name: 'AdminDashboard',
		component: () => import('../views/admin/DashboardView.vue'),
		meta: {
			showTitle: false,
			showFooter: false,
			showCarousel: false,
			showHeader: false,
			showNav: false,
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
		}
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes: routes
});

// redirect to login page if not logged in and trying to access a restricted page
router.beforeEach((to, from, next) => {
	const publicPages = ['/login', '/register', '/', '/home', '/cart', '/product','/admin/dashboard', '/product/:id'];
	const authRequired = !publicPages.includes(to.path);
	const loggedIn = localStorage.getItem('access_token');

	// trying to access a restricted page + not logged in
	// redirect to login page
	if (authRequired && !loggedIn) {
		return next('/login');
	}
	next();
});
export default router