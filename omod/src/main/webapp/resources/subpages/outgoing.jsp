<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">

<div id="dataformD" title="InformationXXXXX">


</div>
<div id="detailsformout" title="Information">


</div>

<div id="detailsformVal" title="Are you sure ?">


</div>

<div id="encountformOut" title="Information">


</div>
<DIV id="incss">

    <!-- 		<a href="#" id="outgoingform">Add Request </a> -->


    <DIV id="errorDivOut" class="header-footer ui-state-default ui-corner-all"
         style="padding: 3px 5px 5px; text-align: center; margin-bottom: 1ex;">
        <div id='red'>You cannot select more than what you have in store</div>
    </DIV>


    <form id="outgoingVal" name="outgoingVal" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend>outgoing name</legend>
            <input type="hidden" name="outgoingedit" id="outgoingedit" value="false"/>
            <input type="hidden" name="outgoinguuid" id="outgoinguuid"/>
            <label>Drug</label>
            <select id="outgoingdrug" name="outgoingdrug">


            </select>
            <label>Total in store:</label>
            <input id="totalstore" type="totalstore" value="000" readonly="readonly"/>
            <label>Location</label>
            <select id="location" name="location">


            </select>

            <br/>


            <label>Quantity out</label>
            <input type="text" name="outgoingquantityin" id="outgoingquantityin" class="required"/>

            <br/>
            <label for="outgoing">Batch NO</label>
            <input type="text" name="outgoingbatch" id="outgoingbatch"/>
            <label for="outgoing">S11 NO</label>
            <input type="text" name="outgoings11" id="outgoings11"/>

            <label for="outgoing">Expire date</label>
            <input type="text" name="outgoingexpire" id="outgoingexpire"/>
            <br/>
            <label>Destination</label>
            <select id="destination" name="destination">


            </select>
            <label>Supplier</label>
            <select id="supplier" name="supplier">


            </select>

            <label>Transactions Type</label>
            <select id="transactions" name="transactions">


            </select>


            <input class="submit" type="submit" value="Submit"/>
        </fieldset>

    </form>

    <form id="outgoingvoid" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend> Void reason</legend>

            <label>Reason</label> <input type="text" name="outgoingreason"
                                         id="outgoingreason" class="required"/> <input type="hidden"
                                                                                       name="outgoinguuidvoid"
                                                                                       id="outgoinguuidvoid"/> <input
                class="submit"
                type="submit" value="Submit"/>
        </fieldset>
        <hr color=#1aad9b width="100%">

    </form>
</DIV>
<div id='parent_div_1out'>


    <table cellpadding="0" cellspacing="0" border="0" class="display" style="width: 450px;"
           id="tinventoryoutgoing">
        <thead>
        <tr>
            <th>UUID</th>
            <th>Drug</th>
            <th>Quantity</th>
            <th>BatchNo</th>
            <th>Months to Expire</th>
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


</div>
<DIV id="incss">
    <div id='parent_div_2out'>
        <form id="outgoingextra" action="#">

            <fieldset>

                <legend>Extra information</legend>


                <!-- 					<label>Batch No</label>  -->
                <!-- 				<input type="text" name="outgoingbatch" id="outgoingbatch"  class="required"/> 			<br /> 	 -->
                <label>S 11 No</label>
                <input type="text" name="outgoings11" id="outgoings11" class="required"/> <br/>

                <!-- 					<label for="outgoingexpire">Expire date</label>  -->
                <!-- 				<input type="text" name="outgoingexpire" id="outgoingexpire222" class="required" /> 		 -->
                <!-- 				<label>Delivery No</label>  -->
                <!-- 				<input type="text" name="delivery" id="delivery" class="required"  />  <br /> -->

                <div id="input1" style="margin-bottom:4px;" class="clonedInput">
                    <label>Quantity</label>
                    <input class="datavalues" type="text" name="outgoingnumber" id="outgoingnumber" class="required"/>
                </div>


                <!--  <input type="button" id="btnDel" value="Remove field" />	<br /> 	 -->
                <input type="hidden" name="one" id="one2"/>
                <input type="hidden" name="two" id="one3"/>
                <input type="hidden" name="three" id="one4"/>
                <input type="hidden" name="four" id="one5"/>

                <input type="hidden" name="one1" id="one22"/>
                <input type="hidden" name="two1" id="one23"/>
                <input type="hidden" name="three1" id="one24"/>
                <input type="hidden" name="four1" id="one25"/>

                <input type="hidden" name="outgoingdrug" id="outgoingdrug"/>
                <input type="hidden" name="outgoinguuidextra" id="outgoinguuidextra"/>
                <input class="submit" type="submit" value="Submit"/>
            </fieldset>

        </form>
    </div>
    <br/><br/>
    <!-- 			<a href="#" id="hideoutgoingform">Hide forms </a> -->


    <fieldset>
        <form id="filteroutgoing" action="#">
            <hr color=#1aad9b width="100%">


            <legend> Filter</legend>

            <label>Drug</label>
            <input type="text" name="filterdrugoutgoing" id="filterdrugoutgoing"/>


            <hr color=#1aad9b width="100%">

        </form>


        <div id="submitAllDivo">
            <input class="submit" type="submit" value="Approve checked" id="submitAllo"/>
        </div>


        <div id="SaveDivo">
            <input class="submit" type="submit" value="Save" id="Saveo"/>
        </div>

    </fieldset>
</DIV>
<table cellpadding="0" cellspacing="0" border="0" class="display"
       id="toutgoing">
    <thead>
    <tr>
        <th width="4%">Edit</th>
        <th>Actions</th>
        <th>UUID</th>
        <th>Drug</th>
        <th>Quantity</th>
        <th>Requested by</th>
        <th>To</th>
        <th>T-Type</th>


        <th>Details</th>
        <th>Details</th>
        <th>Details</th>
        <th>Details</th>
        <th>Details</th>


        <th>Details</th>

        <th>Details</th>

        <th>Approve</th>
        <th>Void</th>
        <th>Status</th>
        <th>Check</th>
        <th>User requesting</th>
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


    </tr>
    </tbody>

</table>


</DIV>

</body>
</html>