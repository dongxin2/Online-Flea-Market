<?php
$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
$category = $request['cate'];
$price = $request['pric'];
$description = $request['desc'];
$file = $request['file'];
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "CS411";
$conn = new mysqli($server, $username, $password, $db_name);
if ($conn->connect_errno)
    echo "Fail to connect!";
if ($file == "" || $price == "") {
    echo json_encode(array('code'=>0,'msg'=>"Please fill in necessary information!"));
}else{
    $cmd = exec("python ./SpamClassifier.py "."\"".$description."\"",$out);
    if($cmd =="HAM")
        $approve = 1;
    else $approve = 0;
    $sql = "INSERT INTO items (path,category,price,description,approve)VALUES ('$file','$category','$price','$description','$approve')";
            if ($conn->query($sql) === TRUE) {
                echo json_encode(array('code'=>1,'msg'=>'Signup successfully!'));
            } else {
                echo json_encode(array('code'=>2,'msg'=>'Unkown mistake!'));
            }
}
// upload item data
    $sql = "SELECT * FROM items";
    $totalItem = 0;
    if($result = mysqli_query($conn,$sql))
    {
        while($row=mysqli_fetch_row($result))
        {
            $arr[$totalItem][0] = $row[0];
            $arr[$totalItem][1] = str_replace ( chr( 194 ) . chr( 160 ), "", $row[4] );
            $arr[$totalItem][2] = str_replace ( chr( 194 ) . chr( 160 ), '', $row[5] );//        $arr[$totalItem][2] = $row[5];
            $totalItem++;
        }
    }
    $jarray = json_encode($arr);
    print_r("jarray = $jarray\n");
    // output data into a txt file
    exec("python ./output.py ".json_encode($jarray)."",$output);
    // run the trainning program

    // output relation matrix into database
    exec("python ./findrecommand.py ", $output);
    $len = sizeof($output);
    for ($x = 0; $x < $len; $x++) {
        print_r("loop:$x\n");
        $output1 = substr($output[$x],1,-1);
        $res = preg_split('~, ~',$output1,11,PREG_SPLIT_NO_EMPTY);
        $sql = "SELECT * from recommand where item_id=" . substr($res[0], 1, -1);
        $result = mysqli_query($conn, $sql);
        if(!mysqli_fetch_array($result)){
            $sql = "INSERT INTO recommand (item_id) VALUES (" . $res[0] . ")";
            print_r("Result is empty");
            $result = mysqli_query($conn, $sql);//执行语句
            $sql = "SELECT * from recommand where item_id=" . $res[0];
            if (empty(mysqli_query($conn, $sql))) {
                print_r("$res[0]\n");
                print_r("Fail to insert!");
            }
        }
        for ($y = 1; $y < 11; $y++) {
            $res1 = substr($res[$y], 1, -1);
            $sql = "UPDATE recommand SET item" . ($y) . "=" . $res1 . " where item_id = " . $res[0] . "";
            print_r("$sql\n");
            $result = mysqli_query($conn, $sql);
        }
    }
$conn->close();
?>