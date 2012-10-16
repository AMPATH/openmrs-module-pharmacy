$(document).ready(function () {
    initHide();
});

function initHide() {
    $('div.customize').hide();
}

function showTable() {
    $('div.customize').show('normal');
}

function showPrint() {
    var content = $("#barcodeImg").html();
    var pwin = window.open('', 'print_content', 'width=300,height=200');
    pwin.document.open();
    pwin.document.write('<html><body onload="window.print()">' + content
        + '</body></html>');
    pwin.document.close();
    setTimeout(function () {
        pwin.close();
    }, 1000);
}

function getValues() {
    var type = $("#type").attr('value');
    var msg = $("#msg").attr('value');
    var height = $("#height").attr('value');
    var moduleWidth = $("#moduleWidth").attr('value');
    var wideFactor = $("#wideFactor").attr('value');
    var format = $("#format").attr('value');
    var hrSize = $("#hrSize").attr('value');
    var hrFont = $("#hrFont").attr('value');
    var res = $("#res").attr('value');
    loadImage(type, msg, height, moduleWidth, wideFactor, format, hrSize,
        hrFont, res);
}

function loadImage(type, msg, height, moduleWidth, wideFactor, format, hrSize, hrFont, res) {
    /***************************************************************************
     * Defaults ****************** type = 'code39'; height='1.0cm'; moduleWidth =
     * '0.2mm'; wideFactor = '2'; format = 'png'; hrSize = '6pt'; hrFont =
     * 'Helvetica'; res = '300';
     **************************************************************************/
    var imageSource = openmrsContextPath
        + "s/moduleServlet/registration/BarcodeServlet?type=" + type
        + "&msg=" + msg + "&height=" + height + "&mw=" + moduleWidth
        + "&wf=" + wideFactor + "&fmt=" + format + "&hrsize=" + hrSize
        + "&hrfont=" + hrFont + "&res=" + res;
    $("#loader").addClass("loading");
    showImage(imageSource);
    return false;
}

function showImage(src) {
    $("#loader img").fadeOut("normal").remove();
    var image = new Image();
    $(image).load(function () {
        $(this).hide();
        $("#loader").append(this).removeClass("loading");
        $(image).fadeIn("slow");
    });
    $(image).attr("src", src);
    $(image).attr("id", 'barcodeImg');
}