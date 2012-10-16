var maxooTable;
var maxeditTr;
var maxLink;
var maxaData;
var oCache = {
    iCacheLower:-1
};

$j("#maxminform").validate(); //
$j("#maxminvoid").validate(); //

$j("#maxmin").hide();//
$j("#maxminvoid").hide();//

getMaxMinFilter();

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tmaxmin', 'maxminName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {


    var oFormObject = document.forms['maxminform'];

    oFormObject.elements["max"].value = maxaData[4];
    oFormObject.elements["maxminedit"].value = 'true';
    oFormObject.elements["maxminuuid"].value = maxaData[2];
    oFormObject.elements["min"].value = maxaData[5];

}

maxooTable = $j('#tmaxmin').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,

    "fnRowCallback":function (nRow, maxaData, iDisplayIndex) {


        var htm = '<ul class="popMax">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popMax" id=' + "popMax" + maxaData[2] + '>';


        if (maxaData[0] == "edit") {
            htm += '<li> <a href="#"  id="editMax"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (maxaData[6] == "void") {
            htm += '<li> <a href="#" id="deleteMax" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="cancelMax"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'maxminName.form',
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
            "aTargets":[ 6 ]
        }
    ]
});

//						maxooTable.fnSetColumnVis( 1, false );
$j('#cancelMax').live('click', function () {
    $j("ul .popMax").hide();

});


$j('#tmaxmin tbody td ul').live('click', function () {


    maxeditTr = this.parentNode.parentNode;
    maxaData = maxooTable.fnGetData(maxeditTr);
    maxLink = "#popMax" + maxaData[2];


    $j(maxLink).show();

//	

});

$j('#editMax').live('click', function () {
    $j("ul .popMax").hide();
    fnFormatDetails(maxeditTr);

});


$j('#deleteMax').live('click', function () {
    $j("ul .popMax").hide();

    var oFormObject = document.forms['maxminvoid'];

    oFormObject.elements["maxminuuidvoid"].value = maxaData[2];
    $j("#maxminvoid").show();//
});

$j("form#maxminform").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#maxminform").valid()) {
        dataString = $j("#maxminform").serialize();

        $j.ajax({
            type:"POST",
            url:"maxminName.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['maxminform'];

                oFormObject.elements["maxminedit"].value = 'false';

                oFormObject.elements["max"].value = "";
                oFormObject.elements["min"].value = "";

            }
        });
        return false;
    }
});

$j("form#maxminvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#maxminvoid").valid()) {
        dataString = $j("#maxminvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"maxminName.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#maxminvoid").hide();//
                var oFormObject = document.forms['maxminvoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});


$j("#maxminreason").autocomplete({
    minLength:0,

    source:formReasons,

    select:function (event, ui) {

        $j("#maxminreason").removeClass("highlight");
        $j(this).autocomplete('close');


    },

    change:function (event, ui) {
        if (!ui.item) {

            $j('#maxminreason').addClass('highlight');

            $j("#maxminreason").val("");
        }
    }

});


$j("#maxmindrug").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#maxmindrug").removeClass('working');

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

        // "Selected: " + ui.item.label :
        // "Nothing selected, input was " + this.value);
    },
    change:function (event, ui) {
        if (!ui.item) {

            $j("#maxmindrug").val("");
        }
    }
});
function getMaxMinFilter() {


}


$j("#maxminreason").autocomplete({
    minLength:0,

    source:formReasons,

    select:function (event, ui) {

        $j("#maxminreason").removeClass("highlight");
        $j(this).autocomplete('close');


    },

    change:function (event, ui) {
        if (!ui.item) {

            $j('#maxminreason').addClass('highlight');

            $j("#maxminreason").val("");
        }
    }

});