import request from '@/utils/request'

export function listTableFieldDesp(query) {
  return request({
    url: '/tableField/list',
    method: 'get',
    params: query
  })
}
export function getTableFieldDespForTableGrid(query) {
  return request({
    url: '/tableField/getTableFieldDespForTableGrid',
    method: 'get',
    params: query
  })
}

export function detail(query) {
  return request({
    url: '/tableField/detail',
    method: 'get',
    params: query
  })
}
export function getTableFieldDesp(query) {
  return request({
    url: '/tableField/getTableFieldDesp',
    method: 'get',
    params: query
  })
}



