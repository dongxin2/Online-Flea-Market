<!--/**-->
<!-- * Created by PhpStorm.-->
<!-- * User: neil-->
<!-- * Date: 2019-03-27-->
<!-- * Time: 15:55-->
<!-- */-->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>update db...</title>
</head>
<body>
<?php
$itemid=$_REQUEST["itemid"];
$action=$_REQUEST["action"];
echo $itemid;
echo $action;
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($server, $username, $password, $db_name);
if($conn->connect_errno)
    echo "Fail to connect!";
$value = 0;
if($action == "approve")
    $value = 1;
$sql = "UPDATE items SET approve = $value where item_id = $itemid;";
if($conn->query($sql) === TRUE){
    echo "finish!";
}
else echo "false!";
$conn->close();
?>
<script>
    alert("UPDATE SUCCESSFULLY!");
    window.location.href="http://localhost:8888/list_view.html";
</script>
</body>
</html>
<!--rel="external nofollow" rel="external nofollow" -->
