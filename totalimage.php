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
$result = mysql_query("select count(1) FROM photo");
$row = mysql_fetch_array($result);

$total = $row[0];
echo "Total rows: " . $total;
 
    // echo no users JSON
    echo json_encode( $total);

?>