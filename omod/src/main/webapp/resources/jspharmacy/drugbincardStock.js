var stockTable;
var url;
var min;
var max;
var dataString;
var eSignCheck=false;
var link;
var editTr;
var uuid;

$j("#stockcard").hide();
$j("#tstockcard").hide();

var oCache = {
    iCacheLower: -1
};


$j("#parent_field").hide();

$j("#incss").hide();


$j("#check").validate({});

$j("#stockcard").validate({
	rules : {
		stockdrug : {
			selectNone : true
		}
	}
});


var currentTime = new Date();
var month = currentTime.getMonth()+ 1;
var day = currentTime.getDate();
var year = currentTime.getFullYear();

//compare to get the diffrence

  t2=day+"/"+month+"/"+year;



  var y=t2.split("/");




  var dateCurrent=new Date(y[2],(y[1]-1),y[0]);



$j("#stockvoid").validate();

getDrugCategory();




function RefreshTable(tableId, urlData) {

	table = $j(tableId).dataTable();
    oCache.iCacheLower = -1;

    table.fnDraw();
}

function AutoReload() {
	RefreshTable('#tstockcard', 'drugBincard.form');

}
/* Formating function for row details */






$j.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings, sNewSource)
{
oSettings.sAjaxSource = sNewSource;
this.fnClearTable(this);
this.oApi._fnProcessingDisplay(oSettings, true );
var that = this;

$j.getJSON(oSettings.sAjaxSource, null, function(json){
/* Got the data - add it to the table */
for (var i=0; i<json.aaData.length; i++)
{
that.oApi._fnAddData(oSettings, json.aaData[i]);
}

oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
that.fnDraw(that);
that.oApi._fnProcessingDisplay(oSettings, false);
});
}

function tableSetUp(category){

url='drugBincard.form?category='+category;

if( $j('#incss').is(':visible') ) {


    oCache.iCacheLower = -1;

    $j('#tstockcard').dataTable().fnReloadAjax(url);

}
else
	{

	stockTable = $j('#tstockcard').dataTable({
		bJQueryUI : true,
		bRetrieve : true,
		bAutoWidth : false,
		bServerSide : true,
		bProcessing : true,
		 bLengthChange: false,
		 bPaginate: true,

 "fnRowCallback": function( nRow, aData, iDisplayIndex ) {


	 $j('td:eq(4)', nRow).html("");


				return nRow;
			},
			 fnDrawCallback: function ( nRow, aData, iDisplayIndex) {
		        	$j('td:eq(4)', stockTable.fnGetNodes()).editable( 'drugBincard.form', {
		                "callback": function( sValue, y ) {
		                    /* Redraw the table from the new data on the server */
                            oCache.iCacheLower = -1;

                            stockTable.fnDraw();
		                },
	                "submitdata": function ( value, settings ) {
	                	return {


	        				"row_id":  uuid,
	        				"column": stockTable.fnGetPosition( this )
	        			};
		    			},
		    			indicator : 'Saving...',
		    	         tooltip   : 'Click to change ...',
		    	         submit  : 'OK',
		                height: "30px",
		                width: "300px"
		            } );
		        },

	      bInfo: false,
		sAjaxSource : url,
        "fnServerData": fnDataTablesPipeline,
		"aoColumnDefs" : [ {
			"bVisible" : false,
			"aTargets" : [ 0 ]
		},

		{
			"bVisible" : false,
			"aTargets" : [ 1,2 ]
		},
		{
			"bVisible" : false,
			"aTargets" : [5,6,10,11,12,13 ]
		}


		]
	});
	}
$j("#tstockcard").show();


$j("#incss").show();


$j("#parent_field").show();

}




$j('#tstockcard tbody tr').live('click', function() {
    var data = stockTable.fnGetData( this );
    uuid=data[2];
    // ... do something with the array / object of data for the row
  } );


$j('select#filterstock').change( function() {




var e = document.getElementById("filterstock");
var str = e.options[e.selectedIndex].value;



if(str!=="-1"){
	tableSetUp(str);
}
} );

getDrugFilter();

$j('select#filterdrugstock').change( function() {




} );




















function months_between(date1, date2) {
    return date2.getMonth() - date1.getMonth() + (12 * (date2.getFullYear() - date1.getFullYear()));
}




function getDrugCategory() {

	$j
	.getJSON(
			"categoryName.form?drop=drop",
			function(result) {

				$j("#filterstock").get(0).options.length = 0;
				$j("#filterstock").get(0).options[0] = new Option("Select",
						"-1");
				$j
						.each(
								result,
								function(index, value) { //bincard"stateList

									$j("#filterstock").get(0).options[$j(
											"#filterstock").get(0).options.length] = new Option(
											value, value);
								});

			});

}




$j("#filterdrugstock").autocomplete({
    search : function() {
        $j(this).addClass('working');
    },

    source : function(request, response) {

        dataString = "searchDrug=" + request.term;

        $j.getJSON("drugDetails.form?drop=drop&" + dataString, function(result) {

            $j("#filterdrugstock").removeClass('working');

            response($j.each(result, function(index, item) {

                return {
                    label : item,
                    value : item
                }
            }));

        });

    },
    minLength : 3,
    select : function(event, ui) {
        stockTable.fnFilter( $j(this).val() );


        // log( ui.item ?
        // "Selected: " + ui.item.label :
        // "Nothing selected, input was " + this.value);
    },
    open : function() {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close : function() {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});

function getDrugFilter() {



}
