var binTable;
var oCache = {
    iCacheLower:-1
};

$j("#bincard").hide();
$j("#highvoid").hide();
function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#thighbincard', 'highBincard.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    $j("#bincard").show();//
    var aData = binTable.fnGetData(nTr);

    var oFormObject = document.forms['bincard'];

    oFormObject.elements["binquantityin"].value = aData[3];
    oFormObject.elements["binmax"].value = aData[4];
    oFormObject.elements["binmin"].value = aData[5];

    oFormObject.elements["binbatch"].value = aData[7];
    oFormObject.elements["bins11"].value = aData[8];
    oFormObject.elements["binexpire"].value = aData[6];
    oFormObject.elements["delivery"].value = aData[9];


    oFormObject.elements["binedit"].value = 'true';
    oFormObject.elements["binuuid"].value = aData[1];

    $j("#bincard").show();//

}

$j("#binexpire").datepicker();

$j(function () {

    $j("#binexpire").datepicker();

});
getDrugFilter();

binTable = $j('#thighbincard').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    fnDrawCallback:function (oSettings) {
        for (var i = 0, iLen = oSettings.aoData.length; i < iLen; i++) {
            var status = oSettings.aoData[i]._aData[2];
            var colorCSS = null;

            colorCSS = 'statusRed';

            oSettings.aoData[i].nTr.className += " " + colorCSS;

        }
    },
    sAjaxSource:'highBincard.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 11 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 0 ]
        }
    ]
});


getFilter();
$j(function () {

    $j("#date").datepicker();

});
//$j('#tbincard tbody td img').live('click', function() {
//	var nTr = this.parentNode.parentNode;
//	getData();
//	fnFormatDetails(nTr);
//
//});

$j('#thighbincard tbody td img').live('click', function () {

    var nTr = this.parentNode.parentNode;

    var aData = binTable.fnGetData(nTr);

    var fbject = document.forms['highvoid'];

    fbject.elements["binuuidvoid"].value = aData[1];

    $j("#highvoid").show();//

});

$j("form#bincard").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#bincard").valid()) {
        dataString = $j("#bincard").serialize();

        $j.ajax({
            type:"POST",
            url:"highBincard.form",
            data:dataString,
            success:function () {

                AutoReload();

                $j("#bincard").hide();//

                var oFormObject = document.forms['bincard'];

                oFormObject.elements["binedit"].value = 'false';
                oFormObject.elements["binmax"].value = "";
                oFormObject.elements["binmin"].value = "";

                oFormObject.elements["binquantityin"].value = "";

            }
        });
        return false;
    }
});
$j("form#highvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#highvoid").valid()) {
        dataString = $j("#highvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"highBincard.form",
            data:dataString,
            success:function () {
                var fbject = document.forms['highvoid'];

                fbject.elements["binuuidvoid"].value = "";

                $j("#highvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});

$j("#bincardform").click(function () {

    getData();
    getDataLocation();
});

function getData() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop",
        function (result) {

            $j("#bindrug").get(0).options.length = 0;
            $j("#bindrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#bindrug").get(0).options[$j(
                        "#bindrug").get(0).options.length] = new Option(
                        value, value);
                });

        });

}


function getFilter() {


}


$j("#filterdrughigh").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdrughigh").removeClass('working');

            response($j.each(result, function (index, item) {

                return {
                    label:item,
                    value:item
                }
            }));

        });

    },
    minLength:3,
    select:function (event, ui) {
        binTable.fnFilter(ui.item.label);
        // log( ui.item ?
        // "Selected: " + ui.item.label :
        // "Nothing selected, input was " + this.value);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
function getDrugFilter() {


}

function getDataLocation() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function (result) {

            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#location").get(0).options[$j(
                        "#location").get(0).options.length] = new Option(
                        value, value);
                });

        });

    $j("#bincard").show("slow");
    $j("#bincardform").show("slow");
}
