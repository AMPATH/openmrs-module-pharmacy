var oTable;
var oCache = {
    iCacheLower:-1
};

$j("#formulation").validate(); //formulationvoid
$j("#formulationvoid").validate(); //
$j("#formulationvoid").hide();//


$j("#cstrength")
    .click(function () {

        $j.getScript("$j{pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugstrength.js",
            function () {

            });

    });

$j("#cunits")
    .click(
    function () {

        $j
            .getScript(
            "$j{pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugunit.js",
            function () {

            });

    });

$j("#cnames")
    .click(
    function () {

        $j
            .getScript(
            "$j{pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugdetails.js",
            function () {

            });

    });

$j("#cdoses")
    .click(
    function () {

        $j
            .getScript(
            "$j{pageContext.request.contextPath}/moduleResources/pharmacy/jspharmacy/drugdoses.js",
            function () {

            });

    });


function RefreshTable(tableId, urlData) {


    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();

}

function AutoReload() {
    RefreshTable('#tformulation',
        'drugFormulation.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = oTable.fnGetData(nTr);

    var oFormObject = document.forms['formulation'];

    oFormObject.elements["formulationname"].value = aData[2];
    oFormObject.elements["edit"].value = 'true';
    oFormObject.elements["uuid"].value = aData[1];

}

oTable = $j('#tformulation').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    sAjaxSource:'drugFormulation.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

//						oTable.fnSetColumnVis( 1, false );

$j('#tformulation tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;

    fnFormatDetails(nTr);

});

$j('#tformulation tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = oTable.fnGetData(nTr);

    var oFormObject = document.forms['formulationvoid'];

    oFormObject.elements["uuidvoid"].value = aData[1];
    $j("#formulationvoid").show();//
});

$j("form#formulation").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#formulation").valid()) {
        dataString = $j("#formulation").serialize();

        $j.ajax({
            type:"POST",
            url:"drugFormulation.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['formulation'];

                oFormObject.elements["edit"].value = 'false';

                oFormObject.elements["formulationname"].value = "";

            }
        });
        return false;
    }
});

$j("form#formulationvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#formulationvoid").valid()) {

        dataString = $j("#formulationvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugFormulation.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#formulationvoid").hide();//
                var oFormObject = document.forms['formulationvoid'];

                oFormObject.elements["reason"].value = "";


            }
        });
        return false;
    }
});
