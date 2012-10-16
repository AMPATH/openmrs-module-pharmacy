<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">

    <DIV id="incss">


        <!-- 		<a href="#" id="bincardform">Add drugs </a> -->


        <form id="lowvoid" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Void reason</legend>

                <label>Reason</label> <input type="text" name="binreason"
                                             id="binreason" class="required"/> <input type="hidden"
                                                                                      name="binuuidvoid"
                                                                                      id="binuuidvoid"/> <input
                    class="submit"
                    type="submit" value="Submit"/>
            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>


        <form id="filter" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Filter</legend>

                <label>Drug</label>
                <input type="text"
                       name="filterdruglow" id="filterdruglow"/>


            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tlowbincard">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>UUID</th>
            <th>Drug</th>
            <th>Quantity</th>
            <th>Category</th>


            <th>Maxlevel</th>
            <th>Minlevel</th>
            <th>Expire date</th>

            <th>Batch No</th>

            <th>S11 No</th>
            <th>Delivery No</th>
            <th>Suppier</th>


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
            <th></th>

            <th></th>


        </tr>
        </tbody>

    </table>


</DIV>

</body>
</html>