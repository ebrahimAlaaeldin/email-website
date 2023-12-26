import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignIn from '../views/SignIn.vue'

const routes = [
  {
    path: '',
    name: 'Home',
    component: Home
  },
  {
    path: '/inbox/:username',
    name: 'inbox',
    component: Home
  },
  {
    path: '/inbox/:username',
    name: 'inbox',
    component: Home
  },
  {
    path: '/sent/:username',
    name: 'sent',
    component: Home
  },
  {
    path: '/trash/:username',
    name: 'trash',
    component: Home 
  },
  {
    path: '/search/:username',
    name: 'search',
    component: Home
  },
  // {
  //   path: '/',
  //   name: 'signin',
  //   component: Home
  // },
  // {
  //   path: '/signup',
  //   name: 'signup',
  //   component: Home
  // },
  {
    path: '/draft/:username',
    name: 'draft',
    component: Home
  },
  {
    path: '/contact/:username',
    name: 'contact',
    component: Home
  },
  {
    path:'/signin',
    name:'signin',
    component: SignIn
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
