package IHM;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Interface.Observateur;

public class Plateau extends JPanel {
	
	private static final long serialVersionUID = 8053431709893521929L;
	private Case cases[][];
	private int joueurCourant;
	private int nbJoueurBloque;
	private boolean finished = false;
	
	public Plateau(Observateur ihm)
	{
		super(new GridLayout(8,8));
		nbJoueurBloque = 0;
		this.cases= new Case[8][8];
		this.joueurCourant = 1;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				this.cases[i][j] = new Case(0,i,j,ihm);
				this.add(cases[i][j]);
			}
		}
		this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		this.setPreferredSize(new Dimension(500,500));
		this.setBorder(new LineBorder(Color.BLACK));
	}
	
	public void restartPlateau()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				this.cases[i][j].setCouleur(0);
			}
		}
	}
	public int compterScore(int couleur)
	{
		int cpt = 0;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(cases[i][j].getCouleur() == couleur)
					cpt++;
			}
		}
		return cpt;
	}
	
	public Case[][] getCases()
	{
		return cases;
	}

	public int getJoueurCourant()
	{
		return joueurCourant;
	}

	public void setJoueurCourant(int joueurCourant)
	{
		this.joueurCourant = joueurCourant;
	}

	public boolean isFini() {
		return finished;
	}

	public void setFini(boolean finished) {
		this.finished = finished;
	}

	public int getNbJoueurBloque() {
		return nbJoueurBloque;
	}
	
	public void setNbJoueurBloque(int nbJoueurBloque)
	{
		this.nbJoueurBloque = nbJoueurBloque;
	}
	
	public void incNbJoueurBloque() {
		this.nbJoueurBloque++;
	}
	
	public void decNbJoueurBloque() {
		this.nbJoueurBloque--;
	}
}
