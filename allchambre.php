<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM chambre") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
   
    $response["chambres"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $chambre = array();
        $chambre["id_chambre"] = $row["id_chambre"];
        $chambre["type"] = $row["type"];
        $chambre["emplacement"] = $row["emplacement"];
		$chambre["description"] = $row["description"];
		$chambre["image"] = base64_encode ($row["image"]);
		
       
 
        // push single product into final response array
        array_push($response["chambres"], $chambre);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No chambre found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>