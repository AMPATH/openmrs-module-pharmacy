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
        <form id="suppliervoid" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label>
                <input type="text" name="supplierreason" id="supplierreason" class="required"/>
                <input type="hidden" name="supplieruuidvoid" id="supplieruuidvoid"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="supplierform" action="#">
            <fieldset>

                <legend>Name</legend>

                <label for="engine">supplier name</label> <input type="text"
                                                                 name="suppliername" id="suppliername"
                                                                 class="required"/><br/>


                <label for="engine">description</label> <input type="text"
                                                               name="description" id="description"/> <input
                    type="hidden" name="supplieredit" id="supplieredit" value="false"/> <input
                    type="hidden" name="supplieruuid" id="supplieruuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tsupplier">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>supplier name</th>
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