Centos安装Docker：



Docker方式安装ClickHouse:


1.docker run -d --name ck --ulimit nofile=262144:262144 -p 8123:8123 -p 9000:9000 -p 9009:9009 yandex/clickhouse-server
2.docker ps -a 查看容器

docker exec -it ck /bin/bash 进入容器
clickhouse-client 进入clickhouse命令行
show databases 查看所有的数据库
7.clickhouse 允许远程访问，将clickhouse的配置文件拷贝出来
docker cp ck:/etc/clickhouse-server/ /opt/clickhouse-server/
8.修改 /opt/clickhouse-server/config.xml 中 65行 注释去掉<listen_host>::</listen_host>
9.用自定义配置文件启动容器
docker run -d --name docker-clickhouse --ulimit nofile=262144:262144 -p 8123:8123 -p 9000:9000 -p 9009:9009 -v /etc/clickhouse-server/config.xml:/etc/clickhouse-server/config.xml yandex/clickhouse-server
端口必须映射出来，不然阿里云的远程访问不到端口


docker run -d --name ck --ulimit nofile=262144:262144 -p 8123:8123 -p 9000:9000 -p 9009:9009 -v /etc/clickhouse-server/config.xml:/etc/clickhouse-server/config.xml yandex/clickhouse-server


docker run -d --name=ck \
-p 8123:8123 -p 9009:9009 -p 9090:9000 \
--ulimit nofile=262144:262144 \
-v /opt/clickhouse/data:/var/lib/clickhouse:rw \
-v /opt/clickhouse-server/config.xml:/etc/clickhouse-server/config.xml \
-v /opt/clickhouse-server/users.xml:/etc/clickhouse-server/users.xml \
-v /opt/clickhouse-server/log:/var/log/clickhouse-server:rw \
yandex/clickhouse-server

//运行已有容器实例
docker run yandex/clickhouse-server:latest  ##my/yandex/clickhouse-server:latest为镜像名和标签

建表语句:
CREATE TABLE test02( id UInt16,col1 String,col2 String,create_date date ) ENGINE = MergeTree(create_date, (id), 8192);
创建视图语句： 
 CREATE VIEW (MATERIALIZED) view_test02 AS
  SELECT 
 	  *
 	FROM test02
进入容器
docker exec -it 02e8394f96f2 /bin/bash
启动server
docker run  --ulimit nofile=262144:262144 --volume=$HOME/some_clickhouse_database:/var/lib/clickhouse yandex/clickhouse-server

 
拷贝到容器
docker cp /Users/apple/sfz/10W-12W.csv   02e8394f96f2:/root/10W-12W.csv
clickhouse-client --query="INSERT INTO database.table_name FORMAT CSVWithNames" < /path/import_filename.csv






