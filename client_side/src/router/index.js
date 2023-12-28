import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import SignIn from '../views/SignIn.vue'

const routes = [
  {
    path: '',
    name: 'Home',
    component: SignIn
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
    path: '/folder/:foldername/:username',
    name: 'folder',
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
  },
  {
    path:'/search/:username/:searchby/:searchfor/:sortby/:pagenumber/:FolderId',
    name:'search',
    component: Home
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
