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
        <form id="maxminvoid" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label> <input type="text"
                                                          name="maxminreason" id="maxminreason" class="required"/>
                <input
                        type="hidden" name="maxminuuidvoid" id="maxminuuidvoid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="maxminform" action="#">
            <fieldset>

                <legend>Name</legend>


                <label>Drug</label>
                <input type="text" name="maxmindrug" id="maxmindrug"/>
                <br/>

                <label for="engine">Min level</label>
                <input type="text" name="max" id="max" class="required"/> <br/>
                <label for="engine">Max level</label>
                <input type="text" name="min" id="min"/> <br/>
                <input type="hidden" name="maxmineedit" id="maxminedit" value="false"/>

                <input
                        type="hidden" name="maxminuuid" id="maxminuuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tmaxmin">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>Drug</th>

            <th>Min level</th>
            <th>Max level</th>
            MaxMinController
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
            <td></td>


        </tr>
        </tbody>

    </table>

</DIV>

</body>
</html>