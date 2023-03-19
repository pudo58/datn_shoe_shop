<template>
	<div class="container-fluid">
		<div class="header">
			<header-component v-if="$route.meta.showHeader == undefined ? true : $route.meta.showHeader"/>
			<nav-component v-if="$route.meta.showNav == undefined ? true : $route.meta.showNav"/>
			<carousel-component v-if="$route.meta.showCarousel == undefined ? true : $route.meta.showCarousel"/>
		</div>
		<main>
			<router-view />
		</main>
		<div class="footer">
			<footer-component v-if="$route.meta.showFooter == undefined ? true : $route.meta.showFooter" />
		</div>
	</div>
</template>
<script lang="ts">
import HeaderComponent from '@/components/HeaderComponent.vue'
import FooterComponent from '@/components/FooterComponent.vue'
import NavComponent from '@/components/NavComponent.vue'
import CarouselComponent from '@/components/CarouselComponent.vue'

export default {
	name: 'LayoutComponent',
	components: {
		HeaderComponent,
		FooterComponent,
		NavComponent,
		CarouselComponent
	},
	data() {
		return {
			// msg: 'Welcome to Your Vue.js App'
		}
	},
	methods: {
	},

}
</script>