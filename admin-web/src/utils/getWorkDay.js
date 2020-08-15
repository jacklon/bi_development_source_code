
function formatTen(f){
  if (parseInt(f,10)<10){
    return '0'+f;
  }
  return f;
}
function formateDate(date){
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  return year + "-" + formatTen(month) + "-" + formatTen(day);
}
export function getworkday(dat,itervalByDay,holiday){
  var holidayMap={};
  for(var i=0;i<holiday.length;i++){
    holidayMap[holiday[i]]='1';
  }
  var str=dat.split("-");
  var date=new Date();
  date.setUTCFullYear(str[0], str[1] - 1, str[2]);
  date.setUTCHours(0, 0, 0, 0);
  var millisceonds =date.getTime();
  for(var i=1;i<=itervalByDay;i++){
    millisceonds +=24*60*60*1000;
    date.setTime(millisceonds);
    if(date.getDay()==0||date.getDay()==6){
      i--;
    }else{//过滤节假日
      var d=formateDate(date);
      if(holidayMap[d]){
        i--;
      }
    }
  }
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();
  var rq = year + "-" + formatTen(month) + "-" + formatTen(day);
  return rq;
}
// getMap(holiday);
// var r = getworkday('2015-1-1',5);
// alert(r);
