<template>
	<!-- Remove the container if you want to extend the Footer to full width. -->
	<div class="my-5">
		<footer class="text-center text-lg-start" style="background-color: #262626;">
			<div class="container d-flex justify-content-center py-4">
				<button type="button" class="btn btn-primary btn-sm btn-floating mx-2" style="background-color: #54456b;">
					<i class="bi bi-facebook"></i>
				</button>
				<button type="button" class="btn btn-primary btn-sm btn-floating mx-2" style="background-color: #54456b;">
					<i class="bi bi-youtube"></i>
				</button>
				<button type="button" class="btn btn-primary btn-sm btn-floating mx-2" style="background-color: #54456b;">
					<i class="bi bi-instagram"></i>
				</button>
				<button type="button" class="btn btn-primary btn-sm btn-floating mx-2" style="background-color: #54456b;">
					<i class="bi bi-twitter"></i>
				</button>
			</div>

			<!-- Copyright -->
			<div class="text-center text-white p-3" style="background-color: rgba(0, 0, 0, 0.2);">
				© 2023 Copyright:
				<a class="text-white" href="#">Aboo Shop</a>
			</div>
			<!-- Copyright -->
		</footer>
	</div>
</template>

<script lang="ts">

export default{
	name: 'FooterComponent',
	methods: {
	}
}
</script>