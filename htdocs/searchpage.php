<?php
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-03-28
 * Time: 16:39
 */
$postdata = file_get_contents("php://input");
$request = json_decode($postdata, true);
//echo $request;
$cate = $request['cate'];
//echo $cate;
$server = "localhost";
$username = "root";
$password = "root";
$db_name = "cs411project";
$conn = new mysqli($server, $username, $password, $db_name);
if($conn->connect_errno)
    echo "Fail to connect!";
$sql = "SELECT * FROM items where category = '$cate'";
$totalItem = 0;
if($result = mysqli_query($conn,$sql))
{
    while($row=mysqli_fetch_row($result))
//        echo "I'm here!";
    {
        $arr['data_content'][$totalItem]['item_id']=$row[0];
        $arr['data_content'][$totalItem]['image_url']=$row[4];
        $arr['data_content'][$totalItem]['category']=$row[2];
        $arr['data_content'][$totalItem]['price']=$row[3];
        $arr['data_content'][$totalItem]['description']=$row[5];
        $arr['data_content'][$totalItem]['approved']=$row[6];
        $arr['data_content'][$totalItem]['seller_id']=$row[7];
        $totalItem++;
    }
}
$pageNum = $request['pageNum'];
$pageSize = 10;
$totalPage = ceil($totalItem/$pageSize);
$startItem = ($pageNum-1)*$pageSize;
$arr['totalItem'] = $totalItem;
$arr['pageSize'] = $pageSize;
$arr['totalPage'] = $totalPage;
echo json_encode($arr);
?>
