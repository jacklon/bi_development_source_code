import request from '@/utils/request'
import Qs from 'qs'

export function listAdmin(query) {
  return request({
    url: '/admin/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}





export function queryAllAdmin(query) {
  return request({
    url: '/admin/queryall',
    method: 'get',
    params: query
  })
}

export function queryallByDeptId(query) {
  return request({
    url: '/admin/queryallByDeptId',
    method: 'get',
    params: query
  })
}


export function createAdmin(data) {
  return request({
    url: '/admin/create',
    method: 'post',
    data
  })
}

export function readminAdmin(id) {
  return request({
    url: '/admin/read',
    method: 'get',
    params: { id }
  })
}

export function updateAdmin(data) {
  return request({
    url: '/admin/update',
    method: 'post',
    data
  })
}

export function modifyPassword(data) {
  return request({
    url: '/admin/modifyPassword',
    method: 'post',
    data
  })
}

export function deleteAdmin(query) {
  return request({
    url: '/admin/delete',
    method: 'get',
    params: query
  })
}

// export function deleteAdmin(query) {
//   return request({
//     url: '/admin/delete',
//     method: 'post',
//     data
//   })
// }

export function changeAvatar(data) {
  return request({
    url: '/admin/changeAvatar',
    method: 'post',
    data
  })
}

export function updateFlowQuery(data) {
  return request({
    url: '/admin/updateFlowQuery',
    method: 'post',
    data
  })
}

export function updateManageBookYear(data) {
  return request({
    url: '/admin/updateManageBookYear',
    method: 'post',
    data
  })
}

export function updateTelPhone(data) {
  return request({
    url: '/admin/updateTelPhone',
    method: 'post',
    data
  })
}
export function getTelPhone(query) {
  return request({
    url: '/admin/getTelPhone',
    method:  'get',
    params: query
  })
}

