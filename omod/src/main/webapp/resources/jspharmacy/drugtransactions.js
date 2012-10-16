var oCache = {
    iCacheLower:-1
};

var binTable = $j('#ttransactions').dataTable({
    bJQueryUI:true,
    bRetrieve:true,
    bAutoWidth:false,
    bServerSide:true,
    bProcessing:true,
    sAjaxSource:'drugTransactions.form',
    "fnServerData":fnDataTablesPipeline,
    "aoColumnDefs":[
        {
            "bVisible":false,
            "aTargets":[ 1 ]
        }
    ]
});


$j("#filterdrugtran").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function (result) {

            $j("#filterdrugtran").removeClass('working');

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
        // log( ui.item ?
        // "Selected: " + ui.item.label :
        // "Nothing selected, input was " + this.value);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
function getFilter() {


}