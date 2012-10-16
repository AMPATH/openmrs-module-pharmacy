<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>

<DIV id="rcombidiv">
    <DIV id="incss">
        <a href="#" id="regimenid">Add regimen </a>

        <form id="regimenvoid" action="#">
            <fieldset>

                <legend>frequency Void reason</legend>

                <label>Reason</label> <input type="text" name="regimenreason"
                                             id="regimenreason" class="required"/> <input type="hidden"
                                                                                          name="regimenuuidvoid"
                                                                                          id="regimenuuidvoid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>


        <form id="regimenform" name="regimenform" action="#">
            <fieldset>

                <legend>Regimen creation</legend>

                <input type="hidden" name="regimenedit" id="regimenedit"
                       value="false"/> <input type="hidden" name="regimenuuid"
                                              id="regimenuuid"/> <label>Regimen name</label> <select
                    id="regimennamecomplete" name="regimennamecomplete">


            </select> <br/> <label>Complete drug </label>


                <input type="text" name="complete"
                       id="complete"/>
                </br>


                <label>Regimen options</label> <select id="optionss"
                                                       name="optionss" onchange="show_value(this.value);">


            </select> </br> <label>Two options Drugs</label>
                <input type="text" name="option1"
                       id="option1"/>

                <input type="text" name="option2"
                       id="option2"/>


                </select>         </br>  <label>Three options Drugs</label>
                <input type="text" name="regimendrug1"
                       id="regimendrug1"/>

                <input type="text" name="regimendrug2"
                       id="regimendrug2"/>


                <input type="text" name="regimendrug3"
                       id="regimendrug3"/>


                </select>         </br>

                <label>Category</label><select id="category" name="category">

                <option value="PMTCT">PMTCT</option>
                <option value="PMTCT2">PMTCT2</option>
                <option value="PMTCT3">PMTCT3</option>
            </select>


                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tregimen">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>Regimen code</th>
            <th>Complte Drug</th>

            <th>Three options(1)</th>
            <th>Three options(2)</th>
            <th>Three options(3)</th>
            <th>Two options(1)</th>
            <th>Two options(2)</th>
            <th>Void</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>


        </tr>
        </tbody>

    </table>
</DIV>

</body>
</html>