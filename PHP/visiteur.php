<?php
$login=$_POST['login'];

// Inclusion de connect_bdd
include("connect_bdd.inc.php");

// On crée la requête PDO et la variable $resultat prend pour valeur le resultat de la requête
$resultat = $connexion->query("select * from Visiteur WHERE login = '$login' ;");
// Le résultat est sous forme de tableau, on utilise fetch(PDO::FETCH_ASSOC) pour parcourir le tableau et stocker le resultat dans la variable ligne
$ligne = $resultat->fetch(PDO::FETCH_ASSOC);

$id_vis = $ligne['id'];
$nom_vis = $ligne['nom'];
$prenom_vis = $ligne['prenom'];
$cp_vis = $ligne['cp'];
$adresse_vis = $ligne['adresse'];
$ville_vis = $ligne['ville'];

$data = array('id_vis' => $id_vis,'nom_vis' => $nom_vis, 'prenom_vis' => $prenom_vis, 'adresse_vis' => $adresse_vis, 'cp_vis' => $cp_vis, 'ville_vis' => $ville_vis);

print(json_encode($data));

// On ferme la connexion PDO
$connexion=null;

