<template>
    <LayoutComponent />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
<script lang="ts">
import { User } from '@/core/model/user.model'
import {UserService} from '@/core/service/user.service'
import LayoutComponent from './components/LayoutComponent.vue'
export default {
  name: 'App',
  components: {
    LayoutComponent
  },
  data() {
    return {
      msg: 'Welcome to Your Vue.js App',
      user: new User(),
      UserService: new UserService()
    }
  },
  methods: {},
  mounted() {

  },
}
</script>
