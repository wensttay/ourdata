<%-- 
    Document   : result.jsp
    Created on : 04/08/2016, 02:28:41
    Author     : kieckegard
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="br.ifpb.simba.ourdata.enums.PlaceType"%>
<%@page import="br.ifpb.simba.ourdata.entity.ResourceItemSearch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>OurData - Search</title>

        <link rel="stylesheet" type="text/css" href="ourdata.css">
        <link rel="stylesheet" href="css/skel.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/style-xlarge.css" />

    </head>
    <body class="landing background">
        <!-- Header -->
        <header id="header">
            <h1 class="tittle" >
                <a href="index.html">
                    <img src="images/OurData.png" alt="Logo" width="60" style="margin: 12px 20px 0 5px; float: left; top: 40px;">
                    OurData
                </a>
            </h1>

            <nav id="nav">
                <ul>
                    <li><a href="index.html#pac-input">Search</a></li>
                    <li><a href="generic.html">About</a></li>
                    <li><a href="index.html#footer">Contact</a></li>
                </ul>
            </nav>
        </header>


        <!-- Table -->
        <section id="main" class="main" style="width: 80%; margin: 0px auto; margin-top: 200px;">
            <c:if test="${requestScope.size > 0}">
                <h3 >We Found <span class="skyblue">${requestScope.size}</span> resources about "${requestScope.nameOfPlace}"!</h3>
            </c:if>
            <c:if test="${requestScope.size == 0}">
                <h3 > There's no resources about "${requestScope.nameOfPlace}"!</h3>
            </c:if>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Ranking</th>
                            <th>Title</th>
                            <th>Format</th>
                            <th>Download</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Test
                        <tr>    
                            <td>1</td>
                            <td>100%</td>
                            <td>We found something really intersting bout' Cajazeiras City!</td>
                            <td>CSV</td>  
                            <td><a class="button alt small" href="http://www.google.com.br" target="_blank" >Download <i class="fa fa-download" aria-hidden="true"></i></a></td>
                        </tr> -->
                        <%
                            List<ResourceItemSearch> pageResources = (ArrayList) request.getAttribute("resourseList");
                            if (pageResources == null)
                            {
                                pageResources = new ArrayList<>();
                            }
                            int count = 0;
                            for (ResourceItemSearch resource : pageResources)
                            {
                                count++;
                        %>
                        <tr>    
                            <td><%= count%></td>
                            <td><%= String.format("%.2f", resource.getRanking() * 100) + "%"%></td>
                            <td><%= resource.getResource().getDescricao()%></td>
                            <td><%=resource.getResource().getFormato()%></td>  
                            <td><a class="button alt small" href="<%=resource.getResource().getUrl()%>" target="_blank" >Download <i class="fa fa-download" aria-hidden="true"></i></a></td>
                        </tr>  
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- Footer -->
        <footer id="footer" style="margin-top: 50px;">
            <div class="container">
                <div class="row">

                    <section class="4u 12u(medium) 12u(small)">
                        <h3>Contact Us</h3>
                        <ul class="icons">
                            <li><a href="#" class="icon rounded fa-twitter"><span class="label">Twitter</span></a></li>
                            <li><a href="#" class="icon rounded fa-facebook"><span class="label">Facebook</span></a></li>
                            <li><a href="#" class="icon rounded fa-google-plus"><span class="label">Google+</span></a></li>
                            <li><a href="#" class="icon rounded fa-linkedin"><span class="label">LinkedIn</span></a></li>
                        </ul>
                        <ul class="tabular">
                            <li>
                                <h3>Address</h3>
                                1234 Somewhere Road<br>
                                Nashville, TN 00000
                            </li>
                            <li>
                                <h3>Mail</h3>
                                <a href="#">someone@untitled.com</a>
                            </li>
                            <li>
                                <h3>Phone</h3>
                                (99) 99999-9999
                            </li>
                        </ul>
                    </section>
                </div>
                <ul class="copyright">
                    <li>&copy; Untitled. All rights reserved.</li>
                </ul>
            </div>
        </footer>

    </body>
</html>
