import request from '@/utils/request'

export function listDicTreeMain(query) {
  return request({
    url: '/treemain/list',
    method: 'get',
    params: query
  })
}

export function detailDicTreeMain(id) {
  return request({
    url: '/treemain/read',
    method: 'get',
    params: { id }
  })
}

export function createDicTreeMain(data) {
  return request({
    url: '/treemain/create',
    method: 'post',
    data
  })
}

export function readDicTreeMain(id) {
  return request({
    url: '/treemain/read',
    method: 'get',
    params: { id }
  })
}

export function updateDicTreeMain(data) {
  return request({
    url: '/treemain/update',
    method: 'post',
    data
  })
}

export function deleteDicTreeMain(data) {
  return request({
    url: '/dicmain/delete',
    method: 'post',
    data
  })
}
