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
        <form id="generalvoid" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label> <input type="text"
                                                          name="generalreason" id="generalreason" class="required"/>
                <input
                        type="hidden" name="generaluuidvoid" id="generaluuidvoid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="generalForm" action="#">
            <fieldset>

                <legend>Name</legend>

                <label for="engine"> Name</label>

                <input type="text" name="generalname" id="generalname" class="required"/><br/>


                <label for="engine">Description</label>
                <input type="text" name="description" id="description"/>
                <input type="hidden" name="generaledit" id="generaledit" value="false"/> <input
                    type="hidden" name="generaluuid" id="generaluuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tgeneral">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th> name</th>
            <th>Description</th>
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