var binTable;
var url;
var min;
var max;
var dataString;
var eSignCheck = false;
var link;
var editTr;

$j("#bincard").hide();
$j("#binvoid").hide();
$j("#tbincard").hide();

var oCache = {
    iCacheLower:-1
};


$j("#parent_field").hide();

$j("#incss").hide();


$j("#check").validate({});

$j("#bincard").validate({
    rules:{
        bindrug:{
            selectNone:true
        }
    }
});


var currentTime = new Date();
var month = currentTime.getMonth() + 1;
var day = currentTime.getDate();
var year = currentTime.getFullYear();

//compare to get the diffrence

t2 = day + "/" + month + "/" + year;


var y = t2.split("/");


var dateCurrent = new Date(y[2], (y[1] - 1), y[0]);


$j("#binvoid").validate();

getDrugCategory();


function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tbincard', 'drugBincard.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['bincard'];

    oFormObject.elements["binquantityin"].value = aData[4];
    oFormObject.elements["binmax"].value = aData[5];
    oFormObject.elements["binmin"].value = aData[6];

    oFormObject.elements["binbatch"].value = aData[9];
    oFormObject.elements["bins11"].value = aData[10];

    oFormObject.elements["delivery"].value = aData[11];


    oFormObject.elements["binedit"].value = 'true';
    oFormObject.elements["binuuid"].value = aData[2];

    $j("#bincard").show();//

}

$j("#binexpire").datepicker();

$j(function () {

    $j("#binexpire").datepicker();

});


function det(aData) {


    $j.ajax({
        type:"GET",
        url:"drugBincard.form?druguuidshow=" + aData,
        success:function () {


        }
    });

}
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

function tableSetUp(category) {

    url = 'drugBincard.form?category=' + category;

    if ($j('#incss').is(':visible')) {


        oCache.iCacheLower = -1;

        $j('#tbincard').dataTable().fnReloadAjax(url);

    }
    else {

        binTable = $j('#tbincard').dataTable({
            bJQueryUI:true,
            bRetrieve:true,
            bAutoWidth:false,
            bServerSide:true,
            bProcessing:true,
            bLengthChange:false,
            bPaginate:true,

            "fnRowCallback":function (nRow, aData, iDisplayIndex) {


                t1 = aData[7];


                var y = t1.split("-");


                var dateGiven = new Date(y[0], (y[1]), y[2]);
                var num = months_between(dateGiven, dateCurrent);
                if (num > 4 && num <= 6)
                    $j('td:eq(5)', nRow).css("background-color", "#FFCC00");
                else if (num > 2 && num <= 4)
                    $j('td:eq(5)', nRow).css("background-color", "#FF9900");
                else if (num <= 2)
                    $j('td:eq(5)', nRow).css("background-color", "#FF0000");


                if (aData[4] <= aData[6])
                    $j('td:eq(4)', nRow).css("background-color", "#FFCC00");

                var htm = '<ul class="popMenu">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popOut" id=' + "popOut" + aData[9] + '>';


                if (aData[0] == "edit") {
                    htm += '<li> <a href="#"  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

                }

                if (aData[13] == "void") {
                    htm += '<li> <a href="#" id="void" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
                }


                htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


                htm += '</ul></li></ul>';


                $j('td:eq(0)', nRow).html(htm);

                return nRow;
            },
            bInfo:false,
            sAjaxSource:url,
            "fnServerData":fnDataTablesPipeline,
            "aoColumnDefs":[
                {
                    "bVisible":false,
                    "aTargets":[ 2 ]
                },
                {
                    "bVisible":false,
                    "aTargets":[ 8 ]
                },
                {
                    "bVisible":false,
                    "aTargets":[ 0 ]
                },
                {
                    "bVisible":false,
                    "aTargets":[ 13 ]
                }


            ]
        }).rowGrouping({    bHideGroupingColumn:false,
                iGroupingColumnIndex:10,
                bExpandableGrouping:true,
                bExpandSingleGroup:true,
                iExpandGroupOffset:-1 })
    }

    $j("#incss").show();

    $j("#tbincard").show();


    $j("#parent_field").show();

}


$j('select#filtercategory').change(function () {


    var e = document.getElementById("filtercategory");
    var str = e.options[e.selectedIndex].value;


    if (str !== "-1") {
        tableSetUp(str);
    }
});

getDrugFilter();


$j(function () {

    $j("#date").datepicker();

});


$j("#edit").live('click', function () {
    $j("ul .popOut").hide();

    getData();

    getUserPass();
    fnFormatDetails(editTr);

    eSignCheck = true;

});

$j("#cancel").live('click', function () {

    $j("ul .popOut").hide();
});

