<?php
    $host='localhost';
	$uname='root';
	$pwd='';
	$db="goldentulip";

	$con = mysql_connect($host,$uname,$pwd) or die("connection failed");
	mysql_select_db($db,$con) or die("db selection failed");

	$nom=$_REQUEST['nom'];
$prenom=$_REQUEST['prenom'];
$genre=$_REQUEST['genre'];
$email=$_REQUEST['email'];
$tel=$_REQUEST['tel'];
$cin=$_REQUEST['cin'];

	$flag['code']=0;

	if($r=mysql_query("INSERT INTO client (`nom`,`prenom`,`genre`,`email`,`tel`,`cin`) VALUES ('$nom','$prenom','$genre','$email','$tel','$cin')",$con))
	{
		$flag['code']=1;
		echo"hi";
	}


	print(json_encode($flag));
	
	mysql_close($con);
?>