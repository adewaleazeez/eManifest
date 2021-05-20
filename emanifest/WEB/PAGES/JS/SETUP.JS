/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var curr_obj = null;
var temp_table = "";
var temp_serialno = 0;
function addRec(operation, table, param){
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Processing your records.";
    $('#showAlert').dialog('open');
    var serialno = readCookie("serialno");
    if(serialno==null) serialno="";
    param += "&serialno="+serialno;
    var url = "/emanifest/SetupServlet?operation="+operation+"&table="+table+param;
    AjaxFunctionSetup(url);
}

function clearLists(){
    document.getElementById("codeslist").innerHTML = "";
}
function getAllRecords(arg2,arg3){
    curr_obj = document.getElementById(arg2);
    temp_table = arg3;
    var url = "/emanifest/ManifestServlet?operation=getAllRecords&table="+arg3;
    AjaxFunctionSetup(url);
    
}

function getRecords(table,serialno){
    if(serialno == null || serialno.length == 0) serialno = "1";
    $('#menuList').dialog('close');
    if(table=='ports'){
        $('#ports').dialog('open');
        curr_obj = document.getElementById(serialno);
        if(curr_obj.value==""){
                document.getElementById("showPrompt").innerHTML = "<b>Alert...</b><br><br>Please select a port code.";
                $('#showPrompt').dialog('open');
                return true;
            }
            serialno= document.getElementById("loadingport").value;
    }
    if(table=='carriers') $('#carriers').dialog('open');
    if(table=='transports') $('#transports').dialog('open');
    if(table=='nations') $('#nations').dialog('open');
    if(table=='containers') $('#containers').dialog('open');
    if(table=='packages') $('#packages').dialog('open');
    if(table=='bols') $('#bols').dialog('open');
    if(table=='boltypes') $('#boltypes').dialog('open');
    if(table=='customs') $('#customs').dialog('open');
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Fetching your records.";
    $('#showAlert').dialog('open');

    temp_serialno = serialno;
    temp_table = table;
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Fetching your data.";
    $('#showAlert').dialog('open');
    var url = "/emanifest/SetupServlet?operation=getAllRecs"+"&table="+table+"&serialno="+serialno;
    AjaxFunctionSetup(url);
    
}

function populateRecords(serialno, table){
    document.getElementById("showAlert").innerHTML = "<b>Please wait...</b><br><br>Fetching your data.";
    $('#showAlert').dialog('open');
    var url = "/emanifest/SetupServlet?operation=getARecord"+"&table="+table+"&serialno="+serialno;
    AjaxFunctionSetup(url);
}

function populateCode(code){
    curr_obj.value = code;
    clearLists();
}

function resetForm(arg){
    document.getElementById("portdescription").value="";
    document.getElementById("portcode").value="";
    document.getElementById("carriercode").value="";
    document.getElementById("carriername").value="";
    document.getElementById("carrieraddress").value="";
    document.getElementById("transportdescription").value="";
    document.getElementById("transportcode").value="";
    document.getElementById("nationdescription").value="";
    document.getElementById("nationcode").value="";
    document.getElementById("containerdescription").value="";
    document.getElementById("containercode").value="";
    document.getElementById("packagedescription").value="";
    document.getElementById("packagecode").value="";
    document.getElementById("boldescription").value="";
    document.getElementById("bolcode").value="";
    document.getElementById("customdescription").value="";
    document.getElementById("customcode").value="";
    if(arg == "xml") return true;
    if(arg=="ports"){
        getRecords(arg,"loadingport");
    }else{
        getRecords(arg,"1");
    }
    
}

var xmlhttp

function AjaxFunctionSetup(arg){
    xmlhttp=GetXmlHttpObject();
    if(xmlhttp == null){
        alert ("Your browser does not support XMLHTTP!");
        return true;
    }

    var timestamp = new Date().getTime();
    var url = arg+"&timestamp="+timestamp;

    xmlhttp.onreadystatechange=stateChangedSetup;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
    
}

