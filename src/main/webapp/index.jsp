<%@page import="br.ifpb.simba.ourdata.entity.ResourceItemSearch"%>
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
        <script type="text/javascript" src="js/jquery-1.12.3.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
        <script type="text/javascript" src="js/script.js"></script>
    </head>
    <body>
    
    <div style="width: 800px; margin: 50px auto 0; ">

        <form action="SearchResources" method="GET" style="width: 100%; margin-bottom: 50px">
            <select style="float: left" name="typeOfPlace">
                <%
                    for (PlaceType placeType : PlaceType.values()) {
                %>
                <option value="<%=placeType.getNameToBd()%>"><%=placeType.getNameToUser()%></option>
                <%
                    }
                %>
            </select>
            <input style="float: right" type="submit" value="Pesquisar">
            <input style="float: right" name="nameOfPlace" type="text" placeholder="Search" />
            
        </form>
        <div class="panel panel-default">
            <div class="panel-heading"><p style="text-align: center; width: 100%; font-size: 20px">Resources encontrados</p></div>
            <table class="table table-hover" style="width: 100%" >
                <%
                    List<ResourceItemSearch> pageResources = (ArrayList) request.getAttribute("resourseList");
                    if (pageResources == null) {
                        pageResources = new ArrayList<>();
                    }
                    for (ResourceItemSearch resource : pageResources) {
                %>
                <tr>
                    <td><%="Ranking: " + resource.getRanking()*100+"%"%></td>
                    <td><%="Titulo: " + resource.getResource().getDescricao()%></td>
                    <td><%="Formato: " + resource.getResource().getFormato()%></td>  
                    <td><a href="<%=resource.getResource().getUrl()%>">Link Para Download</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <%
                if (pageResources.isEmpty()) {
            %>
            <div style="display: table; margin: 20px auto;"> <p>Nenhum Resultado Encontrado</p></div>
            <%
                }
            %>
        </div>
    </div>
    </body>
</html>
