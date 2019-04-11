<?php
header("Access-Control-Allow-Origin:*");
/*星号表示所有的域都可以接受，*/
header("Access-Control-Allow-Methods:GET,POST");
/**
 * Created by PhpStorm.
 * User: neil
 * Date: 2019-03-21
 * Time: 14:25
 */
 echo $files;
$files = $_FILES["img"];
//if($files)echo "received!";
//echo $files;
//$filepath='items/';
//print_r($_FILES);
if(!$files["error"]) {//没有出错
    //判断上传文件类型为png或jpg且大小不超过1024000B
//        echo "im here2";
        //防止文件名重复
        $filename = "items/" . time() . $files["name"];
        //检查文件或目录是否存在
        if (!file_exists($filename)) {
//            echo "im here3";
//            echo $filename;
            $res = move_uploaded_file($files['tmp_name'],$filename);//将临时地址移动到指定地址
            if ($res) {
//                echo "im here4";
                $data['head_img'] = "http://localhost:8888/".$filename;//图片地址
                echo "http://localhost:8888/".$filename;
            }
            else {
                echo "fail to upload!";
            }
        }
}
