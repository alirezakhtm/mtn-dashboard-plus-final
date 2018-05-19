<%-- 
    Document   : absoluteleftsidebar
    Created on : Apr 16, 2018, 4:32:47 PM
    Author     : alirzea
--%>

<%@page import="com.fidar.database.ConstantParameters"%>
<%@page import="com.fidar.security.SecurityOrder"%>
<%
    SecurityOrder securityOrder = new SecurityOrder();
    String username = (String)session.getAttribute("username");
    String password = (String)session.getAttribute("password");
    ConstantParameters answer = securityOrder.whoIsUser(username, password);
    if(answer.equals(ConstantParameters.USER_UNKNOWN)){
        //securityOrder.logOutUser(request);
        RequestDispatcher getOutThisUser = request.getRequestDispatcher("loginpage.jsp");
        getOutThisUser.forward(request, response);
    }
%>
<div class="left-sidebar">
    <!-- Sidebar scroll-->
    <div class="scroll-sidebar">
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav">
            <ul id="sidebarnav">
                <li class="nav-devider"></li>
                <li class="nav-label">Home</li>
                <li> <a class="has-arrow  " href="#" aria-expanded="false"><i class="fa fa-tachometer"></i><span class="hide-menu">Dashboard </span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="UserLogin">Report </a></li>
                    </ul>
                </li>
                <%
                    if(!answer.equals(ConstantParameters.USER_SIMPLE)) {
                %>
                <li class="nav-label">Users</li>
                <li> <a class="has-arrow  " href="#" aria-expanded="false"><i class="fa fa-suitcase"></i><span class="hide-menu">Account</span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="AddUser">Add User</a></li>
                        <li><a href="UserList">User List</a></li>
                    </ul>
                </li>
                <%
                    }
                %>
                <%
                    if(answer.equals(ConstantParameters.USER_ADMIN)) {
                %>
                <li> <a class="has-arrow  " href="#" aria-expanded="false"><i class="fa fa-envelope"></i><span class="hide-menu">Content</span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="ContentConfirm">Confirm</a></li>
                    </ul>
                </li>
                <%
                    }
                %>
                <li class="nav-label">Service</li>
                <li> <a class="has-arrow  " href="#" aria-expanded="false"><i class="fa fa-bar-chart"></i><span class="hide-menu">Report</span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="DailyReport">Daily Report</a></li>
                    </ul>
                </li>
                <%
                    if(answer.equals(ConstantParameters.USER_SIMPLE)) {
                %>
                <li> <a class="has-arrow  " href="#" aria-expanded="false"><i class="fa fa-suitcase"></i><span class="hide-menu">Content</span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="UploadContent">Upload</a></li>
                    </ul>
                </li>
                <%
                    }
                %>
                <%
                    if(answer.equals(ConstantParameters.USER_MASTER)){
                %>
                <li> <a class="has-arrow" href="#" aria-expanded="false"><i class="fa fa-suitcase"></i><span class="hide-menu">New</span></a>
                    <ul aria-expanded="false" class="collapse">
                        <li><a href="AddService">Add Service</a></li>
                    </ul>
                </li>
                <%
                    }
                %>
            </ul>
        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</div>
