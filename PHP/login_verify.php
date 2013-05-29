<?php

$login=$_POST['login'];
$password=$_POST['password'];
$passwordmd5=md5($password);

// Inclusion de connect_auth
include("connect_auth.inc.php");

$loginbdd = $donnees['login'];
$passwordbdd = $donnees['password'];

if ($login == $loginbdd AND $passwordmd5 == $passwordbdd)
$value = "ok"; // for correct login response
else
$value = "false"; // for incorrect login response 

$data = array('result' => $value);

print(json_encode($data));

// On ferme la connexion PDO
$connexion=null;
