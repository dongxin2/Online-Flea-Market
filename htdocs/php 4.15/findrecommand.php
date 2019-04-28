<?php
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-04-11
 * Time: 17:22
 */
$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
//$index = $request['index'];
$server = "127.0.0.1";
$username = "root";
$password = "root";
$db_name = "CS411";
$conn = new mysqli($server, $username, $password, $db_name);
if($conn->connect_errno)
    echo "Fail to connect!";
exec("python ./findrecommand.py ", $output);
//echo json_encode($output);
$len = sizeof($output);
for ($x = 0; $x < $len; $x++) {
    print_r("loop:$x\n");
    $output1 = substr($output[$x],1,-1);
    $res = preg_split('~, ~',$output1,11,PREG_SPLIT_NO_EMPTY);
    print_r("$res[0]\n");
    $sql = "SELECT * from recommand where item_id=".substr($res[0],1,-1);
    print_r("$sql\n");
    $result = mysqli_query($conn, $sql);
    if(!mysqli_fetch_array($result)){
//        print_r("$res[0]\n");
        $sql = "INSERT INTO recommand (item_id) VALUES (".$res[0].")";
        print_r("Result is empty\n");
        $result = mysqli_query($conn, $sql);//执行语句
        $sql = "SELECT * from recommand where item_id=".$res[0];
        if(empty(mysqli_query($conn,$sql))){
            print_r("$res[0]\n");
            print_r("Fail to insert!");
        }
    }
    for($y = 1;$y<11;$y++)
    {
        $res1 = substr($res[$y],1,-1);
        $sql = "UPDATE recommand SET item".($y)."=".$res1." where item_id = ".$res[0]."";
        print_r("$sql\n");
        $result = mysqli_query($conn,$sql);
    }
}
?>