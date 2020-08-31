let fs =require('fs')
let path =require('path')
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const MonacoEditorPlugin = require('monaco-editor-webpack-plugin')
const productionGzipExtensions = ['js', 'css']
function resolve(name){
    return __dirname + '/' + name
}
module.exports = {
    assetsDir:"static",
    pages:{
        index:{
            entry:'src/main.js',
            // template:'public/engine-h5-long.html',
            filename:'index.html'
        }
    },
    // devServer: {
    //     proxy: {
    //         '/': {
    //             target: 'http://localhost:4000',
    //             ws: false,
    //             changeOrigin: true,
    //             pathRewrite:{}
    //         }
    //     }
    // },
    css:{
        loaderOptions:{
            sass:{
                data:fs.readFileSync(path.resolve(__dirname,'./src/common/styles/variables.scss'),'utf-8')
            }
        }
    },
    // productionSourceMap:process.env.NODE_ENV !=='production',
    productionSourceMap: true, // 生产生成 sourceMap 文件

    configureWebpack:config=>{
        if(process.env.NODE_ENV === 'production'){
            config.plugins.push(
                new CompressionWebpackPlugin({
                    filename:'[path].gz[query]',
                    algorithm:'gzip',
                    test:new RegExp('\\.('+productionGzipExtensions.join('|')+')$'),
                    threshold:10240,
                    minRatio:0.8
                })
            )
        }
        else{

        }
        config.plugins.push(new MonacoEditorPlugin({
            // https://github.com/Microsoft/monaco-editor-webpack-plugin#options
            // Include a subset of languages support
            // Some language extensions like typescript are so huge that may impact build performance
            // e.g. Build full languages support with webpack 4.0 takes over 80 seconds
            // Languages are loaded on demand at runtime
            //'javascript', 'css', 'html', 'typescript',
            languages: [ 'json', 'sql', 'javascript']
          }))
    },
    chainWebpack: config=>{
        config.resolve.alias
            .set('@',path.resolve('src'))
            .set('@src',path.resolve('src'))
            .set('@plugins',path.resolve('plugins'))
            .set('@server',path.resolve('server'))
        config.module
            .rule('js')
            .include.add(/engine-template/).end()
            .include.add(/src/).end()
            .include.add(/common/).end()
            .use('babel')
            .loader('babel-loader')
            .tap(options=>{
                return options
            })
        // 设置svg
        const svgRule = config.module.rule('svg')
        svgRule.uses.clear()
        svgRule
            .test(/\.svg$/)
            .include.add( path.resolve('src/icons'))
            // .include.add('D:\\我的工作\\自有研发\\大屏展示\\BI开发源码\\admin-web\\src\\icons')
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'icon-[name]'
            })
            .end()


        // config.module
        //     .rule('svg')
        //     .exclude.add(resolve('src/icons'))
        //     .end();

        // config.module
        //     .rule('icons')
        //     .test(/\.svg$/)
        //     .include.add(resolve('src/icons'))
        //     .end()
        //     .use('svg-sprite-loader')
        //     .loader('svg-sprite-loader')
        //     .options({
        //         symbolId: 'icon-[name]'
        //     });

        const fileRule=config.module.rule('file')
        fileRule.uses.clear()
        fileRule
            .test(/\.svg$/)
            // .exclude.add('src/icons')
            .exclude.add(path.resolve('src/icons'))
            .end()
            .use('file-loader')
            .loader('file-loader')
            .end()
    }
}
