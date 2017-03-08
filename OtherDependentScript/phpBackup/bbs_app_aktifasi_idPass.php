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

$query = "SELECT status_aktif FROM data_karyawan WHERE `employee_number` = $id";
if ($resultSelect = mysqli_query($con, $query)) {
	$row = mysqli_fetch_assoc($resultSelect);
	if($row['status_aktif'] == 0){
		$hashedPass = password_hash($pass,PASSWORD_DEFAULT);
		$query = "UPDATE data_karyawan SET `status_aktif`=1, `password`='$hashedPass' WHERE `employee_number` = $id";
		mysqli_query($con, $query);
		echo '1'; /*sukses*/
	} else {
		echo '2'; /*Status Sudah Aktif */
	}
} else {
	echo '3'; /*data tidak ada*/
}

mysqli_close($con);
 
?>