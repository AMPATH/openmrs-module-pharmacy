<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">


    <DIV id="incss">
        <div id='parent_div_1'>

            <form id="barcodeform" name="barcodeform" action="#">
                <hr color=#1aad9b width="100%">

                <fieldset>

                    <legend>Dispense name</legend>

                    <label>Drug</label>
                    <select id="barcodedrug" name="barcodedrug" class="required">


                    </select>
                    <label>Frequency</label>
                    <select id="frequency" name="frequency" class="required">
                        <option value="1">OD</option>
                        <option value="2">BD</option>

                    </select>
                    <label>Duration in days</label>
                    <input type="text" name="duration" id="duration" class="required"/>

                    <label>Quantity</label>
                    <input type="text" name="quantity" id="quantity" class="required"/>

                    <input type="submit" id="generate" value="Generate Barcode"/>

                </fieldset>

            </form>


        </DIV>
        <div id='parent_div_2'>
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend>Dispense name</legend>


                <div id="barcodeImg">

                </div>
                <div id="cmdPrintDiv"><input type="button" value="Print Barcode" onclick="showPrint()"></div>

                <div id="cmdDownDiv"></div>
            </fieldset>
        </div>
    </DIV>


</body>
</html>