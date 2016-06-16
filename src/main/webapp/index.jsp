<%@page import="java.util.ArrayList"%>
<%@page import="br.ifpb.simba.ourdata.entity.Resource"%>
<%@page import="java.util.List"%>
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

        <form action="SearchResources" method="GET">
            <select name="typeOfPlace">
                <%
                    for (PlaceType placeType : PlaceType.values()) {
                %>
                <option value="<%=placeType.getNameToBd()%>"><%=placeType.getNameToUser()%></option>
                <%
                    }
                %>
            </select>
            <input name="nameOfPlace" type="text" />
            <input type="submit" value="Pesquisar">
        </form>
            
            <table>
                <%
                    List<Resource> pageResources = (ArrayList)request.getAttribute("resourseList");
                    if(pageResources == null){
                        pageResources = new ArrayList<>();
                    }
                    for(Resource resource: pageResources){
                %>
                <tr>
                    <td><%=resource.getDescricao()%></td>
                    <td><%=resource.getFormato()%></td>  
                    <td><%=resource.getUrl()%></td>
                </tr>
                <%
                    }
                %>
            </table>



       
    </body>
</html>
