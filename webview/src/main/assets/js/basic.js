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
