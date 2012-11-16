var oCache = {
    iCacheLower:-1
};

var usersTable;
var userEditTr;
var userLink;
var uuid;
var useraData;
$j("#locationformusers").validate(); //
$j("#locationvoidusers").validate(); //

$j("#locationusers").hide();//
$j("#locationvoidusers").hide();//

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

$j("#username").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "user=" + request.term;

        $j.getJSON("locationUsers.form?" + dataString, function (result) {

            $j("#username").removeClass('working');

            response($j.each(result, function (index, item) {

                return {
                    label:item,
                    value:item
                }
            }));

        });

    },
    minLength:2,
    select:function (event, ui) {

    },
    change:function (event, ui) {
        if (!ui.item) {


            $j("#username").val("");
        }
    }
});


$j("#locationname").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "locations=" + request.term;

        $j.getJSON("locationUsers.form?" + dataString, function (result) {

            $j("#locationname").removeClass('working');

            response($j.each(result, function (index, item) {

                return {
                    label:item,
                    value:item
                }
            }));

        });

    },
    minLength:2,
    select:function (event, ui) {

        // log( ui.item ?
        // "Selected: " + ui.item.label :
        // "Nothing selected, input was " + this.value);
    },

    change:function (event, ui) {
        if (!ui.item) {


            $j("#locationname").val("");
        }
    }

});
function AutoReload() {
    RefreshTable('#tlocationusers', 'locationUsers.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {


    var oFormObject = document.forms['locationformusers'];

    oFormObject.elements["locationname"].value = useraData[4];
    oFormObject.elements["locationedit"].value = 'true';
    oFormObject.elements["locationuuid"].value = useraData[2];
    oFormObject.elements["username"].value = useraData[3];

}

usersTable = $j('#tlocationusers').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bAutoWidth:false,
    bProcessing:true,
    sAjaxSource:'locationUsers.form',
    "fnServerData":fnDataTablesPipeline,
    fnRowCallback:function (nRow, useraData, iDisplayIndex) {


        var htm = '<ul class="popLocationUsers">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popLocationUsers" id=' + "popLocationUsers" + useraData[2] + '>';


        if (useraData[0] == "edit") {
            htm += '<li> <a href=""  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }

        if (useraData[5] == "void") {
            htm += '<li> <a href="" id="deleteUser" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="" id="cancelUser"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[0, 2, 5 ]
        }
    ]
}).rowGrouping({    bHideGroupingColumn:false,
        iGroupingColumnIndex:3,
        bExpandableGrouping:true,
        bExpandSingleGroup:true,
        iExpandGroupOffset:-1 });


$j('#tlocationusers tbody tr').live('click', function () {
    var data = usersTable.fnGetData(this);
    uuid = data[2];

    // ... do something with the array / object of data for the row
});
//						usersTable.fnSetColumnVis( 1, false );

$j('#edit').live('click', function () {
    $j("ul .popLocationUsers").hide();
    fnFormatDetails(userEditTr);
    return false;
});


$j('#cancelUser').live('click', function () {


    $j("ul .popLocationUsers").hide();
    return false;
});


$j('#tlocationusers tbody td ul').live('click', function () {


    userEditTr = this.parentNode.parentNode;
    useraData = usersTable.fnGetData(userEditTr);
    userLink = "#popLocationUsers" + useraData[2];


    $j(userLink).show();

//

});
$j('#deleteUser').live('click', function () {

    $j("ul .popLocationUsers").hide();
    var oFormObjectUser = document.forms['locationvoidusers'];

    oFormObjectUser.elements["locationuuidvoid"].value = useraData[2];

    $j("#locationvoidusers").show();//

    return false;

});

$j("form#locationformusers").submit(function () {
    // we want to store the values from the form input box, then send via ajax below

    if ($j("#locationformusers").valid()) {
        dataString = $j("#locationformusers").serialize();

        $j.ajax({
            type:"POST",
            url:"locationUsers.form",
            data:dataString,
            success:function () {
                AutoReload();
                var oFormObject = document.forms['locationformusers'];

                oFormObject.elements["locationedit"].value = 'false';

                oFormObject.elements["locationname"].value = "";
                oFormObject.elements["username"].value = "";

            }
        });
        return false;
    }
});

$j("form#locationvoidusers").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#locationvoidusers").valid()) {
        dataString = $j("#locationvoidusers").serialize();

        $j.ajax({
            type:"POST",
            url:"locationUsers.form",
            data:dataString,
            success:function () {
                AutoReload();
                $j("#locationvoid").hide();//
                var oFormObject = document.forms['locationvoidusers'];

                oFormObject.elements["reason"].value = "";

            }
        });
        return false;
    }
});


$j("#locationreason").autocomplete({
    minLength:0,

    source:formReasons,

    select:function (event, ui) {

        $j("#locationreason").removeClass("highlight");
        $j(this).autocomplete('close');


    },

    change:function (event, ui) {
        if (!ui.item) {

            $j('#locationreason').addClass('highlight');

            $j("#locationreason").val("");
        }
    }

});