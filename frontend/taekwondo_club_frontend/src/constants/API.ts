export const AuthAPI = {
    GET_USER_INFO: '/users/me',

    LOGIN_WITH_PASSWORD: '/auth/signin',
    FORGOT_PASSWORD: '/auth/forgot-password',
    RESET_PASSWORD: '/auth/reset-password',
    REGISTER: '/auth/register',
    LOGOUT: '/logout',
}

export const AnonymousApi = {
    RULE: '/rules',
    EVENT: '/events',
    SKILL: '/skills',
    CONFESSION: '/confessions',
}

export const UserApi = {
    EDIT_PROFILE: '/users/edit-profile',
}

export const AdminApi = {
    USER_MANAGE: '/admin/users',
    USERS: '/users',
    EVENTS: '/events',
    SKILLS: '/skills',
    RULES: '/rules',
}