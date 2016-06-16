<%@page import="br.ifpb.simba.ourdata.enums.PlaceType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="style.css" type="text/css"/>
    </head>
    <body>

        <form action="" method="GET">
            <select>
                <%
                    for (PlaceType placeType : PlaceType.values()) {
                %>
                <option value="<%=placeType.getNameToBd()%>"><%=placeType.getNameToUser()%></option>
                <%
                    }
                %>
            </select>
            <input type="text" />
            <input type="button" value="Pesquisar">
        </form>
            
            <ul>
                <%
                    for()
                %>
                <li>
                    
                </li>
            </ul>



       
    </body>
</html>
