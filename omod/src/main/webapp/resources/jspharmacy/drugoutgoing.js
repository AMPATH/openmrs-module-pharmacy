var binTable;
var binTable2;
var oFormObjectForm;
var totalElements=0;
var nTrIn;
var dataStringTwo;
var dataStringVal;

var serverData;
var oCache = {
    iCacheLower: -1
};

var editTr;
var link;
var  mySave;

$j("#parent_div_1out").hide();


$j("#tinventoryoutgoing").hide();
$j("#outgoingperm").hide();//


$j("#errorDivOut").hide();
$j("#Saveo").show();
    $j("#submitAllo").hide();
$j("#outgoingVal").hide();
$j("#outgoingvoid").hide();

$j("#outgoingInfo").hide();
$j("#parent_div_2out").hide();


$j("popOutgoing").hide();


$j("#outgoingform").hide();
$j("#hideoutgoingform").hide();


$j("#filteroutgoing").validate();
$j("#outgoingextra").validate();

$j("#outgoingvoid").validate();
$j("#outgoingVal").validate();

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#toutgoing', 'drugOutgoing.form');

}

function RefreshInventory(tableId){

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = binTable.fnGetData(nTr);



//	$j("outgoingVal").show();//
//	var oFormObject = document.forms['outgoingVal'];
//	alert(">>>>>>>>>>>>");
//	oFormObject.elements["outgoingquantityin"].value = aData[3];
//	oFormObject.elements["outgoingedit"].value = 'true';
//	oFormObject.elements["outgoinguuid"].value = aData[1];

    show();

}


function fnDetails(nTr) {
    if(confirm('Are you sure?'))

    {
        var aData = binTable.fnGetData(nTr);

        dataString ="bindrug="+aData[2]+"&"+"binquantityin="+aData[3]+"&binmax="+aData[4]+"&binmin="+aData[5]+"&location="+aData[11]+"&date="+aData[8]+"&outgoing="+aData[1]+"&binedit="+false+"&less="+false;

        $j.ajax({
            type : "POST",
            url : "drugBincard.form",
            data : dataString,
            success : function() {


            }
        });

    }
    else {

    }
}
$j("#outgoingexpire").datepicker();

