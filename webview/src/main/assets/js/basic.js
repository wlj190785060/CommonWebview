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

function replaceImage(index,url){
    var imgs = document.getElementsByTagName("img"),arr=[];
    for(var i = 0; i < imgs.length; i++) {
      if (imgs[i].parentNode.tagName !== 'A') {
        arr.push(imgs[i]);
      }
    }
    if(arr.length > 0){
       var a = arr[index];
       alert(url);
       a.setAttribute("src",url);
    }
 }

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

   function applyNightTheme() {
       var ui_mode_link = document.getElementById("ui_mode_link");
//       ui_mode_link.setAttribute("href", "file:///android_asset/css/night.css");
   }

   function applyDayTheme() {
       var ui_mode_link = document.getElementById("ui_mode_link");
//       ui_mode_link.setAttribute("href", "file:///android_asset/css/day.css");
   }
