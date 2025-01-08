import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";

const routes: RouteRecordRaw[] = [

]

const index = createRouter({
  history: createWebHistory(),
  routes
})

index.beforeEach((from, to, next) => {

})

export default index
