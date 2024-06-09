<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng bài</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #363940;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .wrapper {
            background-color: #fff;
            padding: 25px;
            border-radius: 5px;
            width: 360px;
            max-width: 100%;
            box-sizing: border-box;
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .images {
            display: flex;
            flex-wrap: wrap;
            margin-top: 20px;
        }

        .images .img,
        .images .pic {
            flex-basis: 31%;
            margin-bottom: 10px;
            border-radius: 4px;
        }

        .images .img {
            width: 100px;
            height: 100px;
            background-size: cover;
            margin-right: 10px;
            background-position: center;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }

        .images .img:nth-child(3n) {
            margin-right: 0;
        }

        .images .img span {
            display: none;
            text-transform: capitalize;
            z-index: 2;
        }

        .images .img::after {
            content: '';
            width: 100%;
            height: 100%;
            transition: opacity .1s ease-in;
            border-radius: 4px;
            opacity: 0;
            position: absolute;
        }

        .images .img:hover::after {
            display: block;
            background-color: #000;
            opacity: .5;
        }

        .images .img:hover span {
            display: block;
            color: #fff;
        }

        .images .pic {
            background-color: #F5F7FA;
            align-self: center;
            text-align: center;
            padding: 40px 0;
            text-transform: uppercase;
            color: #848EA1;
            font-size: 12px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="wrapper">
        <h1>Đăng bài</h1>
        <c:url value="/post/add" var="action" />
        <form:form method="post" action="${action}" modelAttribute="post" enctype="multipart/form-data">
            <form:errors path="*" element="div" cssClass="alert alert-danger" />

            <div class="form-floating mb-3 mt-3">
                <form:input class="form-control" id="title" placeholder="Title" path="title" />
                <label for="title">Title</label>
            </div>
            <div class="form-floating mb-3 mt-3">
                <form:textarea class="form-control" id="description" placeholder="Description" path="description" rows="3" />
                <label for="description">Description</label>
            </div>
            <div class="images">
                <div class="pic">Thêm ảnh</div>
                <input type="file" accept="image/*" multiple style="display: none;" id="fileInput" name="files" />
            </div>
            <button type="submit" class="btn btn-primary mt-3">Submit</button>
        </form:form>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        (function ($) {
            $(document).ready(function () {
                var filesArray = [];

                function updateInput() {
                    var dataTransfer = new DataTransfer();
                    filesArray.forEach(file => {
                        dataTransfer.items.add(file);
                    });
                    $('#fileInput')[0].files = dataTransfer.files;
                }

                function uploadImage() {
                    var button = $('.images .pic');
                    var uploader = $('#fileInput');
                    var images = $('.images');

                    button.on('click', function () {
                        uploader.click();
                    });

                    uploader.on('change', function () {
                        var files = uploader[0].files;
                        for (var i = 0; i < files.length; i++) {
                            filesArray.push(files[i]);
                            var reader = new FileReader();
                            reader.onload = function (event) {
                                images.prepend('<div class="img" style="background-image: url(\'' + event.target.result + '\');" data-file-index="' + (filesArray.length - 1) + '"><span>remove</span></div>');
                            };
                            reader.readAsDataURL(files[i]);
                        }
                        updateInput();
                    });

                    images.on('click', '.img', function () {
                        var index = $(this).data('file-index');
                        filesArray.splice(index, 1);
                        $(this).remove();
                        updateInput();
                    });
                }

                uploadImage();
            });
        })(jQuery);
    </script>
</body>
</html>
