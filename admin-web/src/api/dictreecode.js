import request from '@/utils/request'

export function listDicTreeCode(query) {
  return request({
    url: '/treecode/list',
    method: 'get',
    params: query
  })
}

export function createDicTreeCode(data) {
  return request({
    url: '/treecode/create',
    method: 'post',
    data
  })
}

export function readDicTreeCode(id) {
  return request({
    url: '/treecode/read',
    method: 'get',
    params: { id }
  })
}

export function readDicTreeByMenuPath(query) {
  return request({
    url: '/treecode/readByMenuPath',
    method: 'get',
    params: query
  })
}


export function updateDicTreeCode(data) {
  return request({
    url: '/treecode/update',
    method: 'post',
    data
  })
}

export function deleteDicTreeCode(data) {
  return request({
    url: '/treecode/delete',
    method: 'post',
    data
  })
}

export function deleteDicTreeCodeList(data) {
  return request({
    url: '/treecode/deletelist',
    method: 'post',
    data
  })
}
