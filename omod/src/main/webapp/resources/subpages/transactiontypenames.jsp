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
        <form id="transactionsvoid" method="POST" action="">
            <fieldset>

                <legend>Name Void reason</legend>

                <label for="engine">Reason</label> <input type="text"
                                                          name="transactionsreason" id="transactionsreason"
                                                          class="required"/> <input
                    type="hidden" name="transactionsuuidvoid" id="transactionsuuidvoid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
        <form id="transactionsform" action="#">
            <fieldset>

                <legend>Name</legend>

                <label for="engine">transaction name</label>
                <input type="text" name="transactionsname" id="transactionsname" class="required"/> <br/>
                <label for="engine">description</label>
                <input type="text" name="description" id="description"/>
                <input type="hidden" name="transactionseedit" id="transactionsedit" value="false"/>

                <input
                        type="hidden" name="transactionsuuid" id="transactionsuuid"/> <input
                    class="submit" type="submit" value="Submit"/>
            </fieldset>
        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="ttransactions">
        <thead>
        <tr>
            <th width="4%">Edit</th>

            <th>Actions</th>
            <th>UUID</th>
            <th>transaction name</th>
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