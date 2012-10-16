//$j("#drugs").validate(); 

$j("#drugsvoid").validate();
$j("#drugs").hide();//
$j("#drugsvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {

    RefreshTable('#tdrugs', 'drugDetails.form');

}
var oCache = {
    iCacheLower:-1
};

/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = droTable.fnGetData(nTr);

    var oFormObject = document.forms['drugs'];

    oFormObject.elements["drugsname"].value = aData[2];
    oFormObject.elements["drugsedit"].value = 'true';
    oFormObject.elements["drugsuuid"].value = aData[1];

}
function getData() {

    $j.getJSON("drugName.form?drop=drop", function (result) {

        $j("#drugsname").get(0).options.length = 0;
        $j("#drugsname").get(0).options[0] = new Option("Select", "-1");
        $j
            .each(result,
            function (index, value) { //bincard"stateList

                $j("#drugsname").get(0).options[$j("#drugsname").get(
                    0).options.length] = new Option(value,
                    value);
            });

    });
    $j
        .getJSON(
        "drugFormulation.form?drop=drop",
        function (result) {

            $j("#drugsformulation").get(0).options.length = 0;
            $j("#drugsformulation").get(0).options[0] = new Option(
                "Select", "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#drugsformulation").get(0).options[$j(
                        "#drugsformulation").get(0).options.length] = new Option(
                        value, value);
                });

        });

    $j.getJSON("drugStrength.form?drop=drop", function (result) {

        $j("#drugsstrength").get(0).options.length = 0;
        $j("#drugsstrength").get(0).options[0] = new Option("Select", "-1");
        $j.each(result,
            function (index, value) { //bincard"stateList

                $j("#drugsstrength").get(0).options[$j("#drugsstrength").get(
                    0).options.length] = new Option(value, value);
            });

    });
    $j.getJSON("drugUnit.form?drop=drop", function (result) {

        $j("#drugsunits").get(0).options.length = 0;
        $j("#drugsunits").get(0).options[0] = new Option("Select", "-1");
        $j
            .each(result,
            function (index, value) { //bincard"stateList

                $j("#drugsunits").get(0).options[$j("#drugsunits")
                    .get(0).options.length] = new Option(value,
                    value);
            });

    });

    $j("#drugs").show("slow");

}

droTable = $j('#tdrugs').dataTable({
    bJQueryUI:true,
    bServerSide:true,
    bRetrieve:true,
    bProcessing:true,
    sAjaxSource:'drugDetails.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

$j('#tdrugs tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;
    getData();
    fnFormatDetails(nTr);

});

$j('#tdrugs tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = droTable.fnGetData(nTr);

    var fbject = document.forms['drugsvoid'];

    fbject.elements["drugsuuidvoid"].value = aData[1];

    $j("#drugsvoid").show();//
});

$j("form#drugs").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#drugs").valid()) {

        dataString = $j("#drugs").serialize();

        $j.ajax({
            type:"POST",
            url:"drugDetails.form",
            data:dataString,
            success:function () {

                $j("#drugs").hide("slow");

                AutoReload();
                var oFormObject = document.forms['drugs'];

                oFormObject.elements["drugsedit"].value = 'false';
                oFormObject.elements["drugsname"].value = "";

            }
        });
        return false;

    }
});

$j("form#drugsvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#drugsvoid").valid()) {
        dataString = $j("#drugsvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugDetails.form",
            data:dataString,
            success:function () {
                $j("#drugsvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});

$j("#adddrug").click(function () {

    getData();

});
