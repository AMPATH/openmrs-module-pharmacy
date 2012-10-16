var oCache = {
    iCacheLower:-1
};

var ooTable;
var editTr;
var link;
var aData;
$j("#supplierform").validate(); //
$j("#suppliervoid").validate(); //

$j("#supplier").hide();//
$j("#suppliervoid").hide();//


function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tsupplier', 'supplierName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {


    var oFormObject = document.forms['supplierform'];

    oFormObject.elements["suppliername"].value = aData[3];
    oFormObject.elements["supplieredit"].value = 'true';
    oFormObject.elements["supplieruuid"].value = aData[2];
    oFormObject.elements["description"].value = aData[4];

}
ooTable = $j('#tsupplier').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {


        var htm = '<ul class="popSupplier">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popSupplier" id=' + "popSupplier" + aData[2] + '>';


        if (aData[0] == "edit") {
            htm += '<li> <a href="#"  id="editS"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (aData[5] == "void") {
            htm += '<li> <a href="#" id="void" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'supplierName.form',
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
            "aTargets":[ 5 ]
        }
    ]
});

//						ooTable.fnSetColumnVis( 1, false );

$j('#editS').live('click', function () {
    $j("ul .popSupplier").hide();
    fnFormatDetails(editTr);

});

$j('#cancel').live('click', function () {
    $j("ul .popSupplier").hide();

});


$j('#tsupplier tbody td ul').live('click', function () {


    editTr = this.parentNode.parentNode;
    aData = ooTable.fnGetData(editTr);
    link = "#popSupplier" + aData[2];


    $j(link).show();

//	

});
$j('#void').live('click', function () {

    $j("ul .popSupplier").hide();

    var oFormObject = document.forms['suppliervoid'];

    oFormObject.elements["supplieruuidvoid"].value = aData[2];
    $j("#suppliervoid").show();//
});


$j("form#supplierform").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#supplierform").valid()) {
        dataString = $j("#supplierform").serialize();

        $j.ajax({
            type:"POST",
            url:"supplierName.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['supplierform'];

                oFormObject.elements["supplieredit"].value = 'false';

                oFormObject.elements["suppliername"].value = "";
                oFormObject.elements["description"].value = "";

            }
        });
        return false;
    }
});

$j("form#suppliervoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#suppliervoid").valid()) {
        dataString = $j("#suppliervoid").serialize();

        $j.ajax({
            type:"POST",
            url:"supplierName.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#suppliervoid").hide();//
                var oFormObject = document.forms['suppliervoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});


$j("#supplierreason").autocomplete({
    minLength:0,

    source:formReasons,

    select:function (event, ui) {

        $j("#supplierreason").removeClass("highlight");
        $j(this).autocomplete('close');


    },

    change:function (event, ui) {
        if (!ui.item) {

            $j('#supplierreason').addClass('highlight');

            $j("#supplierreason").val("");
        }
    }

});