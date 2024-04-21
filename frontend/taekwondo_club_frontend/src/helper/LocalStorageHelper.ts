export const putLocalStorageObject = (key: string, data: any) => {
    localStorage.setItem(key, JSON.stringify(data))
}

export const getLocalStorageObject = (
    key: string,
    defaultValue: any = null
) => {
    const value = localStorage.getItem(key)
    return value != null ? JSON.parse(value) : defaultValue
}

export const putLocalStorage = (key: string, value: any) => {
    localStorage.setItem(key, value)
}

export const getLocalStorage = (key: string) => {
    return localStorage.getItem(key)
}

export const removeLocalStorage = (key: string) => {
    return localStorage.removeItem(key)
}
