import { reactive } from 'vue'

export default function useRefs<T extends object>() {
  const refs = reactive<T>({} as T)
  const toRef = (refName: keyof T) => (el: any) =>
    ((refs as T)[refName as keyof T] = el)

  return {
    refs,
    toRef,
  }
}
