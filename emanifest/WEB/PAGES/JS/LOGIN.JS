/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function getUsers(arg,obj){
    createCookie("obj",obj,false);
    var url = "/emanifest/UserServlet?option=getAllUsers"+"&active="+arg;
    AjaxFunctionLogin(url);
}

function checkLicense(){
    var d = new Date();
    var date_split = ((d.getMonth()+1)+"/"+d.getDate()+"/"+d.getFullYear()).split('/');
    var day = ((date_split[1].length < 2) ? "0" : "") + date_split[1];
    var mon = ((date_split[0].length < 2) ? "0" : "") + date_split[0];
    var year = date_split[2];
    var today = year+mon+day;
    var url = "/emanifest/UserServlet?option=getLicense&url="+today;
    AjaxFunctionLogin(url);
}

function registerForm(option){
    document.getElementById("showAlert").innerHTML = "<br><br><b>Please wait...</b><br><br>processing your record.";
    $('#showAlert').dialog('open');
    var username = document.getElementById("username").value;
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var active = document.getElementById("selectactive").value;
    var error = "";
    if (username.length == 0) error += "User Name must not be blank<br><br>";
    if (firstname.length == 0) error += "First Name must not be blank<br><br>";
    if (lastname.length == 0) error += "Last Name must not be blank<br><br>";
    if(error.length >0) {
        error = "<br><b>Please correct the following errors:</b> <br><br><br>" + error;
        return error;
    }
    var arg = "&firstName="+firstname+"&lastName="+lastname+"&userName="+username+"&active="+active;
    var url = "/emanifest/UserServlet?option="+option+arg;
    AjaxFunctionLogin(url);
    
}

function clearPassForm(){
    document.getElementById("username2").value = "";
    document.getElementById("firstname2").value = "";
    document.getElementById("lastname2").value = "";
    document.getElementById("oldpassword").value = "";
    document.getElementById("newpassword").value = "";
    document.getElementById("rptpassword").value = "";
}

function getRegister(){
    var theuser = readCookie("currentuser");
    if(theuser != "Administrator"){
        document.getElementById("showPrompt").innerHTML = "<br><br><b>Invalid User...</b><br><br>Only Administrator can manage users.";
        $('#showPrompt').dialog('open');
        return true;
    }
    $('#register').dialog('open');
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Fetching your records.";
    $('#showAlert').dialog('open');

    var url = "/emanifest/UserServlet?option=getAllUsers";
    AjaxFunctionLogin(url);
    
}
function getPassword(){
    document.getElementById("password").value = "";
    document.getElementById("newpassword").value = "";
    document.getElementById("rptpassword").value = "";
    $('#changepass').dialog('open');
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Fetching your records.";
    $('#showAlert').dialog('open');

    var username = readCookie("currentuser");
    var url = "/emanifest/UserServlet?option=getUser&userName="+username;
    AjaxFunctionLogin(url);
    
}

function changePass(){
    document.getElementById("showAlert").innerHTML = "<br><br><b>Please wait...</b><br><br>processing your record.";
    $('#showAlert').dialog('open');
    var username2 = document.getElementById("username2").value;
    var password = document.getElementById("password").value;
    var newpassword = document.getElementById("newpassword").value;
    var rptpassword = document.getElementById("rptpassword").value;
    var error = "";
    if (password.length == 0) error += "Old Password must not be blank<br><br>";
    if (newpassword.length < 6) error += "New Password must be greater than 5 characters<br><br>";
    if (rptpassword != newpassword) error += "New Password does not match Repeat New Password<br><br>";
    if(error.length > 0) {
        error = "<br><b>Please correct the following errors:</b> <br><br><br>" + error;
        //alert("Please correct the following: \n\n" + error);
        return error;
    }
    var arg = "userName="+username2+"&userPassword="+password+"_~_"+newpassword;
    var url = "/emanifest/UserServlet?option=changePass&"+arg;
    AjaxFunctionLogin(url);
    
}

function loginForm(){
    document.getElementById("showAlert").innerHTML = "<br><br><b>Please wait...</b><br><br>Checking your login.";
    $('#showAlert').dialog('open');
    var username2 = document.getElementById("username2").value;
    var password = document.getElementById("password").value;
    var error = "";
    if (username2.length == 0) error += "User Name must not be blank<br><br>";
    if (password.length < 6) error += "Password must be greater than 5 characters<br><br>";
    if(error.length >0) {
        error = "<br><b>Please correct the following errors:</b> <br><br><br>" + error;
        return error;
    }
    var arg = "userName="+username2+"&userPassword="+password;
    var url = "/emanifest/UserServlet?option=loginUser&"+arg;
    AjaxFunctionLogin(url);
    
}