$j(function() {

    $j("#outgoingexpire").datepicker();

});
getDrugFilter();
binTable = $j('#toutgoing').dataTable({
    bJQueryUI : true,
    bRetrieve : true,
    bAutoWidth : false,
    bServerSide : true,
    bProcessing : true,
    bLengthChange: false,
    bPaginate: true,

    "fnRowCallback": function( nRow, aData, iDisplayIndex ) {


        var htm= '<ul class="popOutgoing">	<li> <img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popOutgoing" id='+"popOutgoing"+aData[10]+'>';




//        if(aData[14]=="Approve"){
//            htm +=	'<li> <a href="#" id="approveoutgoing" ><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/approve.png" />Approve</a></li>';
//        }


        if(aData[15]=="Delete"){
            htm +=	'<li> <a href="#" id="voidoutgoing" ><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm +='<li> <a href="#" id="detailsoutgoing"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/details.png" />Details</a></li>';
        htm +='<li> <a href="#" id="canceloutgoing"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';




        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },

    sAjaxSource : 'drugOutgoing.form',
    "fnServerData": fnDataTablesPipeline,
    "aoColumnDefs" : [ {
        "bVisible" : false,
        "aTargets" : [ 0 ]
    }, {
        "bVisible" : false,
        "aTargets" : [ 2 ]
    },{
        "bVisible" : false,
        "aTargets" : [ 7 ]
    },
        {
            "bVisible" : false,
            "aTargets" : [ 8 ]
        },{
            "bVisible" : false,
            "aTargets" : [ 9 ]
        },
        {
            "bVisible" : false,
            "aTargets" : [ 11 ]
        },
        {
            "bVisible" : false,
            "aTargets" : [ 12 ]
        },{
            "bVisible" : false,
            "aTargets" : [ 13 ]
        },{
            "bVisible" : false,

            "aTargets" : [ 14 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn: false,
    iGroupingColumnIndex: 10,
    bExpandableGrouping: true,
	bExpandSingleGroup: true,
	iExpandGroupOffset: -1 })


//$j('select#filterdrugoutgoing').change( function() { binTable.fnFilter( $j(this).val() ); } );


$j("#voidoutgoing").live('click', function() {
    $j("ul .popOutgoing").hide();

    var aData = binTable.fnGetData(editTr);

    var oFormObject = document.forms['outgoingvoid'];

    oFormObject.elements["outgoinguuidvoid"].value = aData[2];

    $j("#outgoingvoid").show();
});

$j("#canceloutgoing").live('click', function() {
    $j("ul .popOutgoing").hide();


});

$j("#approveoutgoing").live('click', function() {

    $j("ul .popOutgoing").hide();

    var aData = binTable.fnGetData(editTr);
    var oFormObject = document.forms['outgoingextra'];
    oFormObject.elements["outgoinguuidextra"].value = aData[2];
    oFormObject.elements["outgoingdrug"].value = aData[3];


    $j("#hideoutgoingform").show();

    $j("#outgoingperm").show();//

   setTable(aData[2]);


});


$j('#toutgoing tbody td ul').live('click', function() {
    editTr = this.parentNode.parentNode;
    var aData = binTable.fnGetData(editTr);
    link="#popOutgoing"+aData[10];



    $j(link).show();




});


$j('#toutgoing tbody td  em').live('click', function() {

    var nTr = this.parentNode.parentNode;
    //getDataSupplier();



});
$j("form#filteroutgoing").submit(function() {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#filteroutgoing").valid()) {
        AutoReload();


        return false;
    }
});
function clear_form_elements(ele) {

    $j(ele).find(':input').each(function() {
        switch(this.type) {
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

$j("#encountformOut" ).dialog({
    autoOpen: false,
    height: 500,
    width:700,
    cache: false,
    modal: true,
    buttons: {

        Ok: function() {
            $j(this).dialog( "close" );
        }
    }
});

$j("#dataformD" ).dialog({
    autoOpen: false,
    height: 500,
    width:700,
    cache: false,
    modal: true,
    buttons: {
        "Yes": function() {

            $j(this).dialog( "close" );


            $j.ajax({
                type : "POST",
                url : "drugOutgoing.form",
                data : serverData,
                success : function() {

                    AutoReload();
                    clear_form_elements("#Permission");


                }
            });
        },
        Cancel: function() {
            $j(this).dialog( "close" );
        }
    }
});
$j("#detailsformout" ).dialog({
    autoOpen: false,
    height: 500,
    width:700,
    cache: false,
    modal: true,
    buttons: {
        "Yes": function() {

            $j(this).dialog( "close" );


            $j.ajax({
                type : "POST",
                url : "drugOutgoing.form",
                data : serverData,
                success : function() {

                    AutoReload();
                    clear_form_elements("#Permission");


                }
            });
        },
        Cancel: function() {
            $j(this).dialog( "close" );
        }
    }
});
function closeOutgoingExtra(){




    AutoReload();


    $j("#incoming").hide();//



    var oFormObject = document.forms['outgoingextra'];


    oFormObject.elements["outgoingnumber"].value = "";


    oFormObject.elements["outgoings11"].value = "";



    oFormObject.elements["outgoinguuidextra"].value = "";


    $j('#tinventoryoutgoing').dataTable().fnReloadAjax("drugBincard.form?dialog=dialog&uuid=123");
    var  i;
    for(i=0;i<totalElements;i++){


        removeInput();
    }

    totalElements=0;


}



$j("form#outgoingextra").submit(function() {


    // we want to store the values from the form input box, then send via ajax below
    if ($j("#outgoingextra").valid()) {
        dataString = $j("#outgoingextra").serialize();
        dataPerm = $j("#Permission").serialize();

        var answers = [];
        $j.each($j('.datavalues'), function() {
            answers.push($j(this).val());



        });

        var answers2 ="&answers="+answers;

        dataStringTwo=dataString+answers2+"&"+dataPerm;

        var i=0;
        var myBoolean=new Boolean();
        myBoolean=true;
        for(var i=1;i<answers.length;i++){
            if(i==1){

                if(parseInt(answers[1])>parseInt(oFormObjectForm.elements["one22"].value)){
                    myBoolean=false;
                }
            }
            else if(i==2){

                if(parseInt(answers[2])>parseInt(oFormObjectForm.elements["one23"].value))
                    myBoolean=false;
            }
            else if(i==3){
                if(parseInt(answers[3])>parseInt(oFormObjectForm.elements["one24"].value))
                    myBoolean=false;
            }
            else if(i==4){
                if(parseInt(answers[5])>parseInt(oFormObjectForm.elements["one25"].value))
                    myBoolean=false;
            }

        }
        if(myBoolean){


            var myResult = dataStringTwo.split("&");

            $j("#detailsformout").empty();
            for(i = 0; i < myResult.length; i++){


                if(i==0)
                    $j('<dl><dt></dt><dd > -'+"S11 No "+myResult[i].substring(myResult[i].indexOf("="))+'</dd></dl> ').appendTo('#detailsformout');
                else if(i==1)
                    $j('<dl><dt></dt><dd > -'+"Quantity "+myResult[i].substring(myResult[i].indexOf("="))+'</dd></dl> ').appendTo('#detailsformout');
                else if(i==14)
                    $j('<dl><dt></dt><dd > -'+"Requested By "+myResult[i].substring(myResult[i].indexOf("="))+'</dd></dl> ').appendTo('#detailsformout');
                else if(i==16)
                    $j('<dl><dt></dt><dd > -'+"Issued By "+myResult[i].substring(myResult[i].indexOf("="))+'</dd></dl> ').appendTo('#detailsformout');
                else if(i==18)
                    $j('<dl><dt></dt><dd > -'+"Authorized By "+myResult[i].substring(myResult[i].indexOf("="))+'</dd></dl> ').appendTo('#detailsformout');

            }


            $j("#detailsformout").dialog("open" );







        }
        else
        {

            $j("#errorDivOut").show();

            $j("#errorDivOut").delay(10000).hide("slow");
        }

        return false;
    }
});

$j.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings, sNewSource)
{
    oSettings.sAjaxSource = sNewSource;
    this.fnClearTable(this);
    this.oApi._fnProcessingDisplay(oSettings, true );
    var that = this;

    $j.getJSON(oSettings.sAjaxSource, null, function(json){
        /* Got the data - add it to the table */
        for (var i=0; i<json.aaData.length; i++)
        {
            that.oApi._fnAddData(oSettings, json.aaData[i]);
        }

        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        that.fnDraw(that);
        that.oApi._fnProcessingDisplay(oSettings, false);
    });
}

function setTable(uuid){

    if( $j('#parent_div_1out').is(':visible') ) {



        $j('#tinventoryoutgoing').dataTable().fnReloadAjax("drugBincard.form?dialog=dialog&uuid="+uuid);

    }
    else
    {


        binTable2 = 	  $j('#tinventoryoutgoing').dataTable({

            bJQueryUI : true,
            bRetrieve : true,
            bAutoWidth : false,
            bServerSide : true,
            bProcessing : true,
            sAjaxSource : 'drugBincard.form?dialog=dialog&uuid='+uuid,
            "fnServerData": fnDataTablesPipeline,
            "aoColumnDefs" : [ {
                "bVisible" : false,
                "aTargets" : [ 0 ]
            }]
        });

    }


    $j("#parent_div_1out").show();
    $j("#parent_div_2out").show();


    //RefreshInventory('#tinventoryval');


}
//$j('#outgoingnumber').attr('disabled','disabled');


function addInput(nTrIn){


    var num     = $j('.clonedInput').length;
    var newNum  = new Number(num + 1);
    totalElements+=1;

    if(newNum<=5){
        var newElem = $j('#input' + num).clone().attr('id', 'input' + newNum);

        newElem.children(':first').attr('id', 'answers' + newNum).attr('answers', 'answers');
        var idtag  = "#answers" + newNum;


        $j('#input' + num).after(newElem);
        $j('#btnDel').attr('disabled','');



        var aData = binTable2.fnGetData(nTrIn);
        oFormObjectForm = document.forms['outgoingextra'];

        var val="one"+newNum;
        oFormObjectForm.elements[val].value = aData[0];
        var valTwo="one2"+newNum;
        oFormObjectForm.elements[valTwo].value = aData[2];
//

    }
    else
        alert("Cannont add more than four at once"+newNum);

    $j('#outgoingnumber').attr('disabled','disabled');



}
function removeInput(){


    var num = $j('.clonedInput').length;

    $j('#input' + num).remove();
    $j('#btnAdd').attr('disabled','');

    if (num-1 == 1){
        $j('#btnDel').attr('disabled','disabled');

        $j('#outgoingnumber').attr('disabled','disabled');
    }

    var val="one"+num;
    oFormObjectForm.elements[val].value = "";
    var valTwo="one2"+num;
    oFormObjectForm.elements[valTwo].value ="";
}

$j('#btnAdd').click(function() {

    var num     = $j('.clonedInput').length;
    var newNum  = new Number(num + 1);

    var newElem = $j('#input' + num).clone().attr('id', 'input' + newNum);

    newElem.children(':first').attr('id', 'answers' + newNum).attr('answers', 'answers');
    var idtag  = "#answers" + newNum;


    $j('#input' + num).after(newElem);
    $j('#btnDel').attr('disabled','');

});

$j('#btnDel').click(function() {
    var num = $j('.clonedInput').length;

    $j('#input' + num).remove();
    $j('#btnAdd').attr('disabled','');

    if (num-1 == 1){
        $j('#btnDel').attr('disabled','disabled');

        $j('#outgoingnumber').attr('disabled','disabled');
    }

    var val="one"+num;
    oFormObjectForm.elements[val].value = "";
    var valTwo="one2"+num;
    oFormObjectForm.elements[valTwo].value ="";


});

$j('#btnDel').attr('disabled','disabled');

$j('#outgoingnumber').attr('disabled','disabled');




$j('#tinventoryoutgoing').delegate('tbody tr  input','click', function() {

    nTrIn = this.parentNode.parentNode;

    var aData2 = binTable2.fnGetData(nTrIn);
    var oFormObject = document.forms['outgoingextra'];

    var myObj=new Array();
    myObj[0]=oFormObject.elements["one2"].value;
    myObj[1]=oFormObject.elements["one3"].value;
    myObj[2]=oFormObject.elements["one4"].value;
    myObj[3]=oFormObject.elements["one5"].value;
    var myBoolean=new Boolean();
    myBoolean=false;


    for(var i=0;i<myObj.length;i++){

        if(myObj[i]==aData2[0]){

            myBoolean=true;
            break;
        }


    }

    if(!myBoolean){

        $j('#outgoingnumber').attr('disabled','');

        addInput(nTrIn);
    }
    else{


        removeInput();
    }
//	
//}
//else if(num==0){
//	
//	alert("out");
////	removeInput();
//}
////	
//  	alert("3333333");
//	
//}else
//	{
//	alert("cvcvcvcv");
//	
//	}


    return true;
});
function voidData(nTr){
    var aData = binTable.fnGetData(nTr);

    var dataVal="outgoinguuidvoid="+aData[1]+"&outgoingreason="+"Dataadded";
    $j.ajax({
        type : "POST",
        url : "drugIncoming.form",
        data : dataVal,


        success : function() {

            AutoReload();
        }
    });
}
$j("#detailsoutgoing").live('click', function() {


    $j("ul .popOutgoing").hide();


    $j("#encountformOut").empty();
    //


    var aData = binTable.fnGetData(editTr);



    $j('<dl><dt></dt><dd > -'+"Drug:"+aData[3]+'</dd></dl> ').appendTo('#encountformOut');
    $j('<dl><dt></dt><dd > -'+"Drug:"+aData[3]+'</dd></dl> ').appendTo('#encountformOut');
    $j('<dl><dt></dt><dd > -'+"Quantity:"+aData[4]+'</dd></dl> ').appendTo('#encountformOut');
    $j('<dl><dt></dt><dd > -'+"Request from:"+aData[5]+'</dd></dl> ').appendTo('#encountformOut');
    $j('<dl><dt></dt><dd > -'+"Request to:"+aData[6]+'</dd></dl> ').appendTo('#encountformOut');

    $j('<dl><dt></dt><dd > -'+"Transaction type:"+aData[7]+'</dd></dl> ').appendTo('#encountformOut');
    $j('<dl><dt></dt><dd > -'+"Supplier:"+aData[8]+'</dd></dl> ').appendTo('#encountformOut');

    $j("#encountformOut").dialog("open" );


//
//
//
//	
//	//overlay();
//	
//	el = document.getElementById("overlay");
//	el.style.visibility = (el.style.visibility == "visible") ? "hidden" : "visible";
//	  

});

$j("#detailsformVal" ).dialog({
    autoOpen: false,
    height: 300,
    width:400,
    cache: false,
    modal: true,
    buttons: {
        "Yes": function() {
            $j.ajax({
                type : "POST",
                url : "drugOutgoing.form",
                data : dataStringVal,
                success : function() {

                    $j("#outgoingVal").hide();//

                    var oFormObject = document.forms['outgoingVal'];

                    oFormObject.elements["outgoingedit"].value = 'false';
                    oFormObject.elements["outgoingmax"].value = "";
                    oFormObject.elements["outgoingmin"].value = "";

                    oFormObject.elements["outgoingquantityin"].value = "";

                }
            });
            $j("#detailsformVal" ).dialog( "close" );

        },
        Cancel: function() {
            $j("#detailsformVal" ).dialog( "close" );
        }
    }
});
$j("form#outgoingVal").submit(function() {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#outgoingVal").valid()) {
        dataStringVal = $j("#outgoingVal").serialize();

        var myResult = dataStringVal.split("&");

        $j("#detailsformVal").empty();
        for(i = 0; i < myResult.length; i++){

            $j('<dl><dt></dt><dd > -'+myResult[i]+'</dd></dl> ').appendTo('#detailsformVal');

        }


        $j("#detailsformVal").dialog("open" );


        return false;
    }
});
$j("form#outgoingvoid").submit(function() {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#outgoingvoid").valid()) {
        dataString = $j("#outgoingvoid").serialize();

        $j.ajax({
            type : "POST",
            url : "drugOutgoing.form",
            data : dataString,
            success : function() {
                $j("#outgoingvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});



$j("#hideoutgoingform").click(function() {

    $j("#hideoutgoingform").hide();
    $j("#parent_div_1out").hide();

    $j("#parent_div_2out").hide();

});
$j("#outgoingform").click(function() {
    getDataLocation();
    getDataDrug();


    getDataSupplier();
    getDataTransactionType();
    getDataLocationTwo();
    show();


    document.getElementById('outgoingdrug').addEventListener('change', change, false);
});
function change() {

    var e = document.getElementById("outgoingdrug");
    var strUser = e.options[e.selectedIndex].value;

    getDataTotal(strUser);
}

function getDataDrug() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop",
        function(result) {

            $j("#outgoingdrug").get(0).options.length = 0;
            $j("#outgoingdrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function(index, value) { //bincard"stateList

                    $j("#outgoingdrug").get(0).options[$j(
                        "#outgoingdrug").get(0).options.length] = new Option(
                        value, value);
                });
            getArrayDialog
        });

}

function getDataLocation() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function(result) {

            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");

            $j.each(
                result,
                function(index, value) { //bincard"stateList

                    $j("#location").get(0).options[$j("#location").get(0).options.length] = new Option(value, value);
                });

        });


}
function getDataLocationTwo() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function(result) {
            $j("#destination").get(0).options.length = 0;
            $j("#destination").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
                result,
                function(index, value) { //bincard"stateList

                    $j("#destination").get(0).options[$j("#destination").get(0).options.length] = new Option(value, value);
                });

        });


}

function getDataSupplier() {

    $j
        .getJSON(
        "supplierName.form?drop=drop",
        function(result) {

            $j("#supplierout").get(0).options.length = 0;
            $j("#supplierout").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function(index, value) { //bincard"stateList

                    $j("#supplierout").get(0).options[$j(
                        "#supplierout").get(0).options.length] = new Option(
                        value, value);
                });

        });


}


function getUsers(){


    $j.getJSON("dispense.form?users=users",
        function(result) {


            $j("#requisitiono").get(0).options.length = 0;
            $j("#requisitiono").get(0).options[0] = new Option("Select",
                "-1");

            $j("#issuedo").get(0).options.length = 0;
            $j("#issuedo").get(0).options[0] = new Option("Select",
                "-1");

            $j("#authorizedo").get(0).options.length = 0;
            $j("#authorizedo").get(0).options[0] = new Option("Select",
                "-1");
            $j.each(
                result,
                function(index, value) { //bincard"stateList



                    $j("#requisitiono").get(0).options[$j(
                        "#requisitiono").get(0).options.length] = new Option(
                        value, value);


                    $j("#issuedo").get(0).options[$j(
                        "#issuedo").get(0).options.length] = new Option(
                        value, value);


                    $j("#authorizedo").get(0).options[$j(
                        "#authorizedo").get(0).options.length] = new Option(
                        value, value);

                });

        });


}

function getDataTransactionType() {

    $j
        .getJSON(
        "transactionsName.form?drop=drop",
        function(result) {

            $j("#transactions").get(0).options.length = 0;
            $j("#transactions").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function(index, value) { //bincard"stateList

                    $j("#transactions").get(0).options[$j(
                        "#transactions").get(0).options.length] = new Option(
                        value, value);
                });

        });


}
function getDataTotal(drug) {

    var url="transactionsName.form?drop=total&drug="+drug;
    $j
        .getJSON(url
        ,
        function(result) {

            var oFormObject = document.forms['outgoingVal'];

            oFormObject.elements["totalstore"].value = result;
        });


}



$j("#filterdrugoutgoing").autocomplete({
    search : function() {
        $j(this).addClass('working');
    },

    source : function(request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function(result) {

            $j("#filterdrugoutgoing").removeClass('working');

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
        binTable.fnFilter( ui.item.label );
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
function getDrugFilter() {



}

function show() {


    $j("#outgoingVal").show("slow");

}



$j('#Saveo').live('click', function() {

//    $j("#submitAllo").show();
//    $j("#outgoingperm").hide();//




    fnGetSelectedVal();

    return false;



});

$j('#submitAllo').live('click', function() {
    $j("#SaveDivo").show();
    $j("#Saveo").show();
    $j("#outgoingperm").show();//

    $j(this).hide();


});


function fnGetSelectedVal(){

    $j("#dataformD").empty();
    dataPerm = $j("#Permission").serialize();
    var myResultData="";
    var check;

    $j('#toutgoing').find('input').each(function(){




        if($j(this).is(':checked')){

            nTrIn = this.parentNode.parentNode;


            var aData2 = binTable.fnGetData(nTrIn);
            var text=aData2[19];

                  var name="#"+aData2[19].substring(11,47);

            $j('<dl><dt></dt><dd > -'+"Drug "+aData2[3]+" & Quantity ="+aData2[4]+'</dd></dl> ').appendTo('#dataformD');
            myResultData+="&drugId="+aData2[2]+"&quantity="+aData2[4]+"&quantityToGive="+$j(name).val();



        }



        });





    var myResult = dataPerm.split("&");

    serverData= myResultData+"&"+dataPerm+"&outgoinguuidextra=cvcvc";


    $j("#dataformD").dialog("open" );



}
