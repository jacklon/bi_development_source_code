import request from '@/utils/request'

export function listSysOperationLog(query) {
  return request(
    {
      url: 'sysOperationLog/list',
      method: 'get',
      params: query
    }
  )
}

export function writeFlowStartDataLog(data) {
  return request(
    {
      url: 'sysOperationLog/writeFlowStartDataLog',
      method: 'post',
      data
    }
  )
}
