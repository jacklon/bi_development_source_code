import request from '@/utils/request'

export function listJob(query) {
  return request({
    url: '/job/list',
    method: 'get',
    params: query
  })
}

export function updateJob(data) {
  return request({
    url: '/job/update',
    method: 'post',
    data
  })
}

export function createJob(data) {
  return request({
    url: '/job/create',
    method: 'post',
    data
  })
}

export function startJob(data) {
  return request({
    url: '/job/start',
    method: 'post',
    data
  })
}

export function deleteJob(query) {
  return request({
    url: '/job/delete',
    method: 'get',
    params: query
  })
}


export function executeJob(query) {
  return request({
    url: '/job/execute',
    method: 'get',
    params: query
  })
}

export function resumeJob(query) {
  return request({
    url: '/job/resume',
    method: 'get',
    params: query
  })
}

export function pauseJob(query) {
  return request({
    url: '/job/pause',
    method: 'get',
    params: query
  })
}

export function detailJob(query) {
  return request({
    url: '/job/detail',
    method: 'get',
    params: query
  })
}









