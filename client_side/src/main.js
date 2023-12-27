/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from '@/plugins'

// Components
import App from './App.vue'
import 'vuetify/styles';
import 'vuetify/dist/vuetify.min.css';
import store from './store';

// Composables
import { createApp } from 'vue'

const app = createApp(App)

registerPlugins(app)
app.use(store);
app.mount('#app')
