function setCurrentStyle(currentStyle){
    var fileref = document.createElement("link")
    fileref.setAttribute("rel", "stylesheet")
    fileref.setAttribute("type", "text/css")
    if(currentStyle === "Dark Theme"){
        fileref.setAttribute("href", "../assets/styles/lessons_dark.css")   
    } else {
        fileref.setAttribute("href", "../assets/styles/lessons.css")  
    }
    if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
    AndroidInterface.showWebView()
}