import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'vue3-toastify/dist/index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import Vue3Toast, { toast } from 'vue3-toastify';
import axios from "axios";


const app = createApp(App);
const options = {
	position: 'top-right',
	timeout: 3000,
	closeOnClick: true,
	pauseOnFocusLoss: true,
	pauseOnHover: true,
	draggable: true,
	draggablePercent: 0.6,
	showCloseButtonOnHover: false,
	hideProgressBar: false,
	closeButton: 'button',
	icon: true,
	rtl: false,
};


axios.defaults.baseURL = "http://localhost:8000";
app.use(Vue3Toast, options);
app.use(router);
app.config.globalProperties.$toast = toast;
app.mount('#app');