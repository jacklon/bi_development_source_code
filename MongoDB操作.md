修改为decimal类型

db.shopgoods.find({'Pricing.Detail':{$type:2}}).forEach(function(x){x.Pricing.Detail=NumberDecimal(x.Pricing.Detail);db.shopgoods.save(x)})
db.shopgoods.find({'Pricing.Member':{$type:2}}).forEach(function(x){x.Pricing.Member=NumberDecimal(x.Pricing.Member);db.shopgoods.save(x)})

type类型:

Double	1	 
String	2	 
Object	3	 
Array	4	 
Binary data	5	 
Undefined	6	已废弃。
Object id	7	 
Boolean	8	 
Date	9	 
Null	10	 
Regular Expression	11	 
JavaScript	13	 
Symbol	14	 
JavaScript (with scope)	15	 
32-bit integer	16	 
Timestamp	17	 
64-bit integer	18	 
Min key	255	Query with -1.
Max key	127	 
增加字段

db.CornGoods.update({}, {$set:{"Price.ActualMoney":0}}, {multi: true})
db.CornOrders.update({}, {$set:{"ReceivingTime":ISODate("0001-01-01T08:00:00.000+08:00")}}, {multi: true}) 


修改列：
db.collection.update(
   <query>,
   <update>,
   {
     upsert: <boolean>,
     multi: <boolean>,
     writeConcern: <document>
   }
)
第一个参数 {"stopTime":{$gte:1}} 更新过滤的条件
第二个参数 {$set:{"createDate":new Date("2017-12-20T00:00:00.000Z")}} 更新的内容
第三个参数 false 如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入
第三个参数 true 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新
db.getCollection('collectionName').update({"stopTime":{$gte:1}},{$set:{"createDate":new Date("2017-12-20T00:00:00.000Z")}},false,true)

db.getCollection('wx_group_member_record_2020-06-21').find({"is_active": "N"}).forEach(
function(item){
db.getCollection('wx_group_member_record_2020-06-21').update({"_id":item._id},{$set:{"is_active":"Y"}})
}
)

在Mongo DB中增加可以访问任务数据库的用户：
进入MongoDB数据库:mongo -uroot -proot
db.createUser({ user: "bi" , pwd: "bi", roles: ["userAdminAnyDatabase", "dbAdminAnyDatabase", "readWriteAnyDatabase"]})

Mongo DB 中备份和恢复数据库
mongodump -h 主机名 -d 数据库名 -o 文件夹
mongorestore -h 主机名:27017 -d 数据库名 文件夹名称

//在最后要加一个授权数据库
const url = `mongodb://${config.db.user}:${config.db.pass}@${config.db.servername}:${config.db.port}/${config.db.DATABASE}?authSource=admin`

