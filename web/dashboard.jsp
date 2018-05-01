<%-- 
    Document   : dashboard
    Created on : Apr 16, 2018, 12:16:44 PM
    Author     : alirzea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="images/favicon.png">
    <title>Dashboard</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/lib/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="css/helper.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:** -->
    <!--[if lt IE 9]>
    <script src="https:**oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https:**oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body class="fix-header">
    <!-- Preloader - style you can find in spinners.css -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
            <circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> 
        </svg>
    </div>
    <!-- Main wrapper  -->
    <div id="main-wrapper">
        <!-- header header  -->
        <div class="header">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <!-- Logo -->
                <%@include file="absolutelogo.jsp" %>
                <!-- End Logo -->
                <div class="navbar-collapse">
                    <!-- toggle and nav items -->
                    <ul class="navbar-nav mr-auto mt-md-0">
                        <!-- This is  -->
                        <li class="nav-item"> <a class="nav-link nav-toggler hidden-md-up text-muted  " href="javascript:void(0)"><i class="mdi mdi-menu"></i></a> </li>
                        <li class="nav-item m-l-10"> <a class="nav-link sidebartoggler hidden-sm-down text-muted  " href="javascript:void(0)"><i class="ti-menu"></i></a> </li>
                        <!-- End Messages -->
                    </ul>
                    <!-- User profile and search -->
                    <ul class="navbar-nav my-lg-0">
                        <!-- Comment -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-bell"></i>
				<div class="notify"> <span class="heartbit"></span> <span class="point"></span> </div>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right mailbox animated zoomIn">
                                <%@include file="absolutemessage.jsp" %>
                            </div>
                        </li>
                        <!-- End Comment -->
                        <!-- Profile -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="#" alt="user" class="profile-pic" /></a>
                            <div class="dropdown-menu dropdown-menu-right animated zoomIn">
                                <ul class="dropdown-user">
                                    <li><a href="LogOut"><i class="fa fa-power-off"></i> Logout</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <!-- End header header -->
        <!-- Left Sidebar  -->
        <%@include file="absoluteleftsidebar.jsp" %>
        <!-- End Left Sidebar  -->
        <!-- Page wrapper  -->
        <div class="page-wrapper" style="height:1200px;">
            <!-- Container fluid  -->
            <div class="container-fluid">
                <!-- Start Page Content -->
                <!-- Chart -->
                <div class="row">
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-title">
                                <h4>Pie chart</h4>
                            </div>
                            <div class="panel-body">
                                <canvas id="pieChart"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-title">
                                <h4>Bar chart</h4>
                            </div>
                            <div class="panel-body">
                                <canvas id="barChart"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Finish chart -->
                <!-- Table of services -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-title">
                                <h4>Table of current services </h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Owner</th>
                                                <th>Admin</th>
                                                <th>Revenue</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th scope="row">1</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-primary">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">2</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-primary">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">3</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-inverse">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-title">
                                <h4>Table of current services </h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Owner</th>
                                                <th>Admin</th>
                                                <th>Revenue</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th scope="row">1</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-primary">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">2</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-primary">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                            <tr>
                                                <th scope="row">3</th>
                                                <td>Golnakhorim</td>
                                                <td><span class="badge badge-inverse">Morteza</span></td>
                                                <td>Vasfa</td>
                                                <td class="color-primary">6,000,000 T</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Finish table of services -->
                <!-- End PAge Content -->
            </div>
            <!-- End Container fluid  -->
            <!-- footer -->
            <%@include file="absolutefooter.jsp" %>
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>
    <!-- End Wrapper -->
    <!-- All Jquery -->
    <script src="js/lib/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="js/lib/bootstrap/js/popper.min.js"></script>
    <script src="js/lib/bootstrap/js/bootstrap.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="js/jquery.slimscroll.js"></script>
    <!--Menu sidebar -->
    <script src="js/sidebarmenu.js"></script>
    <!--stickey kit -->
    <script src="js/lib/sticky-kit-master/dist/sticky-kit.min.js"></script>
    <!--Custom JavaScript -->
    <script src="js/custom.min.js"></script>
    <!-- Pie chart - java script file-->
    <!--<script src="js/lib/flot-chart/jquery.flot.pie.js"></script>-->
    <!--  flot-chart js -->
<!--    <script src="js/lib/flot-chart/excanvas.min.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.pie.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.time.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.stack.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.resize.js"></script>
    <script src="js/lib/flot-chart/jquery.flot.crosshair.js"></script>
    <script src="js/lib/flot-chart/curvedLines.js"></script>
    <script src="js/lib/flot-chart/flot-tooltip/jquery.flot.tooltip.min.js"></script>-->
    <script src="js/lib/chart-js/Chart.bundle.js"></script>
    <script>
        //pie chart
	var ctx = document.getElementById( "pieChart" );
	ctx.height = 300;
	var myChart = new Chart( ctx, {
		type: 'pie',
		data: {
			datasets: [ {
				data: [ 45, 25, 20, 10 ],
				backgroundColor: [
                                    "rgba(0, 123, 255,0.9)",
                                    "rgba(0, 123, 255,0.7)",
                                    "rgba(0, 123, 255,0.5)",
                                    "rgba(0,0,0,0.07)"
                                ],
				hoverBackgroundColor: [
                                    "rgba(0, 123, 255,0.9)",
                                    "rgba(0, 123, 255,0.7)",
                                    "rgba(0, 123, 255,0.5)",
                                    "rgba(0,0,0,0.07)"
                                ]

                            } ],
			labels: [
                            "green",
                            "green",
                            "green"
                        ]
		},
		options: {
			responsive: true
		}
	} );
    </script>
    
    <script>
        //bar chart
	var ctx = document.getElementById( "barChart" );
	//    ctx.height = 200;
	var myChart = new Chart( ctx, {
            type: 'bar',
            data: {
                labels: [ "January", "February", "Alireza", "April", "May", "June", "July" ],
                datasets: [
                    {
                        label: "My First dataset",
                        data: [ 65, 59, 80, 81, 56, 55, 40 ],
                        borderColor: "rgba(0, 123, 255, 0.9)",
                        borderWidth: "0",
                        backgroundColor: "rgba(0, 123, 255, 0.5)"
                    },
                    {
                        label: "My Second dataset",
                        data: [ 28, 48, 40, 19, 86, 27, 90 ],
                        borderColor: "rgba(0,0,0,0.09)",
                        borderWidth: "0",
                        backgroundColor: "rgba(0,0,0,0.07)"
                    }
                ]
            },
            options: {
                scales: {
                    yAxes: [ {
                        ticks: {
                            beginAtZero: true
                        }
                    } ]
                }
            }
	} );
    </script>
</body>

</html>
