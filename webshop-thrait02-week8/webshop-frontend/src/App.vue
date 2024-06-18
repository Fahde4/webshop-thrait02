<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href class="navbar-brand" @click.prevent>HS Esslingen</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/" class="nav-link">
            <font-awesome-icon icon="home" />Home
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/items" class="nav-link">Items</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/items/add" class="nav-link">Add Item</router-link>
        </li>
      </div>

      <div v-if="!isAuthenticated" class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="login">
            <font-awesome-icon icon="sign-in-alt" />LogIn
          </a>
        </li>
      </div>

      <div v-if="isAuthenticated" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logout">
            <font-awesome-icon icon="sign-out-alt" />LogOut
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import Vue from 'vue'

export default {
  name: "app",
  computed: {
    isAuthenticated: () => {
      return Vue.$keycloak.authenticated
    },
    currentUser() {
      return Vue.$keycloak.idTokenParsed.given_name + " " + Vue.$keycloak.idTokenParsed.family_name;
    }
  },
      
  methods: {
    login () {
      Vue.$keycloak.login({ redirectUri: window.location.origin })
    },
    logout () {
      Vue.$keycloak.logout({ redirectUri: window.location.origin })
    }
  }
};
</script>

<style>
.list {
  text-align: left;
  max-width: 750px;
  margin: auto;
}
</style>