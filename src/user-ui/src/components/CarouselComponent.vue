<template>
	<!-- <div id="carouselExampleFade" class="container carousel slide carousel-fade pt-2" style="height: 60%;">
	  <div class="carousel-inner">
		<div class="carousel-item active">
		  <img src="../assets/c-image1.jpg" class="d-block  w-100" alt="...">
		</div>
		<div class="carousel-item">
		  <img src="../assets/c-image2.jpg" class="d-block  w-100" alt="...">
		</div>
		<div class="carousel-item">
		  <img src="../assets/c-image3.jpg" class="d-block  w-100" alt="...">
		</div>
	  </div>
	  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
		<span class="carousel-control-prev-icon" aria-hidden="true"></span>
		<span class="visually-hidden">Previous</span>
	  </button>
	  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next" id="next">
		<span class="carousel-control-next-icon" aria-hidden="true"></span>
		<span class="visually-hidden">Next</span>
	  </button>
	</div> -->
</template>

<script lang="ts">
export default ({
	name: 'CarouselComponent',
	setup() {
		const carouselAutoNext = () => {
			setInterval(() => {
				document.getElementById('next')?.click();
			}, 3000);
		}
		carouselAutoNext();
		return {
			carouselAutoNext
		}
	},

})
</script>