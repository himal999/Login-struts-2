<%-- 
    Document   : dashboard
    Created on : Oct 27, 2022, 5:56:01 PM
    Author     : himal
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="edu.epic.strutslogin.bean.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Dashboard</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous"/>
    </head>
    <body class="vh-100 vw-100 d-flex position-relative flex-column align-items-center">
        <%

            //checking session 
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.setHeader("Expires", "0");

            if (session.getAttribute("username") == null) {
                response.sendRedirect("index.jsp");
            } else {

                User user = (User) session.getAttribute("user");

            }


        %>
        <div  class="col-6 vw-100  position-relative d-flex justify-content-between align-items-center ms-auto bg-primary">
            <h1 class="text-start text-light row m-2">Hello  ${username} !</h1>
            <div class="d-flex flex-row ">
                <form>
                    <button type="button" class=" btn btn-danger m-2" id="btnlogout">Logout</button>
                </form>
            </div>
        </div>
        <div class="col d-flex position-relative flex-column justify-content-center align-items-center">

            <table class="table">
                <thead>
                    <tr >

                        <th scope="col">Name</th>
                        <th scope="col">NIC</th>
                        <th scope="col">Address</th>
                        <th scope="col">DOB</th>
                        <th scope="col">Email</th>
                        <th scope="col">User Name</th>
                        <th scope="col">Password</th>
                        <th scope="col">Action</th>

                    </tr>
                </thead>
                <tbody>

                    <tr>
                        <td><c:out value="${user.getFname()}"/>&nbsp;<c:out value="${user.getLname()}"/></td>
                        <td><c:out value="${user.getNic()}"/></td>
                        <td><c:out value="${user.getAddress()}"/></td>
                        <td><c:out value="${user.getDob()}"/></td>
                        <td><c:out value="${user.getEmail()}"/></td>
                        <td><c:out value="${user.getUsername()}"/></td>
                        <td><c:out value="${user.getPassword()}"/></td>
                        <td><a href="http://localhost:8080/login/update.jsp" class=" btn btn-primary m-2">Edit</a><button id="btnDelete" class="btn btn-warning">Delete</button></td>



                    </tr>


                </tbody>
            </table>








        </div>



        <script src="https://code.jquery.com/jquery-3.6.1.js" integrity="sha256-3zlB5s2uwoUzrXK3BT7AX3FyvojsraNFxCc2vC/7pNI=" crossorigin="anonymous"></script>
        <script type="text/javascript">



            $('#btnDelete').click(function () {


                if (confirm("Are you sure want to delete account?")) {
                    $.ajax({
                        type: "delete",
                        url: "http://localhost:8080/login/dashboard",
                        success: function (msg) {

                            if (msg == 'true') {
                                window.location.href = "index.jsp"
                            }


                        },
                        error: function (err) {
                            console.log(err);
                        },
                    });
                }


            });



            $('#btnlogout').click(function () {
                if (confirm("Are you sure want to logout?")) {
                    $.ajax({
                        type: "get",
                        url: "http://localhost:8080/strutslogin/logout",
                        success: function (resp) {
                            var obj = resp;
                            if (obj.data == 'true') {
                                window.location.href = "index.jsp";
                            }

                        },
                        error: function (err) {
                            console.log(err);
                        }

                    });
                }
            });
        </script>
    </body>
</html>
