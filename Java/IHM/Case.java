package IHM;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Interface.Observateur;

public class Case extends JPanel  implements  MouseListener {
	
	private static final long serialVersionUID = 352327268755320633L;
	
	private Observateur ihm;
	private int numLigne;
	private int numColone;
	private int couleur = 0;
	
	public Case (int couleur, int ligne, int colone, Observateur ihm)
	{
		this.ihm = ihm;
		this.numLigne = ligne;
		this.numColone = colone;
		this.couleur = couleur;
		this.setCouleur(this.couleur);
		this.setBorder(BorderFactory.createLineBorder(new Color(180,180,180)));
		this.addMouseListener(this);
	}
	
	public int getCouleur()
	{
		return couleur;
	}
	
	public void setCouleur(int couleur)
	{
		this.couleur = couleur;
		if(this.couleur == 1)
		{
			this.setBackground(Color.BLACK);
		}
		else if(this.couleur == 2)
		{
			this.setBackground(Color.WHITE);
		}
		else
		{
			this.setBackground(new Color(0,100,0));
		}
	}
	
	public void mousePressed(MouseEvent arg0)
	{
		if(couleur == 0 && ihm.getJoueurType().equals("Human"))
		{
			System.out.printf("ligne : %d, colone %d\n",numLigne,numColone);
			ihm.observeCase(numLigne,numColone);
		}
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
	}
	
	public void mouseEntered(MouseEvent arg0)
	{
	}
	
	public void mouseExited(MouseEvent arg0)
	{
	}
	
	public void mouseReleased(MouseEvent arg0)
	{
	}
}
