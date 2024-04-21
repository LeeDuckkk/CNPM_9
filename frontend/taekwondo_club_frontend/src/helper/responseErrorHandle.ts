import { ElMessage } from 'element-plus'

export const processErrorMessage = (e: any, customMessage?: string) => {
  let message
  if (e.response && e.response.data && e.response.data.description) {
    message = e.response.data.description
  } else if (e.response && e.response.data && e.response.data.data) {
    message = e.response.data.data
  } else {
    message = customMessage ? customMessage : 'Có lỗi xảy ra! Vui lòng thử lại.'
  }
  ElMessage.error(message)
  return message
}
