import { createRouter, createWebHistory } from 'vue-router';
import LandingPage from '../static/LandingPage.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/landing',
            name: 'Home',
            component: LandingPage
        },
        // Other routes here...
    ]
});

export default router;