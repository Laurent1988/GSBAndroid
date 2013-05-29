<?php
$login=$_GET['login'];

// Inclusion de connect_bdd
include("connect_bdd.inc.php");

// On crée la requête PDO et la variable $resultat prend pour valeur le resultat de la requête
$resultat = $connexion->query("SELECT id, nom, prenom FROM Visiteur WHERE login = '$login' ;");
// Le résultat est sous forme de tableau, on utilise fetch(PDO::FETCH_ASSOC) pour parcourir le tableau et stocker le resultat dans la variable ligne
$ligne = $resultat->fetch(PDO::FETCH_ASSOC);

$id_vis = $ligne['id'];
$nom_vis = $ligne['nom'];
$prenom_vis = $ligne['prenom'];

// Requête pour les fiches de frais
$resultat = $connexion->query("SELECT mois, idFraisForfait, quantite FROM LigneFraisForfait WHERE idVisiteur = '$id_vis' ;");

$i=0;

// Boucle while pour enregistrer chaque ligne de frais dans un array.
while($ligne = $resultat->fetch(PDO::FETCH_ASSOC)) {
  ${'moisVal'.$i} = $ligne["mois"];
  ${'idFraisForfaitVal'.$i} = $ligne["idFraisForfait"];
  ${'quantiteVal'.$i} = $ligne["quantite"];
  ${'fraisInfo'.$i} = array('mois'.$i => ${'moisVal'.$i}, 'idFraisForfait'.$i => ${'idFraisForfaitVal'.$i}, 'quantite'.$i => ${'quantiteVal'.$i});
  $i++;
}

// Total des fiches de frais (Attention commence à 0)
$nbFiches = $i;

// On initialise l'array fraisInfo
$fraisInfo = array();

// Boucle for pour ajouter l'ensemble des fiches de frais dans l'array fraisInfo
for ($i=0;$i<$nbFiches;$i++){
$fraisInfo = $fraisInfo + ${'fraisInfo'.$i};
}

// On ajoute l'array fraisInfo dans l'array data
$data = array('id_vis' => $id_vis, 'nom_vis' => $nom_vis, 'prenom_vis' => $prenom_vis, 'nbFiches' => $nbFiches, 'Frais' => $fraisInfo);
print(json_encode($data));

// On ferme la connexion PDO
$connexion=null;

