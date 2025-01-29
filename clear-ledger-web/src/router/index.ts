import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router"
import { RouteMeta } from "@/types"
import { useUserStore } from "@/store/user-store.ts"

const routes: RouteRecordRaw[] = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/layout/dashboard-layout/index.vue"),
    children: [
      {
        path: "ledgers",
        name: "Ledgers",
        component: () => import("@/pages/ledgers/index.vue"),
        meta: {
          title: "我的账本",
        },
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/pages/login/index.vue"),
    meta: {
      title: "登录",
    },
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("@/pages/register/index.vue"),
    meta: {
      title: "注册"
    }
  }
]

const index = createRouter({
  history: createWebHashHistory(),
  routes,
})

index.beforeEach((to, from, next) => {
  if (to.meta) {
    const meta = to.meta as RouteMeta
    document.title = meta.title ? `${meta.title} | 家庭账本` : "家庭账本"
  }

  const { isAuthenticated } = useUserStore()

  if (to.name != "Login" && to.name != "Register" && !isAuthenticated) {
    next({ name: "Login" })
  } else {
    next()
  }
})

export default index
