<%-- 
    Document   : index
    Created on : 04/08/2016, 02:20:06
    Author     : kieckegard
--%>

<%@page import="br.ifpb.simba.ourdata.enums.PlaceType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>OurData</title>

        <link rel="stylesheet" type="text/css" href="ourdata.css">
        <link rel="stylesheet" href="css/skel.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/style-xlarge.css" />

    </head>
    <body class="landing background">
        <!-- Header -->
        <header id="header">
            <h1 class="tittle" >
                <a href="index.jsp">
                    <img src="images/OurData.png" alt="Logo" width="40" height="40" style="margin: 5px 20px 0 5px;">
                    OurData
                </a>
            </h1>
            <nav id="nav">
                <ul>
                    <li><a href="index.html">Home</a></li>
                    <li><a href="generic.html">About</a></li>
                    <li><a href="elements.html">Contact</a></li>
                </ul>
            </nav>
        </header>

        <!-- Banner -->
        <section id="banner">
            <h2>Find everything with <span style="color: skyblue">OurData</span></h2>
            <form method="GET" action="SearchResources" >
                <div class="row uniform" style="width: 80%; margin: 0 auto;">
                    <div class="3u 12u(xsmall)">
                        <div class="select-wrapper">
                            <select class="border-skyblue dark-select" name="typeOfPlace" id="category">
                                <%
                                    for (PlaceType placeType : PlaceType.values()) {
                                %>
                                <option value="<%=placeType.getNameToBd()%>"><%=placeType.getNameToUser()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="9u 12u(xsmall)">
                        <input class="border-skyblue" type="text" name="nameOfPlace" placeholder="Place Name" required>
                    </div>
                </div>
                            
                <ul class="actions">
                    <li>
                        <button type="submit" class="button big">Search!</button>
                    </li>
                </ul>
                            
            </form>

            
        </section>
    </body>
</html>