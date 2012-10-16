var binTable;
var dialog;
var filterString;
var editTr;
var dataString;
var link;
var dataStringValin;
var dataStringValinData;

var myResult;
var oCache = {
    iCacheLower:-1
};

$j("#incomingperm").hide();//
$j("popIncoming").hide();

$j("#incoming").hide();   //incoming
$j("#s11").hide();
$j("#incomingvoid").hide();

$j("#filterincoming").validate();
$j("#incomingextrain").validate({
    rules:{
        supplier:{
            selectNone:true
        }
    }
});


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

    if (num - 1 == 1)
        $j('#btnDel').attr('disabled', 'disabled');
});

$j('#btnDel').attr('disabled', 'disabled');

$j("#incomingInfo").hide();//

$j("#incomingvoid").validate();


$j("#incoming").validate({
    rules:{
        incomingdrug:{
            selectNone:true
        },
        location:{
            selectNone:true
        },
        transactions:{
            selectNone:true
        }
    }
});
$j("#incomingextrain").hide();
$j("#hideincomingform").hide();

function RefreshTable(tableId, urlData) {
    table = $j(tableId).dataTable(
    );
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tincoming', 'drugIncoming.form');
    //getDrugFilter();
}
/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = binTable.fnGetData(nTr);

    var oFormObject = document.forms['incoming'];


    oFormObject.elements["incomingquantityin"].value = aData[4];

    oFormObject.elements["incomingedit"].value = 'true';
    oFormObject.elements["incominguuid"].value = aData[2];

    $j("#incoming").show();//

}

