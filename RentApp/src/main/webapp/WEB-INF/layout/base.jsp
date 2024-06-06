<%-- 
    Document   : base
    Created on : May 22, 2024, 3:45:30 PM
    Author     : Peter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>
            <tiles:insertAttribute name="title" />
        </title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.2/font/bootstrap-icons.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <style>
            body {
                font-family: Arial, sans-serif;
                overflow-y: hidden;
            }
            .sidebar {
                background-color: #343a40;
                position: fixed;
                top: 40px;
                bottom: 0;
                left: 0;
                width: 250px;
                padding-top: 20px;
                z-index: 1000;
            }
            .sidebar .nav-link {
                color: #ffffff;
                padding: 10px 20px;
            }
            .sidebar .nav-link:hover {
                background-color: #495057;
                text-decoration: none;
            }
            .sidebar .nav-link i {
                margin-right: 10px;
            }
            .content {
                margin-left: 250px;
                padding: 20px;
                overflow-y: auto;
                height: 100vh;
                margin-top: 40px
            }
        </style>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
        <div class="sidebar">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="#submenu1" data-bs-toggle="collapse">
                        <i class="bi bi-speedometer2"></i> Dashboard
                    </a>
                    <ul class="collapse" id="submenu1">
                        <li class="nav-item">
                            <a class="nav-link" href="/RentApp/stats">Thống kê</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="">Duyệt bài viết</a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#submenu2" data-bs-toggle="collapse">
                        <i class="bi bi-bootstrap"></i> Bài đăng
                    </a>
                    <ul class="collapse" id="submenu2">
                        <li class="nav-item">
                            <a class="nav-link" href="/RentApp/post/all">Danh sách bài đăng </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Item 2</a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/RentApp/user/all"/>
                        <i class="bi bi-people"></i> Users
                    </a>
                </li>
            </ul>
        </div>
        <div class="content">
            <section class="container py-4">
                <tiles:insertAttribute name="content" />
            </section>
        </div>
        <%--<tiles:insertAttribute name="footer" />--%>
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery (optional, if you need it for other purposes) -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
</html>
