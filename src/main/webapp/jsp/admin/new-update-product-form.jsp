<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

    <c:choose>
        <c:when test="${empty sessionScope.admin}">
            <head>
                <title> Unauthorized acces - You should not be here</title>
                <!-- FAVICON -->
                <c:import url="/jsp/headers/common-header-tags.jsp"/>
            </head>

            <body>
            <h1> UNAUTHORIZED ACCESS</h1>

            <p>You should not be here UNKNOWN USER</p>

            </body>

        </c:when>

        <c:otherwise>
            <head>
              <title>Update Product Form</title>

                <link href="<c:url value="/css/products.css"/>"  type="text/css" rel="stylesheet"/>
                <link href="<c:url value="/css/insertProduct.css"/>" type="text/css" rel="stylesheet"/>

                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <!-- FAVICON -->
                <c:import url="/jsp/headers/common-header-tags.jsp"/>

            </head>
            <body>

                <!--<h1>Update Product Form</h1>-->
                <form  id="UpdateForm" method="POST" enctype="multipart/form-data" action="<c:url value="/seeprod/update"/>" >

                  <input type="hidden" id="Oldname" name="Oldname"  class="form-control form-control-lg">
                  <label for="name">Name:</label><br>
                  <input type="text" id="name" name="name"  class="form-control form-control-lg" maxlength="50"><br>

                  <label for="description">Description:</label><br>
                  <textarea id="description" name="description" rows="4" class="form-control form-control-lg" maxlength="100"></textarea><br>
                  <label for="price">Price:</label><br>
                    <input type="text" id="price" name="price"  pattern="^[0-9]+([,.][0-9]{1,2})?$" required   class="form-control form-control-lg"><br>
                  <label for="bar">Bar:</label>
                  <input type="checkbox" id="bar" name="bar" value="true" ><br>
                  <label for="available">Available:</label>
                  <input type="checkbox" id="available" name="available" value="true" ><br>
                  <label for="categoryUpdate">Category:</label><br>
                  <select name="category" id="categoryUpdate"  class="form-control form-control-lg">
                   <option value="starters">Starters</option>
                            <option value="meat">Meat</option>
                            <option value="beverages">Beverages</option>
                            <option value="side dishes">Side dishes</option>

                    </select><br>
<%--                  <label for="photo">Photo:</label>--%>
<%--                  <input type="file" accept="image/png, image/jpeg, .jpg, .jpeg, .png" id="photo" name="photo"><br>--%>

                    <label for="photo">Photo</label><br>
                    <div style="display: flex; align-items: center;" class="form-control form-control-lg">
                      <div id="photo-update-dropzone" class="form-control form-control-lg" >
                        <p>Drag and drop an image file here, or click to select</p>
                        <div><img id="preview-update-image" src="#" alt="Preview"></div>
                        <input type="file" accept="image/png, image/jpeg, .jpg, .jpeg, .png" id="photo-update" name="photo" class="form-control form-control-lg">
                        <div><button id="remove-update-image" class="btn btn-primary">Remove Image</button></div>
                      </div>
                    </div>

                  <input type="submit" id="UpdateSub" value="Submit" class="btn btn-primary">
                  <!--<button type="submit">Submit</button><br/> -->
                </form>

                <script src="<c:url value="/js/products.js"/>"></script>
                <script src="<c:url value="/js/insertProduct.js"/>"></script>
            </body>
        </c:otherwise>
    </c:choose>
</html>
