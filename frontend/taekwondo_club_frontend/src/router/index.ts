import { createRouter, createWebHistory } from 'vue-router'
import {Paths} from "@/router/paths";
import MainLayout from "@/layouts/main/MainLayout.vue";
import UnauthenticatedLayout from "@/layouts/UnauthenticatedLayout.vue";
import LandingPageView from "@/views/main/LandingPageView.vue";
import HomePageView from "@/views/main/HomePageView.vue";
import RegisterView from "@/views/auth/RegisterView.vue";
import LoginView from "@/views/auth/LoginView.vue";
import ForgotPasswordView from "@/views/auth/ForgotPasswordView.vue";
import ResetPasswordView from "@/views/auth/ResetPasswordView.vue";
import UserManageView from "@/views/admin/user/UserManageView.vue";
import UserInfomationView from "@/views/main/UserInfomationView.vue";
import RuleView from "@/views/main/RuleView.vue";
import SkillView from "@/views/main/SkillView.vue";
import EventView from "@/views/main/EventView.vue";
import ConfessionView from "@/views/main/ConfessionView.vue";
import ConfessionDetailView from "@/components/confession/ConfessionDetailView.vue";

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: Paths.HOME,
            component: HomePageView,
        },
        {
            path: Paths.MAIN,
            component: MainLayout,
            children: [
                {
                    path: Paths.MAIN,
                    component: HomePageView,
                },
                {
                    path: Paths.ADMIN_MANAGE_USER,
                    component: UserManageView,
                },
                {
                    path: Paths.MANAGE_USER,
                    component: UserInfomationView,
                },
                {
                    path: Paths.RULE,
                    component: RuleView,
                },
                {
                    path: Paths.SKILL,
                    component: SkillView,
                },
                {
                    path: Paths.EVENT,
                    component: EventView,
                },
                {
                    path: Paths.CONFESSION,
                    component: ConfessionView,
                },
                {
                    path: '/confession/:id',
                    name: 'ConfessionDetail',
                    component: ConfessionDetailView,
                    props: true
                }
            ]
        },
        {
            path: Paths.LOGIN,
            component: UnauthenticatedLayout,
            children: [
                {
                    path: Paths.LOGIN,
                    component: LoginView,
                },
                {
                    path: Paths.REGISTER,
                    component: RegisterView,
                },
                {
                    path: Paths.FORGOT_PASSWORD,
                    component: ForgotPasswordView,
                },
                {
                    path: Paths.RESET_PASSWORD,
                    component: ResetPasswordView,
                },
            ]
        },
    ],
})