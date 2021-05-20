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
        <script type='text/javascript' src='js/manifest.js'></script>

        <script type="text/javascript">

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

                $("#feedback").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Feedback',
                    height: 500,
                    width: 700,
                    modal: true,
                    buttons: {
                        Ok: function() {
                            $('#feedback').dialog('close');
                        }
                    }
                });

                $("#xmlList").dialog({
                    autoOpen: true,
                    position:'center',
                    title: 'XML Files Generation',
                    height: 500,
                    width: 1000,
                    modal: false,
                    buttons: {
                        Generate_XMLs: function() {
                            getXMLs('filterbutton');
                        },
                        Upload_XMLs: function() {
                            uploadMyXMLs();
                        },
                        Check_FeedBacks: function() {
                            checkMyFeedBacks();
                        },
                        Close: function() {
                            $('#xmlList').dialog('close');
                        }
                    }
                });

            });

        </script>
    </head>
    <body onload="populateRecords('1','xml');">
        <div id="container_id" style="max-height:250px; overflow:auto;"></div>
        <div id="showError"></div>
        <div id="showPrompt"></div>
        <div id="showAlert"></div>
        <div id="xmlList">
            
            <div id="xmlheader">
                <div id="feedback"></div>
                 <table border='0' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:100%;background-color:#6666FF;margin-top:5px;'>
                    <tr style='font-weight:bold; color:white'>
                        <td align='right' width="5%">From:</td><td align='left' width="5%"><input type='text' id='fromdate' name='fromdate' size='10' onclick='javascript:fromDate();' /></td>
                        <td align='right' width="3%">To:</td><td align='left' width="5%"><input type='text' id='todate' name='todate' size='10' onclick='javascript:toDate();' /></td>
                        <td align="right" width="5%">Voyage Number:</td>
                        <td align="left" width="10%">
                            <input type="text" id="voyageno" size="15" onkeyup="getMyVoyagenos()" onfocus="getMyVoyagenos()">
                            <div id="voyagenolist" style="max-height:300px;max-width:350px;overflow:auto;"></div>
                        </td>

                    </tr>
                 </table>
                 
            </div>
            
        </div>


    </body>
</html>
<script type="text/javascript">
    getXMLs('new');
</script>