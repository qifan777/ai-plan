import { createRouter, createWebHistory } from 'vue-router'

import { useHomeStore } from '@/stores/home-store'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout-view',
      component: () => import('@/layout/layout-view.vue'),
      redirect: '/home',
      children: [
        { path: '/home', component: () => import('@/views/home/home-view.vue') },
        { path: '/user', component: () => import('@/views/user/user-view.vue') },
        {
          path: '/role',
          name: 'role',
          component: () => import('@/views/role/role-view.vue'),
        },
        {
          path: '/menu',
          name: 'menu',
          component: () => import('@/views/menu/menu-view.vue'),
        },
        {
          path: '/dict',
          name: 'dict',
          component: () => import('@/views/dict/dict-view.vue'),
        },
        {
          path: '/slideshow',
          component: () => import('@/views/slideshow/slideshow-view.vue'),
        },
        {
          path: '/slideshow-details',
          component: () => import('@/views/slideshow/slideshow-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          },
        },
        {
          path: '/feedback',
          component: () => import('@/views/feedback/feedback-view.vue'),
        },
        {
          path: '/notice',
          component: () => import('@/views/notice/notice-view.vue'),
        },
        {
          path: '/notice-details',
          component: () => import('@/views/notice/notice-details-view.vue'),
          props(to) {
            return { id: to.query.id }
          },
        },
        {
          path: '/ai-session',
          component: () => import('@/views/ai-session/ai-session-view.vue'),
        },
        {
          path: '/ai-message',
          component: () => import('@/views/ai-message/ai-message-view.vue'),
        },
        {
          path: '/post',
          component: () => import('@/views/post/post-view.vue'),
        },
        {
          path: '/post-comment',
          component: () => import('@/views/post-comment/post-comment-view.vue'),
        },
        {
          path: '/task',
          component: () => import('@/views/task/task-view.vue'),
        },
        {
          path: '/sign',
          component: () => import('@/views/sign/sign-view.vue'),
        },
      ],
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/login-view.vue'),
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/login/register-view.vue'),
    },
    {
      path: '/rest-password',
      component: () => import('@/views/login/rest-password-view.vue'),
    },
  ],
})
// 添加路由拦截，在进入路由之前需要校验是否有该菜单的权限

const whiteList = ['/login', '/register', '/rest-password', '/']
router.beforeEach(async (to, from, next) => {
  const homeStore = useHomeStore()
  if (
    whiteList.includes(to.path) ||
    (await homeStore.getMenuList()).findIndex((menu) => menu.path === to.path) >= 0
  ) {
    next()
  } else {
    return next('/')
  }
})
export default router
