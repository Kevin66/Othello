<h1>Statistiques</h1>

<?php
	$db = mysql_connect('localhost', 'root', 'd56die++') or die("Erreur de connexion");
	mysql_select_db('othello', $db) or die("Erreur selection de la table");
	$req = mysql_query('SELECT * FROM `Partie`');
	$totalPartie = mysql_num_rows($req);
	
	
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "enCours"');
	$PartieEnCours = mysql_num_rows($req);
	$PartieFini = $totalPartie - $PartieEnCours; 
	
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "Win1"');
	$GagneNoir = mysql_num_rows($req);
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "Win2"');
	$GagneBlanc = mysql_num_rows($req);
	
	
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "Win1" && `typeJ1` = "Human"');
	$GagneNoirHuman = mysql_num_rows($req);
	
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "Win2" && `typeJ2` = "Human"');
	$GagneBlancHuman = mysql_num_rows($req);
	
	$totalGagneHuman = $GagneNoirHuman + $GagneBlancHuman;
	$totalGagneIA = $PartieFini - $totalGagneHuman;
	echo "Nombre total de parties : ".$totalPartie."</br>
	     <BLOCKQUOTE>Parties finies : ".$PartieFini."</br>
	     Parties en cours : ".$PartieEnCours."</BLOCKQUOTE>
	     Nombre parties gagnées par joueur Noir : ".$GagneNoir."</br>
	     Nombre parties gagnées par joueur Blanc : ".$GagneBlanc."</br></br>
	     Nombre parties gagnées par un humain : ".$totalGagneHuman."</br>
	     Nombre parties gagnées par un ordinaeur : ".$totalGagneIA."</br></br>
	     
	     Liste des parties en cours : </br><BLOCKQUOTE>";
	     
	
	
	$req = mysql_query('SELECT * FROM `Partie` WHERE `statut` = "enCours"');
	// Pour lister l'ensemble des réponses à ta question
	while($ligne = mysql_fetch_array($req))
	{
		 echo 'id : '.$ligne[id].'<br>';  
	} 
?>
