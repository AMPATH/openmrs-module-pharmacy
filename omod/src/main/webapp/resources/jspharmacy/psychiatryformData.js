$j('#AM1').attr('disabled', 'disabled');
$j('#AM2').attr('disabled', 'disabled');
$j('#dose').attr('disabled', 'disabled');
$j('#frequency').attr('disabled', 'disabled');
$j('#indication').attr('disabled', 'disabled');

$j('#dose1').attr('disabled', 'disabled');
$j('#frequency1').attr('disabled', 'disabled');
$j('#indication1').attr('disabled', 'disabled');

$j("input[type='checkbox']").click(function () {

    //if you want to add on checked
    if ($j(this).val() == "2281") {
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

$j('#dose').change(
    function () {
        $j('#frequency').attr('disabled', '');

        var e = document.getElementById("dose");
        if (str != "null") {
            var str = e.options[e.selectedIndex].value;

            if (str == "Select") {

                alert("Must choose the right one");
            } else {
                $j.getJSON("drugDispense.form?dose="
                    + str.substring(str.indexOf('#') + 1),
                    function (result) {
                        $j.each(result, function (index, value) {

                            //bincard"stateList
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


        }

    });

$j('#dose1').change(
    function () {
        $j('#frequency1').attr('disabled', '');
        var e = document.getElementById("dose");

        var str = e.options[e.selectedIndex].value;
        if (str != "null") {
            if (str == "Select") {

                alert("Must choose the right one");
            } else {
                $j.getJSON("drugDispense.form?dose="
                    + str.substring(str.indexOf('#') + 1),
                    function (result) {
                        $j.each(result, function (index, value) { //bincard"stateList
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
        }

    });

$j('#frequency').change(
    function () {
        var e = document.getElementById("frequency");
        var str = e.options[e.selectedIndex].value;
        if (str != "null") {
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
        }

    });

$j('#frequency1').change(
    function () {
        var e = document.getElementById("frequency");
        var str = e.options[e.selectedIndex].value;
        if (str != "null") {
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
        }
    });

$j('#WA1').change(function () {

    var val = document.getElementById("WA1").value;

    var val2 = document.getElementById("AM1").value
    document.getElementById("AM1").value = val2 - val;
});

$j('#WA2').change(function () {
    var val = document.getElementById("WA2").value;
    var val2 = document.getElementById("AM2").value
    document.getElementById("AM2").value = val2 - val;

});
$j('#nextvisit').change(
    function () {

        //alert('Handler for .change() called.'+document.getElementById("nextvisit").value);
        var givenDate = document.getElementById("nextvisit").value;

        var currentTime = new Date();
        var month = currentTime.getMonth() + 1;
        var day = currentTime.getDate();
        var year = currentTime.getFullYear();

        //compare to get the diffrence

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

                            // 									    					inputTags[i].value=_Diff;
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

        //3 weeks

    });

$j('#noofmonths')
    .change(
    function () {
        var inputTags = document.getElementsByTagName('INPUT');
        var givenDays = document.getElementById("noofmonths").value;
        quantity = givenDays;

        if (givenDays <= 6) {
            //week

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

            //3 weeks

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

// 									