$j("#void").live('click', function () {

    $j("ul .popOut").hide();
    var aData = binTable.fnGetData(editTr);

    var fbject = document.forms['binvoid'];

    fbject.elements["binuuidvoid"].value = aData[1];

    $j("#binvoid").show();//
});

$j('#tbincard').delegate('tbody td ul', 'click', function () {
    editTr = this.parentNode.parentNode;
    var aData = binTable.fnGetData(editTr);
    link = "#popOut" + aData[9];

    $j(link).show();


});


$j("#detailsformCheck").dialog({
    autoOpen:false,
    height:250,
    width:300,
    cache:false,
    modal:true,
    buttons:{
        "Ok":function () {


            if ($j("#check").valid()) {
                $j(this).dialog('close');
                var oFormObject = document.forms['check'];

                var val = oFormObject.elements["pass"].value;
                var valt = oFormObject.elements["password"].value;
                if (val == valt) {

                    $j.ajax({
                        type:"POST",
                        url:"drugBincard.form",
                        data:dataString,
                        success:function () {


                            var oFormcheck = document.forms['check'];

                            oFormcheck.elements["password"].value = "";


                            var oFormObject = document.forms['bincard'];

                            oFormObject.elements["binedit"].value = 'false';
                            oFormObject.elements["binmax"].value = "";
                            oFormObject.elements["binmin"].value = "";

                            oFormObject.elements["binquantityin"].value = "";

                            oFormObject.elements["binbatch"].value = "";
                            oFormObject.elements["bins11"].value = "";

                            oFormObject.elements["delivery"].value = "";
                            oFormObject.elements["binexpire"].value = "";


                            oFormObject.elements["binedit"].value = 'true';
                            oFormObject.elements["binuuid"].value = "";
                            $j("#bincard").hide();//

                        }
                    });


                    oCache.iCacheLower = -1;


                    $j('#tbincard').dataTable().fnReloadAjax("drugBincard.form");

                    //AutoReload();

                }
                else
                    alert("Wrong id try again");


            }


        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});
$j('#password').change(function () {


});

$j("#detailsformBin").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {

            $j(this).dialog('close');
            if (eSignCheck) {


                $j("#detailsformCheck").dialog("open");

            }


        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});


$j("form#check").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#check").valid()) {
        $j("#detailsformCheck").dialog('destroy');

    }
});


$j("form#bincard").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#bincard").valid()) {
        dataString = $j("#bincard").serialize();


        var myResult = dataString.split("&");

        $j("#detailsformBin").empty();

        for (i = 0; i < myResult.length; i++) {
            if (i == 2)
                $j('<dl><dt></dt><dd > -' + "Drug " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 3)
                $j('<dl><dt></dt><dd > -' + "Quantity " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 4)
                $j('<dl><dt></dt><dd > -' + "Max value " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 5)
                $j('<dl><dt></dt><dd > -' + "Min value " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 6)
                $j('<dl><dt></dt><dd > -' + "Batch Id " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 7)
                $j('<dl><dt></dt><dd > -' + "S11 No " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 8)
                $j('<dl><dt></dt><dd > -' + "Expire Date " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 9)
                $j('<dl><dt></dt><dd > -' + "Delivery No " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');
            else if (i == 10)
                $j('<dl><dt></dt><dd > -' + "Edit Reason" + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformBin');

        }
        $j("#detailsformBin").dialog("open");

        return false;
    }
});


$j("form#binvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#binvoid").valid()) {
        dataString = $j("#binvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugBincard.form",
            data:dataString,
            success:function () {
                $j("#binvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});


$j("form#filter").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    dataString = "s11=" + $j("#s11number").val();

    binTable.fnFilter(dataString);


    return false;

});


$j("#bincardform").click(function () {

    getData();
    getDataLocation();
});


function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth() + (12 * (date2.getFullYear() - date1.getFullYear()));
}
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


function getUserPass() {

    $j
        .getJSON(
        "dispense.form?pass=pass",
        function (result) {


            $j
                .each(
                result,
                function (index, value) { //bincard"stateList
                    var oFormObject = document.forms['check'];

                    oFormObject.elements["pass"].value = value;
                });

        });

}
function getDrugCategory() {

    $j
        .getJSON(
        "categoryName.form?drop=drop",
        function (result) {

            $j("#filtercategory").get(0).options.length = 0;
            $j("#filtercategory").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#filtercategory").get(0).options[$j(
                        "#filtercategory").get(0).options.length] = new Option(
                        value, value);
                });

        });

}


$j("#filterdrugbin").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdrugbin").removeClass('working');

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

    $j.getJSON(
        "drugDetails.form?drop=location",
        function (result) {

            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
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
