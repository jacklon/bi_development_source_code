import request from '@/utils/request'

export function listBCorpInfo(query) {
  return request(
    {
      url: 'bCorpInfo/list',
      method: 'get',
      params: query
    }
  )
}

export function listAllBCorpInfo(query) {
  return request(
    {
      url: 'bCorpInfo/listAll',
      method: 'get',
      params: query
    }
  )
}


export function createBCorpInfo(data) {
  return request({
    url: '/bCorpInfo/create',
    method: 'post',
    data
  })
}

export function readBCorpInfo(id) {
  return request(
    {
      url: '/bCorpInfo/read',
      method: 'get',
      params: { id }
    }
  )
}

export function updateBCorpInfo(data) {
  return request({
    url: '/bCorpInfo/update',
    method: 'post',
    data
  })
}

export function deleteBCorpInfo(data) {
  return request({
    url: '/bCorpInfo/delete',
    method: 'post',
    data
  })
}

export function deleteBCorpInfoList(data) {
  return request({
    url: '/bCorpInfo/deletelist',
    method: 'post',
    data
  })
}

export function dataTransNewDept(query) {
  return request({
    url: '/bCorpInfo/dataTransNewDept',
    method: 'get',
    params: query
  })
}





