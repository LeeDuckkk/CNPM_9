import { reactive } from 'vue'

export default function useRefs() {
  const $refs = reactive<Map<string, any>>(new Map<string, any>())
  const toRef = (refName: string) => (el: any) => $refs.set(refName, el)

  return {
    $refs,
    toRef,
  }
}
