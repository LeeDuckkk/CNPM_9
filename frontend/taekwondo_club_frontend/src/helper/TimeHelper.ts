import { DateTime } from '@/constants/DateTime'
import dayjs from 'dayjs'

export const formatDateTime = (time: any) => {
  const currentDate = dayjs()
  const givenDate = dayjs(time)

  const diffInYears = currentDate.diff(givenDate, 'year')
  // console.log(formatDate(currentDate), formatDate(givenDate), diffInYears)

  if (diffInYears < -10) {
    return 'UNLIMITED'
  }
  return time ? dayjs(time).format(DateTime.DATE_TIME_FORMAT) : ''
}

export const formatDate = (time: any) => {
  const currentDate = dayjs()
  const givenDate = dayjs(time)

  const diffInYears = currentDate.diff(givenDate, 'year')

  if (diffInYears < -10) {
    return 'UNLIMITED'
  }
  return time ? dayjs(time).format(DateTime.DATE_FORMAT) : ''
}

export const formatDayMonth = (time: any) => {
  return time ? dayjs(time).format(DateTime.DAY_MONTH_FORMAT) : ''
}
