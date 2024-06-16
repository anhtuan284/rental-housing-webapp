<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">User List</h1>

    <form method="get" action="<c:url value='/user/all'/>" class="mb-4">
        <div class="form-row">
            <div class="col">
                <a class="btn btn-success" href="<c:url value='/user' />">Thêm người dùng</a>
            </div>
            <div class="col">
                <input type="text" class="form-control" name="kw" placeholder="Username" value="${param.kw}">
            </div>
            <div class="col">
                <button type="submit" class="btn btn-primary">Filter</button>
            </div>
        </div>
    </form>

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
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td><img src="${user.avatar}" alt="Avatar" class="img-thumbnail" style="width: 50px; height: 50px;"></td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.dateOfBirth}</td>
                <td>${user.roleId.name}</td>
                <td>${user.name}</td>
                <td>${user.cccd}</td>
                <td>${user.numberPhone}</td>
                <td>
                    <a href="${user.id}" class="btn btn-warning btn-sm">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="d-flex justify-content-between">
        <div>Page ${currentPage} of ${totalPages}</div>
        <nav>
            <ul class="pagination">
                <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                    <c:url var="prevUrl" value="/user/all">
                        <c:param name="page" value="${currentPage - 1}"/>
                        <c:forEach var="paramName" items="${param.keySet()}">
                            <c:if test="${paramName != 'page'}">
                                <c:param name="${paramName}" value="${param[paramName]}"/>
                            </c:if>
                        </c:forEach>
                    </c:url>
                    <a class="page-link" href="${currentPage > 1 ? prevUrl : '#'}" tabindex="-1" ${currentPage <= 1 ? 'aria-disabled="true"' : ''}>Previous</a>
                </li>
                <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                    <c:url var="nextUrl" value="/user/all">
                        <c:param name="page" value="${currentPage + 1}"/>
                        <c:forEach var="paramName" items="${param.keySet()}">
                            <c:if test="${paramName != 'page'}">
                                <c:param name="${paramName}" value="${param[paramName]}"/>
                            </c:if>
                        </c:forEach>
                    </c:url>
                    <a class="page-link" href="${currentPage < totalPages ? nextUrl : '#'}" ${currentPage >= totalPages ? 'aria-disabled="true"' : ''}>Next</a>
                </li>
            </ul>
        </nav>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
