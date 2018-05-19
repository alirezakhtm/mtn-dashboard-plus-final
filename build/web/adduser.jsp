<%-- 
    Document   : adduser
    Created on : Apr 18, 2018, 11:30:37 AM
    Author     : alirzea
--%>

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
<!--                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-bell"></i>
				<div class="notify"> <span class="heartbit"></span> <span class="point"></span> </div>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right mailbox animated zoomIn">
                                <%@include file="absolutemessage.jsp" %>
                            </div>
                        </li>-->
                        <!-- End Comment -->
                        <!-- Profile -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle text-muted  " href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><img src="images/bookingSystem/3.png" alt="user" class="profile-pic" /></a>
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
        <%
            if(
                    answer.equals(ConstantParameters.USER_SIMPLE) ||
                    answer.equals(ConstantParameters.USER_UNKNOWN)
                    ){
                securityOrder.logOutUser(request);
                RequestDispatcher dispatcher = request.getRequestDispatcher("loginpage.jsp");
                dispatcher.forward(request, response);
            }
        %>
        <!-- End Left Sidebar  -->
        <!-- Page wrapper  -->
        <div class="page-wrapper" style="height:1200px;">
            <!-- Container fluid  -->
            <div class="container-fluid">
                
                <br/>
                
                <%
                    int add_simple_user = (int)session.getAttribute("add_simple_user");
                    switch(add_simple_user){
                        case 1:
                %>
                
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-primary alert-dismissible fade show">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            User added successfully.
                        </div>
                    </div>
                </div>
                
                <%
                    break;
                    case 0:
                %>
                
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-warning alert-dismissible fade show">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
                            <strong>Failed!</strong> Username is exist or you dont have enough permission to create this account.
                        </div>
                    </div>
                </div>
                
                <%
                    break;
                    }
                %>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-title">
                                <h4>Add Simple User</h4>
                            </div>
                            <div class="panel-body">
                                <form method="post">
                                    <div class="form-group">
                                        <label>Username</label>
                                        <input type="text" class="form-control" placeholder="Enter Username" name="username">
                                    </div>
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input type="text" class="form-control" placeholder="Enter Password" name="password">
                                    </div>
                                    <div class="from-group">
                                        <label>Service name</label>
                                        <select class="form-control" name="service">
                                            <%
                                                MakeInput makeInput = new MakeInput();
                                                String selector_simpleUser = makeInput.getSelector_AddSimpleUser(username);
                                                out.print(selector_simpleUser);
                                            %>
                                        </select>
                                    </div>
                                    <br/>
                                    <input type="hidden" value="add-simple-user" name="action">
                                    <button type="submit" class="btn btn-success" name="btn-addSimpleUser">Create Account</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <%
                            if(answer.equals(ConstantParameters.USER_MASTER)){
                        %>
                        <div class="card">
                            <div class="card-title">
                                <h4>Add Admin User</h4>
                            </div>
                            <div class="panel-body">
                                <form>
                                    <div class="form-group">
                                        <label>Username</label>
                                        <input type="text" class="form-control" placeholder="Username" name="username">
                                    </div>
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input type="text" class="form-control" placeholder="Password" name="password">
                                    </div>
                                    <div class="from-group">
                                        <label>Service name</label>
                                        <select class="form-control" name="serviceSelecter">
                                            <option>yazd1</option>
                                            <option>yazd2</option>
                                            <option>yazd3</option>
                                            <option>yazd4</option>
                                        </select>
                                    </div>
                                    <br/>
                                    <button type="submit" class="btn btn-success">Create Account</button>
                                </form>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
                <!-- second row -->
                <div class="row">
                    <div class="col-md-6">
                        <%
                            if(answer.equals(ConstantParameters.USER_MASTER)){
                        %>
                        <div class="card">
                            <div class="card-title">
                                <h4>Add Master User</h4>
                            </div>
                            <div class="panel-body">
                                <form>
                                    <div class="form-group">
                                        <label>Username</label>
                                        <input type="text" class="form-control" placeholder="Username" name="username">
                                    </div>
                                    <div class="form-group">
                                        <label>Password</label>
                                        <input type="text" class="form-control" placeholder="Password" name="password">
                                    </div>
                                    <br/>
                                    <button type="submit" class="btn btn-success">Create Account</button>
                                </form>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <div class="col-md-6">
                        <%
                            if(answer.equals(ConstantParameters.USER_MASTER)){
                        %>
                        <div class="card">
                            <div class="card-title">
                                <h4>Update Admin User</h4>
                            </div>
                            <div class="panel-body">
                                <form>
                                    <div class="from-group">
                                        <label>Username</label>
                                        <select class="form-control" name="serviceSelecter">
                                            <option>Admin1</option>
                                            <option>Admin2</option>
                                            <option>Admin3</option>
                                            <option>Asmin4</option>
                                        </select>
                                    </div>
                                    <div class="from-group">
                                        <label>Service name</label>
                                        <select class="form-control" name="serviceSelecter">
                                            <option>yazd1</option>
                                            <option>yazd2</option>
                                            <option>yazd3</option>
                                            <option>yazd4</option>
                                        </select>
                                    </div>
                                    <br/>
                                    <button type="submit" class="btn btn-success">Update Access</button>
                                    <button type="submit" class="btn btn-success">Delete Access</button>
                                    <button type="submit" class="btn btn-success">Reset Password</button>
                                </form>
                            </div>
                        </div>
                        <%
                            }
                        %>
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
    
</body>

</html>

