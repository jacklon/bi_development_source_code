import $config from '../../config'


const mysqlConfigDict={
    development:{
        baseURL:$config.devMysqlDbServerBaseUri
    },
    production:{
        baseURL:$config.prdMysqlServerBaseUri
    }
}

const currrentConfigKey=process.env.NODE_ENV
const isDev = process.env.NODE_ENV==='development'
const isTest=process.env.NODE_ENV==='development'

const mysqlConfigObj={
    isDevelop: (isDev||isTest),
    ...mysqlConfigDict[currrentConfigKey],
    baseURL: mysqlConfigDict[currrentConfigKey]["baseURL"],
}

export default mysqlConfigObj
