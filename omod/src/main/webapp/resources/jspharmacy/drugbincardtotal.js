var binTableTotal;

var urlTwo;

$j("#drugtotaldiv").hide();


var currentTime = new Date();
var month = currentTime.getMonth() + 1;
var day = currentTime.getDate();
var year = currentTime.getFullYear();

//compare to get the diffrence

t2 = day + "/" + month + "/" + year;
var oCache = {
    iCacheLower:-1
};


var y = t2.split("/");


var dateCurrent = new Date(y[2], (y[1] - 1), y[0]);


getDrugCategory();


$j.fn.dataTableExt.oApi.fnReloadAjax = function (oSettings, sNewSource) {
    oSettings.sAjaxSource = sNewSource;
    this.fnClearTable(this);
    this.oApi._fnProcessingDisplay(oSettings, true);
    var that = this;

    $j.getJSON(oSettings.sAjaxSource, null, function (json) {
        /* Got the data - add it to the table */
        for (var i = 0; i < json.aaData.length; i++) {
            that.oApi._fnAddData(oSettings, json.aaData[i]);
        }

        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
        that.fnDraw(that);
        that.oApi._fnProcessingDisplay(oSettings, false);
    });
}


$j('select#filtercategorytotal').change(function () {


    var e = document.getElementById("filtercategorytotal");
    var str = e.options[e.selectedIndex].value;


    if (str !== "-1") {


        urlTwo = 'drugBincard.form?bintotal=total&category=' + str;
        if ($j('#drugtotaldiv').is(':visible')) {

            oCache.iCacheLower = -1;

            $j('#tbincardtotal').dataTable().fnReloadAjax(urlTwo);

        }
        else {


            binTableTotal = $j('#tbincardtotal').dataTable({
                bJQueryUI:true,
                bRetrieve:true,
                bAutoWidth:false,
                bServerSide:true,
                bProcessing:true,
                bPaginate:false,

                "fnRowCallback":function (nRow, aData, iDisplayIndex) {


                    t1 = aData[5];


                    if (t1 > 4 && t1 <= 6)
                        $j('td:eq(4)', nRow).css("background-color", "#FFCC00");
                    else if (t1 > 2 && t1 <= 4)
                        $j('td:eq(4)', nRow).css("background-color", "#FF9900");
                    else if (t1 <= 2)
                        $j('td:eq(4)', nRow).css("background-color", "#FF0000");


                    $j('td:eq(4)', nRow).html('');
                    min = aData[4];


                    if (min == 8)
                        $j('td:eq(3)', nRow).css("background-color", "#FFCC00");


                    $j('td:eq(3)', nRow).html('');
                    return nRow;
                },
                bInfo:false,
                sAjaxSource:urlTwo,
                "fnServerData":fnDataTablesPipeline,
                "aoColumnDefs":[
                    {
                        "bVisible":false,
                        "aTargets":[ 1 ]
                    }
                ]
            });


            $j("#drugtotaldiv").show();

        }

    }
});


$j('#tbincardtotal').delegate('tbody td img', 'click', function () {
    var e = document.getElementById("filtercategorytotal");
    var str = e.options[e.selectedIndex].value;


    var nTr = this.parentNode.parentNode;
    if (this.src.match('details_close')) {
        /* This row is already open - close it */
        this.src = "" + jQuery.Page.context + "moduleResources/pharmacy/images/details_open.png";
        binTableTotal.fnClose(nTr);
    }
    else {
        /* Open this row */
        this.src = "" + jQuery.Page.context + "moduleResources/pharmacy/images/details_close.png";
        var aData = binTableTotal.fnGetData(nTr);
        $j.getJSON("drugBincard.form?druguuidshow=" + aData[1] + "&category=" + str,
            function (result) {
                var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
                sOut += '<thead><tr>';
                sOut += '<th>Drug</th>';
                sOut += '<th>Quantity</th>';
                sOut += '<th>Max level</th>';
                sOut += '<th>Min Level</th>';
                sOut += '<th>Expire Date</th>';
                sOut += '<th>Batch no</th>';
                sOut += '<th>s11</th>';
                sOut += '<th>Delivery No</th>';
                sOut += '<th>Supplier</th>';
                sOut += '</tr></thead>';
                sOut += '<tbody>';

                $j.each(result, function (index, value) { //bincard"stateList
                    sOut += '<tr>';
                    $j.each(value, function (index, value) { //bincard"stateList

                        if (index == 5) {

                            var y = value.toString().split("-");

                            var dateGiven = new Date(y[0], (y[1]), y[2]);
                            var num = months_between(dateGiven, dateCurrent);

                            if (num > 4 && num <= 6)
                                sOut += '<td bgcolor="#FFCC00">' + value + '</td>';
                            else if (num > 2 && num <= 4)
                                sOut += '<td bgcolor="#FF9900">' + value + '</td>';
                            else if (num <= 2)
                                sOut += '<td bgcolor="#FF0000">' + value + '</td>';
                        }


                        else if (index == 2) {
                            min = value;


                        }
                        else if (index == 4) {


                            if (min <= value)
                                sOut += '<td bgcolor="#FFCC00">' + value + '</td>';
                            else
                                sOut += '<td>' + value + '</td>';

                        }

                        else {
                            if (index != 2)
                                sOut += '<td>' + value + '</td>';


                        }

                    });

                    sOut += '</tr>';
                });
                sOut += '</tr></tbody>';
                sOut += '</table>';


                binTableTotal.fnOpen(nTr, sOut, 'details');

            });

    }

});


function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth() + (12 * (date2.getFullYear() - date1.getFullYear()));
}


function getDrugCategory() {

    $j
        .getJSON(
        "categoryName.form?drop=drop",
        function (result) {

            $j("#filtercategorytotal").get(0).options.length = 0;
            $j("#filtercategorytotal").get(0).options[0] = new Option("Select",
                "-1");
            $j
                .each(
                result,
                function (index, value) { //bincard"stateList

                    $j("#filtercategorytotal").get(0).options[$j(
                        "#filtercategorytotal").get(0).options.length] = new Option(
                        value, value);
                });

        });

}