function fnDetails(nTr) {
    if (confirm('Are you sure?')) {
        var aData = binTable.fnGetData(nTr);

        dataString = "bindrug=" + aData[2] + "&" + "binquantityin=" + aData[3] + "&binmax=" + aData[4] + "&binmin=" + aData[5] + "&location=" + aData[11] + "&date=" + aData[8] + "&incoming=" + aData[1] + "&binedit=" + false;

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
$j(function () {

    $j("#incomingexpire").datepicker();

});
dialog = $j("#dialog-confirm").dialog({
    resizable:false,
    height:140,
    modal:true,
    buttons:{
        "Are you sure":function () {
            $j(this).dialog('close');
        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});

getDrugFilter();
var num = 0;
binTable = $j('#tincoming').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {


        var htm = '<ul class="popIncoming">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popIncoming" id=' + "popIncoming" + aData[10] + '>';


        if (aData[0] == "edit") {
            htm += '<li> <a href="#"  id="editincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (aData[15] == "Approve") {
            htm += '<li> <a href="#" id="approveincoming" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/approve.png" />Approve from suppliers</a></li>';
        }


        if (aData[16] == "Delete") {
            htm += '<li> <a href="#" id="voidincoming" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="detailsincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/details.png" />Details</a></li>';
        htm += '<li> <a href="#" id="cancelincoming"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'drugIncoming.form',
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
            "aTargets":[ 10 ]
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
        } ,
        {
            "bVisible":false,

            "aTargets":[ 16 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:11,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });
//$j('select#filterdrug').change( function() { binTable.fnFilter( $j(this).val() ); } );

$j("#editincoming").live('click', function () {


    $j("ul .popIncoming").hide();
    //
    getDataLocation();
    getDataDrug();


    getDataSupplier();
    getDataCategory();
    getDataTransactionType();
    getDataLocationTwo();


    fnFormatDetails(editTr);

});


$j("#approveincoming").live('click', function () {

    $j("ul .popIncoming").hide();
    getDataSupplier();
    getUsers();

    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['incomingextrain'];
    oFormObject.elements["incominguuidextra"].value = aData[2];
    oFormObject.elements["incomingdrugg"].value = aData[3];
    $j("#hideincomingform").show();
    $j("#incomingextrain").show();
    $j("#incomingformshow").hide();

    //  $j("#incomingperm").show();//


});
$j("#voidincoming").live('click', function () {
    $j("ul .popIncoming").hide();


    var aData = binTable.fnGetData(editTr);

    var oFormObject = document.forms['incomingvoid'];


    oFormObject.elements["incominguuidvoid"].value = aData[2];
    $j("#incomingvoid").show();


});


$j("#cancelincoming").live('click', function () {

    $j("ul .popIncoming").hide();
});

$j('#tincoming tbody td ul').live('click', function () {
    editTr = this.parentNode.parentNode;
    var aData = binTable.fnGetData(editTr);
    link = "#popIncoming" + aData[10];


    $j(link).show();


});


$j('#tincoming tbody tr').live('click', function () {
//
//   var  editTr = this.parentNode.parentNode;
//    var aData = binTable.fnGetData(editTr);
//
//
//
//    alert(aData[10]);

});


function fnGetSelected(oTAble) {


    $j('#tincoming').find('td').each(function () {


        if ($j(this).hasClass('sorting_1')) {
//             alert("xcxtdc");
        }
    });

//     var aReturn = new Array();
//
//     var aTrs= oTAble.fnGetNodes();
//
//     for(var i=0;i <aTrs.length;i++){
//         alert($j(aTrs[i]));
//         if($j(aTrs[i]).hasClass('sorting_1')){
//
//             var aData = binTable.fnGetData(aTrs[i]);
//             link=aData[10]+aData[11]+aData[12];
//
//
//             alert(link);
//
//         }
//
//     }
}


$j('#tincoming tbody td  dfn').live('click', function () {
    $j("#incomingInfo").show("slow");//
    $j('#incomingInfo .loc').replaceWith("<div id='red'>Its already approved</div>");

    $j("#incomingInfo").delay(5000).hide();

});


function getUsers() {


    $j.getJSON("dispense.form?users=users",
        function (result) {


            $j("#requisition").get(0).options.length = 0;
            $j("#requisition").get(0).options[0] = new Option("Select",
                "-1");

            $j("#issued").get(0).options.length = 0;
            $j("#issued").get(0).options[0] = new Option("Select",
                "-1");

            $j("#authorized").get(0).options.length = 0;
            $j("#authorized").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
                result,
                function (index, value) { //bincard"stateList


                    $j("#requisition").get(0).options[$j(
                        "#requisition").get(0).options.length] = new Option(
                        value, value);


                    $j("#issued").get(0).options[$j(
                        "#issued").get(0).options.length] = new Option(
                        value, value);


                    $j("#authorized").get(0).options[$j(
                        "#authorized").get(0).options.length] = new Option(
                        value, value);

                });

        });


}

function voidData(nTr) {
    var aData = binTable.fnGetData(nTr);


    var dataVal = "incominguuidvoid=" + aData[1] + "&incomingreason=" + "Dataadded";

    $j.ajax({
        type:"POST",
        url:"drugIncoming.form",
        data:dataVal,


        success:function () {

            AutoReload();
        }
    });
}


$j("#encountformDet").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{

        OK:function () {
            $j(this).dialog('close');
        }
    }
});

// details tincoming tbody td a
$j('#detailsincoming').live('click', function () {
    $j("ul .popIncoming").hide();
    $j("#encountformDet").empty();
    //

    var aData = binTable.fnGetData(editTr);


    $j('<dl><dt></dt><dd > -' + "Drug:" + aData[3] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Quantity:" + aData[4] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Destination:" + aData[5] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Transactions type:" + aData[6] + '</dd></dl> ').appendTo('#encountformDet');

    $j('<dl><dt></dt><dd > -' + "Supplier:" + aData[7] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Min level:" + aData[8] + '</dd></dl> ').appendTo('#encountformDet');
    $j('<dl><dt></dt><dd > -' + "Max level:" + aData[9] + '</dd></dl> ').appendTo('#encountformDet');

    $j("#encountformDet").dialog("open");


});

$j("form#filterincoming").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#filterincoming").valid()) {
        AutoReload();


        return false;
    }
});


$j("#detailsformValin").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');
            $j.ajax({
                type:"POST",
                url:"drugIncoming.form",
                data:dataStringValin,
                success:function () {

                    close();
                }
            });

        },
        Cancel:function () {
            $j(this).dialog('close');
        },
        "Print":function () {

            demoTwoPageDocument(dataStringValin);
        }
    }
});

