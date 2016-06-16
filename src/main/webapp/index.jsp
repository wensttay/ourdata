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
    <body style="width: 800px; margin: 100px auto 0; ">

        <form style="width: 100%; margin-bottom: 20px" action="SearchResources" method="GET">
            <select style="width: 100px; height: 26px;" name="typeOfPlace">
                <%
                    for (PlaceType placeType : PlaceType.values()) {
                %>
                <option value="<%=placeType.getNameToBd()%>"><%=placeType.getNameToUser()%></option>
                <%
                    }
                %>
            </select>
            <input style="width: 10%; height: 26px; float: right;" type="submit" value="Pesquisar">
            <input style="width: 70%; height: 20px !important; float: right;" name="nameOfPlace" type="text" />
        </form>
            
            <table width="800px;">
                <%
                    List<Resource> pageResources = (ArrayList)request.getAttribute("resourseList");
                    if(pageResources == null){
                        pageResources = new ArrayList<>();
                    }
                    for(Resource resource: pageResources){
                %>
                <tr style="border: 2px solid black;">
                    <td style="margin: 5px; border: 2px solid black; padding: 5px;"><%="Titulo: " + resource.getDescricao()%></td>
                    <td style="margin: 5px; border: 2px solid black; padding: 5px;"><%="Formato: " + resource.getFormato()%></td>  
                    <td style="margin: 5px; border: 2px solid black; padding: 5px;"><a href="<%=resource.getUrl()%>">Link Para Download</a></td>
                </tr>
                <%
                    }
                %>
            </table>



       
    </body>
</html>
