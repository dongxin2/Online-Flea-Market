<?php
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-04-09
 * Time: 18:41
 */
header("Access-Control-Allow-Origin:*");
/*星号表示所有的域都可以接受，*/
header("Access-Control-Allow-Methods:GET,POST");
$hostname = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($hostname, $username, $password, $db_name);
if($conn->connect_errno)
    echo "Fail to connect!";
    // echo "string";
$sql = "SELECT * FROM items";
$totalItem = 0;
if($result = mysqli_query($conn,$sql))
{
    while($row=mysqli_fetch_row($result))
    {
//        ereg_replace ( "\u00a0", "", $row[4] )
        $arr[$totalItem][0] = $row[0];
        $replace = array(chr( 194 ),chr( 160 ),",");
//        $arr[$totalItem][1] = $row[4];
        $arr[$totalItem][1] = str_replace ( $replace, '', "  ".$row[5] );
        $arr[$totalItem][2] = str_replace ( $replace, '', "  ".$row[6] );
//        $arr[$totalItem][2] = $row[5];
        $totalItem++;
    }
}
$jarray = json_encode($arr);
// $cmd = "python ./output.py ".($jarray)."";
// echo $cmd;
// $ret = system($cmd);
// echo $ret;

// $cmd = system("python ./output.py ".json_encode($arr)." 2>&1",$output);
$cmd = system("/Users/dongxinzhu/anaconda3/bin/python3 ./output.py ".json_encode($arr)." 2>&1",$output);

// echo $cmd;
// echo $output;
// echo $output;
// echo "python nnn";
// echo json_encode($output);
echo "herehere";
echo $cmd;
echo $output;
// $cmd = system("/Users/dongxinzhu/anaconda3/bin/python3 ./InferSent/encoder/2.py 2>&1",$output);
echo "herehere";
echo $cmd;
echo $output;
// echo $cmd;
// echo json_encode($arr);
?>