$j("#detailsformValinT").dialog({
    autoOpen:false,
    height:500,
    width:600,
    cache:false,
    modal:true,
    buttons:{
        "Yes":function () {
            $j(this).dialog('close');

            $j.ajax({
                type:"POST",
                url:"drugIncoming.form",
                data:dataStringValinData,
                success:function () {

                    close();
                }
            });

        },
        Cancel:function () {
            $j(this).dialog('close');
        }
    }
});
function demoTwoPageDocument(dataStringValin) {
    var doc = new jsPDF();


    myResult = dataStringValin.split("&");
    var val = 20;
    var right = 70;

    doc.setProperties({
        title:'Pharmacy request ',
        subject:'Incoming '
    });

    for (i = 0; i < myResult.length; i++) {
        if (i == 0) {

            doc.text(20, 20, "S11 No" + myResult[i].substring(myResult[i].indexOf("=")) + " ");

        }
        else if (i == 1)
            doc.text(20, 30, "Date" + myResult[i].substring(myResult[i].indexOf("=")) + " ");
        else if (i == 2)
            doc.text(20, 40, "Transaction type" + myResult[i].substring(myResult[i].indexOf("=")) + " ");
        else if (i == 3) {
            doc.text(20, 50, "Requesting from" + myResult[i].substring(myResult[i].indexOf("=")) + " ");


            doc.text(20, 60, "Drug");
            doc.text(140, 60, "Category");
            doc.text(180, 60, "Quantity");


        }
        else {

            doc.text(20, right, myResult[i].substring(myResult[i].indexOf("=") + 1, 30) + " ");
            doc.text(140, right, myResult[i + 1].substring(myResult[i + 1].indexOf("=") + 1) + " ");
            doc.text(180, right, myResult[i + 2].substring(myResult[i + 2].indexOf("=") + 1) + " ");

            right += 10;

            i += 2;
        }

    }
//    doc.text(20, 20, "Hello world!");
//    doc.text(20, 30, 'This is client-side Javascript, pumping out a PDF.');
//    doc.addPage();
//    doc.text(20, 20, 'Do you like that?');

    // Output as Data URI
    doc.output('datauri');
}
function close() {


    $j("#incoming").hide();//

    clear_form_elements("#s11");
    clear_form_elements("#incoming");
    $j("#s11").hide();
    $j("#hideincomingform").hide();
    $j("#incomingform").show();
    $j("#incoming").hide();
    $j("#tincoming").show();//
    $j("#incomingextrain").hide();
    AutoReload();

}


