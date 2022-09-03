import { createRouter, createWebHistory } from 'vue-router'

import LibraryView from "../views/LibraryView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: LibraryView
    }
  ]
})

export default router
