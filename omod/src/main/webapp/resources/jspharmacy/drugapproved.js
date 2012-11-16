var binTable;
var binTable2;
var oFormObjectForm;
var totalElements = 0;
var nTrIn;
var dataStringTwo;
var dataStringVal;
var dataString;
var editTr;
var link;
var serverData;
var aDataVal;
var oCache = {
    iCacheLower:-1
};

$j("#parent_div_1").hide();
$j("#approvedperm").hide();//
$j("#errorDivStore").hide();

$j("#approvedVal").hide();
$j("#approvedvoid").hide();

$j("#approvedInfo").hide();
$j("#parent_div_2").hide();

$j("#incomingextraap").hide();

$j("popApproved").hide();


$j("#approvedform").hide();
$j("#hideapprovedform").hide();


$j("#filterapproved").validate();
$j("#approvedextra").validate();
$j("#approvedextra").hide();

$j("#approvedvoid").validate();
$j("#approvedVal").validate();


$j("#incomingextraap").validate({
    rules:{
        supplier:{
            selectNone:true
        }
    }
});
function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tapproved', 'drugApproved.form');

}

function RefreshInventory(tableId) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;
    table.fnDraw();
}

/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = binTable.fnGetData(nTr);


    show();

}


function fnDetails(nTr) {
    if (confirm('Are you sure?')) {
        var aData = binTable.fnGetData(nTr);

        dataString = "bindrug=" + aData[2] + "&" + "binquantityin=" + aData[3] + "&binmax=" + aData[4] + "&binmin=" + aData[5] + "&location=" + aData[11] + "&date=" + aData[8] + "&approved=" + aData[1] + "&binedit=" + false + "&less=" + false;

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
$j("#approvedexpire").datepicker();

$j(function () {

    $j("#approvedexpire").datepicker();

});
getDrugFilter();
binTable = $j('#tapproved').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {


        var htm = '<ul class="popApproved">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popApproved" id=' + "popApproved" + aData[2] + '>';


        htm += '<li> <a href="#" id="approve" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/approve.png" />Add to store</a></li>';


        htm += '<li> <a href="#" id="details"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/details.png" />Details</a></li>';
        htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'drugApproved.form',
    "fnServerData":fnDataTablesPipeline,
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
            "aTargets":[ 7 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 8 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 9 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 11 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 12 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 13 ]
        },
        {
            "bVisible":false,

            "aTargets":[ 14 ]
        },
        {
            "bVisible":false,

            "aTargets":[ 15 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:10,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });


$j('select#filterdrugapproved').change(function () {
    binTable.fnFilter($j(this).val());
});


$j("#void").live('click', function () {
    $j("ul .popApproved").hide();

    var aData = binTable.fnGetData(editTr);

    var oFormObject = document.forms['approvedvoid'];
    oFormObject.elements["approveduuidvoid"].value = aData[1];
    $j("#approvedvoid").show();
});

$j("#cancel").live('click', function () {
    $j("ul .popApproved").hide();


});

$j("#approve").live('click', function () {

    $j("ul .popApproved").hide();
    $j('#incomingnumberap').attr('disabled', 'disabled');

    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['incomingextraap'];


    getDataSupplier();
    getUsers();

    $j("#incomingextraap").show();
    $j("#approvedperm").show();//

});


$j('#tapproved tbody td ul').live('click', function () {
    editTr = this.parentNode.parentNode;
    var aData = binTable.fnGetData(editTr);
    link = "#popApproved" + aData[2];


    $j(link).show();


});
$j('#tapproved tbody td  dfn').live('click', function () {

    $j("#approvedInfo").show();//
    $j("#approvedInfo").delay(5000).hide();

});

$j('#tapproved tbody td  em').live('click', function () {

    var nTr = this.parentNode.parentNode;

});
$j("form#filterapproved").submit(function () {

    if ($j("#filterapproved").valid()) {
        AutoReload();


        return false;
    }
});
//
$j("#detailsApproved").dialog({
    autoOpen:false,
    height:500,
    width:700,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog("close");

            $j.ajax({
                type:"POST",
                url:"drugApproved.form",
                data:serverData,
                success:function () {


                    AutoReload();

                }
            });
        },
        Cancel:function () {
            $j(this).dialog("close");
            $j("#submitAllApp").show();
            $j("#incomingextraap").hide();
            $j("#approvedperm").hide();//

        },
        Print:function () {
            demoTwoPageDocument(aDataVal);
        }
    }
});
$j("#detrailsAp").dialog({
    autoOpen:false,
    height:500,
    width:700,
    cache:false,
    modal:true,
    buttons:{

        Ok:function () {
            $j(this).dialog("close");
        },
        Print:function () {
            demoTwoPageDocument(aDataVal);
        }
    }
});


