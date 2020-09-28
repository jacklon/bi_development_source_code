import request from '@/utils/request'

// datax插件api

export function listDataConnection(params) {
  return request({
    url: '/dataConnection/list',
    method: 'get',
    params
  })
}


export function updateDataConnection(data) {
  return request({
    url: '/dataConnection/update',
    method: 'post',
    data
  })
}

export function createDataConnection(data) {
  return request({
    url: '/dataConnection/create',
    method: 'post',
    data
  })
}

export function deleteDataConnection(data) {
  return request({
    url: '/dataConnection/delete',
    method: 'post',
    params: data
  })
}

export function testDataConnection(data) {
  return request({
    url: '/dataConnection/test',
    method: 'post',
    data
  })
}

export function getAllDataConnection(params) {
  return request({
    url: '/dataConnection/all',
    method: 'get',
    params
  })
}
