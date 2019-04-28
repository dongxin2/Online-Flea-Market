<?php
header("Access-Control-Allow-Origin:*");
/*星号表示所有的域都可以接受，*/
header("Access-Control-Allow-Methods:GET,POST");

$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
$seller = $request['seller'];
$name = $request['name'];
$category = $request['cate'];
$price = $request['pric'];
$description = $request['desc'];
$file = $request['file'];
// echo $category;
// echo $price;
// echo $description;
// echo $file;
//连接数据库
// $server = "127.0.0.1";
$hostname = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($hostname, $username, $password, $db_name);
if ($conn->connect_errno)
    echo "Fail to connect!";
if ($file == "" || $price == "") {//注册
    echo json_encode(array('code'=>0,'msg'=>"Please fill in necessary information!"));
}else{//信息不为空
//        echo "可以进行下一步";
    $cmd = exec("python ./SpamClassifier.py "."\"".$description."\"",$out);
    echo $cmd;
    if($cmd == 'HAM')
      $approve = 1;
    else $approve = 0;
    $sql = "INSERT INTO items (name, image_url,category,price,description,approve,seller_id)VALUES ('$name','$file','$category','$price','$description', '$approve', '$seller')";
    if ($conn->query($sql) === TRUE) {
        echo json_encode(array('code'=>1,'msg'=>'Signup successfully!'));
    } else {
        echo json_encode(array('code'=>2,'msg'=>'Unkown mistake!'));
    }
}

// upload item data
// $sql = "SELECT * FROM items";
// $totalItem = 0;
// if($result = mysqli_query($conn,$sql))
// {
//     while($row=mysqli_fetch_row($result))
//     {
//         $arr[$totalItem][0] = $row[0];
//         $arr[$totalItem][1] = str_replace ( chr( 194 ) . chr( 160 ), "", $row[4] );
//         $arr[$totalItem][2] = str_replace ( chr( 194 ) . chr( 160 ), '', $row[5] );//        $arr[$totalItem][2] = $row[5];
//         $totalItem++;
//     }
// }
// $jarray = json_encode($arr);
// print_r("jarray = $jarray\n");
// output data into a txt file
// exec("python ./output.py ".json_encode($jarray)."",$output);
// run the trainning program

// output relation matrix into database
// exec("python ./findrecommand.py ", $output);
// $len = sizeof($output);
// for ($x = 0; $x < $len; $x++) {
//     // print_r("loop:$x\n");
//     $output1 = substr($output[$x],1,-1);
//     $res = preg_split('~, ~',$output1,11,PREG_SPLIT_NO_EMPTY);
//     $sql = "SELECT * from recommand where item_id=" . substr($res[0], 1, -1);
//     $result = mysqli_query($conn, $sql);
//     if(!mysqli_fetch_array($result)){
//         $sql = "INSERT INTO recommand (item_id) VALUES (" . $res[0] . ")";
//         // print_r("Result is empty");
//         $result = mysqli_query($conn, $sql);//执行语句
//         $sql = "SELECT * from recommand where item_id=" . $res[0];
//         if (empty(mysqli_query($conn, $sql))) {
//             // print_r("$res[0]\n");
//             // print_r("Fail to insert!");
//         }
//     }
//     for ($y = 1; $y < 11; $y++) {
//         $res1 = substr($res[$y], 1, -1);
//         $sql = "UPDATE recommand SET item" . ($y) . "=" . $res1 . " where item_id = " . $res[0] . "";
//         // print_r("$sql\n");
//         $result = mysqli_query($conn, $sql);
//     }

// }
$conn->close();
?>
