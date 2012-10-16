var oCache = {
    iCacheLower:-1
};

var uoTable;
$j("#units").validate();
$j("#unitsvoid").validate();
$j("#unitsvoid").hide();//REGIMENNAMEVOID

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tunits', 'drugUnit.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = uoTable.fnGetData(nTr);

    var oFormObject = document.forms['units'];

    oFormObject.elements["unitsname"].value = aData[2];
    oFormObject.elements["unitsedit"].value = 'true';
    oFormObject.elements["unitsuuid"].value = aData[1];

}

uoTable = $j('#tunits').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugUnit.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

$j('#tunits tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;

    fnFormatDetails(nTr);

});

$j('#tunits tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = uoTable.fnGetData(nTr);

    var fbject = document.forms['unitsvoid'];

    fbject.elements["unitsuuidvoid"].value = aData[1];

    $j("#unitsvoid").show();//
});

$j("form#units").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#units").valid()) {

        dataString = $j("#units").serialize();

        $j.ajax({
            type:"POST",
            url:"drugUnit.form",
            data:dataString,
            success:function () {

                AutoReload();
                var oFormObject = document.forms['units'];

                oFormObject.elements["unitsedit"].value = 'false';
                oFormObject.elements["unitsname"].value = "";

            }
        });
        return false;
    }
});

$j("form#unitsvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#unitsvoid").valid()) {

        dataString = $j("#unitsvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugUnit.form",
            data:dataString,
            success:function () {
                $j("#unitsvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});
