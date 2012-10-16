var ooTable;
var oCache = {
    iCacheLower:-1
};

$j("#nameform").validate(); //
$j("#namevoid").validate(); //

$j("#name").hide();//
$j("#namevoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tname', 'drugName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = ooTable.fnGetData(nTr);

    var oFormObject = document.forms['nameform'];

    oFormObject.elements["namename"].value = aData[2];
    oFormObject.elements["nameedit"].value = 'true';
    oFormObject.elements["nameuuid"].value = aData[1];

}

oooTable = $j('#tname').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugName.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

//						ooTable.fnSetColumnVis( 1, false );

$j('#tname tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;

    fnFormatDetails(nTr);

});

$j('#tname tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = oooTable.fnGetData(nTr);

    var oFormObject = document.forms['namevoid'];

    oFormObject.elements["nameuuidvoid"].value = aData[1];
    $j("#namevoid").show();//
});

$j("form#nameform").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#nameform").valid()) {
        dataString = $j("#nameform").serialize();

        $j.ajax({
            type:"POST",
            url:"drugName.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['nameform'];

                oFormObject.elements["nameedit"].value = 'false';

                oFormObject.elements["namename"].value = "";

            }
        });
        return false;
    }
});

$j("form#namevoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#namevoid").valid()) {
        dataString = $j("#namevoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugName.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#namevoid").hide();//
                var oFormObject = document.forms['namevoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});
