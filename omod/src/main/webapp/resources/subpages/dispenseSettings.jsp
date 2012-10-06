<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">



<DIV id="incss">
    <DIV id="errorDivStore" class="header-footer ui-state-default ui-corner-all"
         style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">



    </DIV>
    <form id="dispenseVal" name="dispenseVal" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend>Dispense name</legend>
            <input type="hidden" name="dispenseedit" id="dispenseedit" value="false" />
            <input type="hidden" name="dispenseuuid" id="dispenseuuid" />

            <input type="hidden" name="inventoryNo" id="inventoryNo" /> </br >
            <label>Drug</label>

            <input type="text" name="dispensedrug" id="dispensedrug" />

            <!-- 				<label>Total in store:</label> -->
            <!-- 				 <input id="totalstore" type="totalstore" value="000" readonly="readonly" /> -->

            <br />




        </fieldset>

    </form>

    <form id="dispensevoid" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend> Void reason</legend>
            </br >
            <label>Reason</label> <input type="text" name="dispensereason"
                                         id="dispensereason" class="required" />
            </br ><input type="text"
                         name="dispenseuuidvoid" id="dispenseuuidvoid" /> <input class="submit"
                                                                                 type="submit" value="Submit" />
        </fieldset>
        <hr color=#1aad9b width="100%">

    </form>
</DIV>
<div id='parent_div_1'>

    <form action="#">
        <table cellpadding="0" cellspacing="0" border="0" class="display" style="width: 450px;"
               id="tinventoryset">
            <thead>
            <tr>
                <th>UUID</th>
                <th>Drug</th>
                <th>Quantity</th>

                <th>BatchNo</th>
                <th>D2Expire</th>
                <th>Select</th>

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



            </tr>
            </tbody>

        </table>

    </form>
</div>


<DIV id="incss">
    <div id='parent_div_2'>
        <form id="dispenseextra" action="#">

            <fieldset>

                <legend>Extra information</legend>



                <label>inventory No</label>
                <input type="text" name="inventoryNo" id="inventoryNo"   class="required" /> </br >

                <label>Option</label>

                <select  id="option" class="required" >
                    <option value="Days">Days</option>
                    <option value="Tablets">Tablets</option>
                    <option value="Bottle">Bottle</option>
                </select>  </br >
                <label>Number</label>
                <input type="text" name="value" id="value"   class="required" /> </br >
                <label>Value</label>
                <input type="text" name="price" id="price"   class="required" /> </br >


                <label>Quantity to dispense </label>
                <input type="text" name="quantity" id="quantity"   class="required" /> </br >

                <label>Form</label>

                <select  id="form" class="required" >
                    <option value="syrup">Syrup</option>
                    <option value="Tablets">Tablets</option>
                </select></br >
                <label>Front inscription</label>
                <input type="text" name="front" id="front"   class="required" /> </br >
                <label>Back inscription</label>
                <input type="text" name="back" id="back"   class="required" /> </br >






                <input type="hidden" name="dispenseedit" id="dispenseedit" value="false" />
                <input type="hidden" name="dispenseuuid" id="dispenseuuid" />

                <input class="submit" type="submit" value="Submit & Create barcode" />
            </fieldset>

        </form>

        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend>Bar code</legend>


            <div id="barcodeImg" >

            </div>
            <div id="cmdPrintDiv"><input type="button" value="Print Barcode" onclick="showPrint()" ></div>

            <div id="cmdDownDiv"></div>
        </fieldset>
    </div>

</DIV>

<DIV id="lower">
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tdispense">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>UUID</th>
            <th>Actions</th>
            <th>Drug</th>
            <th>Drug ID</th>
            <th>Option</th>
            <th>Value</th>
            <th>Price  per value</th>

            <th>Batch</th>
            <th>Expire date</th>

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