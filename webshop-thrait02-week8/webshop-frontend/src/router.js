import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)



const routes = [
  {
    path: "/items/add",
    name: "add-item",
    component: () => import("./components/AddItem"),
    meta: {
      isProtected: true,
      requiresRole: "admin"
    }
  },
  {
    path: "/",
    alias: "/items",
    name: "items",
    component: () => import("./components/ItemList")
  },
  {
    path: "/items/:id",
    name: "item-details",
    component: () => import("./components/Item")

  },
  {
    path: "/items/:id/edit",
    name: "edit-item",
    component: () => import("./components/EditItem"),
    meta: {
      isProtected: true,
      requiresRole: "admin"
    }
  },
  {
    path: "/profile",
    name: "profile",
    component: () => import("./components/UserProfile"),
    meta: {
      isProtected: false
    }
  },
  {
    path: '/unauthorized',
    name: 'Unauthorized',
    component: () => import('./components/Unauthorized.vue'),
    meta: {
      isProtected: false
    }
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.isProtected) {
    // Get the actual url of the app, it's needed for Keycloak
    const currentUrl = window.location;
    const basePath = currentUrl.protocol + "//" + currentUrl.host
    if (!Vue.$keycloak.authenticated) {
      // The page is protected and the user is not authenticated. Force a login.
      Vue.$keycloak.login({ redirectUri: basePath + to.path })
    } else if (Vue.$keycloak.hasResourceRole(to.meta.requiresRole)) {
      // The user was authenticated, and has the app role (is authorized). Update the token.
      Vue.$keycloak.updateToken(70)
        .then(() => {
          next()
        })
        .catch(err => {
          console.error(err)
        })
    } else {
      // The user was authenticated, but did not have the correct role (is not authorized)
      // Redirect the user to an error page
      console.log(to.meta.requiresRole, Vue.$keycloak)
      
      next({ name: 'Unauthorized' })
      
    }
  } else {
    // This page did not require authentication
    next()
  }
})
export default router