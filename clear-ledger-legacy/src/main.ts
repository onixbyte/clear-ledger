import { createApp } from "vue"
import { createPinia } from "pinia"
import PiniaPluginPersistedState from "pinia-plugin-persistedstate"
import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
import "@/style.css"
import App from "@/App.vue"
import router from "@/router"

const pinia = createPinia()
pinia.use(PiniaPluginPersistedState)

createApp(App).use(router).use(ElementPlus).use(pinia).mount("#app")
