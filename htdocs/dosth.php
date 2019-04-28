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
exec("python ./InferSent/encoder/2.py");
//
// echo $cmd;
// echo json_encode($arr);
?>
