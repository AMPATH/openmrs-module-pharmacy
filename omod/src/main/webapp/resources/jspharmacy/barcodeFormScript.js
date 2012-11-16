$j("#psychiatryform").delegate("#btnDel", "click", function () {
    var id = "#" + $j(this).attr("name");

    $j("" + id + "").remove();
});

set();

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

                    $j.each(result, function (index, value) { // bincard"stateList
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
                set();

            }
            e.stop();
            return false;
        }
    });

function set() {

    $j('#AM1').attr('disabled', 'disabled');
    $j('#AM2').attr('disabled', 'disabled');
    $j('#dose').attr('disabled', 'disabled');
    $j('#frequency').attr('disabled', 'disabled');
    $j('#indication').attr('disabled', 'disabled');

    $j('#dose1').attr('disabled', 'disabled');
    $j('#frequency1').attr('disabled', 'disabled');
    $j('#indication1').attr('disabled', 'disabled');


}

$j("#psychiatryform").delegate("input[type='checkbox']", "click", function () {


    if ($j(this).val() == id) {
        if ($j(this).is(":checked")) {

            $j('#dose').attr('disabled', '');

            $j('#indication').attr('disabled', '');

        } else {

            $j('#dose').attr('disabled', 'disabled');
            $j('#frequency').attr('disabled', 'disabled');
            $j('#indication').attr('disabled', 'disabled');

        }

    } else if ($j(this).val() == "2282") {

        if ($j(this).is(":checked")) {

            $j('#dose1').attr('disabled', '');

            $j('#indication1').attr('disabled', '');

        } else {

            $j('#dose1').attr('disabled', 'disabled');
            $j('#frequency1').attr('disabled', 'disabled');
            $j('#indication1').attr('disabled', 'disabled');

        }

    }

});


$j("#psychiatryform").delegate("#dose", "change", function () {
    $j('#frequency').attr('disabled', '');

    var e = document.getElementById("dose");
    var str = e.options[e.selectedIndex].value;

    if (str == "Select") {

        alert("Must choose the right one");
    } else {
        $j.getJSON("drugDispense.form?dose="
            + str.substring(str.indexOf('#') + 1),
            function (result) {
                $j.each(result, function (index, value) {

                    // bincard"stateList
                    if (index == 0) {

                        method = value;

                    } else if (index == 1) {
                        valgiven = value;
                    } else if (index == 2) {
                        price = value;
                    }

                });

            });
    }
    return false;
});


$j("#psychiatryform").delegate("#dose1", "change", function () {

    $j('#frequency1').attr('disabled', '');
    var e = document.getElementById("dose");
    var str = e.options[e.selectedIndex].value;

    if (str == "Select") {

        alert("Must choose the right one");
    } else {
        $j.getJSON("drugDispense.form?dose="
            + str.substring(str.indexOf('#') + 1),
            function (result) {
                $j.each(result, function (index, value) { // bincard"stateList
                    if (index == 0) {

                        methodOne = value;

                    } else if (index == 1) {

                        valgivenOne = value;
                    } else if (index == 2) {

                        priceOne = value;
                    }

                });

            });
    }
    return false;
});


$j("#psychiatryform").delegate("#frequency", "change", function () {

    var e = document.getElementById("frequency");
    var str = e.options[e.selectedIndex].value;

    if (str == "Select") {

        alert("Must choose the right one");
    } else {
        var givenDays = document.getElementById("noofmonths").value;

        if (parseInt(price) == 0) {

            document.getElementById("AM1").value = Math
                .round((parseInt(givenDays) * 100) / 28);
            if (str == "TWICE A DAY (BD)|1888") {

                tot = 2 * parseInt(givenDays);
            } else if (str == ("ONCE A DAY (OD)|1891")) {

                tot = 1 * parseInt(givenDays);

            }
            var inputTags = document.getElementsByTagName('INPUT');

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "QD") {
                    inputTags[i].value = tot;
                }

            }
        } else {
            document.getElementById("AM1").value = Math
                .round((parseInt(givenDays) * parseInt(price))
                / valgiven);
            if (str == "TWICE A DAY (BD)|1888") {

                tot = 2 * parseInt(givenDays);
            } else if (str == ("ONCE A DAY (OD)|1891")) {

                tot = 1 * parseInt(givenDays);

            }
            var inputTags = document.getElementsByTagName('INPUT');

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "QD") {
                    inputTags[i].value = tot;
                }

            }

        }

    }
    return false;
});


