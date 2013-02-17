<?php

include "./Othello.php";

if(isset($_GET["jNOIR"])){
	$jNOIR = $_GET["jNOIR"];
}else{
	$jNOIR = "";
}
if(isset($_GET["win"])){
	$jWin = $_GET["win"];
}else{
	$jWin = "";
}
if(isset($_GET["jBLANC"])){
	$jBLANC = $_GET["jBLANC"];
}else{
	$jNOIR = "";
}
if(isset($_GET["niv1"])){
	$niv1 = $_GET["niv1"];
}else{
	$niv1 = "";
}
if(isset($_GET["niv2"])){
	$niv2 = $_GET["niv2"];
}else{
	$niv2 = "";
}

if(isset($_GET["id"])){
	$id = $_GET["id"];
}else{
	$id = "";
}

if(isset($_GET["newgame"])){
	$newgame = $_GET["newgame"];
}else{
	$newgame = "";
}

if(isset($_GET["IAniv"])){
	$IAniv = $_GET["IAniv"];
}else{
	$IAniv = "";
}

if(isset($_GET["loadgame"])){
	$loadgame = $_GET["loadgame"];
}else{
	$loadgame = "";
}

if(isset($_GET["li"])){
	$li = $_GET["li"];
}else{
	$li = "";
}

if(isset($_GET["co"])){
	$co = $_GET["co"];
}else{
	$co = "";
}

if(isset($_GET["jo"])){
	$joueur = $_GET["jo"];
}else{
	$joueur = "";
}

	if(($joueur == "1" || $joueur == "2") && $IAniv != "" && $id != "")
	{
		header("Content-type:text/xml");
		$othello = new Othello();
		$xml = new DomDocument();
		$name = "othello_".$id.".xml";
		$xml->load($name);
		$xmlColone = $xml->getElementsByTagName('colone');
		$i = 0;
		$j = 0;
		$sPlateau = "";
		foreach($xmlColone as $node)
		{
			$othello->tab[$i][$j] = $node->nodeValue;
			$j = $j + 1;
			$sPlateau = $sPlateau."".$node->nodeValue;
			if($j == 8)
			{
				$j = 0;
				$i = $i+1;
			}
		}
		$cmd = "Othello/othello ".$IAniv." ".$joueur." ".$sPlateau."";
		$lastLine = system($cmd,$return);
		$co = $return%10;
		$li = ($return - $co)/10;
		$nbPions = $othello->retournePion($li,$co,$joueur);
		$xmlColone = $xml->getElementsByTagName('colone');
		if($nbPions != "")
		{
			$i = 0;
			$j = 0;
			foreach($xmlColone as $node)
			{
				$node->nodeValue = $othello->tab[$i][$j];
				if($i == $li && $j == $co)
					$node->nodeValue = $joueur;
				$j = $j + 1;
				if($j == 8)
				{
					$j = 0;
					$i = $i+1;
				}
			}
		
		}
		else
		{
			$xmlPartie = $xml->getElementByTagName('partie');
			if($joueur == "1")
			{
				
				$xmlPartie->item(0)->setAttribute('main',"2");
			}
			else
			{
			
				$xmlPartie->item(0)->setAttribute('main',"1");
			}
		}
			
		$xml->save("othello_".$id.".xml");
		$xml = simplexml_load_file("othello_".$id.".xml");
		echo $xml->asXml();
	}
	else if($jWin != "" && $id != "")
	{
		header("Content-type:text/xml");
		$db = mysql_connect('localhost', 'root', 'd56die++') or die("Erreur de connexion");
		mysql_select_db('othello', $db) or die("Erreur selection de la table");
		$reqString = "UPDATE `othello`.`Partie` SET `statut` = 'Win".$jWin."' WHERE `Partie`.`id` =".$id;
		$req = mysql_query($reqString);
		$xml = simplexml_load_file('othello.xml');
		echo $xml->asXml();
	
	}
	else if($newgame == "1")
	{
		header("Content-type:text/xml");
		$newID = 0;
		$db = mysql_connect('localhost', 'root', 'd56die++') or die("Erreur de connexion");
		mysql_select_db('othello', $db) or die("Erreur selection de la table");
		$req = mysql_query('SELECT * FROM `Partie` WHERE `id` = (SELECT MAX(`id`) FROM `Partie`)');
		$data = mysql_fetch_assoc($req);
		$newID = $data['id']+1;
		$xml = new DomDocument();
		$name = "othello.xml";
		$xml->load($name);
		$xmlPartie = $xml->getElementsByTagName('partie');
		$xmlPartie->item(0)->setAttribute('id',$newID);
		$xmlPartie->item(0)->setAttribute('tour',"1");
		$xmlPartie->item(0)->setAttribute('main',"1");
		$joueur1 = $jNOIR.$niv1;
		$xmlPartie->item(0)->setAttribute('type1',$joueur1);
		$joueur2 = $jBLANC.$niv2;
		$xmlPartie->item(0)->setAttribute('type2',$joueur2);
		$reqSql = "INSERT INTO Partie(id,typeJ1,typeJ2,statut) VALUES('$newID','$joueur1','$joueur2','enCours')";
		mysql_query($reqSql) or die("Erreur requete sql");
		mysql_close($db);
		$xml->save('othello_'.$newID.'.xml');
		$xml = simplexml_load_file('othello_'.$newID.'.xml');
		echo $xml->asXml();
	}
	else if($loadgame != "")
	{
		header("Content-type:text/xml");
		$xml = simplexml_load_file('othello_'.$loadgame.'.xml');
		echo $xml->asXml();
	}

	else if(($joueur == "1" || $joueur == "2") && $li !="" && $co != "" && $id != "")
	{	
		header("Content-type:text/xml");
		$othello = new Othello();
		$xml = new DomDocument();
		$name = "othello_".$id.".xml";
		$xml->load($name);
		$xmlColone = $xml->getElementsByTagName('colone');
		$i = 0;
		$j = 0;
		foreach($xmlColone as $node)
		{
			$othello->tab[$i][$j] = $node->nodeValue;
			$j = $j + 1;
			if($j == 8)
			{
				$j = 0;
				$i = $i+1;
			}
		}
		$nbPions = $othello->retournePion($li,$co,$joueur);
		if($nbPions > 0)
		{
			$i = 0;
			$j = 0;
			foreach($xmlColone as $node)
			{
				$node->nodeValue = $othello->tab[$i][$j];
				if($i == $li && $j == $co)
					$node->nodeValue = $joueur;
				$j = $j + 1;
				if($j == 8)
				{
					$j = 0;
					$i = $i+1;
				}
			}
			
			if($joueur == "1")
			{
				$joueur = "2";
			}
			else
			{
				$joueur = "1";
			}
			$xmlPartie = $xml->getElementsByTagName('partie');
			$xmlPartie->item(0)->setAttribute('main',$joueur);
			foreach($xmlPartie as $xmlPartie)
			{
				$xmlTour = $xmlPartie->getAttribute('tour');
			}
			
			$tour = intval($xmlTour);
			$tour = $tour + 1;
			$xmlTour = strval($tour);
			
			$xmlPartie = $xml->getElementsByTagName('partie');
			$xmlPartie->item(0)->setAttribute('tour',$xmlTour);
		
		}
		
		$xml->save("othello_".$id.".xml");
		$xml = simplexml_load_file("othello_".$id.".xml");
		echo $xml->asXml();
	}
	else
	{
		header('Location: ./site/index.php?page=1');
	}		
?>