$j("form#incoming").submit(function () {

    $j("#incomingperm").hide();//

    // we want to store the values from the form input box, then send via ajax below
    if ($j("#incoming").valid()) {
        dataStringValinData = $j("#incoming").serialize();

        myResult = dataStringValinData.split("&");

        $j("#detailsformValinT").empty();
        for (i = 0; i < myResult.length; i++) {
            if (i == 2)
                $j('<dl><dt></dt><dd > -' + "Drug " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 3)
                $j('<dl><dt></dt><dd > -' + "Location " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 4)
                $j('<dl><dt></dt><dd > -' + "Quantity " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 5)
                $j('<dl><dt></dt><dd > -' + "Transaction Type " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');
            else if (i == 6)
                $j('<dl><dt></dt><dd > -' + "Category " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValinT');


        }


        $j("#detailsformValinT").dialog("open");


        return false;
    }
});
$j("form#s11").submit(function () {
    dataStringValin = $j("#s11").serialize();

//alert(dataStringValin);


    var myResult = dataStringValin.split("&");

    $j("#detailsformValin").empty();
    for (i = 0; i < myResult.length; i++) {
        if (i == 0)
            $j('<dl><dt></dt><dd > -' + "S11 No" + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
        else if (i == 1)
            $j('<dl><dt></dt><dd > -' + "Date " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
        else if (i == 2)
            $j('<dl><dt></dt><dd > -' + "Transaction Type " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
        else if (i == 3) {
            $j('<dl><dt></dt><dd > -' + "Requesting from" + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
        }
        else {

            $j('<dl><dt></dt><dd > -' + "Drug " + i + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
            $j('<dl><dt></dt><dd > -' + "Category " + myResult[i + 1].substring(myResult[i + 1].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');
            $j('<dl><dt></dt><dd > -' + "Quantity " + myResult[i + 2].substring(myResult[i + 2].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformValin');

            i += 2;
        }

    }


    $j("#detailsformValin").dialog("open");


    return false;

});

function clear_form_elements(ele) {

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
$j("#detailsformin").dialog({
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

                    closeExtra();
                }
            });


//			AutoReload();
        },
        Cancel:function () {
            closeExtra();
            $j(this).dialog('close');
//			var newElement = "<tr class='ui-dform-tr'><td class='ui-dform-td'><input type='checkbox' name='DRUGS|90900#7' class='ui-dform-checkbox' value='2281' style='width: 100px;'> <label class='ui-dform-label'>FLUOXETINE (PROZAC)</label> </td> </tr>";
//		     

//		     $j('#driugdiv').append(newElement);

//		     $j("#test").append(newElement);
//		     


            // $j(this).append(newElem);

            $j('#in').after("<label>Quantity in</label> ");
//			$j(this).dialog( 'close');
        }
    }
});

function closeExtra() {


    AutoReload();
    $j("#incoming").hide();//

    clear_form_elements("#incomingextrain");
    clear_form_elements("#Permissionincoming");


    $j("#hideincomingform").hide();
    $j("#incomingform").show();
    $j("#incoming").hide();
    $j("#tincoming").show();//
    $j("#incomingextrain").hide();
    $j("#incomingperm").hide();


}
$j("form#incomingextrain").submit(function () {



    // we want to store the values from the form input box, then send via ajax below
    if ($j("#incomingextrain").valid()) {
        dataString = $j("#incomingextrain").serialize();
        dataPerm = $j("#Permissionincoming").serialize();
        dataString += "&" + dataPerm;
        var myResult = dataString.split("&");

        $j("#detailsformin").empty();
        for (i = 0; i < myResult.length; i++) {
            if (i == 0)
                $j('<dl><dt></dt><dd > -' + "Supplier " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 1)
                $j('<dl><dt></dt><dd > -' + "Incoming quantity " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 2)
                $j('<dl><dt></dt><dd > -' + "Batch No " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 3)
                $j('<dl><dt></dt><dd > -' + "S11 No" + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 4)
                $j('<dl><dt></dt><dd > -' + "Expire Date " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 5)
                $j('<dl><dt></dt><dd > -' + "Delivery No " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 6)
                $j('<dl><dt></dt><dd > -' + "Drug " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');

            else if (i == 9)
                $j('<dl><dt></dt><dd > -' + "Requested By" + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');

            else if (i == 11)
                $j('<dl><dt></dt><dd > -' + "Issued By " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');
            else if (i == 13)
                $j('<dl><dt></dt><dd > -' + "Authorized By " + myResult[i].substring(myResult[i].indexOf("=")) + '</dd></dl> ').appendTo('#detailsformin');


            $j('<form id="test" name="test" ><div id="in"></div></form>').appendTo('#detailsformin');


        }


        $j("#detailsformin").dialog("open");


        return false;
    }
});
$j("form#incomingvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#incomingvoid").valid()) {
        dataString = $j("#incomingvoid").serialize();
        $j.ajax({
            type:"POST",
            url:"drugIncoming.form",
            data:dataString,


            success:function () {
                $j("#incomingvoid").hide();//
                AutoReload();
                var oFormObject = document.forms['incomingvoid'];


                oFormObject.elements["incominguuidvoid"].value = "";
                oFormObject.elements["incomingreason"].value = "";
            }
        });
        return false;
    }
});
$j("#hideincomingform").live('click', function () {
    $j("#hideincomingform").hide();
    $j("#incomingformshow").show();
    $j("#incoming").hide();
    $j("#s11").hide();
    $j("#tincoming").show();//
    $j("#incomingextrain").hide();
});


$j('#incomingformshow').live('click', function () {
    $j("#hideincomingform").show();

    $j("#s11").show();
    $j("#incomingformshow").hide();
    $j("#tincoming").hide();//
    getDataLocation();
    getDataDrug();


    getDataSupplier();
    getDataCategory();
    getDataTransactionType();
    getDataLocationTwo();
    show();
    //document.getElementById('incomingdrug').addEventListener('change', change, false);
});
function change() {

    var e = document.getElementById("incomingdrug");
    var strUser = e.options[e.selectedIndex].value;

    getDataTotal(strUser);
}

/*
 $j("#incomingdrug").autocomplete({
 search : function() {
 $j(this).addClass('working');
 },

 source : function(request, response) {

 dataString = "searchDrug=" + request.term;

 $j.getJSON("drugDetails.form?drop=drop&" + dataString, function(result) {

 $j("#incomingdrug").removeClass('working');

 response($j.each(result, function(index, item) {

 return {
 label : item,
 value : item
 }
 }));

 });

 },
 minLength : 3,
 select : function(event, ui) {



 // log( ui.item ?
 // "Selected: " + ui.item.label :
 // "Nothing selected, input was " + this.value);
 },
 open : function() {
 $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
 },
 close : function() {
 $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
 }
 });
 */

//$j('#filterdrug').change( function() { binTable.fnFilter( $j(this).val() ); } );
$j("input[name=incomingdrug]").live("focus", function () {


    $j(this).autocomplete({
        search:function () {
            $j(this).addClass('working');
        },

        source:function (request, response) {

            dataString = "searchDrug=" + request.term;

            $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

                $j("#incomingdrug").removeClass('working');

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
            //  binTable.fnFilter( ui.item.label );
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


});


$j("#filterdrug").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdrug").removeClass('working');

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
function getDataDrug() {


}
function getDrugFilter() {


}
function getDataLocation() {

    $j
        .getJSON(
        "drugDetails.form?drop=locationAll",
        function (result) {

            $j("#location1").get(0).options.length = 0;
            $j("#location1").get(0).options[0] = new Option("Select",
                "-1");
            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");

            $j.each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
                    $j("#location1").get(0).options[$j("#location1").get(0).options.length] = new Option(value, value);


                });

        });


}


function getDataTotal(drug) {

    var url = "transactionsName.form?drop=totalTwo&drug=" + drug;
    $j
        .getJSON(url
        ,
        function (result) {

            var oFormObject = document.forms['incoming'];

            oFormObject.elements["totalstore"].value = result;
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

            $j("#supplier").get(0).options.length = 0;
            $j("#supplier").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#supplier").get(0).options[$j(
                        "#supplier").get(0).options.length] = new Option(
                        value, value);
                });

        });


}

function getDataCategory() {

    $j
        .getJSON(
        "categoryName.form?drop=drop",
        function (result) {

            $j("#incomingcategory").get(0).options.length = 0;
            $j("#incomingcategory").get(0).options[0] = new Option("Select",
                "-1");
            $j("#incomingcategory1").get(0).options.length = 0;
            $j("#incomingcategory1").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#incomingcategory").get(0).options[$j(
                        "#incomingcategory").get(0).options.length] = new Option(
                        value, value);
                    $j("#incomingcategory1").get(0).options[$j(
                        "#incomingcategory1").get(0).options.length] = new Option(
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

            $j("#transactions1").get(0).options.length = 0;
            $j("#transactions1").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#transactions").get(0).options[$j(
                        "#transactions").get(0).options.length] = new Option(
                        value, value);
                    $j("#transactions1").get(0).options[$j(
                        "#transactions1").get(0).options.length] = new Option(
                        value, value);
                });

        });


}


function show() {


    // $j("#incoming").show("slow");

}