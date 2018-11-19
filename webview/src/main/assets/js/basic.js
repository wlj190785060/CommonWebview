// 预览图片
function imageBrowse(jsObject,index) {
    jsObject.imageBrowse(index);
}

// 预览超链接图片
function imageABrowse(jsObject,index) {
    jsObject.imageABrowse(index);
}

//暂停所有音频
 function musicPause(){
    var audio = document.getElementsByTagName("audio")
    for(var i = 0; i < audio.length; i++) {
        var a = document.getElementsByTagName("audio")[i];
        a.pause();
    }

 }
