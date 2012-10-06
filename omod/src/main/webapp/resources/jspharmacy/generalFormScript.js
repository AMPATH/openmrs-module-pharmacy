$j("#generalform").delegate("#btnDel", "click", function() {
    var id = "#" + $j(this).attr("name");

    $j("" + id + "").remove();
});


var formRegimen = [
"HIV", "CTF", "TB", "DIFLUCAN"

];




$j("#prescriber").autocomplete({
    search : function() {
	$j(this).addClass('working');
    },

    source : function(request, response) {
	// http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form

	dataString = "q=" + request.term;

	$j.getJSON("dispense.form?" + dataString, function(result) {

	    $j("#prescriber").removeClass('working');

	    response($j.each(result, function(index, item) {

		return {
		    label : item,
		    value : item

		}
	    }));

	});

    },
    minLength : 2,
    select : function(event, ui) {

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



var formTags = [
"HIV", "CTF", "TB", "DIFLUCAN"

];


var formR = [];

$j("#form").autocomplete({

    source : formTags,
    minLength : 2,
    select : function(event, ui) {
	formChoosed(ui.item.label);
    },
    open : function() {
	$j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close : function() {
	$j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});

$j("#generalform").delegate("#btnDel", "click", function() {
    var id = "#" + $j(this).attr("name");

    $j("" + id + "").remove();
});



if(jQuery.Age.age>=16){
    
    

    $j('#begin')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+


    			'<td class="ui-dform-td">'
    			+ '<label for="PatientType" class="ui-dform-label"> Patient Type'
    			+ '</label>'
    			+ '<select id="frequency" name="PATIENT TYPE|1724#5" class="ui-dform-select">'
    			+ '<option class="ui-dform-option" value="Select">Select</option>'
    			+ '<option class="ui-dform-option" value="ROUTINE|432">ROUTINE</option>'
    			+ '<option class="ui-dform-option" value="POST EXPOSURE PROPHYLAXIS|2090">POST EXPOSURE PROPHYLAXIS</option>'
    			+ '<option class="ui-dform-option" value="PMTCT |1776">PMTCT </option>'
    			+

    			'</select>'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="PatientType" class="ui-dform-label">Previous regimen'
    			+ '</label>'
    			+

    			'<DIV class="pregimen">'
    			+ '<DIV>'
    			+ '</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="Current Regimen" class="ui-dform-label">Current Regimen'
    			+ '</label>'
    			+ '<input type="text" name="reg" id="reg" class="ui-dform-text">'
    			+ '</td>' +

    			'</tr>' +

    			"'");
    
    

    $j('#first')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label"> ARV Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7623">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">OI Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7626">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">NON ARVs Medication</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="1703">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Inititation of ARVs</label>'
    			+ '<input type="checkbox" id="regimenstart"  name="ARV LINE|1705#7" class="ui-dform-checkbox" value="1703">'
    			+

    			'</td>'
    			+
    			'</tr>' +

    			"'");

    $j('#second')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Regimen change</label>'
    			+ '<input type="checkbox" id="regimenchange" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="1065">'
    			+ '<label class="ui-dform-label">YES</label>'
    			+ '</td>'
    			+

    			'<td class="ui-dform-td"><label class="ui-dform-label"> If yes answer questions→→→→</label></td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Side effects</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="102">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Treatment failures</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="843">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Pregnancy</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="1484">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">TB Drug Interaction (during induction)</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="7391">'
    			+

    			'</td>'
    			+

    			+ '</tr>' +

    			"'");
    

    $j('#third').after(
    	"'" + ' <tr id="drugdiv" class="ui-dform-tr"><th class="ui-dform-th">'
    		+ '</th>' +

    		'<th class="ui-dform-th">Medication ' + '</th>' +

    	

    		'<th class="ui-dform-th">Dose ' + '</th>' +

    		'<th class="ui-dform-th">Quantity ' + '</th>' +

    	

    		'<th class="ui-dform-th">Pill Count ' + '</th>' +

    		'<th class="ui-dform-th">Dispensed ' + '</th>' +

    		'</tr>' +

    		"'");
    

    $j('#butt').before(
    	"'" + ' <tr id="drugdiv" class="ui-dform-tr"><td class="ui-dform-td">'
    		+ '<input type="button" name="" id="btnAdd" value="New" />'
    		+ '</td></tr>' 

    		+"'");
    
    
}
else{
    
    
    




    $j('#begin')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="PatientType" class="ui-dform-label">Weight'
    			+ '</label>'
    			+ '<input type="text" name="Weight|5089#2" id="weigh" class="ui-dform-text">'
    			+ '</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="PatientType" class="ui-dform-label"> Patient Type'
    			+ '</label>'
    			+ '<select id="frequency" name="PATIENT TYPE|1724#5" class="ui-dform-select">'
    			+ '<option class="ui-dform-option" value="Select">Select</option>'
    			+ '<option class="ui-dform-option" value="ROUTINE|432">ROUTINE</option>'
    			+ '<option class="ui-dform-option" value="POST EXPOSURE PROPHYLAXIS|2090">POST EXPOSURE PROPHYLAXIS</option>'
    			+ '<option class="ui-dform-option" value="PMTCT |1776">PMTCT </option>'
    			+

    			'</select>'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="PatientType" class="ui-dform-label">Previous regimen'
    			+ '</label>'
    			+

    			'<DIV class="pregimen">'
    			+ '<DIV>'
    			+ '</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label for="Current Regimen" class="ui-dform-label">Current Regimen'
    			+ '</label>'
    			+ '<input type="text" name="reg" id="reg" class="ui-dform-text">'
    			+ '</td>' +

    			'</tr>' +

    			"'");



    $j('#first')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">1st ARV Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7623">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">2nd ARV Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7624">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Initialization of ARVs</label>'
    			+ '<input type="checkbox"     id="regimenstart"  name="ARV LINE|1705#7" class="ui-dform-checkbox" value="1703">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Inpatient Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7625">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Inpatient Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7625">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">OI Refill</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7626">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Initiation of OI prophylaxis</label>'
    			+ '<input type="checkbox" name="ARV LINE|1705#7" class="ui-dform-checkbox" value="7627">'
    			+

    			'</td>' +

    			'</tr>' +

    			"'");

    $j('#second')
    	.after(
    		"'"
    			+ '<tr  class="ui-dform-tr">'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Regimen change</label>'
    			+ '<input type="checkbox" id="regimenchange" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="1065">'
    			+ '<label class="ui-dform-label">YES</label>'
    			+ '</td>'
    			+

    			'<td class="ui-dform-td"><label class="ui-dform-label"> If yes answer questions→→→→</label></td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Side effects</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="102">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Treatment failures</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="843">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Pregnancy</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="1484">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Genotypic Resistance</label>'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="7391">'
    			+

    			'</td>'
    			+

    			'<td class="ui-dform-td">'
    			+ '<label class="ui-dform-label">Other</label>'
    			+

    			'<input type="text" name="DRUGS|1252#7" class="ui-dform-checkbox" value="">'
    			+ '<input type="checkbox" name="REGIMEN CHANGE|1252#7" class="ui-dform-checkbox" value="5622">'
    			+ '</td>' + '</tr>' +

    			"'");

    $j('#third').after(
    	"'" + ' <tr id="drugdiv" class="ui-dform-tr"><th class="ui-dform-th">'
    		+ '</th>' +

    		'<th class="ui-dform-th">Medication ' + '</th>' +

    		'<th class="ui-dform-th">Weight ' + '</th>' +

    		'<th class="ui-dform-th">Form ' + '</th>' +

    		'<th class="ui-dform-th">Dose ' + '</th>' +

    		'<th class="ui-dform-th">Quantity ' + '</th>' +

    	

    		'<th class="ui-dform-th">Pill Count ' + '</th>' +

    		'<th class="ui-dform-th">Dispensed ' + '</th>' +

    		'</tr>' +

    		"'");
    

    $j('#butt').before(
    	"'" + ' <tr id="drugdiv" class="ui-dform-tr"><td class="ui-dform-td">'
    		+ '<input type="button" name="" id="btnAdd" value="New" />'
    		+ '</td></tr>' 

    		+"'");


}


