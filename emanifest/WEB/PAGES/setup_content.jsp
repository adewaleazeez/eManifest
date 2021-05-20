<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>e-Manifest Interface System</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="css/jquery-ui-1.8.4.custom.css" type="text/css">
        <link href="css/calendar.css" rel="stylesheet" type="text/css"/>
        <link href="css/jqueryFileTree.css" rel="stylesheet" type="text/css"/>

        <script type='text/javascript' src='js/utilities.js'></script>
        <script type='text/javascript' src='js/calendar.js'></script>
        <script type='text/javascript' src='js/jqueryFileTree.js'></script>
        <script type="text/javascript" src="js/jquery.ui.core.js"></script>
        <script type="text/javascript" src="js/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="js/jquery.ui.accordion.js"></script>
        <script type='text/javascript' src='js/setup.js'></script>

        <script type="text/javascript">
            //$(document).ready(function(){
            //    $("#infolist").accordion({
            //        autoHeight: false
            //    });
            //});

            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
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
                    height: 300,
                    width: 300,
                    modal: true,
                    buttons: {
                        Ok: function() {
                            $('#showError').dialog('close');
                        }
                    }
                });

                $("#menuList").dialog({
                    autoOpen: true,
                    position:'center',
                    title: 'Setup Menu',
                    height: 558,
                    width: 350,
                    modal: false,
                    buttons: {
                        Close: function() {
                            $('#menuList').dialog('close');
                        }
                    }
                });

                $("#ports").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Ports Setup',
                    height: 500,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            var countrycode = document.getElementById("loadingport").value;
                            var portcode = document.getElementById("portcode").value;
                            if(portcode==""){
                                document.getElementById("showPrompt").innerHTML = "<b>Alert...</b><br><br>Please select a port code.";
                                $('#showPrompt').dialog('open');
                                return true;
                            }
                            if(countrycode.substr(0,2) != portcode.substr(0,2)){
                                document.getElementById("showError").innerHTML = "<b>Invalid Port Code</b><br><br>The Port code must start with the country code.";
                                $('#showError').dialog('open');
                                return true;
                            }
                            getObj("portdescription", "add", "ports", "portcode", "Port Code");
                        },
                        Delete: function() {
                            getObj("portdescription", "delete", "ports", "portcode", "Port Code");
                        },
                        Update: function() {
                            getObj("portdescription", "update", "ports", "portcode", "Port Code");
                        },
                        New: function() {
                            document.getElementById("portdescription").value="";
                            document.getElementById("portcode").value="";
                        },
                        Close: function() {
                            $('#ports').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#carriers").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Carrier Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("carriername", "add", "carriers", "carriercode", "Carrier Code");
                        },
                        Delete: function() {
                            getObj("carriername", "delete", "carriers", "carriercode", "Carrier Code");
                        },
                        Update: function() {
                            getObj("carriername", "update", "carriers", "carriercode", "Carrier Code");
                        },
                        New: function() {
                            document.getElementById("carriercode").value="";
                            document.getElementById("carriername").value="";
                            document.getElementById("carrieraddress").value="";
                        },
                        Close: function() {
                            $('#carriers').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#transports").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Mode of Transportation Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("transportdescription", "add", "transports", "transportcode", "Transport Code");
                        },
                        Delete: function() {
                            getObj("transportdescription", "delete", "transports", "transportcode", "Transport Code");
                        },
                        Update: function() {
                            getObj("transportdescription", "update", "transports", "transportcode", "Transport Code");
                        },
                        New: function() {
                            document.getElementById("transportdescription").value="";
                            document.getElementById("transportcode").value="";
                        },
                        Close: function() {
                            $('#transports').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#nations").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Nationality of Vessels Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("nationdescription", "add", "nations", "nationcode", "Nation Code");
                        },
                        Delete: function() {
                            getObj("nationdescription", "delete", "nations", "nationcode", "Nation Code");
                        },
                        Update: function() {
                            getObj("nationdescription", "update", "nations", "nationcode", "Nation Code");
                        },
                        New: function() {
                            document.getElementById("nationdescription").value="";
                            document.getElementById("nationcode").value="";
                        },
                        Close: function() {
                            $('#nations').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#containers").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Containers Type Stup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("containerdescription", "add", "containers", "containercode", "Container Code");
                        },
                        Delete: function() {
                            getObj("containerdescription", "delete", "containers", "containercode", "Container Code");
                        },
                        Update: function() {
                            getObj("containerdescription", "update", "containers", "containercode", "Container Code");
                        },
                        New: function() {
                            document.getElementById("containerdescription").value="";
                            document.getElementById("containercode").value="";
                        },
                        Close: function() {
                            $('#containers').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#packages").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Package Type Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("packagedescription", "add", "packages", "packagecode", "Package Code");
                        },
                        Delete: function() {
                            getObj("packagedescription", "delete", "packages", "packagecode", "Package Code");
                        },
                        Update: function() {
                            getObj("packagedescription", "update", "packages", "packagecode", "Package Code");
                        },
                        New: function() {
                            document.getElementById("packagedescription").value="";
                            document.getElementById("packagecode").value="";
                        },
                        Close: function() {
                            $('#packages').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#bols").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Bill of Lading Natures Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("boldescription", "add", "bols", "bolcode", "Bill of Lading Code");
                        },
                        Delete: function() {
                            getObj("boldescription", "delete", "bols", "bolcode", "Bill of Lading Code");
                        },
                        Update: function() {
                            getObj("boldescription", "update", "bols", "bolcode", "Bill of Lading Code");
                        },
                        New: function() {
                            document.getElementById("boldescription").value="";
                            document.getElementById("bolcode").value="";
                        },
                        Close: function() {
                            $('#bols').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#boltypes").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Bill of Lading Types Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("boltypedescription", "add", "boltypes", "boltypecode", "Bill of Lading Types");
                        },
                        Delete: function() {
                            getObj("boltypedescription", "delete", "boltypes", "boltypecode", "Bill of Lading Types");
                        },
                        Update: function() {
                            getObj("boltypedescription", "update", "boltypes", "boltypecode", "Bill of Lading Types");
                        },
                        New: function() {
                            document.getElementById("boltypedescription").value="";
                            document.getElementById("boltypecode").value="";
                        },
                        Close: function() {
                            $('#boltypes').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#customs").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Customs Office Code Setup',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Save: function() {
                            getObj("customdescription", "add", "customs", "customcode", "Custom Code");
                        },
                        Delete: function() {
                            getObj("customdescription", "delete", "customs", "customcode", "Custom Code");
                        },
                        Update: function() {
                            getObj("customdescription", "update", "customs", "customcode", "Custom Code");
                        },
                        New: function() {
                            document.getElementById("customdescription").value="";
                            document.getElementById("customcode").value="";
                        },
                        Close: function() {
                            $('#customs').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

            });
            
            function getObj(desc, operation, table, code, msgcode){
                var tempcode= code;
                var code = document.getElementById(code).value;
                var desc = document.getElementById(desc).value;
                if(code==null || code==""){
                    document.getElementById("showError").innerHTML="<b>Invalid "+msgcode+"!!!</b><br><br>Please enter a value in Port Code and try again.";
                    $('#showError').dialog('open');
                    return true;
                }
                var param = "&code="+code+"&desc="+desc;
                if(tempcode=="carriercode"){
                    param +="&address="+document.getElementById("carrieraddress").value;
                }
                addRec(operation, table, param);
            }
        </script>
    </head>
    <body>
        <div id="container_id" style="max-height:250px; overflow:auto;"></div>
        <div id="showError"></div>
        <div id="showPrompt"></div>
        <div id="showAlert"></div>
        <div id="menuList">
            <h5><a href="javascript:getRecords('ports','loadingport');">Ports Setup</a></h5>
            <h5><a href="javascript:getRecords('carriers','1');">Carriers Setup</a></h5>
            <h5><a href="javascript:getRecords('transports','1');">Mode of Transportation Setup</a></h5>
            <h5><a href="javascript:getRecords('nations','1');">Vessels Nationality Setup</a></h5>
            <h5><a href="javascript:getRecords('containers','1');">Container Types Setup</a></h5>
            <h5><a href="javascript:getRecords('packages','1');">Package Types Setup</a></h5>
            <h5><a href="javascript:getRecords('bols','1');">Bill of Lading Natures Setup</a></h5>
            <h5><a href="javascript:getRecords('boltypes','1');">Bill of Lading Types Setup</a></h5>
            <h5><a href="javascript:getRecords('customs','1');">Customs Office Setup</a></h5>
        </div>

        <div id="ports">
            <table>
                <tr>
                    <td><b>Port Code:</b></td>
                    <td>
                        <input type="text" id="portcode" name="portcode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="portdescription" name="description" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="portheader" style="max-height:50px; overflow:auto;">
                <table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:550px;background-color:#6666FF;margin-top:5px;'>
                    <tr style='font-weight:bold; color:white'>
                        <td width="30%" align="right">
                            Select a Country Code:
                        </td>
                        <td width="30%" align="left">
                            <input type="text" id="loadingport" value="AE" onfocus="clearLists();" size="3" onkeyup="getAllRecords(this.id,'nations');" onclick="getAllRecords(this.id,'nations');"  />
                        </td>
                        <td width="30%" align="left">
                            <input type="button" onclick="getRecords('ports','loadingport')" value="List Ports" />
                            <div id="codeslist" style="max-height:300px; max-width:350px; overflow:auto;"></div>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="portlist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="carriers">
            <table>
                <tr>
                    <td><b>Carrier Code:</b></td>
                    <td>
                        <input type="text" id="carriercode" name="carriercode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Name:</b></td>
                    <td>
                        <input type="text" id="carriername" name="carriername" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Address:</b></td>
                    <td>
                        <input type="text" id="carrieraddress" name="carrieraddress" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="carrierlist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="transports">
            <table>
                <tr>
                    <td><b>Mode of Transportation Code:</b></td>
                    <td>
                        <input type="text" id="transportcode" name="transportcode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="transportdescription" name="transportdescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="transportlist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="nations">
            <table>
                <tr>
                    <td><b>Nationality of Vessel Code:</b></td>
                    <td>
                        <input type="text" id="nationcode" name="nationcode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="nationdescription" name="nationdescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="nationlist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="containers">
            <table>
                <tr>
                    <td><b>Container Code:</b></td>
                    <td>
                        <input type="text" id="containercode" name="containercode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="containerdescription" name="containerdescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="containerlist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="packages">
            <table>
                <tr>
                    <td><b>Package Code:</b></td>
                    <td>
                        <input type="text" id="packagecode" name="packagecode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="packagedescription" name="packagedescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="packagelist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="bols">
            <table>
                <tr>
                    <td><b>Bill of Lading Nature:</b></td>
                    <td>
                        <input type="text" id="bolcode" name="bolcode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="boldescription" name="boldescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="bollist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="boltypes">
            <table>
                <tr>
                    <td><b>Bill of Lading Type:</b></td>
                    <td>
                        <input type="text" id="boltypecode" name="boltypecode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="boltypedescription" name="boltypedescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="boltypelist" style="max-height:250px; overflow:auto;"></div>
        </div>

        <div id="customs">
            <table>
                <tr>
                    <td><b>Custom Office Code:</b></td>
                    <td>
                        <input type="text" id="customcode" name="customcode" size="10" onblur="this.value=capitalize(this.value)"/>
                    </td>
                </tr>

                <tr>
                    <td><b>Description:</b></td>
                    <td>
                        <input type="text" id="customdescription" name="customdescription" size="50" onblur="this.value=capAdd(this.value)"/>
                    </td>
                </tr>
            </table>
            <div id="customlist" style="max-height:250px; overflow:auto;"></div>
        </div>

    </body>
</html>
