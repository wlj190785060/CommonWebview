function imageBrowse(jsObject,index) {
    jsObject.imageBrowse(index);
}

function imageABrowse(jsObject,index) {
    jsObject.imageABrowse(index);
}

function musicPause(){
    var audio = document.getElementsByTagName("audio");
    for(var i = 0; i < audio.length; i++) {
        var a = document.getElementsByTagName("audio")[i];
        a.pause();
    }
 }

 //替换webview中的图片
  function replaceImage(index,url){
        var imgs = document.getElementsByTagName("img"),arr=[];
        for(var i = 0; i < imgs.length; i++) {
          if (imgs[i].parentNode.tagName !== 'A') {
             arr.push(imgs[i]);
          }
         }
         if(arr.length > 0){
             var a = arr[index];
             a.setAttribute("src",url);
         }
   }

 //替换webview中的超链接图片
  function replaceAImage(index,url){
        var imgs = document.getElementsByTagName("img"),arr=[];
        for(var i = 0; i < imgs.length; i++) {
          if (imgs[i].parentNode.tagName === 'A') {
             arr.push(imgs[i]);
          }
         }
         if(arr.length > 0){
             var a = arr[index];
             a.setAttribute("src",url);
         }
   }
