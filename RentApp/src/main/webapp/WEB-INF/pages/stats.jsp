<%-- 
    Document   : stats
    Created on : Apr 24, 2024, 1:11:13 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center text-info mt-1">THỐNG KÊ DOANH THU</h1>

<div class="row">
    <div class="col-md-5 col-12">
        <table class="table">
            <tr>
                <th>Id</th>
                <th>Tên sản phẩm</th>
                <th>Doanh thu</th>
            </tr>
            <c:forEach items="${statsRevenue}" var="s">
                <tr>
                    <td>${s[0]}</td>
                    <td>${s[1]}</td>
                    <td>${String.format("%,d", s[2])} VNĐ</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-7 col-12">
        <canvas id="myChart1"></canvas>
    </div>
</div>

<div class="row">
    <div class="col-md-5 col-12">
        <div>
            <div class="alert alert-info">
                <h4>Năm: ${param.year}</h4>
                <h4>Khoảng thời gian: ${param.peroid}</h4>
            </div>
            <form>
                <div class="form-floating mb-3 mt-3">
                    <input type="number" class="form-control" id="year" placeholder="Năm" name="year">
                    <label for="year">Năm</label>
                </div>

                <div class="form-floating  mb-3 mt-3">
                    <select class="form-select" id="peroid" name="peroid">
                        <option value="MONTH">Tháng</option>
                        <option value="QUARTER">Qúy</option>
                    </select>
                    <label for="sel1" class="form-label">Khoảng thời gian:</label>
                </div>
                <div class="form-floating  mb-3 mt-3">
                    <button class="btn btn-info">Lọc</button>
                </div>
            </form>
        </div>
        <table class="table">
            <tr>

                <th>Thời gian</th>
                <th>Doanh thu</th>
            </tr>
            <c:forEach items="${statsRevenueByPeroid}" var="s">
                <tr>

                    <td>${s[0]}</td>
                    <td>${String.format("%,d", s[1])} VNĐ</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-7 col-12">
        <canvas id="myChart2"></canvas>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    let labels = [];
    let data = [];
    <c:forEach items="${statsRevenue}" var="s">
    labels.push('${s[1]}');
    data.push(${s[2]});
    </c:forEach>

    let label2 = [];
    let data2 = [];
    <c:forEach items="${statsRevenueByPeroid}" var="s">
    label2.push(${s[0]});
    data2.push(${s[1]});
    </c:forEach>
    function drawChart(ctx, labels, data) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                        label: '# Doanh thu',
                        data: data,
                        borderWidth: 1,
                        backgroundColor: ['red', 'green', 'blue', 'gold', 'brown']
                    }],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    window.onload = function () {
        let ctx1 = document.getElementById("myChart1");
        let ctx2 = document.getElementById("myChart2");

        drawChart(ctx1, labels, data);
        drawChart(ctx2, label2, data2);
    }
</script>