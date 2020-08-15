import request from '@/utils/request'

export function listTableDesp(query) {
  return request({
    url: '/tableDesp/list',
    method: 'get',
    params: query
  })
}

export function queryAllTableDesp() {
  return request({
    url: '/tableDesp/queryall',
    method: 'get'
  })
}

export function detailTableDesp(query) {
  return request({
    url: '/tableDesp/detail',
    method: 'get',
    params: query
  })
}

export function createTableDesp(data) {
  return request({
    url: '/tableDesp/create',
    method: 'post',
    data
  })
}

export function readTableDesp(id) {
  return request({
    url: '/tableDesp/read',
    method: 'get',
    params: { id }
  })
}

export function updateTableDesp(data) {
  return request({
    url: '/tableDesp/update',
    method: 'post',
    data
  })
}

export function deleteTableDesp(data) {
  return request({
    url: '/tableDesp/delete',
    method: 'post',
    data
  })
}

export function getTableAndViewList() {
  return request({
    url: '/tableDesp/tableAndViewList',
    method: 'get'
  })
}

export function getTableListAll() {
  return request({
    url: '/tableDesp/tablelistAll',
    method: 'get'
  })
}

export function getTableFieldList(query) {
  return request({
    url: '/tableDesp/tablefieldlist',
    method: 'get',
    params: query
  })
}