function demoTwoPageDocument(aDataVal) {
    var doc = new jsPDF();


    doc.text(20, 20, "Drug:" + aDataVal[3]);

    doc.text(20, 30, "Quantity:" + aDataVal[4]);

    doc.text(20, 40, "Request from:" + aDataVal[5] + " ");

    doc.text(20, 50, "Request to:" + aDataVal[6] + " ");
    doc.text(20, 60, "Transaction type:" + aDataVal[7] + " ");
    doc.text(20, 70, "Supplier:" + aDataVal[8] + " ");

    doc.output('datauri');
}
function closeapprovedExtra() {


    $j("#detailsApproved").dialog("close");

    AutoReload();


    $j("#incoming").hide();//


    var oFormObject = document.forms['approvedextra'];


    oFormObject.elements["approvednumber"].value = "";


    oFormObject.elements["approveds11"].value = "";


    oFormObject.elements["approveduuidextra"].value = "";

    oCache.iCacheLower = -1;
    $j('#tinventoryout').dataTable().fnReloadAjax("drugBincard.form?dialog=dialog&uuid=123");
    var i;
    for (i = 0; i < totalElements; i++) {


        removeInput();
    }

    totalElements = 0;


}


$j("form#approvedextra").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#approvedextra").valid()) {

        $j("#approvedperm").hide();//
        dataString = $j("#approvedextra").serialize();
        var answers = [];
        $j.each($j('.datavalues'), function () {
            answers.push($j(this).val());


        });

        var answers2 = "&answers=" + answers;
        dataStringTwo = dataString + "&" + answers2;
        dataStringTwo += +"&" + answers2;

        var i = 0;
        var myBoolean = new Boolean();
        myBoolean = true;
        for (var i = 1; i < answers.length; i++) {
            if (i == 1) {
                if (answers[1] > oFormObjectForm.elements["one22"].value)
                    myBoolean = false;
            }
            else if (i == 2) {
                if (answers[2] > oFormObjectForm.elements["one23"].value)
                    myBoolean = false;
            }
            else if (i == 3) {

                if (answers[3] > oFormObjectForm.elements["one24"].value)
                    myBoolean = false;
            }
            else if (i == 4) {

                if (answers[4] > oFormObjectForm.elements["one24"].value)
                    myBoolean = false;
            }

        }
        if (myBoolean) {
            var myResult = dataStringTwo.split("&");

            $j("#detailsformapp").empty();
            for (i = 0; i < myResult.length; i++) {
                if (i == 0)
                    $j('<dl><dt></dt><dd > -' + "Requested By " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 1)
                    $j('<dl><dt></dt><dd > -' + "Issued By " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 2)
                    $j('<dl><dt></dt><dd > -' + "Authorized By " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 4)
                    $j('<dl><dt></dt><dd > -' + "S11 No " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 5)
                    $j('<dl><dt></dt><dd > -' + "Quantity " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 6)
                    $j('<dl><dt></dt><dd > -' + "Drug one Id " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 7)
                    $j('<dl><dt></dt><dd > -' + "Drug two Id " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 8)
                    $j('<dl><dt></dt><dd > -' + "Drug thre Id " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 9)
                    $j('<dl><dt></dt><dd > -' + "Drug four Id " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 10)
                    $j('<dl><dt></dt><dd > -' + "Drug one total in store " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 11)
                    $j('<dl><dt></dt><dd > -' + "Drug two total in store " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 12)
                    $j('<dl><dt></dt><dd > -' + "Drug thre total in store " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 13)
                    $j('<dl><dt></dt><dd > -' + "Drug four total in store " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');
                else if (i == 14)
                    $j('<dl><dt></dt><dd > -' + "Drug " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformapp');

            }


            $j("#detailsformapp").dialog("open");


        }
        else {

            $j("#errorDivStore").show();

            $j("#errorDivStore").delay(10000).hide("slow");
        }

        return false;
    }
});

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


//RefreshInventory('#tinventoryval');



//$j('#approvednumber').attr('disabled','disabled');


