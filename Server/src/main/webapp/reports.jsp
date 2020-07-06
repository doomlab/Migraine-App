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
        <h1 class="h2">Reports</h1>
          <br>Summary Report of Active/Inactive Users</br>
          <br><%@include  file="summary_report.html" %></br>

          <br>Report of the Number Enrolled in Research</br>
          <br>Same idea as above</br>

          <br>Headache Days Report - Average Number Across Days - Separated by Gender</br>
          <br><%@include  file="headache_day_graph.html" %></br>

          <br>Total number of days we have data</br>
          <br>Charts of FAMS scores</br>

      </div>

    </main>

  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="static/js/bootstrap.bundle.js"></script>
</body>
</html>
