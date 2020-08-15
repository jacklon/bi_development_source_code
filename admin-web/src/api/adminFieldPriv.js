import request from '@/utils/request'
import Qs from 'qs'

export function listAdminFieldPriv(query) {
  return request({
    url: '/adminFieldPriv/list',
    method: 'get'
  })
}


export function createAdminFieldPriv(data) {
  return request({
    url: '/adminFieldPriv/create',
    method: 'post',
    data
  })
}

export function readAdminFieldPriv(id) {
  return request({
    url: '/adminFieldPriv/read',
    method: 'get',
    params: { id }
  })
}

export function deleteAdminFieldPriv(query) {
  return request({
    url: '/adminFieldPriv/delete',
    method: 'get',
    params: query
  })
}





