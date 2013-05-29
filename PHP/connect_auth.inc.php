<?php
// Inclusion de connexion bdd
include ("connect_bdd.inc.php");

// Selection du doublet login/password
$result = $connexion->query("SELECT login, password FROM AUTH WHERE login = '$login' ;"); 
// Stockage du résultat dans un tableau
$donnees = $result->fetch();

?>
