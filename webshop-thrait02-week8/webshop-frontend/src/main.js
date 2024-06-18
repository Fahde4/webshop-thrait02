import Vue from 'vue';
import App from './App.vue';
import router from './router';
import VeeValidate from 'vee-validate';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

import authentication from "@/plugins/authentication"
	
library.add(faHome);

Vue.config.productionTip = false;

Vue.use(authentication)
Vue.use(VeeValidate)
	
Vue.component('font-awesome-icon', FontAwesomeIcon);
	
Vue.$keycloak
  .init({ checkLoginIframe: false })
  .then(() => {
    new Vue({
      router,
      render: h => h(App)
    }).$mount('#app')
  })