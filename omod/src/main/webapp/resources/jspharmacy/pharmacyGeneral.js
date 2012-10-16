var oCache = {
    iCacheLower:-1
};

var ooTable;
var editTr;
var link;

$j("#generalForm").validate(); //
$j("#generalvoid").validate(); //

$j("#general").hide();//
$j("#generalvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tgeneral', 'generalName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {

    var aData = ooTable.fnGetData(nTr);

    var oFormObject = document.forms['generalform'];

    oFormObject.elements["generalname"].value = aData[3];
    oFormObject.elements["generaledit"].value = 'true';
    oFormObject.elements["generaluuid"].value = aData[2];
    oFormObject.elements["description"].value = aData[4];

}

ooTable = $j('#tgeneral').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    "fnRowCallback":function (nRow, aData, iDisplayIndex) {


        var htm = '<ul class="popGeneral">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popGeneral" id=' + "popGeneral" + aData[2] + '>';


        if (aData[0] == "edit") {
            htm += '<li> <a href="#"  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (aData[5] == "void") {
            htm += '<li> <a href="#" id="deleteg" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'generalName.form',
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
$j('#tgeneral tbody td ul').live('click', function () {


    editTr = this.parentNode.parentNode;
    var aData = ooTable.fnGetData(editTr);
    link = "#popGeneral" + aData[2];


    $j(link).show();

//	

});
$j('#edit').live('click', function () {
    $j("ul .popGeneral").hide();
    fnFormatDetails(editTr);

});


$j('#cancel').live('click', function () {
    $j("ul .popGeneral").hide();

});


$j('#deleteg').live('click', function () {
    $j("ul .popGeneral").hide();
    var aData = ooTable.fnGetData(editTr);

    var oFormObject = document.forms['generalvoid'];

    oFormObject.elements["generaluuidvoid"].value = aData[2];
    $j("#generalvoid").show();//
});

$j("form#generalForm").submit(function () {
    // we want to store the values from the form input box, then send via ajax below


    if ($j("#generalForm").valid()) {
        var data = $j("#generalForm").serialize();


        $j.ajax({
            type:"POST",
            url:"generalName.form",
            data:data,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['generalForm'];

                oFormObject.elements["generaledit"].value = 'false';

                oFormObject.elements["generalname"].value = "";
                oFormObject.elements["description"].value = "";

            }
        });
        return false;
    }
});

function clear_form_elements(ele) {

    $j(ele).find(':input').each(function () {
        switch (this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'textarea':
                $j(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });

}


$j("form#generalvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#generalvoid").valid()) {
        dataString = $j("#generalvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"generalName.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#generalvoid").hide();//
                var oFormObject = document.forms['generalvoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});