$j(".ui-dform-text").css("width","200px");

document.getElementById('reg').style.width = '270px';










function formChoosed(formName) {
    if (formName == "HIV") {

	$j.getScript("" + jQuery.Page.context
		+ "moduleResources/pharmacy/jspharmacy/hivFormScript.js",
		function() {

		});
	
	
    } else if (formName == "CTF") {

	alert("CTF");

    } else if (formName == "TB") {

	alert("TB");

    } else if (formName == "DIFLUCAN") {

	alert("DIFLUCAN");

    }
}





$j("#generalform").delegate("#frequency", "change", function() {

   var filt= $j("#frequency").val();

    var val=     filt.substring(0,filt.indexOf('|'))
    $j.getJSON("dispense.form?regimen=regimen&filter="+val, function(result) {


        var itemsAll = [];

        $j.each(result, function(key, val) {


            $j.each(val, function(index, item) {


                var text=item;

                var strv= text.toString();

                var numbersArray = strv.split(",");

                var value=numbersArray[0]+"/"+numbersArray[1]+"/"+numbersArray[2]+"/"+numbersArray[3]+"/"+numbersArray[4]+"/"+numbersArray[5]+"/"+numbersArray[6]+"|"+numbersArray[7];

                itemsAll.push(value);



            });


        });






        $j("#reg").autocomplete({

            source : itemsAll,
            minLength : 1,
            select : function(event, ui) {


                check(ui.item.label);

            },
            open : function() {
                $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
            },
            close : function() {
                $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
            }
        });



    });

});


    $j("#generalform").delegate("#nextvisit", "change", function() {


    // alert('Handler for .change()
    // called.'+document.getElementById("nextvisit").value);
    var givenDate = document.getElementById("nextvisit").value;

    var currentTime = new Date();
    var month = currentTime.getMonth() + 1;
    var day = currentTime.getDate();
    var year = currentTime.getFullYear();

    // compare to get the diffrence

    t1 = givenDate;
    t2 = day + "/" + month + "/" + year;

    var one_day = 1000 * 60 * 60 * 24;
    var one_week = 1000 * 60 * 60 * 24 * 7;
    var one_month = 1000 * 60 * 60 * 24 * 7;

    var x = t1.split("/");
    var y = t2.split("/");

    dateGiven = new Date(x[2], (x[0] - 1), x[1]);
    var date2 = new Date(y[2], (y[1] - 1), y[0]);

    var dated = new Date(x[2], (x[1] - 1), x[0]);
    var month1 = x[1] - 1;
    var month2 = y[1] - 1;

    _Diff = Math.ceil((dateGiven.getTime() - date2.getTime())
	    / (one_day));
    quantity = _Diff;
    var inputTags = document.getElementsByTagName('INPUT');
    document.getElementById("noofmonths").value = _Diff + 2;


    // 3 weeks

});

$j('#noofmonths')
.change(
	function() {
	    var inputTags = document.getElementsByTagName('INPUT');
	    var givenDays = document.getElementById("noofmonths").value;
	    quantity = givenDays;


	    var date = new Date();
	    var d = addDays(date, givenDays);
	    var curr_date = d.getDate();
	    var curr_month = d.getMonth() + 1;
	    var curr_year = d.getFullYear();
	    var dateFinal = curr_month + "/" + curr_date + "/"
		    + curr_year;

	    document.getElementById("nextvisit").value = dateFinal;

	});

function addDays(myDate, days) {
    return new Date(myDate.getTime() + days * 24 * 60 * 60 * 1000);
}
$j("#generalform").delegate("#remove", "click", function() {

    $j(this).parent().parent().remove();

});
$j("#generalform").delegate("#btnAdd", "click", function() {
    formChoosed($j("#form").val());

    return false;

});