function addInput(nTrIn) {


    var num = $j('.clonedInput').length;
    var newNum = new Number(num + 1);
    totalElements += 1;

    if (newNum <= 5) {
        var newElem = $j('#input' + num).clone().attr('id', 'input' + newNum);

        newElem.children(':first').attr('id', 'answers' + newNum).attr('answers', 'answers');
        var idtag = "#answers" + newNum;


        $j('#input' + num).after(newElem);
        $j('#btnDel').attr('disabled', '');


        var aData = binTable2.fnGetData(nTrIn);
        oFormObjectForm = document.forms['approvedextra'];

        var val = "one" + newNum;
        oFormObjectForm.elements[val].value = aData[0];
        var valTwo = "one2" + newNum;
        oFormObjectForm.elements[valTwo].value = aData[2];
//

    }
    else
        alert("Cannont add more than four at once" + newNum);

    $j('#approvednumber').attr('disabled', 'disabled');


}
function removeInput() {


    var num = $j('.clonedInput').length;

    $j('#input' + num).remove();
    $j('#btnAdd').attr('disabled', '');

    if (num - 1 == 1) {
        $j('#btnDel').attr('disabled', 'disabled');

        $j('#approvednumber').attr('disabled', 'disabled');
    }

    var val = "one" + num;
    oFormObjectForm.elements[val].value = "";
    var valTwo = "one2" + num;
    oFormObjectForm.elements[valTwo].value = "";
}

$j('#btnAdd').click(function () {

    var num = $j('.clonedInput').length;
    var newNum = new Number(num + 1);

    var newElem = $j('#input' + num).clone().attr('id', 'input' + newNum);

    newElem.children(':first').attr('id', 'answers' + newNum).attr('answers', 'answers');
    var idtag = "#answers" + newNum;


    $j('#input' + num).after(newElem);
    $j('#btnDel').attr('disabled', '');

});

$j('#btnDel').click(function () {
    var num = $j('.clonedInput').length;

    $j('#input' + num).remove();
    $j('#btnAdd').attr('disabled', '');

    if (num - 1 == 1) {
        $j('#btnDel').attr('disabled', 'disabled');

        $j('#approvednumber').attr('disabled', 'disabled');
    }

    var val = "one" + num;
    oFormObjectForm.elements[val].value = "";
    var valTwo = "one2" + num;
    oFormObjectForm.elements[valTwo].value = "";


});

$j('#btnDel').attr('disabled', 'disabled');

$j('#approvednumber').attr('disabled', 'disabled');


