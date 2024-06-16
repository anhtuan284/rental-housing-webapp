<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Detail</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4"><spring:message code="user.detail.title"/></h1>
    <c:url var="saveUrl" value="/user"/>
    <form:form method="post" action="${saveUrl}" modelAttribute="user" enctype="multipart/form-data">
        <form:errors path="*" element="div" cssClass="alert alert-danger"/>

        <div class="form-floating mb-3">
            <form:input path="username" class="form-control" id="username" placeholder="Username"/>
            <label for="username"><spring:message code="user.username.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="email" class="form-control" id="email" placeholder="Email"/>
            <label for="email"><spring:message code="user.email.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="password" class="form-control" id="email" placeholder="Password" type="password"/>
            <label for="password"><spring:message code="user.password.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="name" class="form-control" id="name" placeholder="Name"/>
            <label for="name"><spring:message code="user.name.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="dateOfBirth" class="form-control" id="dateOfBirth" placeholder="Date of Birth"/>
            <label for="dateOfBirth"><spring:message code="user.dateOfBirth.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="cccd" class="form-control" id="cccd" placeholder="CCCD"/>
            <label for="cccd"><spring:message code="user.cccd.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="numberPhone" class="form-control" id="numberPhone" placeholder="Number Phone"/>
            <label for="numberPhone"><spring:message code="user.numberPhone.label"/></label>
        </div>

        <div class="form-floating mb-3">
            <form:input path="address" class="form-control" id="address" placeholder="Address"/>
            <label for="address"><spring:message code="user.address.label"/></label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="file" class="form-control"  id="image" path="file" />
            <label for="image">Ảnh sản phẩm</label>

            <c:if test="${user.id > 0}">
                <img src="${user.avatar}" width="200" class="img-fluid" alt="avatar"/>
            </c:if>
        </div>

        <div class="form-floating">
            <form:select class="form-select" id="roleId"  path="roleId">
                <c:forEach items="${roles}" var="r">
                    <c:choose>
                        <c:when test="${r.id==user.roleId.id}">
                            <option value="${r.id}" selected>${r.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${r.id}">${r.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </form:select>
            <label for="categoryId" class="form-label">Role:</label>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary"><spring:message code="ui.button.save"/></button>
            <a href="<c:url value='/user/all'/>" class="btn btn-secondary"><spring:message code="ui.button.cancel"/></a>
            <a href="<c:url value='/user/${user.id}/disable'/>" class="btn btn-danger btn-sm">Disable</a>
        </div>

        <form:hidden path="id"/>
    </form:form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
