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

    MANAGE_USER: '/manage-user',
}

export const ANONYMOUS_ALLOW_PATHS = [
    Paths.HOME,
    Paths.LOGIN,
    Paths.REGISTER,
]