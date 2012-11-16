function fnSetKey(aoData, sKey, mValue) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            aoData[i].value = mValue;
        }
    }
}

function fnGetKey(aoData, sKey) {
    for (var i = 0, iLen = aoData.length; i < iLen; i++) {
        if (aoData[i].name == sKey) {
            return aoData[i].value;
        }
    }
    return null;
}

function fnDataTablesPipeline2(sSource, aoData, fnCallback) {
    var iPipe = 5;
    /* Ajust the pipe size */

    var bNeedServer = false;
    var sEcho = fnGetKey(aoData, "sEcho");
    var iRequestStart = fnGetKey(aoData, "iDisplayStart");
    var iRequestLength = fnGetKey(aoData, "iDisplayLength");
    var iRequestEnd = iRequestStart + iRequestLength;
    oCache.iDisplayStart = iRequestStart;


    bNeedServer = true;


    /* sorting etc changed? */
    if (oCache.lastRequest && !bNeedServer) {
        for (var i = 0, iLen = aoData.length; i < iLen; i++) {
            if (aoData[i].name != "iDisplayStart" && aoData[i].name != "iDisplayLength" && aoData[i].name != "sEcho") {
                if (aoData[i].value != oCache.lastRequest[i].value) {
                    bNeedServer = true;
                    break;
                }
            }
        }
    }

    /* Store the request for checking next time around */
    oCache.lastRequest = aoData.slice();

    if (bNeedServer) {
        if (iRequestStart < oCache.iCacheLower) {
            iRequestStart = iRequestStart - (iRequestLength * (iPipe - 1));
            if (iRequestStart < 0) {
                iRequestStart = 0;
            }
        }

        oCache.iCacheLower = iRequestStart;
        oCache.iCacheUpper = iRequestStart + (iRequestLength * iPipe);
        oCache.iDisplayLength = fnGetKey(aoData, "iDisplayLength");
        fnSetKey(aoData, "iDisplayStart", iRequestStart);
        fnSetKey(aoData, "iDisplayLength", iRequestLength * iPipe);

        $j.getJSON(sSource, aoData, function (json) {
            /* Callback processing */
            oCache.lastJson = jQuery.extend(true, {}, json);

            if (oCache.iCacheLower != oCache.iDisplayStart) {
                json.aaData.splice(0, oCache.iDisplayStart - oCache.iCacheLower);
            }
            json.aaData.splice(oCache.iDisplayLength, json.aaData.length);

            fnCallback(json)
        });
    }
    else {
        json = jQuery.extend(true, {}, oCache.lastJson);
        json.sEcho = sEcho;
        /* Update the echo for each response */
        json.aaData.splice(0, iRequestStart - oCache.iCacheLower);
        json.aaData.splice(iRequestLength, json.aaData.length);
        fnCallback(json);
        return;
    }
}


var binTable;
var uoTable;
var patientID;
var patientName;
var url;
var dateGiven;

var quantity;

var price;
var valgiven;
var method;
var priceOne;
var valgivenOne;
var methodOne;
var id = jQuery.Pid.id;

var url = 'dispense.form?patientID=' + id;
var urljson = '' + jQuery.Page.context + 'module/jsonforms/jsonforms.form?id='
    + id;


$j.getJSON("dispense.form?age=" + id, function (result) {
    $j.each(result, function (key, val) {


        jQuery.Age = {
            age:val
        };

    });

});


uoTable = $j('#tunits').dataTable({

    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,

    sAjaxSource:url,
    "fnServerData":fnDataTablesPipeline2,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        },
        {
            "fnRender":function (oObj) {
                return '<a href=#?uuid=' + oObj.aData[1] + '>' + 'View' + '</a>';

            },
            "aTargets":[ 4 ]
        }
    ]
});

binTable = $j('#tforms')
    .dataTable(
    {

        bRetrieve:true,
        bServerSide:true,
        bProcessing:true,
        bPaginate:false,
        bFilter:false,
        bInfo:false,

        "fnRowCallback":function (nRow, aData, iDisplayIndex) {

            $j('td:eq(0)', nRow)
                .html(
                '<img src="'
                    + jQuery.Page.context
                    + 'moduleResources/pharmacy/images/edit2.png" />');

            $j('td:eq(4)', nRow).html(aData[4]);

            return nRow;
        },
        sAjaxSource:urljson,
        "aoColumnDefs":[
            {
                "bVisible":false,
                "aTargets":[ 1 ]
            }
        ]
    });