$j('#tinventoryout tbody td  input').live('click', function () {
    nTrIn = this.parentNode.parentNode;

    var aData2 = binTable2.fnGetData(nTrIn);
    var oFormObject = document.forms['approvedextra'];

    var myCars = new Array();
    myCars[0] = oFormObject.elements["one2"].value;
    myCars[1] = oFormObject.elements["one3"].value;
    myCars[2] = oFormObject.elements["one4"].value;
    myCars[3] = oFormObject.elements["one5"].value;
    var myBoolean = new Boolean();
    myBoolean = false;
    var myBooleanTwo = new Boolean();
    myBooleanTwo = false;
    for (var i = 0; i < myCars.length; i++) {

        if (myCars[i] == aData2[0]) {

            myBoolean = true;
            break;
        }


    }

    if (!myBoolean) {


        $j('#approvednumber').attr('disabled', '');

        addInput(nTrIn);
    }
    else {


        removeInput();
    }


});
function voidData(nTr) {
    var aData = binTable.fnGetData(nTr);

    var dataVal = "approveduuidvoid=" + aData[1] + "&approvedreason=" + "Dataadded";
    $j.ajax({
        type:"POST",
        url:"drugIncoming.form",
        data:dataVal,


        success:function () {

            AutoReload();
        }
    });
}
$j(function () {

    $j("#incomingexpirea").datepicker();

});
function clear_form_elements(ele) {
    var aData = binTable.fnGetData(editTr);


    $j(ele).find(':input').each(function () {
        switch (this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'hidden':
            case 'textarea':
                $j(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });

}
$j("#detailsformValap").dialog({
    autoOpen:false,
    height:400,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');

            $j.ajax({
                type:"POST",
                url:"drugIncoming.form",
                data:dataString,
                success:function () {

                    clear_form_elements("#incomingextraap");
                    clear_form_elements("#Permissionapproved");
                    $j("#incomingextraap").hide();
                    $j("#approvedperm").hide();//

                }
            });

            oCache.iCacheLower = -1;
            $j('#tapproved').dataTable().fnReloadAjax("drugApproved.form");
        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});

$j("form#incomingextraap").submit(function () {


    $j('#incomingnumberap').attr('disabled', '');

    // we want to store the values from the form input box, then send via ajax below
    if ($j("#incomingextraap").valid()) {
        dataString = $j("#incomingextraap").serialize();
        dataPerm = $j("#Permissionapproved").serialize();
        var all = dataString + "&" + dataPerm + serverData;


        $j.ajax({
            type:"POST",
            url:"drugApproved.form",
            data:all,
            success:function () {
                $j("#submitAllApp").show();
                $j("#incomingextraap").hide();
                $j("#approvedperm").hide();//

                clear_form_elements("#Permissionapproved");
                clear_form_elements("#incomingextraap");

                AutoReload();
            }
        });


        return false;
    }
});
$j("#details").live('click', function () {


    $j("ul .popApproved").hide();


    $j("#detrailsAp").empty();

    var aData = binTable.fnGetData(editTr);
    aDataVal = binTable.fnGetData(editTr);


    $j('<dl><dt></dt><dd > -' + "Drug:" + aData[4] + '</dd></dl> ').appendTo('#detrailsAp');
    $j('<dl><dt></dt><dd > -' + "Quantity:" + aData[4] + '</dd></dl> ').appendTo('#detrailsAp');
    $j('<dl><dt></dt><dd > -' + "Request from:" + aData[5] + '</dd></dl> ').appendTo('#detrailsAp');
    $j('<dl><dt></dt><dd > -' + "Request to:" + aData[6] + '</dd></dl> ').appendTo('#detrailsAp');

    $j('<dl><dt></dt><dd > -' + "Transaction type:" + aData[7] + '</dd></dl> ').appendTo('#detrailsAp');
    $j('<dl><dt></dt><dd > -' + "Supplier:" + aData[8] + '</dd></dl> ').appendTo('#detrailsAp');

    $j("#detrailsAp").dialog("open");

});


$j("form#approvedvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#approvedvoid").valid()) {
        dataString = $j("#approvedvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugApproved.form",
            data:dataString,
            success:function () {
                $j("#approvedvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});


$j("#hideapprovedform").click(function () {

    $j("#hideapprovedform").hide();
    $j("#parent_div_1").hide();

    $j("#parent_div_2").hide();

});
$j("#approvedform").click(function () {
    getDataLocation();
    getDataDrug();


    getDataSupplier();
    getDataTransactionType();
    getDataLocationTwo();
    show();


    document.getElementById('approveddrug').addEventListener('change', change, false);
});
function change() {

    var e = document.getElementById("approveddrug");
    var strUser = e.options[e.selectedIndex].value;

    getDataTotal(strUser);
}

function getDataDrug() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop",
        function (result) {

            $j("#approveddrug").get(0).options.length = 0;
            $j("#approveddrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#approveddrug").get(0).options[$j(
                        "#approveddrug").get(0).options.length] = new Option(
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


function getUsers() {


    $j.getJSON("dispense.form?users=users",
        function (result) {


            $j("#requisitionp").get(0).options.length = 0;
            $j("#requisitionp").get(0).options[0] = new Option("Select",
                "-1");

            $j("#issuedp").get(0).options.length = 0;
            $j("#issuedp").get(0).options[0] = new Option("Select",
                "-1");

            $j("#authorizedp").get(0).options.length = 0;
            $j("#authorizedp").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
                result,
                function (index, value) { //bincard"stateList


                    $j("#requisitionp").get(0).options[$j(
                        "#requisitionp").get(0).options.length] = new Option(
                        value, value);


                    $j("#issuedp").get(0).options[$j(
                        "#issuedp").get(0).options.length] = new Option(
                        value, value);


                    $j("#authorizedp").get(0).options[$j(
                        "#authorizedp").get(0).options.length] = new Option(
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

function getDataTotal(drug) {

    var url = "transactionsName.form?drop=total&drug=" + drug;
    $j
        .getJSON(url
        ,
        function (result) {

            var oFormObject = document.forms['approvedVal'];

            oFormObject.elements["totalstore"].value = result;
        });


}
function getDrugFilter() {


}


$j("#filterdrugapproved").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdrugapproved").removeClass('working');

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

function show() {


    $j("#approvedVal").show("slow");

}


$j('#submitAllApp').live('click', function () {
    $j("#SaveDiv").show();
    $j("#Save").show();
    $j("#outgoingperm").show();//

    // $j(this).hide();

    fnGetSelected();


    getDataSupplier();
    getUsers();


});


function fnGetSelected() {

    $j("#detailsApproved").empty();

    var myResultData = "";

    $j('#tapproved').find('input').each(function () {


        if ($j(this).is(':checked')) {

            nTrIn = this.parentNode.parentNode;

            var aData2 = binTable.fnGetData(nTrIn);
            aDataVal = aData2;
            $j('<dl><dt></dt><dd > -' + "Drug " + aData2[3] + " & Quantity =" + aData2[4] + '</dd></dl> ').appendTo('#detailsApproved');
            myResultData += "&drugId=" + aData2[2] + "&quantity=" + aData2[4];

        }


    });


    serverData = myResultData + "&approveduuidextra=approveduuidextra";


    $j("#detailsApproved").dialog("open");


}
