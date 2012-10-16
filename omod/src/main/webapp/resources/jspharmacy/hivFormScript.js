var pRegimen;

var cRegimen;


var formDose;
$j("#dispensedby").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {
        //   http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form

        dataString = "q=" + request.term;

        $j.getJSON("dispense.form?" + dataString, function (result) {

            $j("#dispensedby").removeClass('working');

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

        //											log( ui.item ?
        //												"Selected: " + ui.item.label :
        //												"Nothing selected, input was " + this.value);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});

$j.getJSON("dispense.form?encounter=enc&Pen=" + jQuery.Pid.Pi, function (result) {


    if (jQuery.isEmptyObject(result)) {

        $j("div.pregimen").text("Not given");
        //document.getElementById('pregimen').style.color = 'blue';
        $j(".pregimen").css("color", "blue");
    }
    else {
        $j.each(result, function (key, val) {


            $j.each(val, function (index, item) {


                var text = item;

                var strv = text.toString();

                var numbersArray = strv.split(",");


                var value = numbersArray[0] + "/" + numbersArray[1] + "/" + numbersArray[2] + "/" + numbersArray[3] + "/" + numbersArray[4] + "/" + numbersArray[5] + "/" + numbersArray[6] + "|" + numbersArray[7];
                pRegimen = value;
                $j("div.pregimen").text(value);
                //document.getElementById('pregimen').style.color = 'blue';
                $j(".pregimen").css("color", "blue");

            });


        });
    }


});
function check(val) {


    cRegimen = val;
    // if(cRegimen!=pRegimen)
    //  {
    //$j("#info").text("Regimen Not ok");

    //document.getElementById('pregimen').style.color = 'blue';
    //$j("#info").css("color","red");
    //setTimeout(function(){
    //  $j("#info").css("color","white");
    //},5000);
    //}
}


if (jQuery.Age.age >= 16) {
    $j('#drugdiv')
        .after(
        "'"
            + '<tr id="" class="ui-dform-tr"><td class="ui-dform-td">'
            + '<input type="button" name="" id="remove" value="X" />'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text"  size="25"  name="DRUGS|1895#7" id="medication" class="ui-dform-checkbox" value=""/>'
            + '</td>'

            + '<td class="ui-dform-td"><input type="text" id="dosemore" name="DRUGS|1900#5" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90902#7" id="month1" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90906#7"  id="pill"  class="ui-dform-checkbox" value=""/>'
            +

            '</td><td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90907#7"   id="dispensed" class="ui-dform-checkbox" value=""/>'
            + '</td>' +

            '</tr>' +

            "'");

}
else {
    $j('#drugdiv')
        .after(
        "'"
            + '<tr id="" class="ui-dform-tr"><td class="ui-dform-td">'
            + '<input type="button" name="" id="remove" value="X" />'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text"  size="25"  name="DRUGS|1895#7" id="medication" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90901#7" id="weight" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '<td class="ui-dform-td"><input type="text" id="formi" name="DRUGS|1939#5" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '<td class="ui-dform-td"><input type="text" id="dose" name="DRUGS|1900#5" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90902#7" id="month1" class="ui-dform-checkbox" value=""/>'
            + '</td>'
            + '</td>'
            + '<td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90906#7"  id="pill"  class="ui-dform-checkbox" value=""/>'
            +

            '</td><td class="ui-dform-td">'
            + '<input type="text" name="DRUGS|90907#7"   id="dispensed" class="ui-dform-checkbox" value=""/>'
            + '</td>' +

            '</tr>' +

            "'");
}
$j("#generalform").delegate("#btnDel", "click", function () {
    var id = "#" + $j(this).attr("name");

    $j("" + id + "").remove();
});


document.getElementById('medication').style.width = '200px';


$j('#medication').focus(function () {


    var val = document.getElementById("reg").value;


    if (val == "") {

        $j("#info").text("Current regimen must be selected before proceeding");
        //document.getElementById('pregimen').style.color = 'blue';
        $j("#info").css("color", "red");


        setTimeout(function () {
            $j("#info").css("color", "white");
        }, 5000);


        $("#reg").focus();

    }

    else if (cRegimen != pRegimen) {

        if ($j("#regimenchange").is(":checked")) {

            regimen();
        } else if ($j("#regimenstart").is(":checked")) {

            regimen();
        }
        else {

            $j("#info").text("Regimen Not ok or give reason for change");

            //document.getElementById('pregimen').style.color = 'blue';
            $j("#info").css("color", "red");
            setTimeout(function () {
                $j("#info").css("color", "white");
            }, 5000);

        }


    }


    else if ((val != "") && (cRegimen == pRegimen)) {
        if (!$j("#regimenchange").is(":checked")) {
            regimen();

        } else {

            $j("#info").text("You should not select change regimen");

            //document.getElementById('pregimen').style.color = 'blue';
            $j("#info").css("color", "red");
            setTimeout(function () {
                $j("#info").css("color", "white");
            }, 8000);

        }


    }


});

function regimen() {


    $j("#medication").autocomplete({
        search:function () {
            $j(this).addClass('working');
        },

        source:function (request, response) {
            //   http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form
            alert(cRegimen);

            dataString = "drugs=" + request.term + "&values=" + cRegimen;


            $j.getJSON("dispense.form?" + dataString, function (result) {

                $j("#medication").removeClass('working');

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

            //											log( ui.item ?
            //												"Selected: " + ui.item.label :
            //												"Nothing selected, input was " + this.value);
        },
        open:function () {
            $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
        },
        close:function () {
            $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
        }
    });


}
$j(document)
    .keydown(
    function (e) {
        if (e.keyCode == 13) {

            var drugId;

            var val = $j("*:focus").attr('id');
            if (val == "barcode") {

                var dataStringVal = $j("#barcode").val();

                var myResult = dataStringVal.split("0");

                for (i = 0; i < myResult.length; i++) {
                    if (i == 0)
                        drugId = myResult[i];

                }

                $j.getJSON("drugDetails.form?drop=drug&id="
                    + drugId, function (result) {

                    $j.each(result, function (index, value) { //bincard"stateList
                        if (index == 0) {

                            name = value;

                        }

                        if (index == 1)
                            id = value;

                    });

                });
                $j("#barcode").val('');

                $j('#drugdiv')
                    .after(
                    '<tr id="'
                        + id
                        + '" class="ui-dform-tr"><td class="ui-dform-td">  <input type="checkbox" name="DRUGS|90900#7" class="ui-dform-checkbox" value="'
                        + id
                        + '"><label class="ui-dform-label">'
                        + name
                        + '</label><input type="button" name="'
                        + id
                        + '" id="btnDel" value="X" /></td><td class="ui-dform-td"><label class="ui-dform-label"></label><div class="ui-dform-radiobuttons"><input type="radio" class="ui-dform-radio" value="CONTINUE|6101" name="v#7"><label class="ui-dform-label">CONTINUE</label><input type="radio" class="ui-dform-radio" value="ADD DRUG(S)|7027" name="v#7"><label class="ui-dform-label">ADD DRUG(S)</label></div></td><td class="ui-dform-td"><label for="dose" class="ui-dform-label"></label><select id="dose" name="MEDICATION DOSAGE (DRUG DOSE)|1900#5" class="ui-dform-select"><option class="ui-dform-option" value="FLUOXETINE (PROZAC)|2281#94">20 mg</option><option class="ui-dform-option" value="FLUOXETINE (PROZAC)|2281#95">40 mg</option></select></td><td class="ui-dform-td"><label class="ui-dform-label"></label><div class="ui-dform-checkboxes"><input type="checkbox" class="ui-dform-checkbox" value="OP|7477" name="ROUTE|7463#7"><label class="ui-dform-label">OP</label></div></td><td class="ui-dform-td"><label for="frequency" class="ui-dform-label"></label><select id="frequency" name="MEDICATION FREQUENCY PER DAY|1896#5" class="ui-dform-select"><option class="ui-dform-option" value="ONCE A DAY (OD)|1891">ONCE A DAY (OD)</option><option class="ui-dform-option" value="TWICE A DAY (BD)|1888">TWICE A DAY (BD)</option><option class="ui-dform-option" value="OTHER NON-CODED|5622">OTHER NON-CODED</option></select></td><td class="ui-dform-td"><label for="indication" class="ui-dform-label"></label><select id="indication" name="#5" class="ui-dform-select"><option class="ui-dform-option" value="DEPRESSION|207">DEPRESSION</option><option class="ui-dform-option" value="SCHIZOPHRENIA|467">SCHIZOPHRENIA</option><option class="ui-dform-option" value="OTHER NON-CODED|5622">OTHER NON-CODED</option></select></td><td class="ui-dform-td"><label for="NUMBER OF DAYS|1892" class="ui-dform-label">_Days</label><input type="text" name="MEDICATION DURATION|1897#2" id="NUMBER OF DAYS|1892" class="ui-dform-text"><label for="NUMBER OF WEEKS|1893" class="ui-dform-label">_Weeks</label><input type="text" name="MEDICATION DURATION|1897#2" id="NUMBER OF WEEKS|1893" class="ui-dform-text"><label for="NUMBER OF MONTHS|1894" class="ui-dform-label">_Months</label><input type="text" name="MEDICATION DURATION|1897#2" id="NUMBER OF MONTHS|1894" class="ui-dform-text"></td><td class="ui-dform-td"><label for="QD" class="ui-dform-label">No</label><input type="text" name="QD" id="QD" class="ui-dform-text"></td><td class="ui-dform-td"><label for="PD" class="ui-dform-label">Pill No</label><input type="text" name="PD" id="PD" class="ui-dform-text"></td><td class="ui-dform-td"><label for="AM1" class="ui-dform-label">Amount</label><input type="text" name="AM" id="AM1" class="ui-dform-text"><br class="ui-dform-br"><label for="RN1" class="ui-dform-label">Receipt No</label><input type="text" name="RN" id="RN1" class="ui-dform-text"><br class="ui-dform-br"><label for="WN1" class="ui-dform-label">Waiver No</label><input type="text" name="WN" id="WN1" class="ui-dform-text"><br class="ui-dform-br"><label for="IN1" class="ui-dform-label">Invoice No</label><input type="text" name="IN" id="IN1" class="ui-dform-text"><br class="ui-dform-br"><label for="WA1" class="ui-dform-label">Waiver Amount</label><input type="text" name="WA" id="WA1" class="ui-dform-text"></td></tr>');

            }
            e.stop();
            return false;
        }
    });


$j("#Current").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {
        //   http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form

        dataString = "q=" + request.term;

        $j.getJSON("dispense.form?" + dataString, function (result) {

            $j("#prescriber").removeClass('working');

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

        //											log( ui.item ?
        //												"Selected: " + ui.item.label :
        //												"Nothing selected, input was " + this.value);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});

$j("#prescriber").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {
        //   http://localhost:8080/openmrs/module/htmlwidgets/conceptSearch.form

        dataString = "q=" + request.term;

        $j.getJSON("dispense.form?" + dataString, function (result) {

            $j("#prescriber").removeClass('working');

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

        //											log( ui.item ?
        //												"Selected: " + ui.item.label :
        //												"Nothing selected, input was " + this.value);
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});


$j('#weigh').change(
    function () {
        var wgth = $j("#weigh").val();

        if (wgth >= 3 && wgth <= 5.9) {

            $j("#weight").val("3 - 5.9");
        }
        else if (wgth >= 6 && wgth <= 9.9) {

            $j("#weight").val("6 - 9.9");
        }

        else if (wgth >= 10 && wgth <= 13.9) {

            $j("#weight").val("10 - 13.9");
        }

        else if (wgth >= 14 && wgth <= 19.9) {

            $j("#weight").val("14 - 19.9");
        }

        else if (wgth >= 20 && wgth <= 24.9) {

            $j("#weight").val("20 - 24.9");
        } else if (wgth >= 25) {

            $j("#weight").val("25");
        }

    });


var formForm = [
// Africa
    "Sol | 7464,138", "150mg | 1936,28"

];

$j("#formi").autocomplete({
    minLength:0,

    source:formForm,

    select:function (event, ui) {
        $j(this).autocomplete('close');
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});


var formDoseMore = [
    // Africa
    "1 tablet BD | 1888", "1 tablet OD | 1891", "1 tablet NOCTE | 2176", "2 tablet BD | 1888", "3 tablet BD | 1888"

];


var formDose = [
    // Africa
    "3 ml BD | 1888", "4 ml BD | 1888", "6 ml BD | 1888", "7 ml BD | 1888",
    "1/2 tab BD | 1888",

    "9 ml BD | 1888", "1 tab am 1639,1/2 tab pm | 1641", "1 tab BD | 1888"

];


$j("#dosemore").autocomplete({
    minLength:0,

    source:formDoseMore,

    select:function (event, ui) {
        $j(this).autocomplete('close');
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});

$j("#dose").autocomplete({
    minLength:0,

    source:formDose,

    select:function (event, ui) {
        $j(this).autocomplete('close');
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});


var formDisp = [
// Africa
    "0", "1", "2", "3"

];

$j("#dispensed").autocomplete({
    minLength:0,

    source:formDisp,
    select:function (event, ui) {
        $j(this).autocomplete('close');
    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