function getUser(username){
    var error = "";
    if (username.length == 0) error += "Username must not be blank<br><br>";
    if(error.length >0) {
    //    error = "<br><b>Please correct the following errors:</b> <br><br><br>" + error;
        //$('#showAlert').dialog('close');
        //document.getElementById("showError").innerHTML = error;
        //$('#showError').dialog('open');
        return true;
    }
    document.getElementById("showAlert").innerHTML = "<br><br><b>Please wait...</b><br><br>Authenticating your username.";
    $('#showAlert').dialog('open');
    var url = "/emanifest/UserServlet?option=getUser&userName="+username;
    AjaxFunctionLogin(url);
    
}

function populateUsers(a,b,c,d,e){
    document.getElementById(readCookie("obj")).value=a;
    if(readCookie("obj")=="email"){
        document.getElementById("firstname").value=b;
        document.getElementById("lastname").value=c;
    }
    //clearUsersList();
}

function populateRecords(a,b,c,d){
    document.getElementById("username").value=a;
    document.getElementById("firstname").value=b;
    document.getElementById("lastname").value=c;
    document.getElementsByName("selectactive").value=d;
    document.getElementById("username").disabled = true;
    //clearUsersList();
}

function clearUsersList(){
    document.getElementById("userlist").innerHTML = "";
}

function clearLoginForm(){
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    //clearUsersList();
}

function clearRegisterForm(){
    document.getElementById("firstname").value = "";
    document.getElementById("lastname").value = "";
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    document.getElementById("repeatpassword").value = "";
    //clearUsersList();
}

var xmlhttp

function AjaxFunctionLogin(arg){

    xmlhttp=GetXmlHttpObject();
    if(xmlhttp == null){
        alert ("Your browser does not support XMLHTTP!");
        return true;
    }

    var timestamp = new Date().getTime();
    var url = window.location+"";
    var break_url = url.split("/emanifest");
    url = break_url[0] + arg+"&timestamp="+timestamp+"&url="+break_url[0];

    xmlhttp.onreadystatechange=stateChangedLogin;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
    
}

