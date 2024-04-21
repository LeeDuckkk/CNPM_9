import { FilterOperator } from '@/common/constants'

export interface ListQueryParam {
  page: number
  size: number
}

export interface SortOption {
  key: string
  title: string
  sortField: string
  sortAscending: boolean
}

export const DEFAULT_SORT_OPTION: SortOption = {
  key: 'updateTime-desc',
  title: 'Thời gian cập nhật',
  sortAscending: false,
  sortField: 'updateTime',
}

export function getDefaultQueryParam(routeParams: any): ListQueryParam {
  let page = 0
  try {
    if (routeParams && routeParams.page) {
      page = Math.max(Number.parseInt(routeParams.page) - 1, 0)
    }
  } catch (e) {
    console.log(e)
  }
  return { page, size: 10 }
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface CommonFilter {}

export interface ServerParams {
  sortAscending?: boolean
  sortField?: string
  page?: number
  size?: number
  searchKey?: string
}

export type FilterOperation<T extends CommonFilter> = {
  [k in keyof T]: typeof FilterOperator[keyof typeof FilterOperator]
}

export type FilterDescription<T extends CommonFilter> = {
  [k in keyof T]: FilterDescriptionValue
}

export interface DictionaryFilter extends CommonFilter {
  word?: string
}

export interface PropertyEntityFilter extends CommonFilter {
  id?: string
  indexStatus?: string | null
  verifyStatus?: string | null
  title?: string | null
}

export interface PropertyEntityPlanFilter extends CommonFilter {
  id?: string
  name?: string | null
  planId?: string | null
}

export interface PropertyEntityOrganizationFilter extends CommonFilter {
  id?: string
  name?: string | null
  organizationId?: string | null
}

export interface PropertyEntityFulFilter extends CommonFilter {
  id?: string
  name?: string | null
  email?: string | null
  planId?: string | null
  organizationId?: string | null
  emailConfirmed?: string | null
}

export interface PropertyEntityEmailFilter extends CommonFilter {
  id?: string
  name?: string | null
  email?: string | null
  emailConfirmed?: string | null
}

export interface FilterDescriptionValue {
  label: string
  displayValue: (value: any, extra?: any) => string
}

export const DictionaryFilterOperator: FilterOperation<DictionaryFilter> = {
  word: FilterOperator.CONTAIN,
}
export const PropertyEntityFilterOperator: FilterOperation<PropertyEntityFilter> =
  {
    id: FilterOperator.EQUAL,
    indexStatus: FilterOperator.EQUAL,
    verifyStatus: FilterOperator.EQUAL,
    title: FilterOperator.CONTAIN,
  }

export const PropertyEntityOrganizationFilterOperator: FilterOperation<PropertyEntityPlanFilter> =
  {
    id: FilterOperator.EQUAL,
    name: FilterOperator.CONTAIN,
    planId: FilterOperator.EQUAL,
  }
export const PropertyEntityOrganizationTokenFilterOperator: FilterOperation<PropertyEntityOrganizationFilter> =
  {
    id: FilterOperator.EQUAL,
    name: FilterOperator.CONTAIN,
    organizationId: FilterOperator.EQUAL,
  }
export const PropertyEntityEmailAndEmailConfirmedFilterOperator: FilterOperation<PropertyEntityEmailFilter> =
  {
    id: FilterOperator.EQUAL,
    email: FilterOperator.CONTAIN,
    name: FilterOperator.CONTAIN,
    emailConfirmed: FilterOperator.EQUAL,
  }
export const PropertyEntityFullFilter: FilterOperation<PropertyEntityFulFilter> =
  {
    id: FilterOperator.EQUAL,
    name: FilterOperator.CONTAIN,
    planId: FilterOperator.EQUAL,
    organizationId: FilterOperator.EQUAL,
    emailConfirmed: FilterOperator.EQUAL,
    email: FilterOperator.EQUAL,
  }

export interface ScheduleTaskFilter extends PropertyEntityFilter {
  affectEntityId: string
  type: string
  status: string
}

export const ScheduleTaskFilterOperator: FilterOperation<ScheduleTaskFilter> = {
  ...PropertyEntityFilterOperator,
  affectEntityId: FilterOperator.EQUAL,
  type: FilterOperator.EQUAL,
  status: FilterOperator.EQUAL,
}

export interface ScheduleLogFilter extends PropertyEntityFilter {
  taskId: string
  type: string
  status: string
}

export function overrideSearchKey(
  params: any,
  filterKey: string,
  filterValue: any
): any {
  const overrideParams = { ...params }
  const existReg = new RegExp(`${filterKey}:[a-zA-Z0-9]+,*`, 'g')
  const searchKey =
    params.searchKey && params.searchKey.length > 0
      ? params.searchKey.replace(existReg, '')
      : ''
  overrideParams.searchKey = `${filterKey}:${filterValue},` + searchKey
  return overrideParams
}
