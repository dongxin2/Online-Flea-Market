<?php
$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
$names = $request['name'];
$pwd = $request['opwd'];
$npwd = $request['npwd'];
//echo $names;
//echo $pwd;
//echo $pwd;
//连接数据库
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($server, $username, $password, $db_name);
if ($conn->connect_errno) {
    echo "连接失败";
} else {
//    echo "connected!";
    //创建数据表
    $sql = "SELECT * FROM users WHERE user_id='$names'";//查询
    $result = mysqli_query($conn, $sql);//执行语句
    $row = mysqli_fetch_array($result);//拿到结果
    if ($row) {
        if ($pwd == $row[password]) {
//                            echo "correct!";
            {
                $reset = "UPDATE users SET password='$npwd' WHERE user_id='$names'";
                if($conn->query($reset) == true){
                    echo json_encode(array('code'=>0,"msg"=>"Reset successfully!"));
                }
                else echo json_encode(array('code'=>1,"msg"=>"Unknown mistake!"));
            }
        } else {
//                            echo "not correct!";
            echo json_encode(array('code'=>2,"msg"=>"Incorrect password!"));
        }
    } else {
//                echo "not a user!";
        echo json_encode(array('code'=>3,"msg"=>"Invalid username!"));
    }

}
$conn->close();
?>
