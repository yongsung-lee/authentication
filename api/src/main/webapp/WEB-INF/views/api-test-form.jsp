<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>API test</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>

<p>1. /open-api/hello (no token)</p>

<input type="button" id="hello" value="search">
<br>
<textarea id="helloResponse" rows="5" cols="100">
</textarea>

<br>
<p>2. /oauth/token </p>

<input type="text" id="username" placeholder="username">
<input type="password" id="password" placeholder="password">
<input type="button" id="getToken" value="create token">
<input type="text" id="accessToken" placeholder="accessToken">
<input type="text" id="refreshToken" placeholder="refreshToken">

<br>

<p>3. /api/users ( ADMIN, USER )</p>

<input type="button" id="users" value="search">
<br>
<textarea id="usersResponse" rows="5" cols="100">
</textarea>

<br>
<p>4. /api/user/{id} ( ADMIN )</p>

<input type="text" id="userId" placeholder="user ID">
<input type="button" id="user" value="search">
<br>
<textarea id="userResponse" rows="5" cols="100">
</textarea>

</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){

        $('#getToken').click(function(){

            var data = {
                username : $('#username').val(),
                password : $('#password').val(),
                grant_type : 'password',
                scope : 'any'
            };

            $.ajax({
                url:'http://localhost:8080/oauth/token',
                type:'post',
                data: data,
                beforeSend : function(xhr){
                    xhr.setRequestHeader("Authorization", "Basic YnJhbmR1eDoxMjM0");
                    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                },
                success:function(data){
                    $('#accessToken').val(data.access_token);
                    $('#refreshToken').val(data.refresh_token);
                },
                error:function(response, status, error){
                    if (response.status != 200) {
                        alert("code:"+response.status+"\n"+"message:"+response.responseText+"\n"+"error:"+error);
                    }
                }
            })
        });

        $('#hello').click(function(){
            $('#helloResponse').html('');
            $.ajax({
                url:'http://localhost:8888/open-api/hello',
                type:'get',
                success:function(data){
                    $('#helloResponse').html(JSON.stringify(data));
                },
                error:function(response, status, error){
                    if (response.status != 200) {
                        alert("code:"+response.status+"\n"+"message:"+response.responseText+"\n"+"error:"+error);
                    }
                }
            })

        });

        $('#users').click(function(){
            $('#usersResponse').html('');
            $.ajax({
                url:'http://localhost:8888/api/users',
                type:'get',
                beforeSend : function(xhr){
                    xhr.setRequestHeader("Authorization", "Bearer " + $('#accessToken').val());
                    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                },
                success:function(data){
                    $('#usersResponse').html(JSON.stringify(data));
                },
                error:function(response, status, error){
                    if (response.status != 200) {
                        alert("code:"+response.status+"\n"+"message:"+response.responseText+"\n"+"error:"+error);
                    }
                }
            })

        });

        $('#user').click(function(){
            $('#userResponse').html('');
            $.ajax({
                url:'http://localhost:8888/api/user/' + $('#userId').val(),
                type:'get',
                beforeSend : function(xhr){
                    xhr.setRequestHeader("Authorization", "Bearer " + $('#accessToken').val());
                    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                },
                success:function(data){
                    $('#userResponse').html(JSON.stringify(data));
                },
                error:function(response, status, error){
                    if (response.status != 200) {
                        alert("code:"+response.status+"\n"+"message:"+response.responseText+"\n"+"error:"+error);
                    }
                }
            })

        });

    });
</script>