var oCache = {
    iCacheLower: -1
};




var locationaOoTable;
var locationEditTr;
var locationaLink;
var  uuid;
var locationaData;
$j("#locationform").validate(); //
$j("#locationvoid").validate(); //

$j("#location").hide();//
$j("#locationvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tlocation', 'locationName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {


    var oFormObject = document.forms['locationform'];

    oFormObject.elements["locationname"].value = locationaData[3];
    oFormObject.elements["locationedit"].value = 'true';
    oFormObject.elements["locationuuid"].value = locationaData[2];
    oFormObject.elements["description"].value = locationaData[4];

}

locationaOoTable = $j('#tlocation').dataTable({
    bJQueryUI : true,
    bRetrieve : true,
    bServerSide: true,
    bAutoWidth: false,
    bProcessing : true,
    sAjaxSource : 'locationName.form',
    "fnServerData": fnDataTablesPipeline,
    fnRowCallback: function( nRow, locationaData, iDisplayIndex ) {


        var htm= '<ul class="popLocation">	<li> <img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popLocation" id='+"popLocation"+locationaData[2]+'>';


        if(locationaData[0]=="edit"){
            htm +=	'<li> <a href=""  id="edit"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if(locationaData[5]=="void"){
            htm +=	'<li> <a href="" id="deletel" ><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }



        htm +='<li> <a href="" id="cancelLoc"><img src="'+jQuery.Page.context+'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';




        htm += '</ul></li></ul>';




        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },

    "aoColumnDefs" : [ {
        "bVisible" : false,
        "aTargets" : [0,2,5 ]
    } ]
});



$j('#tlocation tbody tr').live('click', function() {
    var data = locationaOoTable.fnGetData( this );
    uuid=data[2];

    // ... do something with the array / object of data for the row
} );
//						locationaOoTable.fnSetColumnVis( 1, false );

$j('#edit').live('click', function() {

    $j("ul .popLocation").hide();
    fnFormatDetails(locationEditTr);
    return false;
});


$j('#cancelLoc').live('click', function() {


    $j("ul .popLocation").hide();
    return false;
});


$j('#tlocation tbody td ul').live('click', function() {



    locationEditTr = this.parentNode.parentNode;
     locationaData = locationaOoTable.fnGetData(locationEditTr);
    locationaLink="#popLocation"+locationaData[2];


    $j(locationaLink).show();

//	

});
$j('#deletel').live('click', function() {
    $j("ul .popLocation").hide();


    var oFormObject = document.forms['locationvoid'];

    oFormObject.elements["locationuuidvoid"].value = locationaData[2];
    $j("#locationvoid").show();//
    return false;
});

$j("form#locationform").submit(function() {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#locationform").valid()) {
        dataString = $j("#locationform").serialize();

        $j.ajax({
            type : "POST",
            url : "locationName.form",
            data : dataString,
            success : function() {
                AutoReload();
                var oFormObject = document.forms['locationform'];

                oFormObject.elements["locationedit"].value = 'false';

                oFormObject.elements["locationname"].value = "";
                oFormObject.elements["description"].value = "";

            }
        });
        return false;
    }
});

$j("form#locationvoid").submit(function() {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#locationvoid").valid()) {
        dataString = $j("#locationvoid").serialize();

        $j.ajax({
            type : "POST",
            url : "locationName.form",
            data : dataString,
            success : function() {
                AutoReload();
                $j("#locationvoid").hide();//
                var oFormObject = document.forms['locationvoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});



$j("#locationreason").autocomplete({
    minLength : 0,

    source : formReasons,

    select : function(event, ui) {

        $j("#locationreason").removeClass("highlight");
        $j(this).autocomplete('close');



    } ,

    change: function(event, ui) {
        if (!ui.item) {

            $j('#locationreason').addClass('highlight');

            $j("#locationreason").val("");
        }
    }

});
