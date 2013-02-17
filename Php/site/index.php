<html>
  <head>
    <title>Mon Othello</title>
  <style type="text/css">
  #sommaire
  {
  position:absolute;
  background-color:green;
  left:10px;
  width:130px;
  }
 
  #page
  {
  position:absolute;
  background-color:#AAAAAA;
  left:200px;
  width:550px;
  height:1700px;
  }
  </style>
  </head>
  <body>
 
    <div id="sommaire">
        <h3>Sommaire</h3>
        <a href="index.php?page=1">Accueil</a><br/>
        <a href="index.php?page=2">Règles</a><br/>
        <a href="index.php?page=3">Télechargment</a><br/>
        <a href="index.php?page=5">Statistiques</a><br/>
    </div>
 
    <div id="page">
        <?php
	header("Content-type:text/html; charset=UTF-8");
        if (isset($_GET['page'])) $numero=$_GET['page']; else $numero='1';
        if($numero != '5')
        {
        	require 'page'.$numero.'.html';
        }
        else
        {
        	require 'page'.$numero.'.php';
        }
        ?>
    </div>
 
  </body>
</html>