function stateChangedLogin(){
    if (xmlhttp.readyState==4){
        var resp = xmlhttp.responseText;
        var break_resp = "";
        $('#showAlert').dialog('close');
        if(resp.match("getAllUsers")){
            if(resp == "getAllUsers"){
                return true;
            }
            break_resp = resp.split("getAllUsers");
            var allrecords = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;wi0dth:550px;background-color:#6666FF;margin-top:5px;'>";
            allrecords += "<tr style='font-weight:bold; color:white'>";
            allrecords += "<td width='5%'>S/No</td><td width='20%'>Username</td><td width='20%'>Firstname</td><td width='20%'>Lastname</td><td width='10%'>Active</td></tr>";

            var recordlist = document.getElementById('userlist');
            var counter = 0;
            var rsp = "";
            var flg = 0;
            var break_row = "";
            for(var i=0; i < (break_resp.length-1); i++){
                break_row = break_resp[i].split("~_~");
                if (flg == 1) {
                    flg = 0;
                    rsp += "<tr style='font-weight:bold; color:#999999;background-color:#FFFFFF;'>";
                    rsp += "<td width='5%' align='right'>" + (++counter) + ".</td>";
                    rsp += "<td width='20%'><a href=javascript:populateRecords('" + break_row[0] + "','" + break_row[1] + "','" + break_row[2] + "','" + break_row[3] + "')>" + break_row[0] + "</a></td>";
                    rsp += "<td width='20%'>" + break_row[1] + "</td>";
                    rsp += "<td width='20%'>" + break_row[2] + "</td>";
                    rsp += "<td width='20%'>" + break_row[3] + "</td></tr>";
                } else {
                    flg = 1;
                    rsp += "<tr style='font-weight:bold; color:#FFFFFF;background-color:#999999;'>";
                    rsp += "<td width='5%' align='right'>" + (++counter) + ".</td>";
                    rsp += "<td width='20%'><a href=javascript:populateRecords('" + break_row[0] + "','" + break_row[1] + "','" + break_row[2] + "','" + break_row[3] + "')>" + break_row[0] + "</a></td>";
                    rsp += "<td width='20%'>" + break_row[1] + "</td>";
                    rsp += "<td width='20%'>" + break_row[2] + "</td>";
                    rsp += "<td width='20%'>" + break_row[3] + "</td></tr>";
                }
            }
            recordlist.innerHTML = allrecords+rsp+"</table>";
        }

        if(resp.match("getUser")){
            break_resp = resp.split("getUser");
            document.getElementById("username2").value = break_resp[1];
            document.getElementById("firstname2").value = break_resp[2];
            document.getElementById("lastname2").value = break_resp[3];
            $('#showPrompt').dialog('close');
            document.getElementById("password").focus();
            return true;
        }

        if(resp.match("inserted") || resp.match("recordexists")){
            $('#showPrompt').dialog('close');
            if(resp=="inserted"){
                getRegister();
                resp = "<br><b>Congratulations!!!</b><br><br>The user has been created";
                resp += "<br><br><br><br>Thank you.";
                document.getElementById("showPrompt").innerHTML = resp;
                $('#showPrompt').dialog('open');
                document.getElementById("firstname").value="";
                document.getElementById("lastname").value="";
                document.getElementById("username").value="";
                document.getElementById("username").disabled = false;
                document.getElementById("password").value="";
                document.getElementById("repeatpassword").value="";
            }else{
                resp = "<br><b>Too bad!!!</b><br><br>The username you typed already exists";
                resp += "<br><br>Please type another username and try again.";
                resp += "<br><br><br><br>Thank you.";
                document.getElementById("showPrompt").innerHTML = resp;
                $('#showPrompt').dialog('open');
            }
        }

        if(resp.match("updated") || resp.match("recordnotexist")){
            $('#showPrompt').dialog('close');
            if(resp=="updated"){
                getRegister();
                resp = "<br><b>Congratulations!!!</b><br><br>The user has been  updated";
                resp += "<br><br><br><br>Thank you.";
                document.getElementById("showPrompt").innerHTML = resp;
                $('#showPrompt').dialog('open');
                document.getElementById("firstname").value="";
                document.getElementById("lastname").value="";
                document.getElementById("username").value="";
                document.getElementById("username").disabled = false;
                document.getElementById("password").value="";
                document.getElementById("repeatpassword").value="";
                
            }else{
                resp = "<br><b>Too bad!!!</b><br><br>The username you typed does exist";
                resp += "<br><br>Please select another username and try again.";
                resp += "<br><br><br><br>Thank you.";
                document.getElementById("showPrompt").innerHTML = resp;
                $('#showPrompt').dialog('open');
            }
        }

        if(resp == "invalidlogin"){
            $('#showPrompt').dialog('close');
            resp = "<br><b>Invalid Username or Password!!!</b><br><br>The Username or Password you typed is invalid";
            resp += "<br><br>Please type another username and/or password and try again.";
            resp += "<br><br><br><br>Thank you.";
            document.getElementById("showPrompt").innerHTML = resp;
            $('#showPrompt').dialog('open');
            return true;
        }

        if(resp.match("validlogin") && !resp.match("invalidlogin")){
            $('#showPrompt').dialog('close');
            break_resp = resp.split("validlogin");
            if(!break_resp[1].match('Yes')){
                resp = "<br><b>Inactive User!!!</b><br><br>The username used is not acive again.";
                resp += "<br><br>Please contact the system administrator and try to login again.";
                resp += "<br><br><br><br>Thank you.";
                document.getElementById("showPrompt").innerHTML = resp;
                $('#showPrompt').dialog('open');
                return true;
            }
            createCookie("currentuser",document.getElementById("username2").value,false);
            window.location = "home.jsp";
            return true;
        }

        if(resp == "invalidusername"){
            $('#showPrompt').dialog('close');
            resp = "<br><b>Invalid username!!!</b><br><br>The Username you typed is invalid";
            resp += "<br><br>Please type another username and try again.";
            resp += "<br><br><br><br>Thank you.";
            document.getElementById("showPrompt").innerHTML = resp;
            $('#showPrompt').dialog('open');
            document.getElementById("firstname2").value="";
            document.getElementById("lastname2").value="";
            document.getElementById("username2").value="";
            document.getElementById("password").value="";
            return true;
        }

        if(resp == "invalidpassword"){
            $('#showPrompt').dialog('close');
            resp = "<br><b>Invalid Password!!!</b><br><br>The old Password you typed is invalid";
            resp += "<br><br>Please type another password and try again.";
            resp += "<br><br><br><br>Thank you.";
            document.getElementById("showPrompt").innerHTML = resp;
            $('#showPrompt').dialog('open');
            return true;
        }

        if(resp == "passchanged"){
            $('#showPrompt').dialog('close');
            resp = "<br><b>Successful Password Change!!!</b><br><br>You have successfully changed your password";
            resp += "<br><br>Please click ok to continue.";
            resp += "<br><br><br><br>Thank you.";
            document.getElementById("showPrompt").innerHTML = resp;
            $('#showPrompt').dialog('open');
            $('#changepass').dialog('close');
            $('#menuList').dialog('open');
            return true;
        }

        /*if(resp == "licensexpired"){
            alert("Your software license has expired!!! \n\nPlease contact the software administrator for renewal")
            createCookie("licenseexpire","Yes",false);
            window.close();
        }*/
    }
    return true;
}

function GetXmlHttpObject(){
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }

    if (window.ActiveXObject){
        // code for IE6, IE5
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}
