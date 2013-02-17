#include "othello.h"
int prof;
int Max = 0;
int Min = 0;
int resultat;
int main(int argc, char** argv)
{
	int plateau[size][size];
	int i,j,k;
	//Test du nombre d'argurment exemple syntaxe "./othello 10 1 0000000000000000000000000002100000012000000000000000000000000000"
		if(argc != 4)
		{
			printf("Erreur de syntaxe\n");
			printf("%s profondeur max plateau\n",argv[0]);
			return 1;
		}
	//Test de la profondeur, profondeur maximum recommandé 12
		prof = atoi(argv[1]);
		if(prof <= 0)
		{
			printf("Erreur de profondeur %d (profondeur > 0)\n",prof);
			return 2;
		}
	//Test du joueur Max soit NOIR soit BLANC
		Max =  atoi(argv[2]);
		if(Max != NOIR && Max != BLANC)
		{
			printf("Erreur de la valeur max %d (max = 1 ou max = 2)\n",Max);
			return 3;
		}
	//Affectation de la valeur du joueur Min
		if(Max == NOIR)
		{
			Min = BLANC;		//Si Max = NOIR alors Min = BLANC
		}
		else
		{
			Min = NOIR;		//Sinon Min = NOIR
		}
	//Test du plateau : 64 caracteres chacun est égal à 0, 1 ou 2
		if(strlen(argv[3]) != 64)
		{
			printf("Erreur plateau taille %d\n",(int)strlen(argv[3]));
			return 4;
		}
		for(i=0;i<64;i++)
		{
			char num = argv[3][i];
			if(atoi(&num) != 0 && atoi(&num) != 1 && atoi(&num) != 2)
			{
				printf("Erreur sur la case %d (%d)\n",i ,atoi(&num));
				return 5;
			}
		}
		k=0;
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				char num = argv[3][k];
				plateau[i][j] = atoi(&num);
				k++;
			}
		}

	Alphabeta(plateau,Max,0,-1000000, +1000000);
	return resultat;
}

void afficherOthellier(int plateau[][size])
{
	int i,j;
	for(i=0;i<size;i++)
	{
		for(j=0;j<size;j++)
		{
			printf("%d ",plateau[i][j]);
		}
		printf("\n");
	}
}

int compterPion(int plateau[][size])
{
	int i,j;
	int pionMax = 0;
	int pionMin = 0;
	for(i=0;i<size;i++)
	{
		for(j=0;j<size;j++)
		{
			if(plateau[i][j] == Max)
			{
				pionMax++;
			}
			else if(plateau[i][j] == Min)
			{
				pionMin++;
			}
		}
	}
	return (pionMax-pionMin);
}

int testRetournement(int plateau[][size], int l, int c, int dir, int couleur)
{
	int i,j,couleurInverse;
	int nombrePionRetourne = 0;
	int ok = 0;
	if(couleur == NOIR)
	{
		couleurInverse = BLANC;
	}
	else
	{
		couleurInverse = NOIR;
	}
	if((l > -1 || l < size) && (c > -1 || c < size))
	{
		switch(dir)
		{
			case 0 : 
				for(i=l-1;i>-1;i--)
				{
					if(i < 0)
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][c] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][c] == couleur)
					{
						i = -1;
						ok = 1;
					}
					else
					{	
						i = -1;
						nombrePionRetourne = 0;
					}
				}
			break;
			case 1 : 
				i = l-1;
				j = c+1;
				while(i > -1 && j < size)
				{
					if(i < 0 || j > 7)
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][j] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][j] == couleur)
					{
						i = -1;
						ok = 1;
					}
					else
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					i--;
					j++;
				}
			break;
			case 2 : 
				for(i=c+1;i<size;i++)
				{
					if(i > 7)
					{
						i = size;
						nombrePionRetourne = 0;
					}
					else if(plateau[l][i] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[l][i] == couleur)
					{
						i = size;
						ok = 1;
					}
					else
					{
						i = size;
						nombrePionRetourne = 0;
					}
				}
			break;
			case 3 : 
				i = l+1;
				j = c+1;
				while(i < size && j < size)
				{
					if(i > 7 || j > 7)
					{
						i = size;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][j] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][j] == couleur)
					{
						i = size;
						ok = 1;
					}
					else
					{
						i = size;
						nombrePionRetourne = 0;
					}
					i++;
					j++;
				}
			break;
			case 4 : 
				for(i=l+1;i<size;i++)
				{
					if(i > 7)
					{
						i = size;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][c] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][c] == couleur)
					{
						i = size;
						ok = 1;
					}
					else
					{
						i = size;
						nombrePionRetourne = 0;
					}
				}
			break;
			case 5 : 
				i = l+1;
				j = c-1;
				while(i < size && j > -1)
				{
					if(i > 7 || j < 0)
					{
						i = size;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][j] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][j] == couleur)
					{
						i = size;
						ok = 1;
					}
					else
					{
						i = size;
						nombrePionRetourne = 0;
					}
					i++;
					j--;
				}
			break;
			case 6 : 
				for(i=c-1;i>-1;i--)
				{
					if(i < 0)
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					else if(plateau[l][i] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[l][i] == couleur)
					{
						i = -1;
						ok = 1;
					}
					else
					{
						i = -1;
						nombrePionRetourne = 0;
					}
				}
			break;
			case 7 : 
				i = l-1;
				j = c-1;
				while(i > -1 && j > -1)
				{
					if(i < 0 || j < 0)
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					else if(plateau[i][j] == couleurInverse)
					{
						nombrePionRetourne++;
					}
					else if(plateau[i][j] == couleur)
					{
						i = -1;
						ok = 1;
					}
					else
					{
						i = -1;
						nombrePionRetourne = 0;
					}
					i--;
					j--;
				}
			break;
		}
	}
	if(ok == 0)
	{
		return 0;
	}
	else
	{
		return nombrePionRetourne;
	}
}

