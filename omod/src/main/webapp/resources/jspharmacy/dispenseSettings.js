var binTable;
var binTable2;
var oFormObjectForm;
var totalElements = 0;
var nTrIn;
var batchEditTr;
var batchaData;
var batchLink
var oCache = {
    iCacheLower:-1
};

$j("#errorDivStore").hide();


$j("#dispensevoid").hide();


$j("#parent_div_2").hide();


$j("#parent_div_1").hide();

$j("#lower").hide();


$j("#dispenseextra").validate();

$j("#dispensevoid").validate();
$j("#dispenseVal").validate();

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#toutgoing', 'drugOutgoing.form');

}


function RefreshInventory(tableId) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function fnFormatDetails(nTr) {

    var batchaData = binTable.fnGetData(nTr);


    show();

}


function fnDetails(nTr) {
    if (confirm('Are you sure?')) {
        var batchaData = binTable.fnGetData(nTr);

        dataString = "bindrug=" + batchaData[2] + "&" + "binquantityin=" + batchaData[3] + "&binmax=" + batchaData[4] + "&binmin=" + batchaData[5] + "&location=" + batchaData[11] + "&date=" + batchaData[8] + "&outgoing=" + batchaData[1] + "&binedit=" + false + "&less=" + false;

        $j.ajax({
            type:"POST",
            url:"drugBincard.form",
            data:dataString,
            success:function () {


            }
        });

    }
    else {

    }
}


function SetDispenseTable(id) {
    oCache.iCacheLower = -1;
    var url = 'drugDispense.form?drugID=' + id;

    if ($j('#lower').is(':visible')) {

        oCache.iCacheLower = -1;
        $j('#tdispense').dataTable().fnReloadAjax(url);

    }
    else {

        binTable = $j('#tdispense').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bAutoWidth:false,
            bServerSide:true,
            bProcessing:true,
            sAjaxSource:url,

            "fnServerData":fnDataTablesPipeline,
            "fnRowCallback":function (nRow, batchaData, iDisplayIndex) {


                var htm = '<ul class="popSet">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popSet" id=' + "popSet" + batchaData[2] + '>';


                if (batchaData[10] == "void") {
                    htm += '<li> <a href="#" id="deleteBatch" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
                }


                htm += '<li> <a href="#" id="cancelBatch"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


                htm += '</ul></li></ul>';


                $j('td:eq(0)', nRow).html(htm);

                return nRow;
            },
            "aoColumnDefs":[
                {
                    "bVisible":false,
                    "aTargets":[ 0 ]
                },
                {
                    "bVisible":false,
                    "aTargets":[ 2 ]
                },
                {
                    "bVisible":false,
                    "aTargets":[ 10 ]
                }
            ]
        });


    }

    $j("#lower").show();

}

$j('#tdispense tbody td ul').live('click', function () {


    nTrIn = this.parentNode.parentNode;
    batchaData = binTable.fnGetData(nTrIn);
    batchLink = "#popSet" + batchaData[2];


    $j(batchLink).show();

});


$j('#cancelBatch').live('click', function () {
    $j("ul .popSet").hide();

});
$j('#deleteBatch').live('click', function () {


    $j("ul .popSet").hide();

    var oFormObjectB = document.forms['dispensevoid'];
    oFormObjectB.elements["dispenseuuidvoid"].value = batchaData[2];


    $j("#dispensevoid").show();


});

$j('#editBatch').live('click', function () {


});

$j("form#filteroutgoing").submit(function () {

    if ($j("#filteroutgoing").valid()) {
        AutoReload();


        return false;
    }
});


getDrugFilter();

$j.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings, sNewSource) {
    oSettings.sAjaxSource = sNewSource;
    this.fnClearTable(this);
    this.oApi._fnProcessingDisplay(oSettings, true);
    var that = this;

    $j.getJSON(oSettings.sAjaxSource, null, function (json) {
        /* Got the data - add it to the table */
        for (var i = 0; i < json.aaData.length; i++) {
            that.oApi._fnAddData(oSettings, json.aaData[i]);
        }

        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        that.fnDraw(that);
        that.oApi._fnProcessingDisplay(oSettings, false);
    });
}


function setTable(uuid) {
    var url = 'drugDispenseStore.form?uuid=' + uuid;
    if ($j('#parent_div_1').is(':visible')) {


        oCache.iCacheLower = -1;
        $j('#tinventoryset').dataTable().fnReloadAjax(url);

    }
    else {


        binTable2 = $j('#tinventoryset').dataTable({

            bJQueryUI:true,
            bRetrieve:true,
            bAutoWidth:false,
            bServerSide:true,
            bProcessing:true,
            sAjaxSource:url,
            "aoColumnDefs":[
                {
                    "bVisible":false,
                    "aTargets":[ 0 ]
                }
            ]
        });

    }
    $j("#parent_div_2").show();

    $j("#parent_div_1").show();


}


function voidData(nTr) {
    var batchaData = binTable.fnGetData(nTr);

    var dataVal = "outgoinguuidvoid=" + batchaData[1] + "&outgoingreason=" + "Dataadded";
    $j.ajax({
        type:"POST",
        url:"drugIncoming.form",
        data:dataVal,


        success:function () {

            AutoReload();
        }
    });
}


