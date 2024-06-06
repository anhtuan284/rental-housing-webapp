<%--
  Created by IntelliJ IDEA.
  User: atuan
  Date: 6/6/24
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<body>
<div class="container mt-5">
    <h1 class="mb-4">User List</h1>
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Avatar</th>
            <th>Username</th>
            <th>Email</th>
            <th>Date of Birth</th>
            <th>Role ID</th>
            <th>Name</th>
            <th>CCCD</th>
            <th>Phone Number</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td><img src="${user.avatar}" alt="Avatar" class="img-thumbnail" style="width: 50px; height: 50px;">
                </td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.dateOfBirth}</td>
                <td>${user.roleId.name}</td>
                <td>${user.name}</td>
                <td>${user.cccd}</td>
                <td>${user.numberPhone}</td>0
                <td>
                    <a href="${user.id}" class="btn btn-primary btn-sm">View</a>
                    <a href="${user.id}/edit" class="btn btn-warning btn-sm">Edit</a>
                    <a href="${user.id}/diable" class="btn btn-danger btn-sm">Disable</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
