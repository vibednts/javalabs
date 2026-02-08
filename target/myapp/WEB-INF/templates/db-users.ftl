<html>
<head><title>Users DB</title></head>
<body>
<h2>Users from MySQL</h2>

<table border="1">
    <tr><th>ID</th><th>Name</th><th>Email</th></tr>

    <#list users as u>
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
        </tr>
    </#list>

</table>

</body>
</html>
s