$j('#tforms').delegate(
    'tbody tr td img',
    'click',
    function () {

        $j("#psychiatryform").empty();
        $j("#generalform").empty();

        var nTr = this.parentNode.parentNode;
        //getData(nTr);
        var aData = binTable.fnGetData(nTr);

        var pid = aData[4];

        var formid = aData[2];

        $j.getJSON("" + jQuery.Page.context
            + "module/jsonforms/jsonforms.form?datajson=" + aData[1],
            function (result) {


                getDataDispense(result, pid, formid);

            });

        return false;

    });

$j('#tunits').delegate(
    'tbody td a',
    'click',
    function () {

        var nTr = this.parentNode.parentNode;
        //getData(nTr);
        var aData = uoTable.fnGetData(nTr);


        $j.getJSON("dispense.form?encounterDetails=" + aData[1], function (result) {
            $j("#encountform").empty();

            $j.each(result, function (index, value) { //bincard"stateList
                if (index == 1) {

                    $j("#encountform").dialog('option', 'title', value);

                } else
                    $j('<dl><dt></dt><dd > -' + value + '</dd></dl> ')
                        .appendTo('#encountform');

            });

            $j("#encountform").dialog("open");

        });

    });

function RefreshTable(tableId) {
    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}
$j("#dispenseform").dialog({
    autoOpen:false,
    height:600,
    width:1300,
    cache:false,
    modal:true
});
$j("#encountform").dialog({

    title:"Patient",
    autoOpen:false,
    height:400,
    width:600,
    cache:false,
    modal:true
});
function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth()
        + (12 * (date2.getFullYear() - date1.getFullYear()));
}

function getDrugDispense(drug) {

    $j.getJSON("drugDetails.form?drop=drug&id=" + drug, function (result) {

        $j.each(result, function (index, value) { //bincard"stateList

        });

    });

}

function getDataDispense(data, pid, formid) {

    if (formid.substring(0, 7) == "General") {
        // ...

        $j("#dispenseform").dialog("open");
        $j("#generalform").empty();
        $j("#generalform").buildForm(JSON.parse(data));

        $j.getScript("" + jQuery.Page.context
            + "moduleResources/pharmacy/jspharmacy/generalFormScript.js",
            function () {

            });


        jQuery.Pid = {
            Pi:pid
        };


        $j.getJSON("dispense.form?Pid=" + pid, function (result) {

            $j.each(result, function (index, value) { //bincard"stateList

                oFormObject = document.forms['generalform'];
                oFormObject.elements["patient|1#1"].value = value;

            });

        });


        $j(".ui-dform-tr input").attr("style", "width: 100px;");

        var oFormObject = document.forms['generalform'];
        oFormObject.elements["Patient_id|2#2"].value = pid;


    } else if (formid.substring(0, 7) == "Psychia") {
        // ...

        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#psychiatryform").buildForm(JSON.parse(data));

        $j.getScript("" + jQuery.Page.context
            + "moduleResources/pharmacy/jspharmacy/psychiatryformData.js",
            function () {

            });


        $j.getJSON("dispense.form?Pid=" + pid, function (result) {

            $j.each(result, function (index, value) { //bincard"stateList

                oFormObject = document.forms['psychiatryform'];
                oFormObject.elements["patient|1#1"].value = value;

            });

        });


        $j(".ui-dform-tr input").attr("style", "width: 100px;");

        var oFormObject = document.forms['psychiatryform'];
        oFormObject.elements["Patient_id|2#2"].value = pid;

        $j('#psychiatryform').delegate('input', 'focus',
            function () {


                $j(this).closest('.ui-dform-tr').css('background-color', '#dfdfdf');
            }).delegate('input', 'blur', function () {
                $j(this).closest('.ui-dform-tr').css('background-color', 'white');
            });

    } else if (formid.substring(0, 7) == "Barcode") {
        // ...

        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#psychiatryform").buildForm(JSON.parse(data));

        $j.getScript("" + jQuery.Page.context
            + "moduleResources/pharmacy/jspharmacy/barcodeFormScript.js",
            function () {

            });


        $j.getJSON("dispense.form?Pid=" + pid, function (result) {

            $j.each(result, function (index, value) { //bincard"stateList

                oFormObject = document.forms['psychiatryform'];
                oFormObject.elements["patient|1#1"].value = value;

            });

        });


        $j(".ui-dform-tr input").attr("style", "width: 100px;");

        var oFormObject = document.forms['psychiatryform'];
        oFormObject.elements["Patient_id|2#2"].value = pid;
    }
    else{
      alert("cv");


        $j("#dispenseform").dialog("open");
        $j("#psychiatryform").empty();
        $j("#psychiatryform").buildForm(JSON.parse(data));
    }

}
