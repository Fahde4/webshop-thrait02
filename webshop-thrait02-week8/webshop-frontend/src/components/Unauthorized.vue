<template>
    <div>
      <h1>Unauthorized</h1>
      <div>Hello {{ firstName }} {{ lastName }}</div>
      <div>Your account does not have access to that page.</div>
    </div>
  </template>
  
  <script>
  import Vue from 'vue'
  
  export default {
    name: "unauthorized-view",
    data: function () {
      return {
        firstName: '',
        lastName: ''
      }
    },
    beforeMount() {
      this.firstName = Vue.$keycloak.tokenParsed.given_name
      this.lastName = Vue.$keycloak.tokenParsed.family_name
    }
  }
  </script>
  
  <style scoped>
  
  </style>