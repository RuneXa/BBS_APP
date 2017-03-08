<?php
include("bbs_db_params.php");
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$json = file_get_contents('php://input');
$obj = json_decode($json); 
echo $json;

$tanggal = $obj->{'tanggal'};
$bulan = $obj->{'bulan'};
$employee_number = $obj->{'employee_number'};
$nama = $obj->{'nama'};
$departemen = $obj->{'departemen'};
$index_area_observed = $obj->{'area_observed'};
$observation = $obj->{'observation'};
$index_observation_category = $obj->{'observation_category'};
$index_risk_category = $obj->{'risk_category'};

$query = "INSERT INTO obs_test VALUES ('$tanggal','$bulan','$employee_number','$nama','$departemen',$index_area_observed,'$observation',$index_observation_category,$index_risk_category)";
echo $query;
if(mysqli_query($con, $query)){
    echo "Records added successfully.";
} else{
    echo "ERROR: Could not able to execute $query. " . mysqli_error($con);
}

mysqli_close($con);
 
?>