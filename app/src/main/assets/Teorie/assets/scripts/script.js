function setCurrentStyle(currentStyle){
    var fileref = document.createElement("link")
    fileref.setAttribute("rel", "stylesheet")
    fileref.setAttribute("type", "text/css")
    if(currentStyle === "Dark Theme"){
        fileref.setAttribute("href", "../assets/styles/dark_theme.css")   
    } else {
        fileref.setAttribute("href", "../assets/styles/light_theme.css")  
    }
    if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
}