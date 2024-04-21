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

export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: Paths.HOME,
            component: LandingPageView,
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