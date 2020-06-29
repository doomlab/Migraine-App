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
