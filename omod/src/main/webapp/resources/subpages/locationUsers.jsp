<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript">

    </script>
</head>
<body>

<DIV id="dnames">

    <DIV id="incss">
        <form id="locationvoidusers" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label> <input type="text"
                                                          name="locationreason" id="locationreason" class="required" /> <input
                    type="hidden" name="locationuuidvoid" id="locationuuidvoid" /> <input
                    class="submit" type="submit" value="Submit" />
            </fieldset>
        </form>
        <form id="locationformusers" action="#">


            <!-- <button onclick="window.open('data:application/vnd.ms-excel,' + document.getElementById('tlocation').outerHTML.replace(/ /g, '%20'))">
                Excell
            </button>
            -->
            <fieldset>

                <legend>Name</legend>

                <label >User name</label>
                <input type="text" 	name="username" id="username" class="required" /> <br />

                <label for="engine">Location name</label>
                <input type="text" 	name="locationname" id="locationname" class="required" /> <br />
                <input type="hidden" name="locationeedit" id="locationedit" value="false" />

                <input
                        type="hidden" name="locationuuid" id="locationuuid" /> <input
                    class="submit" type="submit" value="Submit" />
            </fieldset>
        </form>
    </DIV>

    <!-- <button onclick="window.open('data:application/vnd.ms-excel,' + document.getElementById('tlocation').outerHTML.replace(/ /g, '%20'))">
       Get
   </button>
    -->
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tlocationusers">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>User name</th>
            <th>Location</th>

            <th>Void</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>



        </tr>
        </tbody>

    </table>

</DIV>

</body>
</html>