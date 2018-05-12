<%-- 
    Document   : dailyreport
    Created on : Apr 30, 2018, 10:58:06 AM
    Author     : alirzea
--%>

<%@page import="com.fidar.report.chart.ChartDataset"%>
<%@page import="com.fidar.report.table.TableDataset"%>
<%@page import="com.fidar.formal.input.MakeInput"%>
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
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-title">
                                <h4>Add Simple User</h4>
                            </div>
                            <div class="panel-body">
                                <form>
                                    <div class="from-group">
                                        <label>Service name</label>
                                        <select class="form-control" name="serviceSelecter">
                                            <%
                                                MakeInput makeInput = new MakeInput();
                                                String selector_dailyReport_serviceName = makeInput.getSelector_AddSimpleUser(username);
                                                out.println(selector_dailyReport_serviceName);
                                            %>
                                        </select>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 col-sm-4">
                                            <div class="from-group">
                                                <label>Year</label>
                                                <select class="form-control" name="year">
                                                    <option>2017</option>
                                                    <option>2018</option>
                                                    <option>2019</option>
                                                    <option>2020</option>
                                                    <option>2021</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-4">
                                            <div class="from-group">
                                                <label>Month</label>
                                                <select class="form-control" name="month">
                                                    <option>01</option>
                                                    <option>02</option>
                                                    <option>03</option>
                                                    <option>04</option>
                                                    <option>05</option>
                                                    <option>06</option>
                                                    <option>07</option>
                                                    <option>08</option>
                                                    <option>09</option>
                                                    <option>10</option>
                                                    <option>11</option>
                                                    <option>12</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-4 col-sm-4">
                                            <div class="from-group">
                                                <label>Day</label>
                                                <select class="form-control" name="day">
                                                    <option>01</option>
                                                    <option>02</option>
                                                    <option>03</option>
                                                    <option>04</option>
                                                    <option>05</option>
                                                    <option>06</option>
                                                    <option>07</option>
                                                    <option>08</option>
                                                    <option>09</option>
                                                    <option>10</option>
                                                    <option>11</option>
                                                    <option>12</option>
                                                    <option>13</option>
                                                    <option>14</option>
                                                    <option>15</option>
                                                    <option>16</option>
                                                    <option>17</option>
                                                    <option>18</option>
                                                    <option>19</option>
                                                    <option>20</option>
                                                    <option>21</option>
                                                    <option>22</option>
                                                    <option>23</option>
                                                    <option>24</option>
                                                    <option>25</option>
                                                    <option>26</option>
                                                    <option>27</option>
                                                    <option>28</option>
                                                    <option>29</option>
                                                    <option>30</option>
                                                    <option>31</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <button type="submit" class="btn btn-success">Generate Report</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- second row -->
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-title">
                                <h4>Table Basic </h4>

                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Price</th>
                                                <th>Success</th>
                                                <th>Fail</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                TableDataset tableDataset = new TableDataset();
                                                String date = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
                                                String serviceName = request.getParameter("serviceSelecter");
                                                String table_details = "";
                                                if(serviceName != ""){
                                                    table_details = tableDataset.getReportDetails(date, serviceName);
                                                }
                                                out.println(table_details);
                                            %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-title">
                                <h4>Details</h4>
                            </div>
                            <div class="panel-body">
                                <%
                                    String details = "";
                                    details = tableDataset.getReportDetails(date, serviceName);
                                    out.println(details);
                                %>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- third row -->
                <div class="row">
                    <div class="col-md-6">
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
                <%
                    ChartDataset chartDataset = new ChartDataset();
                    String data = chartDataset.getDatasetBarChart_RevinueReport();
                    out.println(data);
                %>
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

