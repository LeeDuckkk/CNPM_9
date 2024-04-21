export const CommonConfig = {
  INTERVAL_RELOAD_DATA: 15_000,
}
export const FilterOperator = {
  NONE: '',
  EQUAL: ':',
  NEGATION: '!',
  GREATER_THAN: '>',
  LESS_THAN: '<',
  CONTAIN: '~',
  IN: '#',
} as const
export const FILTER_PARAMS_SEPARATOR = ','
