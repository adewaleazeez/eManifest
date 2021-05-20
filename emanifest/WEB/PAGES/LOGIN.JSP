<%-- 
    Document   : login
    Created on : 24-Dec-2009, 13:30:33
    Author     : Adewale Azeez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>eManifest Systems</title>
        <link href="css/emsportal.css" rel="stylesheet" type="text/css"/>
        <!--link href="css/login.css" rel="stylesheet" type="text/css"/-->
        <script type='text/javascript' src='js/utilities.js'></script>
        <script type='text/javascript' src='js/login.js'></script>
        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.4.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.4.custom.min.js"></script>

        <style type="text/css">
            body { font-size: 62.5%; }
            label, input { display:block; }
            input.text { margin-bottom:12px; width:95%; padding: .4em; }
            fieldset { padding:0; border:0; margin-top:25px; }
            h1 { font-size: 1.2em; margin: .6em 0; }
            div#users-contain { width: 350px; margin: 20px 0; }
            div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
            div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
            .ui-dialog .ui-state-error { padding: .3em; }
            .validateTips { border: 1px solid transparent; padding: 0.3em; }

        </style>
        <script type="text/javascript">
            checkLicense();
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $("#dialog").dialog("destroy");

                $("#loginFormPanel").dialog({
                    autoOpen: true,
                    position:[300,150],
                    title: 'Login',
                    height: 250,
                    width: 450,
                    modal: false,
                    buttons: {
                        Login: function() {
                            /*if(readCookie("licenseexpire")=="Yes") {
                                alert("Your software license has expired!!! \n\nPlease contact the software administrator for renewal")
                                return true;
                            }*/
                            var reg = loginForm();
                            if(reg.length>0){
                                $('#showAlert').dialog('close');
                                document.getElementById("showError").innerHTML = reg;
                                $('#showError').dialog('open');
                            }
                            
                        },
                        Reset: function() {
                            clearUsersList();
                            clearLoginForm();
                        }
                    }
                });

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
                $('#loginFormPanel').dialog('open');

                if(navigator.appName == "Microsoft Internet Explorer"){
                        $('#loginFormPanel').dialog({ modal: false, height: 280, width: 450 });
                }


            });
        </script>
    </head>
    <body>
        <table width="100%">
            <tr>
                <td colspan="2">
                    <div align="center">
                        <img src="images/eManifestLogo.PNG" width="100%" height="60" title="EMS Portal Logo" alt="EMS Portal Logo"/>
                    </div>
                </td>
            </tr>
            <div id="showError"></div>
            <div id="showAlert"></div>
            <div id="showPrompt"></div>
            <div id="userlist" style="max-height:250px; max-width:400px; overflow:auto;"></div>
            <tr>
                <td>
                    <div style="height:235px; width:415px; top:5px; left:10px" onmousedown="clearUsersList();" id="loginFormPanel">
                        <table style="width:380px">
                            <tr class="formLabel">
                                <td><b>User Name:</b></td>
                                <!-- UserName -->
                                <td class="input">
                                    <input type="text" id="username2" name="username2" onblur="getUser(this.value);" size="30" tabindex="1" />

                                </td>
                            </tr>

                            <tr class="formLabel">
                                <td><b>First Name:</b></td>
                                <!-- FirstName -->
                                <td class="input">
                                    <input type="text" disabled="true" id="firstname2" name="firstname2" onfocus="clearUsersList();" size="20" />
                                </td>
                            </tr>

                            <tr class="formLabel">
                                <td><b>Last Name:</b></td>
                                <!-- LastName -->
                                <td class="input">
                                    <input type="text" disabled="true" id="lastname2" name="lastname2" onfocus="clearUsersList();" size="20" />
                                </td>
                            </tr>

                            <tr class="formLabel">
                                <td><b>Password:</b></td>
                                <!-- Password -->
                                <td class="input"><!--onkeypress="checkEnter(event)" -->
                                    <input type="password" id="password" onfocus="clearUsersList()" onkeypress="checkEnter(event)" name="password" size="20" maxlength=20  tabindex="2" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="footer_container" id="footer_container">
                        <div class="footer_left">
                            <div style="display:inline;text-align:center">
                                &#169;&nbsp;Copyright 2009&nbsp;<a href="" target="_blank">Immaculate High Tech Systems Ltd.</a>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
<script>
    writeFooter("footer_container");
</script>