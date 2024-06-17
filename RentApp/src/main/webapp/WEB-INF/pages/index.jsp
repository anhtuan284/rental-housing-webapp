<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!--<h1><spring:message code="label.title"/></h1>
<h2>${pageContext.request.contextPath}</h2>-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bing Maps Example</title>
    <script type='text/javascript' src='http://www.bing.com/api/maps/mapcontrol?key=AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P' async defer></script>
    <script type="text/javascript">
        var map;
        var pushpin;

        function searchAndShowMap() {
            var address = document.getElementById('address').value;

            // Wait for the Bing Maps API to be loaded
            Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
                // Initialize the map
                map = new Microsoft.Maps.Map(document.getElementById('map'), {
                    credentials: 'AnmtdlciSHCT7-QaOKIk_DNILKWHw4ehMIsCGTXHi-HTGuGaoQ4KfQppjtyYsh5P'
                });

                // Create a geocoder
                var searchManager = new Microsoft.Maps.Search.SearchManager(map);

                // Geocode the address
                searchManager.geocode({
                    where: address,
                    callback: function (geocodeResult, userData) {
                        if (geocodeResult && geocodeResult.results && geocodeResult.results.length > 0) {
                            var location = geocodeResult.results[0].location;

                            // Set map view to the geocoded location
                            map.setView({
                                center: new Microsoft.Maps.Location(location.latitude, location.longitude),
                                zoom: 12
                            });

                            // Create and add a draggable pushpin at the geocoded location
                            pushpin = new Microsoft.Maps.Pushpin(new Microsoft.Maps.Location(location.latitude, location.longitude), {
                                draggable: true
                            });

                            // Add dragend event listener to update marker position
                            Microsoft.Maps.Events.addHandler(pushpin, 'dragend', function (e) {
                                var newLocation = e.target.getLocation();
                                console.log('New Latitude:', newLocation.latitude);
                                console.log('New Longitude:', newLocation.longitude);
                            });

                            // Add the pushpin to the map
                            map.entities.push(pushpin);
                        } else {
                            alert('Địa chỉ không được tìm thấy trên bản đồ.');
                        }
                    },
                    errorCallback: function (geocodeError) {
                        alert('Đã xảy ra lỗi khi tìm kiếm địa chỉ.');
                    }
                });
            });
        }
    </script>
    <style>
        #map {
            height: 400px;
            width: 100%;
        }
    </style>
</head>
<body>
    <input type="text" id="address" placeholder="Nhập địa chỉ">
    <button onclick="searchAndShowMap()">Tìm kiếm</button>
    <div id="map"></div>
</body>
</html>
