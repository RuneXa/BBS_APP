<?php
include("bbs_db_params.php");
 
$con = mysqli_connect(HOST,USER,PASS,DB);

$result_data = mysqli_query($con,"SELECT * FROM obs_test");
$results = array();

if(mysqli_num_rows($result_data) > 0){
    while($row = mysqli_fetch_assoc($result_data)) {
        echo "
		<td>$row[0]</td>
		<td>$row[1]</td>
		<td>$row[2]</td>
		<td>$row[3]</td>
		<td>$row[4]</td>
		<td>$row[5]</td>
		<td>$row[6]</td>
		<td>$row[7]</td>
		<td>$row[8]</td>
		";
	}
}

mysqli_close($con);
?>