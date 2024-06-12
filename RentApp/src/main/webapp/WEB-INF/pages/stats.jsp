<%-- 
    Document   : stats
    Created on : Apr 24, 2024, 1:11:13 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<main role="main" class="col-md-12 ml-sm-auto col-lg-12 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Analytics Dashboard</h1>
        <div class="btn-toolbar mb-2 mb-md-0">
            <div class="btn-group mr-2">
                <button type="button" class="btn btn-sm btn-outline-secondary"><spring:message
                        code="admin.user.label"/></button>
                <button type="button" class="btn btn-sm btn-outline-secondary"><spring:message
                        code="admin.post.label"/></button>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3">
            <div class="card mb-4 shadow btn-outline-success">
                <div class="card-body">
                    <h5 class="card-title">Renter</h5>
                    <p class="card-text fw-bolder ">${usersByRole[2][1]}</p>
                    <p class="text-success">3.65% Since last week</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4 shadow btn-outline-success">
                <div class="card-body">
                    <h5 class="card-title">Landlord</h5>
                    <p class="card-text fw-bolder ">${usersByRole[1][1]}</p>
                    <p class="text-success">6.65% Since last week</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4 shadow btn-outline-success">
                <div class="card-body">
                    <h5 class="card-title">Published Post</h5>
                    <p class="card-text fw-bolder ">${post[0][1]}</p>
                    <p class="text-success">5.25% Since last week</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card mb-4 shadow btn-outline-success">
                <div class="card-body">
                    <h5 class="card-title">Uncensored Post</h5>
                    <c:choose>
                        <c:when test="${post[1] != null}">
                            <p class="card-text fw-bolder ">${post[1][1]}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="card-text fw-bolder ">0</p>
                        </c:otherwise>
                    </c:choose>
                    <p class="text-danger">-2.25% Since last week</p>
                </div>
            </div>
        </div>
    </div>
    <form class="row">
        <div class="form-floating mb-3 mt-3 col-3">
            <input type="text" value="${year}" class="form-control" id="year" placeholder="Năm" name="year">
            <label for="year">Năm</label>
        </div>
        <div class="form-floating mx-3 mt-3 col-3">
            <select class="form-select" id="period" name="period">
                <option value="MONTH" selected>Theo tháng</option>
                <c:choose>
                    <c:when test="${period=='QUARTER'}">
                        <option value="QUARTER" selected>Theo quý</option>
                    </c:when>
                    <c:otherwise>
                        <option value="QUARTER">Theo quý</option>
                    </c:otherwise>
                </c:choose>
            </select>
            <label for="period" class="form-label">Chọn thời gian:</label>
        </div>
        <div class="form-floating mb-3 mt-3 col-3">
            <button class="btn btn-success">Lọc</button>
        </div>
    </form>
    <div class="row">
        <div class="col-md-5 p-2 mx-3 my-3 shadow rounded">
            <canvas id="usersByPeriodChart"></canvas>
        </div>
        <div class="col-md-6 p-2 mx-3 my-3 shadow rounded">
            <canvas id="countUsersCreatedChart"></canvas>
        </div>
        <div class="col-md-5 p-2 mx-3 my-3 shadow rounded">
            <canvas id="usersByRoleChart"></canvas>
        </div>
    </div>
</main>
<script src="<c:url value="/js/stats.js"/>"></script>
<script>


    let labels = [];
    let data = [];
    <c:forEach items="${usersByRole}" var="p">
    labels.push('${p[0]}');
    data.push(${p[1]});
    </c:forEach>

    let label2 = [];
    let data2 = [];
    <c:forEach items="${usersByPeriod}" var="p">
    label2.push(${p[0]});
    data2.push(${p[1]});
    </c:forEach>

    let label3 = [];
    let data3 = [];
    <c:forEach items="${countUsersCreated}" var="p">
    label3.push(${p[0]});
    data3.push(${p[1]});
    </c:forEach>

    const usersByRoleCtx = document.getElementById('usersByRoleChart').getContext('2d');
    renderDoughNutChart(usersByRoleCtx, labels, data, 'Count', 'Users sorted by ROLE');

    const usersByPeriodCtx = document.getElementById('usersByPeriodChart').getContext('2d');
    renderBarChart(usersByPeriodCtx, label2, data2, `${period}`, `Created users by ${period} / ${year}`);

    const countUsersCreatedCtx = document.getElementById('countUsersCreatedChart').getContext('2d');
    renderLineChart(countUsersCreatedCtx, label3, data3, `Total at ${period}`, 'Total Users');
</script>