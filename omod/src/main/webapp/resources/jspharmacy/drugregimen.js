var oTable;
var oCache = {
    iCacheLower:-1
};

var editTr;
var link;
$j("#regimenform").hide();//		
$j("#regimenvoid").hide();//		

$j("#regimenvoid").validate();
$j("#regimenform").validate({
    rules:{
        regimennamecomplete:{
            selectNone:true
        },

        optionss:{
            selectNone:true
        },
        drug2:{
            selectNone:true
        },
        drug3:{
            selectNone:true
        }
    }
});

function show_value(val) {

    if (val == "No") {
        $j("#regimendrug1").rules("remove");
        $j("#regimendrug2").rules("remove");

    } else {

        $j("#regimendrug1").rules("add", {
            selectNone:true
        })
        $j("#regimendrug2").rules("add", {
            selectNone:true
        })

    }
}
;

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw(false);
}

function AutoReload() {
    RefreshTable('#tregimen', 'drugRegimen.form');

}
/* Formating function for row details */
function fnFormatDetails(nTr) {
    $j("#regimenform").show();//
    var aData = oTable.fnGetData(nTr);

    var oFormObject = document.forms['regimenform'];

    oFormObject.elements["regimenedit"].value = 'true';
    oFormObject.elements["regimenuuid"].value = aData[2];

}
function getData() {

    $j
        .getJSON(
        "regimenName.form?drop=drop",
        function (result) {

            $j("#regimennamecomplete").get(0).options.length = 0;
            $j("#regimennamecomplete").get(0).options[0] = new Option(
                "Select", "-1");
            $j
                .each(
                result,
                function (index, value) { // bincard"stateList

                    $j("#regimennamecomplete").get(0).options[$j(
                        "#regimennamecomplete")
                        .get(0).options.length] = new Option(
                        value, value);
                });

            $j("#regimennameoption").get(0).options.length = 0;
            $j("#regimennameoption").get(0).options[0] = new Option(
                "Select", "-1");
            $j
                .each(
                result,
                function (index, value) { // bincard"stateList

                    $j("#regimennameoption").get(0).options[$j(
                        "#regimennameoption")
                        .get(0).options.length] = new Option(
                        value, value);
                });

        });
    $j("#optionss").get(0).options.length = 0;
    $j("#optionss").get(0).options[0] = new Option("Select",
        "-1");

    $j("#optionss").get(0).options[$j("#optionss").get(0).options.length] = new Option(
        "No", "No");
    $j("#optionss").get(0).options[$j("#optionss").get(0).options.length] = new Option(
        "Yes", "Yes");

    $j("#regimenform").show("slow");

}


$j("#option1").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#option1").removeClass('working');

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
        /// binTable.fnFilter( ui.item.label );

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});


$j("#option2").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#option2").removeClass('working');

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
        /// binTable.fnFilter( ui.item.label );

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});


$j("#complete").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#complete").removeClass('working');

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
        /// binTable.fnFilter( ui.item.label );

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});


$j("#regimendrug1").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#regimendrug1").removeClass('working');

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


    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});


$j("#regimendrug2").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#regimendrug2").removeClass('working');

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

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});


$j("#regimendrug3").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#regimendrug3").removeClass('working');

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

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});

$j("#regimendrug4").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#regimendrug4").removeClass('working');

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

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    }
});
oTable = $j('#tregimen').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bServerSide:true,
    bProcessing:true,

    "fnRowCallback":function (nRow, aData, iDisplayIndex) {


        var htm = '<ul class="popRegimenC">	<li> <img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/items.png" alt="" /><ul class="popRegimenC" id=' + "popRegimenC" + aData[2] + '>';


        if (aData[0] == "edit") {
            htm += '<li> <a href="#"  id="edit"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/edit2.png" />Edit</a></li>';

        }


        if (aData[10] == "void") {
            htm += '<li> <a href="#" id="delete" ><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/delete.png" />Void</a></li>';
        }


        htm += '<li> <a href="#" id="cancel"><img src="' + jQuery.Page.context + 'moduleResources/pharmacy/images/cancel.png" />Back</a></li>';


        htm += '</ul></li></ul>';


        $j('td:eq(0)', nRow).html(htm);

        return nRow;
    },

    sAjaxSource:'drugRegimen.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 0 ]
        },
        {
            "bVisible":false,
            "aTargets":[10, 2 ]
        }
    ]
});
$j('#cancel').live('click', function () {
    $j("ul .popRegimenC").hide();

});


$j('#tregimen tbody td ul').live('click', function () {


    editTr = this.parentNode.parentNode;
    var aData = oTable.fnGetData(editTr);
    link = "#popRegimenC" + aData[2];


    $j(link).show();

//	

});
$j('#edit').live('click', function () {
    $j("ul .popRegimenC").hide();
    getData();
    fnFormatDetails(editTr);

});
$j("#delete").live('click', function () {


    var aData = oTable.fnGetData(editTr);

    var fbject = document.forms['regimenvoid'];

    fbject.elements["regimenuuidvoid"].value = aData[2];
    $j("ul .popRegimenC").hide();
    $j("#regimenvoid").show();//

});

$j("form#regimenform").submit(function () {
    // we want to store the values from the form input box, then send via ajax
    // below
    if ($j("#regimenform").valid()) {

        dataString = $j("#regimenform").serialize();
        $j.ajax({
            type:"POST",
            url:"drugRegimen.form",
            data:dataString,
            success:function () {

                AutoReload();

                $j("#regimenform").hide();//

                var oFormObject = document.forms['regimenform'];

                oFormObject.elements["regimenedit"].value = 'false';

            }
        });
        return false;
    }
});

$j("form#regimenvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax
    // below

    if ($j("#regimenvoid").valid()) {
        dataString = $j("#regimenvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"drugRegimen.form",
            data:dataString,
            success:function () {
                $j("#regimenvoid").hide();//
                AutoReload();
            }
        });
        return false;
    }
});
$j("#regimenid").click(function () {

    getData();

});
