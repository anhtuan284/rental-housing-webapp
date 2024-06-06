<%--
  Created by IntelliJ IDEA.
  User: atuan
  Date: 6/6/24
  Time: 3:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body>
<div class="container mt-5">
    <h1 class="mb-4">User Detail</h1>
    <div class="card">
        <div class="card-header">
            <h3>${user.name}</h3>
        </div>
        <div class="card-body">
            <p><strong>ID:</strong> ${user.id}</p>
            <p><strong>Avatar:</strong> <img src="${user.avatar}" alt="Avatar" style="width:100px;height:100px;"></p>
            <p><strong>Username:</strong> ${user.username}</p>
            <p><strong>Email:</strong> ${user.email}</p>
            <p><strong>Name:</strong> ${user.name}</p>
            <p><strong>Date of Birth:</strong> ${user.dateOfBirth}</p>
            <p><strong>CCCD:</strong> ${user.cccd}</p>
            <p><strong>Number Phone:</strong> ${user.numberPhone}</p>
            <p><strong>Address:</strong> ${user.address}</p>
            <p><strong>Created Date:</strong> ${user.createdDate}</p>
            <p><strong>Updated Date:</strong> ${user.updatedDate}</p>
        </div>
        <div class="card-footer">
            <a href="/user/all" class="btn btn-secondary">Back to List</a>
            <a href="#" class="btn btn-warning">Edit</a>
            <a href="#" class="btn btn-danger">Delete</a>
        </div>
    </div>
</div>
<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
