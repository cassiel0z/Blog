// Composables
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        component: () => import(/* webpackChunkName: "home" */ '@/views/Home.vue'),
        meta:{
          title:'首页'
        }
      },
    ],
  },
  {
    path: '/404',
    component: () => import('@/views/404Page.vue'),
    meta:{
      title:'首页'
    }
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

//全局前置路由守卫
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = to.meta.title as string
  }
  next()
})

//全局后置路由守卫
router.afterEach((to) => {
  if (!localStorage.token && to.matched.length === 0) {
    router.push('/404')
  }
})

export default router
