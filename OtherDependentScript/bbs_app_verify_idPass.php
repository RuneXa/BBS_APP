<?php
include("bbs_db_params.php");
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$json = file_get_contents('php://input');
$obj = json_decode($json); 

$id = $obj->{'id'};
$pass = $obj->{'pass'};

$query = "SELECT password, status_aktif FROM data_karyawan WHERE `employee_number` = $id";
if ($resultSelect = mysqli_query($con, $query)) {
	$row = mysqli_fetch_assoc($resultSelect);
	if($row['status_aktif'] == 1){
		if(password_verify($pass,$row['password'])){
			$query = "select employee_number, nama, departemen from data_karyawan WHERE `employee_number` = $id";
			$resultSelect = mysqli_query($con,$query);
			$resultArr = array();
			$row = mysqli_fetch_array($resultSelect);
			array_push($resultArr,	
			array('employee_number'=>$row[0],
				'nama'=>$row[1],
				'departemen'=>$row[2])
			);
			echo json_encode(array('returnCode'=>'1','returnValue'=>json_encode($resultArr))); /*Sukses*/
		} else {
			echo json_encode(array('returnCode'=>'0')); /*fail*/
		}
	} else {
		echo json_encode(array('returnCode'=>'2')); /*Status Belum Aktif */
	}

} else {
	echo json_encode(array('returnCode'=>'3')); /*data tidak ada*/
}

mysqli_close($con);
 
?>