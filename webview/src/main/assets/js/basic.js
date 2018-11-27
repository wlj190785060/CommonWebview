function imageBrowse(jsObject,index) {
       alert("haha");
    jsObject.imageBrowse(index);
}

function imageABrowse(jsObject,index) {
alert("heihei");
    jsObject.imageABrowse(index);
}

function musicPause(){
    var audio = document.getElementsByTagName("audio");
    for(var i = 0; i < audio.length; i++) {
        var a = document.getElementsByTagName("audio")[i];
        a.pause();
    }

 }
