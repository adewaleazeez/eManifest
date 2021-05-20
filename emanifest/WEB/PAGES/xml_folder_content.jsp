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

                $("#xmlList").dialog({
                    autoOpen: true,
                    position:'center',
                    title: 'XML Folders Setup',
                    height: 450,
                    width: 850,
                    modal: false,
                    buttons: {
                        Save: function() {
                            var manifest = document.getElementById("manifest").value;
                            var bol = document.getElementById("bol").value;
                            var reg = document.getElementById("reg").value;
                            var batch = document.getElementById("batch").value;
                            var error = "";
                            if(manifest==null || manifest=="") error += "Manifest folder must not be blank<br><br>";
                            if(bol==null || bol=="") error += "Bill of Lading folder must not be blank<br><br>";
                            if(reg==null || reg=="") error += "Registration Request folder must not be blank";
                            if(batch==null || batch=="") error += "Batch file must not be blank";
                            if(error.length > ""){
                                document.getElementById("showError").innerHTML="<b>Please Correct The Following!!!</b><br><br><br>"+error;
                                $('#showError').dialog('open');
                                return true;
                            }
                            var param = "&manifest="+manifest+"&lading="+bol+"&registration="+reg+"&batchfile="+batch;
                            addRec("update", "xml", param);

                        },
                        Clear: function() {
                            document.getElementById("manifest").value="";
                            document.getElementById("bol").value="";
                            document.getElementById("reg").value="";

                        },
                        Close: function() {
                            $('#xmlList').dialog('close');
                        }
                    }
                });

                $("#container_id").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Select an XML Folder',
                    height: 280,
                    width: 350,
                    modal: false,
                    buttons: {
                        Close: function() {
                            $('#container_id').dialog('close');
                        }
                    }
                });

                    

            });

            function getFolder(path){
                createCookie("path",path,false);
                $('#container_id').dialog('open');
                $('#container_id').fileTree({
                    root: '/',
                    script: 'jqueryFileTree.jsp',
                    expandSpeed: 1000,
                    collapseSpeed: 1000,
                    multiFolder: false
                }, function(file) {
                    document.getElementById(readCookie("path")).value='C:'+file;
                });
            }
            
        </script>
    </head>
    <body>
        <div id="container_id" style="max-height:250px; overflow:auto;"></div>
        <div id="showError"></div>
        <div id="showPrompt"></div>
        <div id="showAlert"></div>
        <div id="xmlList">
            <table>
                <tr>
                    <td>
                        <h5>Manifest Folder:</h5>
                    </td>
                    <td>
                        <h5><input type="text" id="manifest" name="manifest" readonly size="60" /></h5>
                    </td>
                    <td>
                        <h5><input type="button" value="..." onclick="getFolder('manifest')" /></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Bill of Lading Folder:</h5>
                    </td>
                    <td>
                        <h5><input type="text" id="bol" name="bol" readonly size="60" /></h5>
                    </td>
                    <td>
                        <h5><input type="button" value="..." onclick="getFolder('bol')" /></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Registration Folder:</h5>
                    </td>
                    <td>
                        <h5><input type="text" id="reg" name="reg" readonly size="60" /></h5>
                    </td>
                    <td>
                        <h5><input type="button" value="..." onclick="getFolder('reg')" /></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Upload Batch File:</h5>
                    </td>
                    <td>
                        <h5><input type="text" id="batch" name="batch" readonly size="60" /></h5>
                    </td>
                    <td>
                        <h5><input type="button" value="..." onclick="getFolder('batch')" /></h5>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
<script type="text/javascript">
    populateRecords('1','xml');
</script>