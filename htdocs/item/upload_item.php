<?php
$postdata = file_get_contents("php://input");
if($postdata)echo "received!";
$request = json_decode($postdata, true);
$category = $request['cate'];
$price = $request['pric'];
$description = $request['desc'];
$file = $request['file'];
//echo $category;
//echo $price;
//echo $description;
//echo $file;
//连接数据库
$server = "127.0.0.1";
$username = "root";
$password = "root";
$db_name = "CS411";
$conn = new mysqli($server, $username, $password, $db_name);
if ($conn->connect_errno)
    echo "Fail to connect!";
if ($file == "" || $price == "") {//注册
    echo json_encode(array('code'=>0,'msg'=>"Please fill in necessary information!"));
}else{//信息不为空
//        echo "可以进行下一步";
    $sql = "INSERT INTO items (path,category,price,description)VALUES ('$file','$category','$price','$description')";
            if ($conn->query($sql) === TRUE) {
                echo json_encode(array('code'=>1,'msg'=>'Signup successfully!'));
            } else {
                echo json_encode(array('code'=>2,'msg'=>'Unkown mistake!'));
            }
}

$conn->close();
?>