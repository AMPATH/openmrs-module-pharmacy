var oCache = {
    iCacheLower:-1
};

var uoTable;
var editTr;
var link;
$j("#drugregimenname").validate();
$j("#regimennamevoid").validate();

$j("#regimennamevoid").hide();//

function RefreshTable(tableId, urlData) {

    ttable = $j(tableId).dataTable();

    oCache.iCacheLower = -1;

    ttable.fnDraw();
}


function AutoReload() {
    RefreshTable('#tregimenname', 'regimenName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    var aData = uoTable.fnGetData(nTr);

    var oFormObject = document.forms['drugregimenname'];

    oFormObject.elements["regimennamename"].value = aData[3];
    oFormObject.elements["regimennameedit"].value = 'true';
    oFormObject.elements["regimennameuuid"].value = aData[2];


}

uoTable = $j('#tregimenname').dataTable(
    {  bJQueryUI:true,
        bRetrieve:true,
        bServerSide:true,
        bAutoWidth:false,
        bProcessing:true,
        "fnRowCallback":function (nRow, aData, iDisplayIndex) {


            var htm = '<ul class="popRegimenN">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popRegimenN" id=' + "popRegimenN" + aData[2] + '>';


            if (aData[0] == "edit") {
                htm += '<li> <a href="#"  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

            }

            if (aData[4] == "void") {
                htm += '<li> <a href="#" id="void" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
            }


            htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Cancel</a></li>';


            htm += '</ul></li></ul>';


            $j('td:eq(0)', nRow).html(htm);

            return nRow;
        },
        sAjaxSource:'regimenName.form',
        "fnServerData":fnDataTablesPipeline,
        "aoColumnDefs":[
            { "bVisible":false, "aTargets":[ 0 ] },
            { "bVisible":false, "aTargets":[ 2 ] },
            { "bVisible":false, "aTargets":[ 4 ] }
        ]
    });


$j('#edit').live('click', function () {
    $j("ul .popRegimenN").hide();
    fnFormatDetails(editTr);

});

$j('#void').live('click', function () {

    $j("ul .popRegimenN").hide();
    var aData = uoTable.fnGetData(editTr);

    var fbject = document.forms['regimennamevoid'];

    fbject.elements["regimennameuuidvoid"].value = aData[2];

    $j("#regimennamevoid").show();//

});


$j('#cancel').live('click', function () {
    $j("ul .popRegimenN").hide();

});


$j('#tregimenname tbody td ul').live('click', function () {


    editTr = this.parentNode.parentNode;
    var aData = uoTable.fnGetData(editTr);
    link = "#popRegimenN" + aData[2];


    $j(link).show();

//							

});


$j("form#drugregimenname")
    .submit(
    function () {
        // we want to store the values from the form input box, then send via ajax below
        if ($j("#drugregimenname").valid()) {
            dataString = $j("#drugregimenname").serialize();

            $j.ajax({
                type:"POST",
                url:"regimenName.form",
                data:dataString,
                success:function () {
                    AutoReload();
                    var oFormObject = document.forms['drugregimenname'];

                    oFormObject.elements["regimennameedit"].value = 'false';
                    oFormObject.elements["regimennamename"].value = "";

                }
            });
            return false;
        }
    });

$j("form#regimennamevoid")
    .submit(
    function () {
        // we want to store the values from the form input box, then send via ajax below
        if ($j("#regimennamevoid").valid()) {

            dataString = $j("#regimennamevoid").serialize();

            $j.ajax({
                type:"POST",
                url:"regimenName.form",
                data:dataString,
                success:function () {
                    $j("#regimennamevoid").hide();//

                    AutoReload();
                }
            });
            return false;
        }
    });
						
						