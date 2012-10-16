var doTable;
$j("#doses").validate();
$j("#dosesvoid").validate();
$j("#doses").hide();//
$j("#dosesvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}
var oCache = {
    iCacheLower:-1
};

function AutoReload() {
    RefreshTable('#tdoses', 'drugDoses.form');

}
function getData() {

    $j.getJSON("drugDetails.form?drop=drop", function (result) {

        $j("#dosesdrug").get(0).options.length = 0;
        $j("#dosesdrug").get(0).options[0] = new Option("Select", "-1");
        $j
            .each(result,
            function (index, value) { //bincard"stateList

                $j("#dosesdrug").get(0).options[$j("#dosesdrug").get(
                    0).options.length] = new Option(value,
                    value);
            });

    });

    $j.getJSON("drugFrequency.form?drop=drop",
        function (result) {

            $j("#dosesfrequency").get(0).options.length = 0;
            $j("#dosesfrequency").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(result, function (index, value) { //bincard"stateList

                $j("#dosesfrequency").get(0).options[$j("#dosesfrequency")
                    .get(0).options.length] = new Option(value, value);
            });

        });

    $j("#doses").show("slow");
}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = doTable.fnGetData(nTr);

    var oFormObject = document.forms['doses'];

    oFormObject.elements["dosequantity"].value = aData[4];
    oFormObject.elements["dosesedit"].value = 'true';
    oFormObject.elements["dosesuuid"].value = aData[1];

    $j("#doses").show();//

}

doTable = $j('#tdoses').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugDoses.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});

$j('#tdoses tbody td img').live('click', function () {
    var nTr = this.parentNode.parentNode;
    getData();
    fnFormatDetails(nTr);

});

$j('#tdoses tbody td a').live('click', function () {
    var nTr = this.parentNode.parentNode;

    var aData = doTable.fnGetData(nTr);

    var fbject = document.forms['dosesvoid'];

    fbject.elements["dosesuuidvoid"].value = aData[1];

    $j("#dosesvoid").show();//
});

$j("form#doses").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#doses").valid()) {
        dataString = $j("#doses").serialize();

        $j.ajax({
            type:"POST",
            url:"drugDoses.form",
            data:dataString,
            success:function () {
                $j("#doses").hide();//
                AutoReload();
                var oFormObject = document.forms['doses'];

                oFormObject.elements["dosesedit"].value = 'false';

                oFormObject.elements["dosequantity"].value = "";

            }
        });
        return false;
    }
});

$j("form#dosesvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#dosesvoid").valid()) {
        dataString = $j("#dosesvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugDoses.form",
            data:dataString,
            success:function () {
                $j("#dosesvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});

$j("#adddoses").click(function () {

    getData();
});