int retournePion(int plateau[][size], int l, int c, int couleur)
{
	int i,j,dir;
	int nombrePionRetourne = 1;	
	for(dir=0;dir<8;dir++)
	{
		if(testRetournement(plateau,l,c,dir,couleur) > 0)
		{	
			switch(dir)
			{
				case 0 : 
					for(i=l-1;i>-1;i--)
					{
						if(plateau[i][c] == couleur)
						{
							i = -1;
						}
						else
						{
							plateau[i][c] = couleur;
							nombrePionRetourne++;
						}
					}
				break;
				case 1 : 
					i = l-1;
					j = c+1;
					while(i > -1 && j < size)
					{
						if(plateau[i][j] == couleur)
						{
							i = -1;
						}
						else
						{
							plateau[i][j] = couleur;
							nombrePionRetourne++;
						}
						i--;
						j++;
					}
				break;
				case 2 : 
					for(i=c+1;i<size;i++)
					{
						if(plateau[l][i] == couleur)
						{
							i = size;
						}
						else
						{
							plateau[l][i] = couleur;
							nombrePionRetourne++;
						}
					}
				break;
				case 3 : 
					i = l+1;
					j = c+1;
					while(i < size && j < size)
					{
						if(plateau[i][j] == couleur)
						{
							i = size;
						}
						else
						{
							plateau[i][j] = couleur;
							nombrePionRetourne++;
						}
						i++;
						j++;
					}
				break;
				case 4 : 
					for(i=l+1;i<size;i++)
					{
						if(plateau[i][c] == couleur)
						{
							i = size;
						}
						else
						{
							plateau[i][c] = couleur;
							nombrePionRetourne++;
						}
					}
				break;
				case 5 : 
					i = l+1;
					j = c-1;
					while(i < size && j > -1)
					{
						if(plateau[i][j] == couleur)
						{
							i = size;
						}
						else
						{
							plateau[i][j] = couleur;
							nombrePionRetourne++;
						}
						i++;
						j--;
					}
				break;
				case 6 : 
					for(i=c-1;i>-1;i--)
					{
						if(plateau[l][i] == couleur)
						{
							i = -1;
						}
						else
						{
							plateau[l][i] = couleur;
							nombrePionRetourne++;
						}
					}
				break;
				case 7 : 
					i = l-1;
					j = c-1;
					while(i > -1 && j > -1)
					{
						if(plateau[i][j] == couleur)
						{
							i = -1;
						}
						else
						{
							plateau[i][j] = couleur;
							nombrePionRetourne++;
						}
						i--;
						j--;
					}
				break;
			}
		}
	}
	return nombrePionRetourne;
}

int Alphabeta(int plateau[][size], int couleur,int p,int alpha, int beta)
{
	int i,j,k,l;
	int bestMove[2];
	if(p == prof)
	{
		return compterPion(plateau);
	}
	if(couleur == Max)
	{
		for(i=0;i<size;i++)
		{
			for(j=0;j<size;j++)
			{
				if(plateau[i][j] == VIDE)
				{
					int tempPlateau[size][size];
					for(k=0;k<size;k++)
					{
						for(l=0;l<size;l++)
						{
							tempPlateau[k][l] = plateau[k][l]; 
						}
					}
					if(retournePion(tempPlateau,i,j,Max) > 1)
					{
						tempPlateau[i][j] = Max;
						int score = Alphabeta(tempPlateau,Min,p+1,alpha,beta);
						if(score > alpha)
						{
							alpha = score;
							bestMove[0] = i;
							bestMove[1] = j;
							if(alpha >= beta)
							{
								i = size;
							}
						}
					}
				}
			}
		}
		if(p==0)
		{
			
			/*printf("P : %dBestMove : %d/%d\n",p,bestMove[0],bestMove[1]);
			afficherOthellier(plateau);
			printf("\n");*/
			resultat = bestMove[0]*10+bestMove[1];
		}
		return alpha;
	}
	else
	{
		for(i=0;i<size;i++)
		{
			for(j=0;j<size;j++)
			{
				if(plateau[i][j] == VIDE)
				{
					int tempPlateau[size][size];
					for(k=0;k<size;k++)
					{
						for(l=0;l<size;l++)
						{
							tempPlateau[k][l] = plateau[k][l]; 
						}
					}
					if(retournePion(tempPlateau,i,j,Min) > 1)
					{
						tempPlateau[i][j] = Min;
						int score = Alphabeta(tempPlateau,Max,p+1,alpha,beta);
						if(score < beta)
						{
							beta = score;
							bestMove[0] = i;
							bestMove[1] = j;
							if(alpha >= beta)
							{
								i = size;
							}
						}
					}
				}
			}
		}
		if(p==0)
		{
			/*printf("P : %dBestMove : %d/%d\n",p,bestMove[0],bestMove[1]);
			afficherOthellier(plateau);
			printf("\n");*/
			resultat = bestMove[0]*10+bestMove[1];
		}
		return beta;
	}
}
