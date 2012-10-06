<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

<DIV id="dtab_1">


    <div id="detailsformBin" title="Are you sure?">



    </div>


    <div id="detailsformCheck" title="Password check">
        <form id="check" action="#">






            <label>User password</label>
            <input type="password" name="password" id="password" class="required" /> <br />
            <input type="hidden" name="pass" id="pass" /> <br />

        </form>
    </div>
    <form id="categoryfilter" action="#">
        <hr color=#1aad9b width="100%">

        <fieldset>

            <legend> Category Filter</legend>

            <label>Drug Category</label>

            <select id="filtercategory" name="filtercategory">


            </select>


        </fieldset>
        <hr color=#1aad9b width="100%">

    </form>


    <DIV id="incss">
        <!-- 		<a href="#" id="bincardform">Add drugs </a> -->



        <form id="bincard" name="bincard" action="#">


            <fieldset>

                <legend>Bincard name</legend>
                <input type="hidden" name="binedit" id="binedit" value="false" />
                <input
                        type="hidden" name="binuuid" id="binuuid" />
                <label>Drug</label>
                <select id="bindrug" name="bindrug" class="required">


                </select>				<br /> 	<br />



                <label>Quantity</label> <input type="text"
                                               name="binquantityin" id="binquantityin" class="required" />			<br /> 	<br />



                <label>Max
                    value</label> <input type="text" name="binmax" id="binmax" /> <br /> 	<br />
                <label
                        for="engine">Min value</label> <input type="text" name="binmin"
                                                              id="binmin" /><br /> 	<br />

                <label>Batch No</label>
                <input type="text" name="binbatch" id="binbatch"  class="required"/> 			<br /> 	<br />
                <label>S 11 No</label>
                <input type="text" name="bins11" id="bins11" class="required" /> 			<br /> 	<br />

                <label for="incomingexpire">Expire date</label>
                <input type="text" name="binexpire" id="binexpire" class="required" /> 			<br /> 	<br />
                <label>Delivery No</label>
                <input type="text" name="delivery" id="delivery" class="required"  /><br /> 	<br />


                <label>Change Reasons</label>	<br />



                <input type="checkbox" name="bincom" value="wrong entry" /> wrong entry
                <input type="checkbox" name="bincom2" value="Issued to wrong facility " /> Issued to wrong facility

                <input type="checkbox" name="bincom3" value="Return " /> Return 	<br />

                <input class="submit"
                       type="submit" value="Submit" />
            </fieldset>

        </form>

        <form id="binvoid" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Void reason</legend>

                <label>Reason</label> <input type="text" name="binreason"
                                             id="binreason" class="required" /> <input type="hidden"
                                                                                       name="binuuidvoid" id="binuuidvoid" /> <input class="submit"
                                                                                                                                     type="submit" value="Submit" />
            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>


        <form id="filter" action="#">
            <hr color=#1aad9b width="100%">

            <fieldset>

                <legend> Filter</legend>

                <label>Drug</label>
                <input type="text"
                       name="filterdrugbin" id="filterdrugbin" />    </br>


                <label>S11</label>
                <input type="text"
                       name="s11number" id="s11number" />



                <input class="submit"
                       type="submit" value="Search" />

            </fieldset>
            <hr color=#1aad9b width="100%">

        </form>
    </DIV>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="tbincard">
        <thead>
        <tr>
            <th width="4%">Edit</th>
            <th>Actions</th>
            <th>UUID</th>
            <th>Drug</th>
            <th>Quantity</th>


            <th>Minlevel</th>
            <th>Maxlevel</th>
            <th>Expire date</th>
            <th>Batch No</th>
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

            <th></th>



        </tr>
        </tbody>

    </table>


</DIV>

</body>
</html>