import request from '@/utils/requestLongTime'

export function listMall() {
  return request({
    url: '/config/mall',
    method: 'get'
  })
}


export function listExpress() {
  return request({
    url: '/config/express',
    method: 'get'
  })
}


export function listOrder() {
  return request({
    url: '/config/order',
    method: 'get'
  })
}

export function listWx() {
  return request({
    url: '/config/wx',
    method: 'get'
  })
}

export function listWeishang() {
  return request({
    url: '/config/weishang',
    method: 'get'
  })
}


export function listSysParas() {
  return request({
    url: '/config/sysParas',
    method: 'get'
  })
}
export function saveConfig(data) {
  return request({
    url: '/config/saveConfig',
    method: 'post',
    data
  })
}

export function updateToolFlowStartMainCurrentInfo(query) {
  return request({
    url: '/config/updateToolFlowStartMainCurrentInfo',
    method: 'get',
    params: query
  })
}

export function updateToolFlowStartMainLuPaiInfo(query) {
  return request({
    url: '/config/updateToolFlowStartMainLuPaiInfo',
    method: 'get',
    params: query
  })
}

export function updateToolFlowStartMainSongSheInfo(query) {
  return request({
    url: '/config/updateToolFlowStartMainSongSheInfo',
    method: 'get',
    params: query
  })
}
export function updateToolFlowStartMainJiaopianInfo(query) {
  return request({
    url: '/config/updateToolFlowStartMainJiaopianInfo',
    method: 'get',
    params: query
  })
}

export function updateToolFlowStartMainRukuInfo(query) {
  return request({
    url: '/config/updateToolFlowStartMainRukuInfo',
    method: 'get',
    params: query
  })
}

export function updateShouldFinishTime(query) {
  return request({
    url: '/config/updateShouldFinishTime',
    method: 'get',
    params: query
  })
}

export function updateNodeFactDays(query) {
  return request({
    url: '/config/updateNodeFactDays',
    method: 'get',
    params: query
  })
}




