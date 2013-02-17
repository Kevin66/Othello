#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define size 8
#define VIDE 0
#define NOIR 1
#define BLANC 2

void afficherOthellier(int[][size]);
int compterPion(int[][size]);
int testeRetourne(int[][size],int,int,int,int);
int retournePion(int[][size],int,int,int);
int Alphabeta(int[][size],int,int,int,int);
