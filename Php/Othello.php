<?php
class Othello{	
	public $tab = array(array());
	
	public function __construct(){
	}
	public function __destruct(){
	}
	
	
	public function testCase($d,$li,$co,$joueur)
	{
		$ok = 0;
		$nombrePionRetourne = 0;
		if($joueur == 1)
			$joueurInverse = 2;
		else
			$joueurInverse = 1;
		
		if(($li > -1 || $li < 8) && ($co > -1 || $co < 8))
		{
			switch($d)
			{
				case 0 :
				for($i=$li-1;$i>-1;$i--)
				{
					if($i < 0)
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$co] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$co] == $joueur)
					{
						$i = -1;
						$ok = 1;
					}
					else
					{	
						$i = -1;
						$nombrePionRetourne = 0;
					}
				}
				break;
			case 1 : 
				$i = $li-1;
				$j = $co+1;
				
				while($i > -1 && $j < 8)
				{
					if($i < 0 || $j > 7)
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$j] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$j] == $joueur)
					{
						$i = -1;
						$ok = 1;
					}
					else
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					$i--;
					$j++;
				}
			break;
			case 2 : 
				for($i=$co+1;$i<8;$i++)
				{
					if($i > 7)
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$li][$i] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$li][$i] == $joueur)
					{
						$i = 8;
						$ok = 1;
					}
					else
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
				}
			break;
			case 3 : 
				$i = $li+1;
				$j = $co+1;
				
				while($i < 8 && $j < 8)
				{
					if($i > 7 || $j > 7)
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$j] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$j] == $joueur)
					{
						$i = 8;
						$ok = 1;
					}
					else
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
					$i++;
					$j++;
				}
			break;
			case 4 : 
				for($i=$li+1;$i<8;$i++)
				{
					if($i > 7)
					{
						$i = _;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$co] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$co] == $joueur)
					{
						$i = 8;
						$ok = 1;
					}
					else
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
				}
			break;
			case 5 : 
				$i = $li+1;
				$j = $co-1;
				
				while($i < 8 && $j > -1)
				{
					if($i > 7 || $j < 0)
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$j] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$j] == $joueur)
					{
						$i = 8;
						$ok = 1;
					}
					else
					{
						$i = 8;
						$nombrePionRetourne = 0;
					}
					$i++;
					$j--;
				}
			break;
			case 6 : 
				for($i=$co-1;$i>-1;$i--)
				{
					if($i < 0)
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$li][$i] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$li][$i] == $joueur)
					{
						$i = -1;
						$ok = 1;
					}
					else
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
				}
			break;
			case 7 : 
				$i = $li-1;
				$j = $co-1;
				
				while($i > -1 && $j > -1)
				{
					if($i < 0 || $j < 0)
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					else if($this->tab[$i][$j] == $joueurInverse)
					{
						$nombrePionRetourne++;
					}
					else if($this->tab[$i][$j] == $joueur)
					{
						$i = -1;
						$ok = 1;
					}
					else
					{
						$i = -1;
						$nombrePionRetourne = 0;
					}
					$i--;
					$j--;
				}
			break;
				
			}
		}
		if($ok == 0)
		{
			return 0;
		}
		else
		{
			return $nombrePionRetourne;
		}
	}
	
	public function retournePion($li,$co,$joueur)
	{
		$nombrePion = 0;	
		for($dir=0;$dir<8;$dir++)
		{
			if($this->testCase($dir,$li,$co,$joueur) > 0)
			{	
				switch($dir)
				{
					case 0 : 
						for($i=$li-1;$i>-1;$i--)
						{
							if($this->tab[$i][$co] == $joueur)
							{
								$i = -1;
							}
							else
							{
								$this->tab[$i][$co] = $joueur;
								$nombrePion++;
							}
						}
					break;
					case 1 : 
						$i = $li-1;
						$j = $co+1;
						while($i > -1 && $j < 8)
						{
							if($this->tab[$i][$j] == $joueur)
							{
								$i = -1;
							}
							else
							{
								$this->tab[$i][$j] = $joueur;
								$nombrePion++;
							}
							$i--;
							$j++;
						}
					break;
					case 2 : 
						for($i=$co+1;$i<8;$i++)
						{
							if($this->tab[$li][$i] == $joueur)
							{
								$i = 8;
							}
							else
							{
								$this->tab[$li][$i] = $joueur;
								$nombrePion++;
							}
						}
					break;
					case 3 : 
						$i = $li+1;
						$j = $co+1;
						while($i < 8 && $j < 8)
						{
							if($this->tab[$i][$j] == $joueur)
							{
								$i = 8;
							}
							else
							{
								$this->tab[$i][$j] = $joueur;
								$nombrePion++;
							}
							$i++;
							$j++;
						}
					break;
					case 4 : 
						for($i=$li+1;$i<8;$i++)
						{
							if($this->tab[$i][$co] == $joueur)
							{
								$i = 8;
							}
							else
							{
								$this->tab[$i][$co] = $joueur;
								$nombrePion++;
							}
						}
					break;
					case 5 : 
						$i = $li+1;
						$j = $co-1;
						while($i < 8 && $j > -1)
						{
							if($this->tab[$i][$j] == $joueur)
							{
								$i = 8;
							}
							else
							{
								$this->tab[$i][$j] = $joueur;
								$nombrePion++;
							}
							$i++;
							$j--;
						}
					break;
					case 6 : 
						for($i=$co-1;$i>-1;$i--)
						{
							if($this->tab[$li][$i] == $joueur)
							{
								$i = -1;
							}
							else
							{
								$this->tab[$li][$i] = $joueur;
								$nombrePion++;
							}
						}
					break;
					case 7 : 
						$i = $li-1;
						$j = $co-1;
						while($i > -1 && $j > -1)
						{
							if($this->tab[$i][$j] == $joueur)
							{
								$i = -1;
							}
							else
							{
								$this->tab[$i][$j] = $joueur;
								$nombrePion++;
							}
							$i--;
							$j--;
						}
					break;
				}
			}
		}
		return $nombrePion;
	}
}
?>
