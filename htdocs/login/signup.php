<?php
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-02-15
 * Time: 15:36
 */
$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
$names = $request['name'];
$pwd = $request['pwd'];
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($server, $username, $password, $db_name);
if ($conn->connect_errno)
    echo "Fail to connect!";
    if ($names == "" || $pwd == "") {//注册
        echo json_encode(array('code'=>0,'msg'=>"Username or password could not be empty!"));
    }else{//用户名不为空
//        echo "可以进行下一步";
        $result  = filter_var($names, FILTER_VALIDATE_EMAIL);
        if($result == false)
            echo json_encode(array('code'=>1,'msg'=>"Invalid username!Please signup with your email!"));
        else {$sql = "SELECT * FROM users WHERE user_id='$names'";
        $result = mysqli_query($conn,$sql);
        $row = mysqli_fetch_array($result);
        if($row){
            echo json_encode(array('code'=>2,'msg'=>"Username already exists!"));
        }else{
            $sql = "INSERT INTO users (user_id,user_group,password)VALUES ('$names','1','$pwd')";
            if ($conn->query($sql) === TRUE) {
                echo json_encode(array('code'=>3,'msg'=>'Signup successfully!'));
            } else {
                echo json_encode(array('code'=>4,'msg'=>'Unkown mistake!'));
            }}}
    }

$conn->close();
?>
