$j("#parent_div_2").hide();


$j("#barcodeform").validate();

function showPrint() {
    var content = $j("#barcodeImg").html();
    var pwin = window.open('', 'print_content', 'width=300,height=200');
    pwin.document.open();
    pwin.document.write('<html><body onload="window.print()">' + content
        + '</body></html>');
    pwin.document.close();
    setTimeout(function () {
        pwin.close();
    }, 1000);
}


getDrugFilter();

$j("form#barcodeform").submit(function () {
    if ($j("#barcodeform").valid()) {

        var oFormObject = document.forms['barcodeform'];


        var drug = oFormObject.elements["barcodedrug"].value;

        var drugId = drug.substr(drug.indexOf('|') + 1);

        var frequency = oFormObject.elements["frequency"].value;

        var duration = oFormObject.elements["duration"].value;

        var quantity = oFormObject.elements["quantity"].value;
        var msg = drugId + "0" + frequency + "0" + duration + "0" + quantity;
        var type = "code39";
        var height = "10mm";
        var moduleWidth = "0.12mm";
        var wideFactor = "2.5";
        var format = "png";

        var qz = "10mw";
        var hrp = "none";
        var url = jQuery.Page.context;
        loadImage(url, type, msg, height, moduleWidth, wideFactor, format, qz,
            hrp);
        return false;
    }
});


function loadImage(url, type, msg, height, moduleWidth, wideFactor, format, qz, hrp) {

    $j("#spinner").show();
    var imageSource = url + "moduleServlet/pharmacy/Barcode?type=" + type + "&msg=" + msg + "&height=" + height + "&mw=" + moduleWidth + "&wf=" + wideFactor + "&fmt=" + format + "&qz=" + qz + "&hrp=" + hrp;
    showImage(imageSource);
    return false;
}

function showImage(src) {

    $j("#barcodeImg").empty();
    $j("#cmdDownDiv").empty();
    $j("#barcodeImg").append("<img id='theImg' src='" + src + "'/>");


    $j("#spinner").hide();
    $j("#parent_div_2").show();

}
function getDrugFilter() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop&bar=bar",
        function (result) {

            $j("#barcodedrug").get(0).options.length = 0;
            $j("#barcodedrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#barcodedrug").get(0).options[$j(
                        "#barcodedrug").get(0).options.length] = new Option(
                        value, value);
                });

        });

}
