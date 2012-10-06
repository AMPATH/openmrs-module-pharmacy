var TypesTable;
oCache.iCacheLower = -1;
var linkType;
var typeEditTr;
var typeaData;

$j("#transactionsform").validate(); //
$j("#transactionsvoid").validate(); //

$j("#transactions").hide();//
$j("#transactionsvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#ttransactions', 'transactionsName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {



    var oFormObject = document.forms['transactionsform'];

    oFormObject.elements["transactionsname"].value = typeaData[3];
    oFormObject.elements["transactionsedit"].value = 'true';
    oFormObject.elements["transactionsuuid"].value = typeaData[2];
    oFormObject.elements["description"].value = typeaData[4];

}

TypesTable = $j('#ttransactions').dataTable({
    bJQueryUI : true,
    bRetrieve : true,
    bServerSide : true,
    bAutoWidth: false,
    bProcessing : true,
    "fnRowCallback": function( nRow, typeaData, iDisplayIndex ) {


        var htm= '<ul class="popTransaction">	<li> <img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popTransaction" id='+"popTransaction"+typeaData[2]+'>';


        if(typeaData[0]=="edit"){
            htm +=	'<li> <a href="#"  id="edit"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if(typeaData[5]=="void"){
            htm +=	'<li> <a href="#" id="void" ><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }



        htm +='<li> <a href="#" id="cancel"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';




        htm += '</ul></li></ul>';




        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource : 'transactionsName.form',
    "fnServerData": fnDataTablesPipeline,
    "aoColumnDefs" : [ {
        "bVisible" : false,
        "aTargets" : [ 0 ]
    },
        {
            "bVisible" : false,
            "aTargets" : [ 2 ]
        },
        {
            "bVisible" : false,
            "aTargets" : [ 5 ]
        } ]
});

//						TypesTable.fnSetColumnVis( 1, false );

$j('#edit').live('click', function() {
    $j("ul .popTransaction").hide();
    fnFormatDetails(typeEditTr);

});

$j('#cancel').live('click', function() {
    $j("ul .popTransaction").hide();

});


$j('#ttransactions tbody td ul').live('click', function() {



    typeEditTr = this.parentNode.parentNode;
     typeaData = TypesTable.fnGetData(typeEditTr);
    linkType="#popTransaction"+typeaData[2];


    $j(linkType).show();

//	

});


$j('#void').live('click', function() {
    $j("ul .popTransaction").hide();

    var oFormObjectmm = document.forms['transactionsvoid'];

    oFormObjectmm.elements["transactionsuuidvoid"].value = typeaData[2];
    $j("#transactionsvoid").show();//


});

$j("form#transactionsform").submit(function() {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#transactionsform").valid()) {
        dataString = $j("#transactionsform").serialize();

        $j.ajax({
            type : "POST",
            url : "transactionsName.form",
            data : dataString,
            success : function() {
                AutoReload();
                var oFormObject = document.forms['transactionsform'];

                oFormObject.elements["transactionsedit"].value = 'false';

                oFormObject.elements["transactionsname"].value = "";
                oFormObject.elements["description"].value = "";

            }
        });
        return false;
    }
});

$j("form#transactionsvoid").submit(function() {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#transactionsvoid").valid()) {
        dataString = $j("#transactionsvoid").serialize();

        $j.ajax({
            type : "POST",
            url : "transactionsName.form",
            data : dataString,
            success : function() {
                AutoReload();
                $j("#transactionsvoid").hide();//
                var oFormObject = document.forms['transactionsvoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});



$j("#transactionsreason").autocomplete({
    minLength : 0,

    source : formReasons,

    select : function(event, ui) {

        $j("#transactionsreason").removeClass("highlight");
        $j(this).autocomplete('close');



    } ,

    change: function(event, ui) {
        if (!ui.item) {

            $j('#transactionsreason').addClass('highlight');

            $j("#transactionsreason").val("");
        }
    }

});