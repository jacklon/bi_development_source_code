
//ArrayBuffer转字符串
//实际调用
// ab2str(arrayBuffer,function(str){
//   //str为字符串
// });
export function arraybuffer2str(u,f) {
  var b = new Blob([u]);
  var r = new FileReader();
  r.readAsText(b, 'utf-8');
  r.onload = function (){if(f)f.call(null,r.result)}
}


//字符串转字符串ArrayBuffer
// str2ab(str,function(ab){
//   //ab为ArrayBuffer
// });
export function str2arraybuffer(s,f) {
  var b = new Blob([s],{type:'text/plain'});
  var r = new FileReader();
  r.readAsArrayBuffer(b);
  r.onload = function (){if(f)f.call(null,r.result)}
}


