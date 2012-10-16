var doTable;
var oCache = {
    iCacheLower:-1
};

$j("#frequency").validate();//frequencyvoid
$j("#frequencyvoid").validate();//
$j("#frequencyvoid").hide();//
function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tfrequency', 'drugFrequency.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = doTable.fnGetData(nTr);

    var oFormObject = document.forms['frequency'];
    oFormObject.elements["frequencyname"].value = aData[2];
    oFormObject.elements["frequencyedit"].value = 'true';
    oFormObject.elements["frequencyuuid"].value = aData[1];

}

doTable = $j('#tfrequency').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugFrequency.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

var table = $j(doTable).dataTable();
oCache.iCacheLower = -1;

table.fnDraw();

$j('#tfrequency tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;

    fnFormatDetails(nTr);

});
$j('#tfrequency tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = doTable.fnGetData(nTr);

    var fbject = document.forms['frequencyvoid'];

    fbject.elements["frequencyuuidvoid"].value = aData[1];

    $j("#frequencyvoid").show();//
});

$j("form#frequency").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#frequency").valid()) {
        dataString = $j("#frequency").serialize();

        $j.ajax({
            type:"POST",
            url:"drugFrequency.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['frequency'];

                oFormObject.elements["frequencyedit"].value = 'false';
                oFormObject.elements["frequencyname"].value = "";

            }
        });
        return false;
    }
});

$j("form#frequencyvoid").submit(function () {

    // we want to store the values from the form input box, then send via ajax below
    if ($j("#frequencyvoid").valid()) {
        dataString = $j("#frequencyvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugFrequency.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#frequencyvoid").hide();//

            }
        });
        return false;
    }
});
