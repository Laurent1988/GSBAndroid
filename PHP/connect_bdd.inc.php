<?php

// Paramètres de connexion
$server = "localhost";
$bdd = "gsb";
$user = "root";
$pass = "d072812198788lv";

try
{
        // On créer un connexion à la base de données via un objet PDO
        $connexion = new PDO('mysql:host='.$server.';dbname='.$bdd, $user, $pass);
        // On fixe l'encodage en UTF-8
        $connexion->query("SET NAMES UTF8");
}
catch (Exception $e)
{
        die('Erreur : ' . $e->getMessage());
}
?>
