import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/display.css'
import App from './App.vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import '@fortawesome/fontawesome-free/css/all.css'

import AOS from 'aos'
import 'aos/dist/aos.css'
import './assets/tailwind.css'
import {router} from "@/router"

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

AOS.init()

app.mount('#app')

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
