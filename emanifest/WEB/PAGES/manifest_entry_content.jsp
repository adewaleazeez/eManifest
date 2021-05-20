<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>e-Manifest Interface System</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.4.custom.css" rel="stylesheet" />
        <link href="css/mycss.css" rel="stylesheet" type="text/css"/>
        <link href="css/emsportal.css" rel="stylesheet" type="text/css"/>
        <link href="css/calendar.css" rel="stylesheet" type="text/css"/>

        <!--link rel="stylesheet" href="css/jquery-ui-1.8.4.custom.css" type="text/css">
        <link href="css/calendar.css" rel="stylesheet" type="text/css"/-->
        <link href="css/jqueryFileTree.css" rel="stylesheet" type="text/css"/>

        <script type='text/javascript' src='js/utilities.js'></script>
        <script type='text/javascript' src='js/calendar.js'></script>
        <script type='text/javascript' src='js/jqueryFileTree.js'></script>
        <script type="text/javascript" src="js/jquery.ui.core.js"></script>
        <script type="text/javascript" src="js/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="js/jquery.ui.accordion.js"></script>
        <script type='text/javascript' src='js/manifest.js'></script>

        <script type="text/javascript">

            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $(document).ready(function(){
                    $("#infolist").accordion({
                        autoHeight: false
                    });
                });

                $("#dialog").dialog("destroy");

                $("#showPrompt").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Alert!!!',
                    height: 300,
                    width: 300,
                    modal: true,
                    buttons: {
                        Ok: function() {
                            $('#showPrompt').dialog('close');
                        }
                    }
                });

                $("#showResponse").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Click Yes/No',
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        Yes: function() {
                            $('#showResponse').dialog('close');
                            doDetails("delete");
                        },
                        No: function() {
                            $('#showResponse').dialog('close');
                        }
                    }
                });

                $("#showAlert").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Alert!!!',
                    height: 280,
                    width: 350,
                    modal: true
                });

                $("#showError").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Error Message',
                    height: 400,
                    width: 500,
                    modal: true,
                    buttons: {
                        Ok: function() {
                            $('#showError').dialog('close');
                        }
                    }
                });

                $("#listings").dialog({
                    autoOpen: true,
                    position:'center',
                    title: 'List of Bills of Ladings',
                    height: 500,
                    width: 1000,
                    modal: false,
                    buttons: {
                        Add_BOL: function() {
                            var entryDate = readCookie("entrydate");
                            var Registry_Number = readCookie("voyageno");
                            if(Registry_Number==null || Registry_Number=="" || entryDate==null || entryDate==""){
                                $('#showAlert').dialog('close');
                                document.getElementById("showPrompt").innerHTML = "<b>Invalid dates/voyage number</b><br><br>Check your dates and voyage number.";
                                $('#showPrompt').dialog('open');
                                return true;
                            }
                            doListings("newbol");
                        },
                        New_Manifest: function() {
                            doListings("newman");
                        },
                        Close: function() {
                            $('#listings').dialog('close');
                        }
                    }
                });

                $("#details").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Detail Data Entry - Items with red labels are mandatory',
                    height: 500,
                    width: 950,
                    modal: false,
                    buttons: {
                        Save: function() {
                            doDetails("add");
                        },
                        Delete: function() {
                            $('#showResponse').dialog('open');
                        },
                        Update: function() {
                            doDetails("update");
                        },
                        //New: function() {
                        //    doDetails("new");
                        //},
                        Close: function() {
                            $('#details').dialog('close');
                            $('#listings').dialog('open');
                        }
                    }
                });

            });
            
        </script>

        <style type="text/css">
            /*div { padding: 8px; border: 1px solid blue;}
            legend { font-weight: bold; }*/
        </style>

    </head>
    <body>
        <div id="showResponse"><br><br>Are you sure you want to delete this record?</div>
        <div id="showError"></div>
        <div id="showPrompt"></div>
        <div id="showAlert"></div>
        <div id="listings">
            <div id="bollistheader" style="max-height:40px;max-width:980px;"></div>
            <div id="voyagenolist" style="max-height:300px;max-width:450px;overflow:auto;"></div>
            <div id="bollist" style="max-height:300px;max-width:980px;overflow:auto;"></div>
        </div>

        <div id="details" style="overflow: auto; height: 500px; width: 950px; position: relative;">
            <div id="infolist" style="overflow: auto; height: 450px; width: 900px; position: relative;">
                <h3><a href="#">Manifest</a></h3>
                <div>
                    <!--legend>Manifest</legend-->
                    <table>
                        <tr>
                            <td style=" color: red"><b>Voyage Number</b><br><input type="text" id="Registry_Number" onfocus="clearLists();" size="17" /></td>
                            <td style=" color: red"><b>Departure Date</b><br><input type="text" id="Departure_Date" onfocus="clearLists();" name="Departure_Date" onclick="displayDatePicker('Departure_Date', false, 'dmy', '/');" /></td>
                            <td style=" color: red"><b>Customs Office Code</b><br><input type="text" id="Customs_Office_Code" onfocus="clearLists();" size="4" onkeyup="getAllRecords(this.id,'customs');" onclick="getAllRecords(this.id,'customs');"  /></td>
                        </tr>
                        <tr>
                            <td><b>Captain/Pilot Name</b><br><input type="text" id="Master_Information" onfocus="clearLists();" size="35" /></td>
                            <td><b>Last Discharge Date</b><br><input type="text" id="Last_Discharge_Date" onfocus="clearLists();" name="Last_Discharge_Date" onclick="displayDatePicker('Last_Discharge_Date', false, 'dmy', '/');" /></td>
                            <td style=" color: red"><b>Arrival Date</b><br><input type="text" id="Date_of_Arrival" onfocus="clearLists();" name="Date_of_Arrival" onclick="displayDatePicker('Date_of_Arrival', false, 'dmy', '/');" /></td>
                        </tr>
                        <tr>
                            <td><b>Time of Arrival</b><br><input type="text" id="Time_of_Arrival" onfocus="clearLists();" size="10" /></td>
                            <td style=" color: red"><b>Loading Port Code</b><br>
                                <table>
                                    <tr>
                                        <td>
                                            <input type="text" id="loadingport" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'nations');" onclick="getAllRecords(this.id,'nations');"  />
                                        </td>
                                        <td>
                                            <input type="text" id="Place_of_Departure_Code" name="Place_of_Departure_Code" onfocus="clearLists();" size="6" onkeyup="getAllRecords(this.id,'ports');" onclick="getAllRecords(this.id,'ports');"  />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td style=" color: red"><b>Unloading Port Code</b><br>
                                <table>
                                    <tr>
                                        <td>
                                            <input type="text" id="unloadingport" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'nations');" onclick="getAllRecords(this.id,'nations');"  />
                                        </td>
                                        <td>
                                            <input type="text" id="Place_of_Destination_Code" name="Place_of_Destination_Code" onfocus="clearLists();" size="6" onkeyup="getAllRecords(this.id,'ports');" onclick="getAllRecords(this.id,'ports');"  />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Carrier Code</b><br><input type="text" id="Carrier_Code" onfocus="clearLists();" size="17" onkeyup="getAllRecords(this.id,'carriers');" onclick="getAllRecords(this.id,'carriers');" /></td>
                            <td><b>Carrier Name</b><br><input type="text" id="Carrier_Name" onfocus="clearLists();" size="35" /></td>
                            <td><b>Carrier Address</b><br><input type="text" id="Carrier_Address" onfocus="clearLists();" size="35" /></td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Plane/Vessel Name</b><br><input type="text" id="Name_of_Transporter" onfocus="clearLists();" size="27" /></td>
                            <td><b>Place of Transporter</b><br><input type="text" id="Place_of_Transporter" onfocus="clearLists();" size="35" /></td>
                            <td style=" color: red"><b>Mode of Transport Code</b><br><input type="text" id="Mode_of_Transport_Code" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'transports');" onclick="getAllRecords(this.id,'transports');"  /></td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Plane/Vessel Nationality</b><br><input type="text" id="Nationality_of_Transporter_Code" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'nations');" onclick="getAllRecords(this.id,'nations');"  /></td>
                            <td><b>Registration Number</b><br><input type="text" id="Registration_Number" onfocus="clearLists();" size="35" /></td>
                            <td><b>Registration Date</b><br><input type="text" readonly id="Registration_Date" readonly onfocus="clearLists();" name="Registration_Date" /></td>
                        </tr>
                        <tr>
                            <td><b>Gross Tonnage</b><br><input type="text" id="Gross_Tonnage" onfocus="clearLists();" size="15" /></td>
                            <td><b>Net Tonnage</b><br><input type="text" id="Net_Tonnage" onfocus="clearLists();" size="15" /></td>
                            <td><b>Entry Date</b><br><input type="text" id="entryDate" readonly onfocus="clearLists();" name="entryDate" /></td>
                        </tr>
                    </table>
                </div>
                <h3><a href="#">Bill of Lading</a></h3>
                <div>
                    <!--legend>Bill of Lading</legend-->
                    <table>
                        <tr>
                            <td style=" color: red"><b>Line Number</b><br><input type="text" id="Line_Number" readonly onfocus="clearLists();" size="10" /></td>
                            <td style=" color: red"><b>Bill of Lading Reference</b><br><input type="text" id="Bol_Reference" onfocus="clearLists();" size="17" /></td>
                            <td><b>Previous Document Reference</b><br><input type="text" id="Previous_Document_Reference" onfocus="clearLists();" size="17" /></td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Bill of Lading Nature</b><br><input type="text" id="Bol_Nature" onfocus="clearLists();" size="2" onkeyup="getAllRecords(this.id,'bols');" onclick="getAllRecords(this.id,'bols');"  /></td>
                            <td><b>Unique Carrier Reference</b><br><input type="text" id="Unique_Carrier_Reference" onfocus="clearLists();" size="35" /></td>
                            <td style=" color: red"><b>Goods Description</b><br><textarea id="Goods_Description" onfocus="clearLists();" rows="2" cols="30"></textarea></td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Total Gross Mass Manifested</b><br><input type="text" id="Total_Gross_Mass_Manifested" onfocus="clearLists();" size="10" /></td>
                            <td><b>Volume in Cubic Meters</b><br><input type="text" id="Volume_in_Cubic_Meters" onfocus="clearLists();" size="10" /></td>
                            <td style=" color: red"><b>Bill of Lading Type Code</b><br><input type="text" id="BOL_Type_Code" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'boltypes');" onclick="getAllRecords(this.id,'boltypes');"  /></td>
                        </tr>
                        <tr>
                            <td><b>Exporter Code</b><br><input type="text" id="Exporter_Code" onfocus="clearLists();" size="17" /></td>
                            <td style=" color: red"><b>Exporter Name</b><br><input type="text" id="Exporter_Name" onfocus="clearLists();" size="35" /></td>
                            <td><b>Exporter Address</b><br><textarea cols="30" rows="2" id="Exporter_Address" onfocus="clearLists();"></textarea></td>
                        </tr>
                        <tr>
                            <td><b>Consignee Code</b><br><input type="text" id="Consignee_Code" onfocus="clearLists();" size="17" /></td>
                            <td style=" color: red"><b>Consignee Name</b><br><input type="text" id="Consignee_Name" onfocus="clearLists();" size="35" /></td>
                            <td><b>Consignee Address</b><br><textarea cols="30" rows="2" id="Consignee_Address" onfocus="clearLists();"></textarea></td>
                        </tr>
                        <tr>
                            <td><b>Notify Code</b><br><input type="text" id="Notify_Code" onfocus="clearLists();" size="17" /></td>
                            <td><b>Notify Name</b><br><input type="text" id="Notify_Name" onfocus="clearLists();" size="35" /></td>
                            <td><b>Notify Address</b><br><textarea cols="30" rows="2" id="Notify_Address" onfocus="clearLists();"></textarea></td>
                        </tr>
                        <tr>
                            <td><b>Number of Seals</b><br><input type="text" id="Number_of_Seals" onfocus="clearLists();" size="10" /></td>
                            <td><b>Marks of Seals</b><br><input type="text" id="Marks_of_Seals" onfocus="clearLists();" size="20" /></td>
                            <td><b>Sealing Party Code</b><br><input type="text" id="Sealing_Party_Code" onfocus="clearLists();" size="3" /></td>
                        </tr>
                        <tr>
                            <td><b>General Information</b><br><textarea id="General_Information" onfocus="clearLists();" rows="2" cols="30"></textarea></td>
                            <!--td><b>Operation Location Code</b><br><input type="text" id="Location_Code" onfocus="clearLists();" size="17" /></td-->
                            <td><b>Operation Location Code</b><br>
                                <table>
                                    <tr>
                                        <td>
                                            <input type="text" id="locationport" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'nations');" onclick="getAllRecords(this.id,'nations');"  />
                                        </td>
                                        <td>
                                            <input type="text" id="Location_Code" name="Location_Code" onfocus="clearLists();" size="6" onkeyup="getAllRecords(this.id,'ports');" onclick="getAllRecords(this.id,'ports');"  />
                                        </td>
                                    </tr>
                                </table>
                            <td><b>Location Information</b><br><input type="text" id="Location_Information" onfocus="clearLists();" size="35" /></td>
                        </tr>
                        <tr>
                            <td style=" color: red"><b>Package Type Code</b><br><input type="text" id="Package_Type_Code" onfocus="clearLists();" size="17"  onkeyup="getAllRecords(this.id,'packages');" onclick="getAllRecords(this.id,'packages');" /></td>
                            <td style=" color: red"><b>Number of Packages</b><br><input type="text" id="Number_of_Packages" onfocus="clearLists();" size="10" /></td>
                            <td><b>Shipping_Marks</b><br><textarea id="Shipping_Marks" rows="2" cols="30" onfocus="clearLists();"></textarea></td>
                        </tr>

                        <!--tr>
                            <td><b>Empty Full Indicator</b><br><input type="text" id="Empty_Full_Indicator" onfocus="clearLists();" size="3" /></td>
                            <td><b>Container Type Code</b><br><input type="text" id="Container_Type_Code" onfocus="clearLists();" size="4" onkeyup="getAllRecords(this.id,'containers');" onclick="getAllRecords(this.id,'containers');" /></td>
                            <td style=" color: red"><b>Total Number of Containers</b><br><input type="text" id="Total_Number_of_Containers" onfocus="clearLists();" size="10" /></td>
                        </tr>
                        <tr>
                            <td><b>Container Identification</b><br><input type="text" id="Container_Identification" onfocus="clearLists();" size="17" /></td>
                            <td><b>Container Number</b><br><input type="text" id="Container_Number" onfocus="clearLists();" size="10" /></td>
                            <td><b>Container Sealing Party Code</b><br><input type="text" id="Container_Sealing_Party_Code" onfocus="clearLists();" size="3" /></td>
                        </tr>
                        <tr>
                            <td><b>Marks of Seals #1</b><br><input type="text" id="Marks_of_Seals_1" onfocus="clearLists();" size="10" /></td>
                            <td><b>Marks of Seals #2</b><br><input type="text" id="Marks_of_Seals_2" onfocus="clearLists();" size="10" /></td>
                            <td><b>Marks of Seals #3</b><br><input type="text" id="Marks_of_Seals_3" onfocus="clearLists();" size="10" /></td>
                        </tr-->
                    </table>
                </div>

                <h3><a href="#">Containers</a></h3>
                <div>
                    <!--legend>Containers</legend-->
                    <table>
                        <tr>
                            <td colspan="3">
                                <table>
                                    <tr>
                                        <td>
                                            <div id="container_content"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="8">
                                            <input type="button" id="addcontainers" onclick="addContainer()" value="Add a Container" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="codeslist" style="max-height:300px; max-width:350px; overflow:auto; position: relative;"></div>
        </div>
    </body>
</html>
<script type="text/javascript">
    getBols('new');
</script>