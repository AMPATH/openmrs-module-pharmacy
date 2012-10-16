<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">

    <div id="detailsApproved" title="Information">


    </div>
    <div id="detrailsAp" title="Information">


    </div>

    <div id="detailsformValap" title="Are you sure ?">


    </div>
    <DIV id="incss">

        <a href="#" id="approvedform">Show Add Request </a>
        <a href="#" id="hideapprovedform">Hide Add Request </a>

        <div id="submitAllApp">
            <input class="submit" type="submit" value="Add to store checked" id="submitAllAp"/>
        </div>


        <form id="approvedvoid" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Void reason</legend>

                <label>Reason</label> <input type="text" name="approvedreason"
                                             id="approvedreason" class="required"/> <input type="hidden"
                                                                                           name="approveduuidvoid"
                                                                                           id="approveduuidvoid"/>
                <input class="submit"
                       type="submit" value="Submit"/>
            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>


        <form id="incomingextraap" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend>Extra information</legend>
                <br/>
                <label>Supplier</label>
                <select id="supplierout" name="supplierout" class="required">


                </select> <br/>
                <label>Delivery No</label>
                <input type="text" name="delivery" id="delivery" class="required"/> <br/>

                <input class="submit" type="submit" value="Submit"/>
            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>


        <form id="filterapproved" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Filter</legend>

                <label>Drug</label>
                <input type="text" name="filterdrugapproved" id="filterdrugapproved"/>


            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tapproved">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>Drug</th>
            <th>Quantity</th>
            <th>From</th>
            <th>T-Type</th>

            <th>Details</th>
            <th>Details</th>
            <th>Details</th>
            <th>Details</th>
            <th>Details</th>
            <th>Details</th>
            <th>Details</th>


            <th>Details</th>

            <th>Approve</th>
            <th>Status</th>
            <th>Check</th>

            <th>user authorized</th>
            <th>user issued</th>


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