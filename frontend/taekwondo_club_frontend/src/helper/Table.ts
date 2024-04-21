import { formatDate, formatDateTime } from '@/helper/TimeHelper'

export const tableDateTimeFormatter = (row: any, column: any) => {
  return formatDateTime(row[column.property])
}

export const tableDateFormatter = (row: any, column: any) => {
  return formatDate(row[column.property])
}

export const tableFullDateTimeFormatter = (row: any, column: any) => {
  const time = formatDateTime(row[column.property])
  const [gioPhut, ngayThangNam] = time.split(' ')

  const [gio, phut] = gioPhut.split(':')
  const [ngay, thang, nam] = ngayThangNam.split('/')

  const date = new Date(
    Number(nam),
    Number(thang) - 1,
    Number(ngay),
    Number(gio),
    Number(phut)
  )

  const res = date.toLocaleString('vi', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })

  return res.replace(/ /g, ', ')
}
