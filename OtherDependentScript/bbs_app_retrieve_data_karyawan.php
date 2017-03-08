<?php
include("bbs_db_params.php");
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select employee_number, nama, departemen from data_karyawan";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('employee_number'=>$row[0],
'nama'=>$row[1],
'departemen'=>$row[2]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>