export const Paths = {
    HOME: '/',
    MAIN: '/main',
    LOGIN: '/signin',
    REGISTER: '/register',
    FORGOT_PASSWORD: '/forgot-password',
    RESET_PASSWORD: '/reset-password',
    LOGIN_COMPLETE: '/login-complete',
    REGISTER_SUCCESS: '/register-success',
    CHANGE_PASSWORD: '/change-password',

    ADMIN_MANAGE_USER: '/admin/users',
    ADMIN_MANAGE_SKILL: '/admin/skills',
    ADMIN_MANAGE_RULE: '/admin/rules',
    ADMIN_MANAGE_EVENT: '/admin/events',
    ADMIN_MANAGE_CONFESSION: '/admin/confessions',

    MANAGE_USER: '/manage-user',

    RULE: '/rules',
    EVENT: '/events',
    SKILL: '/skills',
    CONFESSION: '/confessions',

    CONTACT: '/contact',
    ABOUT: '/about',
    FAQ: '/faq',
}

export const ANONYMOUS_ALLOW_PATHS = [
    Paths.HOME,
    Paths.LOGIN,
    Paths.REGISTER,
    Paths.FORGOT_PASSWORD,
]