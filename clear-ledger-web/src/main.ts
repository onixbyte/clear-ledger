import { createApp } from "vue"
import { createPinia } from "pinia"
import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
import "@/style.css"
import App from "@/App.vue"
import router from "@/router"

createApp(App)
  .use(router)
  .use(ElementPlus)
  .use(createPinia())
  .mount("#app")
