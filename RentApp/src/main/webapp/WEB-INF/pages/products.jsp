<%-- 
    Document   : products
    Created on : Apr 10, 2024, 1:31:22 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-info mt-1">QUẢN TRỊ SẢN PHẨM</h1>

<c:url value="/products" var="action" />
<c:if test="${errMsg != null}">
    <div class="alert alert-danger">${errMsg}</div>
</c:if>
<form:form method="post" action="${action}" modelAttribute="product" enctype="multipart/form-data">
    <form:errors path="*" element="div" cssClass="alert alert-danger" />
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" path="name" id="name" placeholder="Tên sản phẩm" name="name" />
        <label for="name">Tên sản phẩm</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control" path="price" id="price" placeholder="Giá sản phẩm" name="price" />
        <label for="name">Giá sản phẩm</label>
    </div>
    <div class="form-floating">
        <form:select class="form-select" id="category" name="category" path="categoryId">
            <c:forEach items="${categories}" var="c">
                <c:choose>
                    <c:when test="${c.id == product.categoryId.id}">
                        <option value="${c.id}" selected>${c.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${c.id}">${c.name}</option>
                    </c:otherwise>
                </c:choose>
                
            </c:forEach>t
        </form:select>
        <label for="category" class="form-label">Select list (select one):</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="file" accept=".png,.jpg" class="form-control" path="file" id="file" name="file" />
        <label for="file">Ảnh sản phẩm</label>
        <c:if test="${product.image != null}">
            <img src="${product.image }" width="200" />
        </c:if>
    </div>
    <div class="form-floating mb-3 mt-3">
        <button type="submit" class="btn btn-info">Thêm sản phẩm</button>
        <form:hidden path="id" />
    </div>
</form:form>


