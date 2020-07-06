<!doctype html>
<html lang="en">
  <head>

    <%@include  file="header.html" %>

  </head>
  <body>
  <script src="static/js/dashboard.js"></script>

  <%@include  file="nav-top.html" %>

<div class="container-fluid">
  <div class="row">

    <%@include  file="nav-side.html" %>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">

      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">User Administration</h1>

        <br>A table of users so you can edit their accounts (locked versus unlocked).</br>
        <br><%@include  file="edit_table.html" %></br>

        <br>A table of users who are enrolled in a study with a marker that they have been paid or not</br>
        <br>example found at https://editor.datatables.net/examples/inline-editing/simple.html</br>

      </div>

    </main>

  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="static/js/bootstrap.bundle.js"></script>
</body>
</html>
