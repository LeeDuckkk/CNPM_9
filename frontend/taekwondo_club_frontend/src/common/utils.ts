import { FILTER_PARAMS_SEPARATOR, FilterOperator } from '@/common/constants'
import type { CommonFilter, FilterOperation } from '@/common/models'

export const toSearchParam = <T extends CommonFilter>(
  filterObj: T,
  filterOperator: FilterOperation<T>
) => {
  const filterEntry = Object.keys(filterObj)
    .map((key) => {
      const _key = key as keyof T
      if (filterOperator[_key] && filterObj[_key]) {
        const value = filterObj[_key] + ''
        if (value.includes(FilterOperator.NEGATION)) {
          return (
            FilterOperator.NEGATION +
            key +
            filterOperator[_key] +
            value.replace(FilterOperator.NEGATION, '')
          )
        } else {
          return key + filterOperator[_key] + value
        }
      }
      return null
    })
    .filter((entry) => !!entry)
  return filterEntry.join(FILTER_PARAMS_SEPARATOR)
}

export const toQueryParam = (serverParams: any) => {
  const results = {} as any
  if (!serverParams) return results
  Object.keys(serverParams).forEach((key) => {
    const value = serverParams && serverParams[key]
    console.log(key, value)
    if (value && ('' + value).length > 0) {
      results[key] = serverParams[key]
    }
  })
  console.log(results)
  return results
}
