var binTable;
var oCache = {
    iCacheLower:-1
};

$j("#bincard").hide();
$j("#lowvoid").hide();

function RefreshTable(tableId, urlData) {

    table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
    RefreshTable('#tlowbincard', 'lowBincard.form');

}


getDrugFilter();

binTable = $j('#tlowbincard').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'lowBincard.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 11 ]
        },
        {
            "bVisible":false,
            "aTargets":[ 0 ]
        }
    ]
});


getFilter();
$j(function () {

    $j("#date").datepicker();

});

$j('#tlowbincard tbody td img').live('click', function () {

    var nTr = this.parentNode.parentNode;

    var aData = binTable.fnGetData(nTr);

    var fbject = document.forms['lowvoid'];

    fbject.elements["binuuidvoid"].value = aData[1];

    $j("#lowvoid").show();//

});

$j("form#bincard").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#bincard").valid()) {
        dataString = $j("#bincard").serialize();

        $j.ajax({
            type:"POST",
            url:"lowBincard.form",
            data:dataString,
            success:function () {

                AutoReload();

                $j("#bincard").hide();//

                var oFormObject = document.forms['bincard'];

                oFormObject.elements["binedit"].value = 'false';
                oFormObject.elements["binmax"].value = "";
                oFormObject.elements["binmin"].value = "";

                oFormObject.elements["binquantityin"].value = "";

            }
        });
        return false;
    }
});
$j("form#lowvoid").submit(function () {
    // we want to store the values from the form input box, then send via ajax below
    if ($j("#lowvoid").valid()) {
        dataString = $j("#lowvoid").serialize();

        $j.ajax({
            type:"POST",
            url:"lowBincard.form",
            data:dataString,
            success:function () {
                $j("#lowvoid").hide();//
                var fbject = document.forms['lowvoid'];

                fbject.elements["binuuidvoid"].value = "";


                AutoReload();
            }
        });
        return false;
    }
});

$j("#bincardform").click(function () {

    getData();
    getDataLocation();
});

function getData() {

    $j
        .getJSON(
        "drugDetails.form?drop=drop",
        function (result) {

            $j("#bindrug").get(0).options.length = 0;
            $j("#bindrug").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#bindrug").get(0).options[$j(
                        "#bindrug").get(0).options.length] = new Option(
                        value, value);
                });

        });

}


function getFilter() {


}


$j("#filterdruglow").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdruglow").removeClass('working');

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
        binTable.fnFilter(ui.item.label);

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
function getDrugFilter() {


}

function getDataLocation() {

    $j
        .getJSON(
        "drugDetails.form?drop=location",
        function (result) {

            $j("#location").get(0).options.length = 0;
            $j("#location").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#location").get(0).options[$j(
                        "#location").get(0).options.length] = new Option(
                        value, value);
                });

        });

    $j("#bincard").show("slow");
    $j("#bincardform").show("slow");
}
