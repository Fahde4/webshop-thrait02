import axios from "axios";
import Vue from 'vue'

const instance = axios.create({
  baseURL: "http://localhost:8206/api-thrait02/item-management/v1"
});

if (Vue.$keycloak.token) {
  instance.interceptors.request.use(config => {
    config.headers['Authorization'] = 'Bearer ' + Vue.$keycloak.token;
    return config;
  });
} 

export default instance;