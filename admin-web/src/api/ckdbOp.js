import request from '@/utils/request'

export function ckdbOpTest(query) {
  return request({
    url: '/ckdbop/test',
    method: 'get',
    params: query
  })
}
