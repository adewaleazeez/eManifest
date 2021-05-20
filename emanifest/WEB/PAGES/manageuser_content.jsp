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
        <script type='text/javascript' src='js/login.js'></script>

        <script type="text/javascript">
            $(function() {
                // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
                $("#dialog").dialog("destroy");

                $("#showPrompt").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Alert!!!',
                    height: 300,
                    width: 350,
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
                    title: 'User Management Menu',
                    height: 258,
                    width: 350,
                    modal: false,
                    buttons: {
                        Close: function() {
                            $('#menuList').dialog('close');
                        }
                    }
                });

                $("#register").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Manage Users',
                    height: 500,
                    width: 800,
                    modal: false,
                    buttons: {
                        Save: function() {
                            var reg = registerForm('insertUser');
                            if(reg.length>0){
                                $('#showAlert').dialog('close');
                                document.getElementById("showError").innerHTML = reg;
                                $('#showError').dialog('open');
                            }
                        },
                        Update: function() {
                            var reg = registerForm('updateUser');
                            if(reg.length>0){
                                $('#showAlert').dialog('close');
                                document.getElementById("showError").innerHTML = reg;
                                $('#showError').dialog('open');
                            }
                        },
                        New: function() {
                            document.getElementById("username").disabled = false;
                            clearRegisterForm();
                        },
                        Close: function() {
                            $('#register').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

                $("#changepass").dialog({
                    autoOpen: false,
                    position:'center',
                    title: 'Change Users Password',
                    height: 450,
                    width: 600,
                    modal: false,
                    buttons: {
                        Change_Password: function() {
                            var reg = changePass();
                            if(reg.length>0){
                                $('#showAlert').dialog('close');
                                document.getElementById("showError").innerHTML = reg;
                                $('#showError').dialog('open');
                            }
                        },
                        Clear: function() {
                            clearPassForm();
                        },
                        Close: function() {
                            $('#changepass').dialog('close');
                            $('#menuList').dialog('open');
                        }
                    }
                });

            });
            
        </script>
    </head>
    <body>
        <div id="container_id" style="max-height:250px; overflow:auto;"></div>
        <div id="showError"></div>
        <div id="showPrompt"></div>
        <div id="showAlert"></div>
        <div id="menuList">
            <h5><a href="javascript: getRegister();">Manage Users</a></h5>
            <h5><a href="javascript: getPassword();">Change Users Password</a></h5>
        </div>

        <div id="register">
            <table style="width:380px">
                <tr class="formLabel">
                    <td><b>User Name:</b></td>
                    <!-- UserName -->
                    <td class="input">
                        <input type="text" id="username" name="username" size="30" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td><b>First Name:</b></td>
                    <!-- FirstName -->
                    <td class="input">
                        <input type="text" id="firstname" name="firstname" onblur="this.value=capAdd(this.value);" size="20" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td><b>Last Name:</b></td>
                    <!-- LastName -->
                    <td class="input">
                        <input type="text" id="lastname" name="lastname" onblur="this.value=capAdd(this.value);" size="20" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td><b>Active:</b></td>
                    <td class="input">
                        <select id="selectactive" name="selectactive" class="textField" >
                            <option>Yes</option>
                            <option>No</option>
                        </select>
                    </td>
                </tr>

            </table>
            <div id="userlist" style="max-height:250px; max-width:795px; overflow:auto;"></div>
        </div>

        <div id="changepass">
            <table style="width:550px;">
                <tr class="formLabel">
                    <td width="30%"><b>User Name:</b></td>
                    <!-- UserName -->
                    <td width="70%" class="input">
                        <input type="text" id="username2" name="username2" size="27" disabled="true" readonly class="textField" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td width="30%"><b>First Name:</b></td>
                    <!-- FirstName -->
                    <td width="70%" class="input">
                        <input type="text" id="firstname2" name="firstname2" disabled="true" readonly size="20" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td width="30%"><b>Last Name:</b></td>
                    <!-- LastName -->
                    <td width="70%" class="input">
                        <input type="text" id="lastname2" name="lastname2" disabled="true" readonly size="20" />
                    </td>
                </tr>

                <tr class="formLabel">
                    <td width="30%"><b>Old Password:</b></td>
                    <!-- Password -->
                    <td width="70%" class="input">
                        <input type="password" id="password" name="password" size="20" maxlength=20 class="textField" />
                    </td>
                </tr>
                <tr class="formLabel">
                    <td width="30%"><b>New Password:</b></td>
                    <!-- Password -->
                    <td width="70%" class="input">
                        <input type="password" id="newpassword" name="newpassword" size="20" maxlength=20 class="textField" />
                    </td>
                </tr>
                <tr class="formLabel">
                    <td width="30%"><b>Repeat New Password:</b></td>
                    <!-- Password -->
                    <td width="70%" class="input">
                        <input type="password" id="rptpassword" name="rptpassword" size="20" maxlength=20 onkeypress="checkEnter(event)" class="textField" />
                    </td>
                </tr>
            </table>
        </div>

    </body>
</html>
