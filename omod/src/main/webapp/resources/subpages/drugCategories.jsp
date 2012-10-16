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
        <form id="categoryvoid" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label> <input type="text"
                                                          name="categoryreason" id="categoryreason" class="required"/>
                <input
                        type="hidden" name="categoryuuidvoid" id="categoryuuidvoid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="categoryform" action="#">
            <fieldset>

                <legend>Name</legend>

                <label for="engine">category name</label> <input type="text"
                                                                 name="categoryname" id="categoryname"
                                                                 class="required"/><br/>


                <label for="engine">description</label> <input type="text"
                                                               name="description" id="description"/> <input
                    type="hidden" name="categoryedit" id="categoryedit" value="false"/> <input
                    type="hidden" name="categoryuuid" id="categoryuuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>


    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tcategory">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>category name</th>
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