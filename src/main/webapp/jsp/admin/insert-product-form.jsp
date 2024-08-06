<!--
Copyright 2018-2023 University of Padua, Italy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Author: Gabriel Taormina (gabriel.taormina@studenti.unipd.it)
Version: 1.0
Since: 1.0
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html>

  <c:choose>
    <c:when test="${empty sessionScope.admin}">
      <head>
        <title> Unauthorized access </title>
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


        <link href="<c:url value="/css/insertProduct.css"/>" type="text/css" rel="stylesheet"/>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <title>Insert Product Form</title>
        <!-- FAVICON -->
        <c:import url="/jsp/headers/common-header-tags.jsp"/>
      </head>
      <body>

      <!--<h1>Insert Product Form</h1> -->
      <form method="POST" id="insertForm" enctype="multipart/form-data" action="<c:url value="/insert"/>">
        <label for="name" class="block">Name</label>
        <input type="text" id="name" name="name" required  class="form-control form-control-lg" maxlength="50">
        <label for="description" class="block">Description</label>
        <textarea type="text" id="description" name="description" rows="4" class=" form-control form-control-lg" maxlength="100"></textarea>
        <label for="priceInsert" class="block">Price</label>
        <input type="text" id="priceInsert" name="price"  pattern="^[0-9]+([,.][0-9]+)?$" required   class="form-control form-control-lg block">
        <div>
            <label for="bar">Bar</label>
            <input type="checkbox" id="bar" name="bar" value="true">
        </div>
        <div>
            <label for="available">Available</label>
            <input type="checkbox" id="available" name="available" value="true">
        </div>
        <label for="categoryInsert" class="block">Category</label>
        <select name="category" id="categoryInsert" required  class="form-control form-control-lg">
         <option value="starters">Starters</option>
                  <option value="meat">Meat</option>
                  <option value="beverages">Beverages</option>
                  <option value="side dishes">Side dishes</option>

        </select><br>

          <label for="photo">Photo</label><br>
          <div style="display: flex; align-items: center;" class="form-control form-control-lg">
              <div id="photo-insert-dropzone" class="form-control form-control-lg">
                  <p>Drag and drop an image file here, or click to select</p>
                  <img id="preview-insert-image" src="#" alt="Preview">
                  <input type="file" accept="image/png, image/jpeg, .jpg, .jpeg, .png" id="photo-insert" name="photo" class="form-control form-control-lg">
                  <button id="remove-insert-image" class="btn btn-primary">Remove Image</button>
              </div>
          </div>

        <input type="submit"  id="submitInsert" value="Submit" class="btn btn-primary">

<!--<option value="first course">First course</option>
          <option value="starters">Starters</option>
          <option value="meat">Meat</option>
          <option value="beverages">Beverages</option>
          <option value="side dishes">Side dishes</option> -->

      </form>

      <script src="<c:url value="/js/insertProduct.js"/>"></script>

      </body>
    </c:otherwise>
  </c:choose>
</html>
