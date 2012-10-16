var catooTable;
var catLink;
var cataeditTr;
var cataData;

$j("#categoryform").validate(); //
$j("#categoryvoid").validate(); //

$j("#category").hide();//
$j("#categoryvoid").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}
var oCache = {
    iCacheLower:-1
};

function AutoReload() {
    RefreshTable('#tcategory', 'categoryName.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {


    var oFormObject = document.forms['categoryform'];

    oFormObject.elements["categoryname"].value = cataData[3];
    oFormObject.elements["categoryedit"].value = 'true';
    oFormObject.elements["categoryuuid"].value = cataData[2];
    oFormObject.elements["description"].value = cataData[4];

}

catooTable = $j('#tcategory').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    "fnRowCallback":function (nRow, cataData, iDisplayIndex) {


        var htm = '<ul class="popCategory">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popCategory" id=' + "popCategory" + cataData[2] + '>';


        if (cataData[0] == "edit") {
            htm += '<li> <a href="#"  id="editCat"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (cataData[5] == "void") {
            htm += '<li> <a href="#" id="deleteCat" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="cancelCat"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    sAjaxSource:'categoryName.form',
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

//						catooTable.fnSetColumnVis( 1, false );

$j('#editCat').live('click', function () {
    $j("ul .popCategory").hide();
    fnFormatDetails(cataeditTr);

});
$j('#cancelCat').live('click', function () {
    $j("ul .popCategory").hide();

});


$j('#tcategory tbody td ul').live('click', function () {


    cataeditTr = this.parentNode.parentNode;
    cataData = catooTable.fnGetData(cataeditTr);
    catLink = "#popCategory" + cataData[2];


    $j(catLink).show();

//	

});
$j('#deleteCat').live('click', function () {
    $j("ul .popCategory").hide();

    var oFormObject = document.forms['categoryvoid'];

    oFormObject.elements["categoryuuidvoid"].value = cataData[2];
    $j("#categoryvoid").show();//
});

$j("form#categoryform").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#categoryform").valid()) {
        dataString = $j("#categoryform").serialize();

        $j.ajax({
            type:"POST",
            url:"categoryName.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['categoryform'];

                oFormObject.elements["categoryedit"].value = 'false';

                oFormObject.elements["categoryname"].value = "";
                oFormObject.elements["description"].value = "";

            }
        });
        return false;
    }
});

$j("form#categoryvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#categoryvoid").valid()) {
        dataString = $j("#categoryvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"categoryName.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#categoryvoid").hide();//
                var oFormObject = document.forms['categoryvoid'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});


$j("#categoryreason").autocomplete({
    minLength:0,

    source:formReasons,

    select:function (event, ui) {

        $j("#categoryreason").removeClass("highlight");
        $j(this).autocomplete('close');


    },

    change:function (event, ui) {
        if (!ui.item) {

            $j('#categoryreason').addClass('highlight');

            $j("#categoryreason").val("");
        }
    }

});
