<?php
include("bbs_db_params.php");

$con = mysqli_connect(HOST,USER,PASS,DB);

$setExcelName = "data";
$con = mysqli_connect(HOST,USER,PASS,DB);

$result_data = mysqli_query($con,"SELECT * FROM obs_test");
$results = array();

if(mysqli_num_rows($result_data) > 0){
    while($row = mysqli_fetch_assoc($result_data)) {
        if (!$flag) {
            // display field/column names as first row
            echo implode("\t", array_keys($row)) . "\r\n";
            $flag = true;
        }
        echo implode("\t", array_values($row)) . "\r\n";
	}
}

//This Header is used to make data download instead of display the data
header("Content-type: application/octet-stream");
header("Content-Disposition: attachment; filename=".$setExcelName."_Report.xls");
header("Pragma: no-cache");
header("Expires: 0");

mysqli_close($con);
?>