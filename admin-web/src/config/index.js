import $config from '../../config'


const configDict={
    development:{
        baseURL:$config.devBaseUri
    },
    production:{
        baseURL:$config.prdBaseUri
    }
}

const currrentConfigKey=process.env.NODE_ENV
const isDev = process.env.NODE_ENV==='development'
const isTest=process.env.NODE_ENV==='development'

const configObj={
    isDevelop: (isDev||isTest),
    ...configDict[currrentConfigKey],
    baseURL: configDict[currrentConfigKey]["baseURL"],
    leftPanelWidth: 332,
    screenPixelWidth: 1920,
    screenPixelHeight: 1080,
    editorWrapPanelWidth: 2320,
    editorWrapPanelHeight: 1480,
    thumbnailWidth: 190,
    thumbnailHeight: 110
}

export default configObj
