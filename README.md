# 数据挖掘

大数据挖掘系统，采用前后端分离技术:SpringBoot，Mybatis，Shiro，JWT，Vue & Element

DataX：解决数据导入导出问题
ClickHouse：解决列式存储和计算问题

 
## 技术栈

> 1. Spring Boot
> 2. Vue
> 3、DataX
> 4、ClickHouse 
> 

技术架构：
后端
    1、基础框架：Spring Boot 2.0.3.RELEASE
    2、持久层框架：Mybatis-plus_3.0.6
    3、安全框架：Apache Shiro 1.4.0-RC2，Jwt_3.4.1
    4、数据库连接池：阿里巴巴Druid 1.1.10
    5、缓存框架：redis
    6、日志打印：logback
    7、其他：fastjson，poi，Swagger-ui，quartz, lombok（简化代码）等。

前端
    1、Vue 2.5.22,Vuex,Vue Router
    2、Axios
    3、element-ui
    4、webpack
    5、vue-cropper - 头像裁剪组件
    6、@antv/g2 - Alipay AntV 数据可视化图表
    7、Viser-vue - antv/g2 封装实现
    8、eslint，@vue/cli 3.2.1
    9、vue-print-nb - 打印

开发环境

  语言：Java 8

  IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

  IDE(前端)： WebStorm 或者 IDEA

  依赖管理：Maven

   数据库：MySQL5.0 

   缓存：Redis

## 快速启动

 
2. 数据库依次导入litemall-db/sql下的数据库文件

    打包
        ```
        cd litemall
     
        cd ./admin-webin
        cnpm install
        
        cnpm run build:dep
        
        cd ..
        
        mvn clean package
        cp -f ./litemall-all/target/litemall-all-*-exec.jar ./deploy/litemall/litemall.jar
        ```
        如果是在Windows下，可以直接用命令方式启动：
        java -jar bianwu.jar --servercd.port=9090
        
        //如果在Linux下，用以下命令方式启动
        nohup java -jar --servercd.port=8089 litemall.jar & 
        
        //阿里云RDS本机端口映射
        netsh interface portproxy add v4tov4 listenport=映射端口 connectaddress=RDS服务器IP connectport=端口
        netsh interface portproxy delete v4tov4 listenport=映射端口
4. 启动管理后台前端

    打开命令行，输入以下命令
    ```bash
    npm install -g cnpm --registry=https://registry.npm.taobao.org
    cd litemall/litemall-admin
    cnpm install
    cnpm run dev
    ```
    此时，浏览器打开，输入网址`http://localhost:9527`, 此时进入管理后台登录页面。
    
   
## 开发计划
 
## 问题
 
//注册组件
mvn install:install-file -Dfile=taobao-sdk-java-auto_1455552377940-20160607.jar -DgroupId=com.taobao -DartifactId=api -Dversion=1.0 -Dpackaging=jar

如果删除了Mysql中的表字段，在视图里有恰好有这些字段，则用下面的方法查询视图语句:
SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS
WHERE  TABLE_NAME = '视图名称';

mysql 查询死锁并且删除死锁
SELECT * FROM INFORMATION_SCHEMA.INNODB_TRX;
kill 81431


 

原设计器有一个错误，导致图形显示问题：
好像是canvas-panel.vue文件中:className="item.uuid === activeElementUUID? 
'actived': 'noActived'影响的。  把className改成classNameActive


