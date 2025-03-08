export type Pagination<T = unknown> = {
  records: T[]
  pageNumber: number
  pageSize: number
  totalPage: number
  totalRow: number
}
