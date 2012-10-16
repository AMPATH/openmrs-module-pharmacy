var soTable;
$j("#strengthvoid").validate();
$j("#strength").validate();
$j("#strengthvoid").hide();//
var oCache = {
    iCacheLower:-1
};

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tstrength', 'drugStrength.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = soTable.fnGetData(nTr);

    var oFormObject = document.forms['strength'];

    oFormObject.elements["strengthname"].value = aData[2];
    oFormObject.elements["strengthedit"].value = 'true';
    oFormObject.elements["strengthuuid"].value = aData[1];

}

soTable = $j('#tstrength').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugStrength.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

$j('#tstrength tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;

    fnFormatDetails(nTr);

});

$j('#tstrength tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = soTable.fnGetData(nTr);

    var fbject = document.forms['strengthvoid'];

    fbject.elements["strengthuuidvoid"].value = aData[1];

    $j("#strengthvoid").show();//
});

$j("form#strength").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("form#strength").valid()) {
        dataString = $j("#strength").serialize();

        $j.ajax({
            type:"POST",
            url:"drugStrength.form",
            data:dataString,
            success:function () {
                var oFormObject = document.forms['strength'];

                oFormObject.elements["strengthedit"].value = 'false';
                oFormObject.elements["strengthname"].value = "";
                AutoReload();
            }
        });
        return false;
    }
});
$j("form#strengthvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#strengthvoid").valid()) {
        dataString = $j("#strengthvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugStrength.form",
            data:dataString,
            success:function () {
                $j("#strengthvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});
