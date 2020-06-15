<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Clinvest Migraine Research App dashboard page built with Bootstrap">
    <meta name="author" content="Phil Grim, Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <title>Migraine Research Application</title>

    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.css" rel="stylesheet">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
    <!-- Custom styles for this template -->
    <link href="static/css/dashboard.css" rel="stylesheet">
  </head>
  <body>
  <script src="static/js/dashboard.js"></script>

    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
  <a class="navbar-brand col-md-3 col-lg-2 mr-0 px-3" href="#"><img alt="Clinvest" src="static/images/clinvest_plain_logo.png" width="100" height="30"></a>
  <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-toggle="collapse" data-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <ul class="navbar-nav px-3">
    <li class="nav-item text-nowrap">
      <a class="nav-link" href="#">Sign out</a>
    </li>
  </ul>
</nav>

<div class="container-fluid">
  <div class="row">
    <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
      <div class="sidebar-sticky pt-3">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link active" href="#">
              <span data-feather="home"></span>
              Dashboard <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <span data-feather="users"></span>
              User Administration
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <span data-feather="bar-chart-2"></span>
              Reports
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <span data-feather="layers"></span>
              Data
            </a>
          </li>
        </ul>
      </div>
    </nav>

    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">

      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">Dashboard</h1>

          - On the main dashboard page, maybe we can put the basic common widgets people will use (make quick images for these?)
          - On the user admin page, put a table of users that you can edit their account (locked versus unlocked) maybe use the hover class?
            - https://mdbootstrap.com/docs/jquery/tables/editable/ or maybe this one with the option to go in and edit: https://mdbootstrap.com/docs/jquery/tables/search/
            - somewhere we will need a thing that says if they have been paid already for X research project

          - on the Reports page, we can put a few reports:
            - summary report of the number of users (active / inactive)
              - https://mdbootstrap.com/snippets/jquery/marta-szymanska/1363695
            - report of the number of users who are enrolled in the research
              - same card as above
            - average number of headache days in the last week/month for users (maybe we can make a graph?)
              - or maybe just the total number of days we have data entered?
            - average fams score for users (maybe graph like a histogram or violin plot would be good)

          - on the data page:
            - download summary list of users (would include name, email, number days diary, number entries FAMS)
              - this report may also need start and end dates?
            - download the diary + fams + user information (not user name, but the gender / head information)
            - this would need to be filtered by the research agreement times

      </div>

    </main>

  </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="static/js/bootstrap.bundle.js"></script>
</body>
</html>
