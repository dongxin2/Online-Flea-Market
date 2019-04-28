<?php
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-04-09
 * Time: 18:41
 */
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "CS411";
$conn = new mysqli($server, $username, $password, $db_name);
if($conn->connect_errno)
    echo "Fail to connect!";
$sql = "SELECT * FROM items";
$totalItem = 0;
if($result = mysqli_query($conn,$sql))
{
    while($row=mysqli_fetch_row($result))
    {
//        ereg_replace ( "\u00a0", "", $row[4] )
        $arr[$totalItem][0] = $row[0];
//        $arr[$totalItem][1] = $row[4];
        $arr[$totalItem][1] = str_replace ( chr( 194 ) . chr( 160 ), "", $row[4] );
        $arr[$totalItem][2] = str_replace ( chr( 194 ) . chr( 160 ), '', $row[5] );
//        $arr[$totalItem][2] = $row[5];
        $totalItem++;
    }

}
$jarray = json_encode($arr);
echo $arr;
echo $jarray;
$cmd = system("python ./output.py ".json_encode($jarray)."");
//echo json_encode($arr);
?>