$j('#tinventoryset').delegate(' tbody td  input', 'click', function () {
    nTrIn = this.parentNode.parentNode;

    var aData2 = binTable2.fnGetData(nTrIn);
    var oFormObject = document.forms['dispenseextra'];

    var myCars = new Array();
    myCars[0] = oFormObject.elements["inventoryNo"].value;


    var myBoolean = new Boolean();
    myBoolean = false;
    var myBooleanTwo = new Boolean();
    myBooleanTwo = false;
    oFormObjectForm = document.forms['dispenseextra'];

    var val = oFormObjectForm.elements['inventoryNo'].value;
    if (val.length == 0) {


    }
    else {


    }
    for (var i = 0; i < myCars.length; i++) {


        if (myCars[i] == aData2[0]) {
            myBoolean = true;
            break;
        }


    }

    if (!myBoolean) {

        var batchaData = binTable2.fnGetData(nTrIn);
        oFormObjectForm = document.forms['dispenseextra'];

        oFormObjectForm.elements['inventoryNo'].value = batchaData[0];


    }
    else {
        var batchaData = binTable2.fnGetData(nTrIn);
        oFormObjectForm = document.forms['dispenseextra'];


        oFormObjectForm.elements['inventoryNo'].value = "";


    }

});


$j("form#dispensevoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#dispensevoid").valid()) {
        dataString = $j("#dispensevoid").serialize();
        var strUser = $j("#dispensedrug").val();

        dataString += "&dispensedrug=" + strUser;


        $j.ajax({
            type:"POST",
            url:"drugDispense.form",
            data:dataString,
            success:function () {
                $j("#dispensevoid").hide();//
                var oFormObject = document.forms['dispensevoid'];

                oFormObject.elements["dispenseuuidvoid"].value = "";
                oFormObject.elements["dispensereason"].value = "";

                var strUser = $j("#dispensedrug").val();

                SetDispenseTable(strUser);
            }
        });
        return false;
    }
});


$j("form#dispenseextra").submit(function () {
    // we want to store the values from the form input box, then send via ajax below


    if ($j("#dispenseextra").valid()) {


        dataString = $j("#dispenseextra").serialize();


        var strUser = $j("#dispensedrug").val();


        var drugId = strUser.substr(0, strUser.indexOf('|'));


        var drug = strUser.substr(strUser.indexOf('|') + 1);
        var oFormObject = document.forms['dispenseextra'];

        var quantity = oFormObject.elements["quantity"].value;

        var msg = drug + "0" + quantity;
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
        var optionVal = $j("#option option:selected").text();
        dataString += "&dispensedrug=" + drugId + "&optionval=" + optionVal;
        $j.ajax({
            type:"POST",
            url:"drugDispense.form",
            data:dataString,
            success:function () {


                var strUser = $j("#dispensedrug").val();

                var drug = strUser.substr(0, strUser.indexOf('|'));

                SetDispenseTable(drug);

                var oFormObject = document.forms['dispenseextra'];


                oFormObject.elements["inventoryNo"].value = "";

                oFormObject.elements["dispenseedit"].value = "false";


                oFormObject.elements["price"].value = "";
                oFormObject.elements["value"].value = "";


            }
        });


        return false;


    }


});

function loadImage(url, type, msg, height, moduleWidth, wideFactor, format, qz, hrp) {

    $j("#spinner").show();

    var imageSource = url + "moduleServlet/pharmacy/Barcode?type=" + type + "&msg=" + msg + "&height=" + height + "&mw=" + moduleWidth + "&wf=" + wideFactor + "&fmt=" + format + "&qz=" + qz + "&hrp=" + hrp;
    showImage(imageSource);
    return false;
}

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
function showImage(src) {

    $j("#barcodeImg").empty();
    $j("#cmdDownDiv").empty();
    $j("#barcodeImg").append("<img id='theImg' src='" + src + "'/>");


    $j("#spinner").hide();

}
$j("#hideoutgoingform").click(function () {

    $j("#hideoutgoingform").hide();
    $j("#parent_div_1").hide();

    $j("#parent_div_2").hide();

});


$j("#outgoingform").click(function () {
    getDataLocation();
    getDataDrug();


    getDataSupplier();
    getDataTransactionType();
    getDataLocationTwo();
    show();


});

function getDataDrug() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop",
        function (result) {

            $j("#outgoingdrug").get(0).options.length = 0;
            $j("#outgoingdrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#outgoingdrug").get(0).options[$j(
                        "#outgoingdrug").get(0).options.length] = new Option(
                        value, value);
                });

        });

}

function getDataLocation() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function (result) {

            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");

            $j.each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
                });

        });


}
function getDataLocationTwo() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function (result) {
            $j("#destination").get(0).options.length = 0;
            $j("#destination").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#destination").get(0).options[$j("#destination").get(0).options.length] = new Option(value, value);
                });

        });


}

function getDataSupplier() {

    $j
        .getJSON(
        "supplierName.form?drop=drop",
        function (result) {

            $j("#supplierout").get(0).options.length = 0;
            $j("#supplierout").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#supplierout").get(0).options[$j(
                        "#supplierout").get(0).options.length] = new Option(
                        value, value);
                });

        });


}

function getDataTransactionType() {

    $j
        .getJSON(
        "transactionsName.form?drop=drop",
        function (result) {

            $j("#transactions").get(0).options.length = 0;
            $j("#transactions").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#transactions").get(0).options[$j(
                        "#transactions").get(0).options.length] = new Option(
                        value, value);
                });

        });


}
function getDataTotal(drug) {

    var url = "transactionsName.form?drop=total&drug=" + drug;
    $j
        .getJSON(url
        ,
        function (result) {

            var oFormObject = document.forms['dispenseVal'];

            oFormObject.elements["totalstore"].value = result;
        });


}


$j("#dispensedrug").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&bar=bar&" + dataString, function (result) {

            $j("#dispensedrug").removeClass('working');

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

        var strUser = ui.item.label
        var drugId = strUser.substr(0, strUser.indexOf('|'));

        setTable(drugId);

        SetDispenseTable(drugId);

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

function show() {


    $j("#outgoingVal").show("slow");

}