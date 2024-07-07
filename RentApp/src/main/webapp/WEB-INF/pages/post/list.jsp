<%-- 
    Document   : list
    Created on : Jun 3, 2024, 3:10:03 PM
    Author     : atuan
--%>

<%-- 
    Document   : list
    Created on : Jun 3, 2024, 3:10:03 PM
    Author     : atuan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bài viết</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <style>
            .post-container {
                margin-bottom: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
                overflow: hidden;
                transition: transform 0.3s ease-in-out;
            }
            .post-container:hover {
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            }
            .post-body {
                padding: 15px;
                background-color: #fff;
            }
            .user-info {
                display: flex;
                align-items: center;
                margin-bottom: 0.5rem;
            }
            .user-avatar {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                margin-right: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .user-name {
                font-weight: bold;
                font-size: 1rem;
                color: #343a40;
            }
            .post-details {
                margin-top: 10px;
            }
            .card-title {
                font-size: 1.25rem;
                font-weight: bold;
                margin-bottom: 0.5rem;
                line-height: 1.2;
                color: #007bff;
            }
            .card-text {
                font-size: 1rem;
                margin-bottom: 0.75rem;
                line-height: 1.4;
                color: #495057;
            }
            .card-subtitle {
                font-size: 0.875rem;
                font-weight: bold;
                margin-bottom: 0.5rem;
                color: #6c757d;
            }
            .list-group-item {
                padding: 0.5rem 1rem;
                border: none;
                font-size: 0.95rem;
                color: #495057;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                font-size: 0.9rem;
                padding: 0.5rem 1rem;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                transition: background-color 0.3s, border-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
            }
            .post-body img {
                max-width: 100%;
                border-radius: 5px;
                margin-bottom: 10px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .carousel-inner {
                max-height: 400px;
                overflow: hidden;
            }
            .carousel-item img {
                max-height: 400px;
                width: auto;
                margin: 0 auto;
            }
            .carousel-control-prev,
            .carousel-control-next {
                width: 5%;
            }
            .carousel-control-prev-icon,
            .carousel-control-next-icon {
                background-color: rgba(0, 0, 0, 0.5);
                border-radius: 50%;
            }
            .posts-wrapper {
                display: flex;
                justify-content: center;
            }
            /* Modal */
            #mapModal .modal-dialog {
                max-width: 800px;
            }
            #map {
                width: 100%;
                height: 500px;
            }
        </style>
    </head>
   <body>
        <div class="container mt-4">
            <h1 class="text-center text-info">Bài viết</h1>
            <div class="row posts-wrapper">
                <c:forEach var="post" items="${posts}">
                    <div class="col-md-8">
                        <div class="post-container mb-4">
                            <div class="post-body">
                                <div class="user-info mb-2">
                                    <img src="${post.userId.avatar}" alt="Avatar" class="user-avatar">
                                    <h6 class="user-name">${post.userId.name}</h6>
                                </div>
                                <div class="post-details">
                                    <h2 class="card-title">${post.title}</h2>
                                    <p class="card-text">${post.description}</p>
                                    <h6 class="card-subtitle mb-2">Chi tiết:</h6>
                                    <span class="pe-2"><strong>Địa chỉ: </strong> ${post.location.address}  </span>
                                    <span class="pe-2"><strong>Diện tích:</strong> ${post.propertyDetail.acreage} </span>
                                    <span class="pe-2"><strong>Giá:</strong> ${post.propertyDetail.price}  </span>
                                    <span class="pe-2"><strong>Sức chứa:</strong> ${post.propertyDetail.capacity}  </span>
                                    <button type="button" class="btn btn-primary view-map-btn" data-lat="${post.location.latitude}" data-lng="${post.location.longitude}" data-toggle="modal" data-target="#mapModal">Xem vị trí trên bản đồ</button>
                                    <div id="carouselExampleIndicators${post.postId}" class="carousel slide" data-interval="false" data-ride="carousel">
                                        <ol class="carousel-indicators">
                                            <c:forEach var="image" items="${post.imageSet}" varStatus="loop">
                                                <li data-target="#carouselExampleIndicators${post.postId}" data-slide-to="${loop.index}" class="<c:if test="${loop.first}">active</c:if>"></li>
                                            </c:forEach>
                                        </ol>
                                        <div class="carousel-inner">
                                            <c:forEach var="image" items="${post.imageSet}" varStatus="loop">
                                                <div class="carousel-item <c:if test="${loop.first}">active</c:if>">
                                                    <img src="${image.url}" class="d-block w-100" alt="Ảnh bài viết">
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <a class="carousel-control-prev" href="#carouselExampleIndicators${post.postId}" role="button" data-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                        <a class="carousel-control-next" href="#carouselExampleIndicators${post.postId}" role="button" data-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="my-2 text-center">
                                <c:url value="/admin/post/reject/${post.postId}" var="url" />
                                <button type="button" class="btn btn-danger" onclick="reject('${url}', ${post.postId})">Từ chối</button>
                                <c:url value="/admin/post/approve/${post.postId}" var="url" />
                                <button type="button" class="btn btn-info" onclick="approvePost('${url}', ${post.postId})">Cập nhật</button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
     <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage - 1}&<c:forEach var="entry" items="${params.entrySet()}">${entry.key}=${entry.value}&</c:forEach>" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                        <a class="page-link" href="?page=${i}&<c:forEach var="entry" items="${params.entrySet()}">${entry.key}=${entry.value}&</c:forEach>">${i}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage + 1}&<c:forEach var="entry" items="${params.entrySet()}">${entry.key}=${entry.value}&</c:forEach>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
        </div>

        <!-- Map Modal -->
        <div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="mapModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="mapModalLabel">Vị trí trên bản đồ</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div id="map"></div>
                    </div>
                </div>
            </div>
        </div>


        <!-- Scripts -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script type='text/javascript' src='http://www.bing.com/api/maps/mapcontrol?callback=loadMap' async defer></script>
        <script>
            var map, pushpin;

            function loadMap() {
                map = new Microsoft.Maps.Map(document.getElementById('map'), {
                    credentials: 'AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P'
                });
            }

            function approvePost(url, postId) {
                fetch(url, { method: 'post' })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to approve post');
                        }
                        location.reload(); // Tải lại trang sau khi duyệt bài đăng thành công
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Failed to approve post'); // Hiển thị thông báo lỗi nếu có
                    });
            }
               function reject(url, postId) {
                fetch(url, { method: 'post' })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to reject post');
                        }
                        location.reload(); // Tải lại trang sau khi duyệt bài đăng thành công
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('Failed to reject post'); // Hiển thị thông báo lỗi nếu có
                    });
            }

            function initMapModal(lat, lng) {
                if (!map) {
                    loadMap();
                }
                var location = new Microsoft.Maps.Location(lat, lng);
                map.setView({ center: location, zoom: 15 });

                if (pushpin) {
                    map.entities.remove(pushpin);
                }
                pushpin = new Microsoft.Maps.Pushpin(location);
                map.entities.push(pushpin);
            }

            $(document).ready(function () {
                $('.view-map-btn').on('click', function () {
                    var lat = $(this).data('lat');
                    var lng = $(this).data('lng');
                    initMapModal(lat, lng);
                });

                $('#mapModal').on('shown.bs.modal', function () {
                    if (map) {
                        map.setView(map.getOptions());
                    }
                });
            });
        </script>
    </body>
</html>