$j("#psychiatryform").delegate("#frequency1", "change", function () {

    var e = document.getElementById("frequency");
    var str = e.options[e.selectedIndex].value;

    if (str == "Select") {

        alert("Must choose the right one");
    } else {
        var givenDays = document.getElementById("noofmonths").value;
        if (parseInt(price) == 0) {

            document.getElementById("AM2").value = Math
                .round((parseInt(givenDays) * 100) / 28);
            if (str == "TWICE A DAY (BD)|1888") {

                tot = 2 * parseInt(givenDays);
            } else if (str == ("ONCE A DAY (OD)|1891")) {

                tot = 1 * parseInt(givenDays);

            }
            var inputTags = document.getElementsByTagName('INPUT');

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "QD1") {
                    inputTags[i].value = tot;
                }

            }
        } else {

            document.getElementById("AM2").value = Math
                .round((parseInt(givenDays) * parseInt(priceOne))
                / valgivenOne);
            if (str == "TWICE A DAY (BD)|1888") {

                tot = 2 * parseInt(givenDays);
            } else if (str == ("ONCE A DAY (OD)|1891")) {

                tot = 1 * parseInt(givenDays);

            }
            var inputTags = document.getElementsByTagName('INPUT');

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "QD1") {
                    inputTags[i].value = tot;
                }

            }

        }
    }

    return false;
});
$j("#psychiatryform").delegate("#WA1", "change", function () {


    var val = document.getElementById("WA1").value;

    var val2 = document.getElementById("AM1").value
    document.getElementById("AM1").value = val2 - val;

    return false;
});


$j("#psychiatryform").delegate("#WA2", "change", function () {


    var val = document.getElementById("WA2").value;
    var val2 = document.getElementById("AM2").value
    document.getElementById("AM2").value = val2 - val;
    return false;

});

$j("#psychiatryform").delegate("#nextvisit", "change", function () {

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

    if (_Diff <= 7) {

        for (var i = 0; i < inputTags.length; i++) {

            if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                inputTags[i].value = _Diff;
            }
            if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                inputTags[i].value = "";
            }

            if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                inputTags[i].value = "";

            }
        }

    } else if (_Diff > 7) {

        var one_week = 1000 * 60 * 60 * 24 * 7;
        var week = Math.ceil((dateGiven.getTime() - date2.getTime())
            / (one_week));

        if (week < 4) {

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                    inputTags[i].value = "";
                }
                if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                    inputTags[i].value = week;
                }

                if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                    inputTags[i].value = "";

                }
            }

        } else if (week >= 4) {
            var numb = months_between(date2, dateGiven);
            for (var i = 0; i < inputTags.length; i++) {

                if (numb == 0) {
                    if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                        inputTags[i].value = 1;

                    }

                } else {

                    if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                        inputTags[i].value = numb;

                        // inputTags[i].value=_Diff;
                    }

                }

                if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                    inputTags[i].value = "";
                }
                if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                    inputTags[i].value = "";
                }

            }

        }
    }


});


$j('#noofmonths')
    .change(
    function () {
        var inputTags = document.getElementsByTagName('INPUT');
        var givenDays = document.getElementById("noofmonths").value;
        quantity = givenDays;

        if (givenDays <= 6) {
            // week

            for (var i = 0; i < inputTags.length; i++) {

                if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                    inputTags[i].value = givenDays;
                }

                if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                    inputTags[i].value = "";
                }

                if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                    inputTags[i].value = "";
                }
            }

        } else if (givenDays > 6) {

            if (typeof dateGiven === 'undefined') {
                // your code here.

                dateGiven = new Date();
            }
            ;

            var one_week = 1000 * 60 * 60 * 24 * 7;
            var week = Math.ceil(((addDays(dateGiven, givenDays)
                .getTime()) - dateGiven.getTime())
                / (one_week));

            if (week <= 4) {
                // document.getElementById("noofmonths").value=parseInt(givenDays)+2;
                for (var i = 0; i < inputTags.length; i++) {

                    if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                        inputTags[i].value = "";
                    }
                    if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                        inputTags[i].value = week;
                    }

                    if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                        inputTags[i].value = "";

                    }
                }

            } else if (week > 4) {

                var months = months_between(dateGiven, addDays(
                    dateGiven, givenDays));

                for (var i = 0; i < inputTags.length; i++) {

                    if (inputTags[i].id == "NUMBER OF DAYS|1892") {
                        inputTags[i].value = "";
                    }
                    if (inputTags[i].id == "NUMBER OF WEEKS|1893") {
                        inputTags[i].value = "";
                    }

                    if (inputTags[i].id == "NUMBER OF MONTHS|1894") {
                        inputTags[i].value = months;

                    }

                }

            }

            // 3 weeks

        }

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

$j("#prescriber").autocomplete({
    search:function () {
        $j(this).addClass('working');
    },

    source:function (request, response) {
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

    },
    open:function () {
        $j(this).removeClass("ui-corner-all").addClass("ui-corner-top");
    },
    close:function () {
        $j(this).removeClass("ui-corner-top").addClass("ui-corner-all");
    }
});
