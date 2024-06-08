<%--
  Created by IntelliJ IDEA.
  User: atuan
  Date: 6/6/24
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<body>
<div class="container mt-5">
    <h1 class="mb-4"><User List></h1>
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th><spring:message code="user.id.label"/></th>
            <th><spring:message code="user.avatar.label"/></th>
            <th><spring:message code="user.username.label"/></th>
            <th><spring:message code="user.email.label"/></th>
            <th><spring:message code="user.dateOfBirth.label"/></th>
            <th><spring:message code="user.roleId.label"/></th>
            <th><spring:message code="user.name.label"/></th>
            <th><spring:message code="user.cccd.label"/></th>
            <th><spring:message code="user.numberPhone.label"/></th>
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
