<template>
    <div class="col-md-12">
      <div class="card card-container">
      <img
          id="profile-img"
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          class="profile-img-card"
        />
      <label>
        <strong>{{userName}}</strong>
      </label>
      <p>
        <strong>Id:</strong>
        {{userId}}
      </p>
      <p>
        <strong>Email:</strong>
        {{eMail}}
      </p>
      <p>
        <strong>Token:</strong>
        {{accessToken.substring(0, 20)}} ... {{accessToken.substr(accessToken.length - 20)}}
      </p>
      </div>
    </div>
  </template>
  
  <script>
  import Vue from 'vue';
  export default {
    name: 'user-profile-view',
    computed: {
      isAuthenticated: () => {
        return Vue.$keycloak.authenticated
      },
      accessToken() {
       return Vue.$keycloak.token;
      },
      userName() {
        return Vue.$keycloak.idTokenParsed.given_name + " " + Vue.$keycloak.idTokenParsed.family_name;
      },
      userId() {
        return Vue.$keycloak.idTokenParsed.preferred_username;
      },
      eMail() {
        return Vue.$keycloak.idTokenParsed.email;
      }
    },
  };
  </script>
  
  <style scoped>
  label {
    display: block;
    margin-top: 10px;
  }
  .card-container.card {
    max-width: 350px !important;
    padding: 40px 40px;
  }
  .card {
    background-color: #f7f7f7;
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  }
  .profile-img-card {
    width: 96px;
    height: 96px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
  }
  </style>