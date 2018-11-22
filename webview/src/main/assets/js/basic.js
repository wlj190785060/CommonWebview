function imageBrowse(jsObject,index) {
    alert(jsObject);
    jsObject.imageBrowse(index);
}

function imageABrowse(jsObject,index) {
    alert(jsObject);
    jsObject.imageABrowse(index);
}

function musicPause(){
    var audio = document.getElementsByTagName("audio")
    for(var i = 0; i < audio.length; i++) {
        var a = document.getElementsByTagName("audio")[i];
        a.pause();
    }

 }
