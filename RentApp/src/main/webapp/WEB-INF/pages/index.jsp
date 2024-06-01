<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<style>
    .post-container {
        margin-bottom: 20px;
    }
    .post-body {
        padding: 15px;
        border: 1px solid #ddd;
        border-radius: 5px;
    }
    .user-info {
        display: flex;
        align-items: center;
        margin-bottom: 0.5rem;
    }
    .user-avatar {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        margin-right: 10px;
    }
    .user-name {
        font-weight: bold;
        font-size: 0.9rem;
    }
    .post-details {
        margin-top: 10px;
    }
    .card-title {
        font-size: 1.125rem;
        font-weight: bold;
        margin-bottom: 0.25rem;
        line-height: 1.2;
    }
    .card-text {
        font-size: 1rem;
        margin-bottom: 0.5rem;
        line-height: 1.2;
    }
    .card-subtitle {
        font-size: 0.875rem;
        font-weight: bold;
        margin-bottom: 0.5rem;
        color: #8e8e8e;
    }
    .list-group-item {
        padding: 0.25rem 0;
        border: none;
        font-size: 0.9rem;
        color: #262626;
    }
    .btn-primary {
        background-color: #0095f6;
        border-color: #0095f6;
        font-size: 0.9rem;
        padding: 0.375rem 0.75rem;
        border-radius: 3px;
    }
    .btn-primary:hover {
        background-color: #0058b7;
        border-color: #0058b7;
    }
    .post-body img {
        max-width: 100%;
        border-radius: 5px;
        margin-bottom: 10px;
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
        background-color: black;
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

<div class="container">
    <h1 class="text-center text-info">Bài viết</h1>
    <div class="row posts-wrapper">
        <c:forEach var="post" items="${posts}">
            <div class="col-md-8">
                <div class="post-container">
                    <div class="post-body">
                        <div class="user-info">
                            <img src="${post.userId.avatar}" alt="Avatar" class="user-avatar">
                            <h6 class="user-name">${post.userId.name}</h6>
                        </div>
                        <div class="post-details">
                            <h2 class="card-title">${post.title}</h2>
                            <p class="card-text">${post.description}</p>
                            <c:forEach var="propertyDetail" items="${post.propertyDetailSet}" varStatus="loop">
                                <h6 class="card-subtitle mb-2 text-muted">Thông tin về property:</h6>
                                <ul class="list-group">
                                    <li class="list-group-item">Địa chỉ: ${propertyDetail.address}</li>
                                    <li class="list-group-item">Diện tích: ${propertyDetail.acreage}</li>
                                    <li class="list-group-item">Giá: ${propertyDetail.price}</li>
                                    <li class="list-group-item">Sức chứa: ${propertyDetail.capacity}</li>
                                    <!-- Thêm các thông tin khác của property tại đây -->
                                </ul>
                                <button type="button" class="btn btn-primary view-map-btn" data-lat="${propertyDetail.latitude}" data-lng="${propertyDetail.longitude}" data-toggle="modal" data-target="#mapModal">Xem vị trí trên bản đồ</button>
                            </c:forEach>
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

                    <div class="mt-3">
                        <c:url value="/api/admin/post/approve/${post.postId}" var="url" />
                        <button type="button" class="btn btn-info" onclick="approvePost('${url}', ${post.postId})">cập nhật</button>
                    </div>


                </div>              
            </div>
        </c:forEach>
    </div>
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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
                            function initMap() {
                                // This function is required for Google Maps API to work
                            }

                            document.addEventListener('DOMContentLoaded', function () {
                                var carousels = document.querySelectorAll('.carousel');
                                carousels.forEach(function (carousel) {
                                    var startX;
                                    var endX;

                                    carousel.addEventListener('touchstart', function (event) {
                                        startX = event.touches[0].clientX;
                                    });

                                    carousel.addEventListener('touchmove', function (event) {
                                        endX = event.touches[0].clientX;
                                    });

                                    carousel.addEventListener('touchend', function (event) {
                                        var threshold = 50; // Minimum distance for a swipe
                                        if (startX - endX > threshold) {
                                            $(carousel).carousel('next');
                                        } else if (endX - startX > threshold) {
                                            $(carousel).carousel('prev');
                                        }
                                    });

                                    var mouseDown = false;

                                    carousel.addEventListener('mousedown', function (event) {
                                        mouseDown = true;
                                        startX = event.clientX;
                                    });

                                    carousel.addEventListener('mousemove', function (event) {
                                        if (mouseDown) {
                                            endX = event.clientX;
                                        }
                                    });

                                    carousel.addEventListener('mouseup', function (event) {
                                        mouseDown = false;
                                        var threshold = 50; // Minimum distance for a
                                        var threshold = 50; // Minimum distance for a swipe
                                        if (startX - endX > threshold) {
                                            $(carousel).carousel('next');
                                        } else if (endX - startX > threshold) {
                                            $(carousel).carousel('prev');
                                        }
                                    });

                                    carousel.addEventListener('mouseleave', function (event) {
                                        mouseDown = false;
                                    });
                                });

                                // Event listener for map buttons
                                $('.view-map-btn').on('click', function () {
                                    var latitude = parseFloat($(this).data('lat'));
                                    var longitude = parseFloat($(this).data('lng'));

                                    $('#mapModal').on('shown.bs.modal', function () {
                                        var map = new google.maps.Map(document.getElementById('map'), {
                                            center: {lat: latitude, lng: longitude},
                                            zoom: 15
                                        });
                                        var marker = new google.maps.Marker({
                                            position: {lat: latitude, lng: longitude},
                                            map: map
                                        });
                                    });
                                });
                            });

                            function loadScript() {
                                var script = document.createElement('script');
                                script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyDJ5yuhRClCTJ7mz69NjPs8Pz6AF4ksdIc&callback=initMap`;
                                script.async = true;
                                script.defer = true;
                                document.head.appendChild(script);
                            }


                            function approvePost(url, postId) {
                                fetch(url, {  method: 'get'})
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
</script>

