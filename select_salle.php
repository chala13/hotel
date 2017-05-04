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
 $capacite=$_REQUEST['capacite'];
 intval($capacite);
// get all products from products table

$result = mysql_query("SELECT *FROM sallereunion WHERE reserver = 0 and capacite < $capacite") or die(mysql_error());
 if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["salles"] = array();

    while ($row = mysql_fetch_array($result)) {
        // temp user array
     
     
        $salle["nom"] = $row["nom"];
		$salle["capacite"] = $row["capacite"];
        $salle["image"] = base64_encode ($row["image"]);
	 array_push($response["salles"], $salle);
       
 
}
echo(json_encode($response));
 }
?>