<%-- 
    Document   : showfeedback
    Created on : Nov 15, 2010, 1:20:40 PM
    Author     : Adewale Azeez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/xml; charset=ISO-8859-1">
        <title>eManifest Systems - Press CTRL + P to Print</title>

    </head>
    <body>
        <%
                    String url = request.getParameter("url");
                    String type = request.getParameter("type");
        %>
        <input type = "hidden" id="url" value = "<%=url%>" />
        <input type = "hidden" id="type" value = "<%=type%>" />
        <div id="reportarea" ></div>
    </body>
</html>
<script type="text/javascript" language="javascript">
    var req;
    var url;
    var type;

    function ajaxFunction(arg) {
        if (window.XMLHttpRequest) { // Non-IE browsers
            req = new XMLHttpRequest();
            req.onreadystatechange = processStateChange;
            try {
                req.open("GET", arg, true);
            } catch (e) {
                alert(e);
            }
            req.send(null);
        } else if (window.ActiveXObject)  { // IE Browsers
            req = new ActiveXObject("Microsoft.XMLHTTP");
            if (req) {
                req.onreadystatechange=processStateChange;
                req.open("GET", arg, true);
                req.send();
            }
        }
    }

    function processStateChange() {
        if (req.readyState==4){
            var resp = req.responseText;
            var break_resp = "";
            if(resp.match("getXMLObject")){
                break_resp = resp.split("getXMLObject");
                var table = "<table border='1' style='border-color:#fff;border-style:solid;border-width:1px; height:10px;width:96%;background-color:#6666FF;margin-top:5px;'>";

                if(type=="html"){
                    var token = "";
                    var tagName="";
                    var tagValue="";
                    var nameFlag = false;
                    var valueFlag = false;
                    var colorflag = 1;
                    table += "<tr style='font-weight:bold; color:white'><td>Tag Names</td><td>Tag Values</td></tr>";
                    for(var k=0;k<break_resp[1].length;k++){
                        token = break_resp[1].substring(k,k+1);
                    
                        if(break_resp[1].substring(k,k+2) =="</"){
                            nameFlag = false;
                            valueFlag = false;
                            if(tagName.length != 0 && tagValue.length != 0){
                                if(colorflag == 0){
                                    colorflag = 1;
                                    table += "<tr style='background-color:#CCCCCC;'><td>"+tagName+"</td><td>"+tagValue+"</td><tr>";
                                } else {
                                    colorflag = 0;
                                    table += "<tr style='background-color:#FFFFFF;'><td>"+tagName+"</td><td>"+tagValue+"</td><tr>";
                                }
                            }
                            tagName = ""; tagValue = "";
                            continue;
                        }
                        if(token=="<"){
                            nameFlag = true;
                            tagName = ""; tagValue = "";
                            continue;
                        }
                        if(token==">"){
                            nameFlag = false;
                            valueFlag = true;
                            tagValue = "";
                            continue;
                        }
                        if(nameFlag) tagName += token;
                        if(valueFlag) tagValue += token;

                    }
                    table += "</table>";
                    document.getElementById("reportarea").innerHTML = table;
                }else{
                    //alert(break_resp[1]);
                   document.getElementById("reportarea").innerText =break_resp[1];
                }
            }
        }
    }

    function openFeedback(){
        url = document.getElementById('url').value;
        type = document.getElementById('type').value;
        arg = "/emanifest/ManifestServlet?operation=getXMLObject"+"&table="+url;
        ajaxFunction(arg);
        return true;
    }
    openFeedback();
</script>