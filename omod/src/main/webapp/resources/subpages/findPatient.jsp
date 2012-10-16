<div id="corn">
    <form method="post" name="drg">


        <fieldset id="parent_field">
            <legend>
                <c:if test="${empty patient}">Enter and select the patient:</c:if>
                <c:if test="${not empty patient}">Profile</c:if>
            </legend>
            <input id="hiddenfield" type="hidden" name="fieldCount"/>

            <div>
                <div>

                    <c:choose>
                        <c:when test="${empty patient}">

                            <style>
                                #openmrsSearchTable_wrapper {
                                    /* Removes the empty space between the widget and the Create New Patient section if the table is short */
                                    /* Over ride the value set by datatables */
                                    min-height: 0px;
                                    height: auto !important;
                                }
                            </style>
                            <openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js"/>
                            <openmrs:htmlInclude
                                    file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
                            <openmrs:htmlInclude
                                    file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
                            <openmrs:htmlInclude
                                    file="/scripts/jquery-ui/js/openmrsSearch.js"/>

                            <script type="text/javascript">
                                var lastSearch;
                                $j(document).ready(function () {


                                    new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectionHandler,
                                            [
                                                {fieldName:"identifier", header:omsgs.identifier},
                                                {fieldName:"givenName", header:omsgs.givenName},
                                                {fieldName:"middleName", header:omsgs.middleName},
                                                {fieldName:"familyName", header:omsgs.familyName},
                                                {fieldName:"age", header:omsgs.age},
                                                {fieldName:"gender", header:omsgs.gender},
                                                {fieldName:"birthdateString", header:omsgs.birthdate},
                                            ],
                                            {
                                                searchLabel:'<spring:message code="Patient.searchBox" javaScriptEscape="true"/>'
                                                        < c
                                    :
                                    if test = "${not empty param.phrase}" >
                                            , searchPhrase:
                                    '<spring:message text="${ param.phrase }" javaScriptEscape="true"/>'
                                    < /c:if>
                                });

                                //set the focus to the first input box on the page(in this case the text box for the search widget)
                                var inputs = document.getElementsByTagName("input");
                                if (inputs[0])
                                    inputs[0].focus();


                                })
                                ;

                                function doSelectionHandler(index, data) {
                                    alert("xxxxxxxxxxxx" + data.patientId);
                                    //document.location = "findLilyPatient.form?patientId=" + data.patientId;
                                }

                                //searchHandler for the Search widget
                                function doPatientSearch(text, resultHandler, getMatchCount, opts) {
                                    lastSearch = text;
                                    DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
                                }


                            </script>

                            <div>

                                <div class="searchWidgetContainer" id="findPatients"></div>

                            </div>

                        </c:when>
                        <c:otherwise>
                            <c:if test="${not empty patient}">
                                <table border="0" style="background-color: #B4D1B6;">
                                    <tr>
                                        <td><b>Name: &nbsp;</b> <c:if
                                                test="${not empty patient.givenName}">${patient.givenName} &nbsp;</c:if>
                                            <c:if test="${not empty patient.middleName}">${patient.middleName}
                                                &nbsp;</c:if>
                                            <c:if test="${not empty patient.familyName}">${patient.familyName}</c:if>
                                        </td>
                                        <td><b>DOB (Age): &nbsp;</b> <c:if
                                                test="${not empty patient.birthdate}">${patient.birthdate}
                                            &nbsp;(${patient.age} )</c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Identifiers : &nbsp;</b> <c:if
                                                test="${not empty patient.identifiers}">${patient.identifiers} </c:if>
                                        </td>
                                        <td><b>Gender : &nbsp;</b> <c:if
                                                test="${not empty patient.gender}">${patient.gender} </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><b>Next visit: &nbsp;</b></td>
                                    </tr>

                                    <tr>
                                        <td><b>Source of patient: &nbsp;</b></td>
                                    </tr>

                                </table>

                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
        </fieldset>
        <c:if test="${not empty patient}">
            <c:if test="${patient.dead}">
                <div id="patientDashboardDeceased" class="retiredMessage">
                    <div>
                        <spring:message code="Patient.patientDeceased"/>
                        <c:if test="${not empty patient.deathDate}">
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <spring:message code="Person.deathDate"/>: <openmrs:formatDate
                                date="${patient.deathDate}"/>
                        </c:if>
                        <c:if test="${not empty patient.causeOfDeath}">
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <spring:message code="Person.causeOfDeath"/>: <openmrs:format
                                concept="${patient.causeOfDeath}"/>
                            <c:if test="${not empty causeOfDeathOther}">
                                &nbsp;:&nbsp;<c:out value="${causeOfDeathOther}"></c:out>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:if>


        </c:if>


    </form>
</div>