var temp_resp="";
function stateChangedSetup(){
    if (xmlhttp.readyState==4){
        var resp = xmlhttp.responseText;
        var break_resp = "";
        $('#showAlert').dialog('close');
        if(resp.match("getAllRecs")){
            if(resp == temp_table + "getAllRecs"){
                temp_resp="";
                return true;
            }
            resp = temp_resp + resp;
            break_resp = resp.split("getAllRecs");
            var allrecords = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:550px;background-color:#6666FF;margin-top:5px;'>";
            allrecords += "<tr style='font-weight:bold; color:white'>";
            if(break_resp[2]=="carriers"){
                allrecords += "<td width='5%'>S/No</td><td width='10%'>Codes</td><td width='85%'>Names</td></tr>";
            }else{
                allrecords += "<td width='5%'>S/No</td><td width='10%'>Codes</td><td width='85%'>Descriptions</td></tr>";
            }

            var recordlist = null;
            if(break_resp[0]=="bols") recordlist = document.getElementById('bollist');
            if(break_resp[0]=="boltypes") recordlist = document.getElementById('boltypelist');
            if(break_resp[0]=="carriers") recordlist = document.getElementById('carrierlist');
            if(break_resp[0]=="customs") recordlist = document.getElementById('customlist');
            if(break_resp[0]=="nations") recordlist = document.getElementById('nationlist');
            if(break_resp[0]=="containers") recordlist = document.getElementById('containerlist');
            if(break_resp[0]=="packages") recordlist = document.getElementById('packagelist');
            if(break_resp[0]=="ports") recordlist = document.getElementById('portlist');
            if(break_resp[0]=="transports") recordlist = document.getElementById('transportlist');
            var counter = 0;
            var rsp = "";
            var flg = 0;
            var break_row = "";
            for(var i=1; i < (break_resp.length-1); i++){
                if(temp_table == break_resp[i].trim()) {
                    continue;
                }
                break_row = break_resp[i].split("_~_");
                if (flg == 1) {
                    flg = 0;
                    rsp += "<tr style='font-weight:bold; color:#999999;background-color:#FFFFFF;'>";
                    rsp += "<td width='5%' align='right'>" + (++counter) + ".</td>";
                    rsp += "<td width='10%'><a href=javascript:populateRecords('" + break_row[0] + "','" + break_resp[0] + "')>" + break_row[1] + "</a></td>";
                    rsp += "<td width='85%'>" + break_row[2] + "</td></tr>";
                } else {
                    flg = 1;
                    rsp += "<tr style='font-weight:bold; color:#FFFFFF;background-color:#999999;'>";
                    rsp += "<td width='5%' align='right'>" + (++counter) + ".</td>";
                    rsp += "<td width='10%'><a href=javascript:populateRecords('" + break_row[0] + "','" + break_resp[0] + "')>" + break_row[1] + "</a></td>";
                    rsp += "<td width='85%'>" + break_row[2] + "</td></tr>";
                }
            }
            
            recordlist.innerHTML = allrecords+rsp+"</table>";
            temp_resp = resp;
            if(temp_table == "ports"){
                temp_resp="";
                return true;
            }else{
                temp_serialno = parseInt(temp_serialno) + 100;
            }
            var url = "/emanifest/SetupServlet?operation=getAllRecs"+"&table="+temp_table+"&serialno="+temp_serialno;
            AjaxFunctionSetup(url);
        }

        if(resp.match("getAllRecords")){
            var keyword = curr_obj.value;
            var allCodes = resp.split("getAllRecords");
            var inner_codeslist = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:100%;background-color:#6666FF;margin-top:5px;'>";
            if(navigator.appName == "Microsoft Internet Explorer"){
                inner_codeslist = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:30%;background-color:#6666FF;margin-top:5px;'>";
            }else{
                inner_codeslist = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:100%;background-color:#6666FF;margin-top:5px;'>";
            }
            inner_codeslist += "<tr style='font-weight:bold; color:white'>";
            inner_codeslist += "<td>S/No</td><td>Codes</td><td>Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td align='right'><a title='Close' style='font-weight:bold; font-size:20px; color:white;background-color:red;' href=javascript:clearLists()>X</a></td></tr>";

            var codeslist = document.getElementById('codeslist');
            codeslist.style.zIndex = 100;
            codeslist.style.position = "absolute";

            //codeslist.style.top = (findPosY(curr_obj) - 120 + curr_obj.clientHeight) + 'px';
            //codeslist.style.left = (findPosX(curr_obj) - 210) + 'px';
            //codeslist.style.top = ($(curr_obj).position().top + 23) + 'px';
            //codeslist.style.left = ($(curr_obj).position().left) + 'px';
            if(navigator.appName=="Microsoft Internet Explorer"){
                //var height = $(window).height()/2;
                //var width = $(document).width()/2;
                codeslist.style.top = '200px';
                codeslist.style.left ='200px';
                codeslist.style.top = (findPosY(curr_obj) - 52 + curr_obj.clientHeight) + 'px';
                codeslist.style.left = (findPosX(curr_obj) - 340) + 'px';
            }else{
                codeslist.style.top = ($(curr_obj).position().top + 23) + 'px';
                codeslist.style.left = ($(curr_obj).position().left) + 'px';
            }

            var token = "";
            var colorflag = 0;
            var k=0;
            if(keyword.trim().length==0){
                for(k=0; k<allCodes.length; k++){
                    if(allCodes[k].trim().length>0){
                        token = allCodes[k].split("_~_");
                        if(colorflag == 0){
                            colorflag = 1;
                            inner_codeslist += "<tr style='background-color:#CCCCCC;'><td align='right'>"+token[0]+".</td><td><a href=javascript:populateCode('"+token[1]+"')>"+token[1]+"</a></td><td>"+token[2]+"</td><td>&nbsp;</td></tr>";
                        } else {
                            colorflag = 0;
                            inner_codeslist += "<tr style='background-color:#FFFFFF;color:#CCCCCC'><td align='right'>"+token[0]+".</td><td><a href=javascript:populateCode('"+token[1]+"')>"+token[1]+"</a></td><td>"+token[2]+"</td><td>&nbsp;</td></tr>";
                        }
                    }
                }
            } else {
                for(k=0; k<allCodes.length; k++){
                    if(allCodes[k].trim().length>0 && (allCodes[k].toUpperCase().match(keyword.toUpperCase()))){
                        token = allCodes[k].split("_~_");
                        if(colorflag == 0){
                            colorflag = 1;
                            inner_codeslist += "<tr style='background-color:#CCCCCC;'><td align='right'>"+token[0]+".</td><td><a href=javascript:populateCode('"+token[1]+"')>"+token[1]+"</a></td><td>"+token[2]+"</td><td>&nbsp;</td></tr>";
                        } else {
                            colorflag = 0;
                            inner_codeslist += "<tr style='background-color:#FFFFFF;color:#CCCCCC'><td align='right'>"+token[0]+".</td><td><a href=javascript:populateCode('"+token[1]+"')>"+token[1]+"</a></td><td>"+token[2]+"</td><td>&nbsp;</td></tr>";
                        }
                    }
                }
            }
            inner_codeslist += "</table>";
            codeslist.style.zIndex = 100;
            codeslist.innerHTML = "";
            codeslist.innerHTML = inner_codeslist;
            return true;
        }

        if(resp.match("getARecord")){
            break_resp = resp.split("getARecord");
            createCookie("serialno",break_resp[1],false)
            if(break_resp[0]=="xml"){
                document.getElementById("manifest").value = break_resp[2];
                document.getElementById("bol").value = break_resp[3];
                document.getElementById("reg").value = break_resp[4];
                document.getElementById("batch").value = break_resp[5];
            }
            if(break_resp[0]=="bols"){
                document.getElementById("bolcode").value = break_resp[2];
                document.getElementById("boldescription").value = break_resp[3];
            }
            if(break_resp[0]=="boltypes"){
                document.getElementById("boltypecode").value = break_resp[2];
                document.getElementById("boltypedescription").value = break_resp[3];
            }
            if(break_resp[0]=="carriers"){
                document.getElementById("carriercode").value = break_resp[2];
                document.getElementById("carriername").value = break_resp[3];
                document.getElementById("carrieraddress").value = break_resp[4];
            }
            if(break_resp[0]=="customs"){
                document.getElementById("customcode").value = break_resp[2];
                document.getElementById("customdescription").value = break_resp[3];
            }
            if(break_resp[0]=="nations"){
                document.getElementById("nationcode").value = break_resp[2];
                document.getElementById("nationdescription").value = break_resp[3];
            }
            if(break_resp[0]=="containers"){
                document.getElementById("containercode").value = break_resp[2];
                document.getElementById("containerdescription").value = break_resp[3];
            }
            if(break_resp[0]=="packages"){
                document.getElementById("packagecode").value = break_resp[2];
                document.getElementById("packagedescription").value = break_resp[3];
            }
            if(break_resp[0]=="ports"){
                document.getElementById("portcode").value = break_resp[2];
                document.getElementById("portdescription").value = break_resp[3];
            }
            if(break_resp[0]=="transports"){
                document.getElementById("transportcode").value = break_resp[2];
                document.getElementById("transportdescription").value = break_resp[3];
            }
            return true;
        }

        if(resp.match("added")){
            document.getElementById("showPrompt").innerHTML = "<b>Record Added!!!</b><br><br>Your record was successfully added.";
            $('#showError').dialog('open');
            $('#showPrompt').dialog('open');
            $('#showError').dialog('close');
            break_resp = resp.split("added");
            resetForm(break_resp[0]+"s");
            return true;
        }

        if(resp.match("updated")){
            document.getElementById("showPrompt").innerHTML = "<b>Record Updated!!!</b><br><br>Your record was successfully updated.";
            $('#showError').dialog('open');
            $('#showPrompt').dialog('open');
            $('#showError').dialog('close');
            break_resp = resp.split("updated");
            resetForm(break_resp[0]+"s");
            return true;
        }

        if(resp.match("deleted")){
            document.getElementById("showPrompt").innerHTML = "<b>Record Deleted!!!</b><br><br>Your record was successfully deleted.";
            $('#showError').dialog('open');
            $('#showPrompt').dialog('open');
            $('#showError').dialog('close');
            break_resp = resp.split("deleted");
            resetForm(break_resp[0]+"s");
            return true;
        }

        if(resp.match("bolexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Bill of Lading code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("carrierexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Carrier code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("customexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Customs office code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("nationexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Nationality of vessel code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("containerexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Container code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("packageexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Pacckage code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("portexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Port code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("transportexists")){
            document.getElementById("showError").innerHTML = "<b>Code Exists!!!</b><br><br>Mode of transportation code already exists.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("bolnotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Bill of Lading code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("carriernotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Carrier code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("customnotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Customs office code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("nationnotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Nationality of vessel code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("containernotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Container code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("packagenotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Pacckage code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("portnotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Port code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

        if(resp.match("transportnotexist")){
            document.getElementById("showError").innerHTML = "<b>Code Not Existing!!!</b><br><br>Mode of transportation code does not exist.";
            $('#showError').dialog('open');
            return true;
        }

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
