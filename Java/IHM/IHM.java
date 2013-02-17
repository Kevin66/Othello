package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;


import Interface.Observateur;
import Xml.Parser;

public class IHM extends JFrame implements Observateur, Runnable, ActionListener{

	private static final long serialVersionUID = 7808816139970735405L;

	private Parser parser;

	private JTextField addr = new JTextField();
	private Plateau plateau;
	private Information information;
	
	private JMenuBar menu;
	private JMenu menuPartie;
	private JMenuItem itemNouvellePartie;
	private JMenuItem itemChargerPartie;
	private JMenuItem itemQuitterPartie;
	
	private Thread thread;

	private boolean started;
	private boolean changed;
	
	String nivJ1 = "";
	String nivJ2 = "";
	String typeJ1 ="";
	String typeJ2 = "";
	
	public IHM()
	{
		started = false;
		changed= false;
		plateau = new Plateau(this);
		information = new Information();
		parser = new Parser();

		menu = new JMenuBar();
		menuPartie = new JMenu("Partie");
		itemNouvellePartie = new JMenuItem("Nouvelle Partie");
		itemChargerPartie = new JMenuItem("Charger Partie");
		itemQuitterPartie = new JMenuItem("Quitter Partie");
		menuPartie.add(itemNouvellePartie);
		menuPartie.add(itemChargerPartie);
		menuPartie.add(itemQuitterPartie);
		menu.add(menuPartie);
		
		itemNouvellePartie.addActionListener(this);
		itemChargerPartie.addActionListener(this);
		itemQuitterPartie.addActionListener(this);

		setTitle("Othello");
		setPreferredSize(new Dimension(700,500));
		setResizable(false);
		getContentPane().add(plateau, BorderLayout.WEST);
		JPanel frame = new JPanel(new BorderLayout());
		frame.add(information, BorderLayout.NORTH);
		frame.add(addr, BorderLayout.SOUTH);
		getContentPane().add(frame, BorderLayout.EAST);
		setJMenuBar(menu);
		pack();
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == itemNouvellePartie)
		{
			new NouvellePartie(this);
		}
		else if(e.getSource() == itemChargerPartie)
		{
			new ChargerPartie(this);
		}
		else if(e.getSource() == itemQuitterPartie)
		{
			dispose();
			System.exit(0);
		}
	}
	
	public void changerJoueurCourant()
	{
		if(plateau.getJoueurCourant() == 1)
		{
			plateau.setJoueurCourant(2);
			information.setBackGroundScore(2);
		}
		else
		{
			plateau.setJoueurCourant(1);
			information.setBackGroundScore(1);
		}

		//changed = true;
	}
	
	public void observeCase(int l, int c) 
	{
		if(this.isStarted())
		{
			if(this.parser.setURL(("index.php?li="+l+"&co="+c+"&jo="+plateau.getJoueurCourant()+"&id=").concat(information.getIdPartie()),addr.getText()) == 1);
			{
				parser.setPlateau(plateau.getCases());
				int tempScoreJ1 = this.information.getScoreJ1();
				int tempScoreJ2 = this.information.getScoreJ2();
				information.setScoreJ1(this.plateau.compterScore(1));
				information.setScoreJ2(this.plateau.compterScore(2));
				
				if(tempScoreJ1 != this.information.getScoreJ1() || tempScoreJ2 != this.information.getScoreJ2())
				{
					changerJoueurCourant();
					changed = true;
				}	
			}
		}
	}
	
	public void faireJouerIA(int x, String y)
	{
		if(parser.setURL(("index.php?jo="+x+"&IAniv="+y+"&id=").concat(information.getIdPartie()),addr.getText()) == 1)
		{
			parser.setPlateau(plateau.getCases());		
			int tempScoreJ1 = this.information.getScoreJ1();
			int tempScoreJ2 = this.information.getScoreJ2();
			this.information.setScoreJ1(this.plateau.compterScore(1));
			this.information.setScoreJ2(this.plateau.compterScore(2));
			
			if(tempScoreJ1 != this.information.getScoreJ1() || tempScoreJ2 != this.information.getScoreJ2())
			{
				changerJoueurCourant();
				changed = true;
			}	
		}
	}

	public void run() {
		int winner = 0;
		while(!plateau.isFini())
		{
			if(isStarted() && isChanged())
			{
				if(peutJouer())
				{
					plateau.decNbJoueurBloque();
					if(this.plateau.getJoueurCourant()==1)
					{
						changed = false;
						if(!typeJ1.equals("Human"))
						{
							System.out.printf("Joueur 1 IA niveau %s\n",nivJ1);
							faireJouerIA(1,nivJ1);
						}
					}
					else
					{
						changed = false;
						if(!typeJ2.equals("Human"))
						{
							System.out.printf("Joueur 2 IA niveau %s\n",nivJ2);
							faireJouerIA(2,nivJ2);
						}
					}
				}
				else
				{
					plateau.incNbJoueurBloque();
					changerJoueurCourant();
					changed = true;
				}
			}
			
			if(plateau.compterScore(1)+plateau.compterScore(2) == 64)
			{
				if(plateau.compterScore(1) > plateau.compterScore(2))
				{
					System.out.print("Le vainqeur est le joueur Noir");
					winner = 1;
				}
				else if(plateau.compterScore(1) < plateau.compterScore(2))
				{
					System.out.print("Le vainqeur est le joueur Blanc");
					winner = 2;
				}
				
				plateau.setFini(true);
			}
			else if(plateau.getNbJoueurBloque() == 2)
			{
				if(plateau.compterScore(1)>plateau.compterScore(2))
				{
					winner = 1;
				}
				else if(plateau.compterScore(1)<plateau.compterScore(2))
				{
					winner = 2;
				}
				if(winner != 0)
				{
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							if(plateau.getCases()[i][j].getCouleur() == 0)
							{
								plateau.getCases()[i][j].setCouleur(winner);
							}
						}
					
					}
					information.setScoreJ1(plateau.compterScore(1));
					information.setScoreJ2(plateau.compterScore(2));
					System.out.printf("Le vainqeur est le joueur %d",winner);
				}
				else
				{

					System.out.print("Egalite");
				}
				plateau.setFini(true);
			}
			
			
		}
		parser.setURL(("index.php?win="+winner+"&id=").concat(information.getIdPartie()),addr.getText());
		final JFrame frameWinner = new JFrame();
		JPanel panelWinner = new JPanel();
		JPanel panelOK = new JPanel();
		 JButton bOK = new JButton("OK");
		frameWinner.setSize(200, 400);
		if(winner == 1)
			panelWinner.add(new JLabel("Vainqueur : NOIR",JLabel.CENTER));
		else if(winner == 2)
			panelWinner.add(new JLabel("Vainqueur : BLANC",JLabel.CENTER));
		else
			panelWinner.add(new JLabel("Egalite",JLabel.CENTER));
		
		panelOK.add(bOK,JButton.CENTER);
		frameWinner.getContentPane().add(panelWinner, BorderLayout.CENTER);
		frameWinner.getContentPane().add(panelOK, BorderLayout.SOUTH);
		frameWinner.pack();
		frameWinner.setVisible(true);
		frameWinner.setResizable(false);
		bOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				frameWinner.dispose();
				nivJ1 = "";
				nivJ2 = "";
				typeJ1 ="";
				typeJ2 = "";
				plateau.restartPlateau();
				plateau.setJoueurCourant(1);
				information.setBackGroundScore(1);
				information.setIdPartie(parser.getId());
				information.setTypeJ1(typeJ1 +nivJ1);
				information.setTypeJ2(typeJ2 +nivJ2);				
				information.setScoreJ1(plateau.compterScore(1));
				information.setScoreJ2(plateau.compterScore(2));
			}	
		});
		
	}

	public boolean peutJouer()
	{
		boolean jouable = false;
		int couleur = plateau.getJoueurCourant();
		int couleurInverse;
		if(couleur == 1)
		{
			couleurInverse = 2;
		}
		else
		{
			couleurInverse = 1;
		}
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(plateau.getCases()[i][j].getCouleur() == 0)
				{
					for(int dir=0;dir<8;dir++)
					{
						int cpt = 0;
						switch(dir)
						{
							case 0 :
								for(int k=i-1;k>-1;k--)
								{
									if(k < 0)
									{
										k = -1;
										cpt = 0;
									}
									else if(plateau.getCases()[k][j].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][j].getCouleur() == couleur)
									{
										k = -1;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{	
										k = -1;
										cpt = 0;
									}
								}
							break;
							case 1 : 
								int k = i-1;
								int l = j+1;
								while(k > -1 && l < 8)
								{
									if(k < 0 || l > 7)
									{
										k = -1;
										cpt = 0;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleur)
									{
										k = -1;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = -1;
										cpt = 0;
									}
									k--;
									l++;
								}
							break;
							case 2 : 
								for(k=j+1;k<8;k++)
								{
									if(k > 7)
									{
										k = 8;
										cpt = 0;
									}
									else if(plateau.getCases()[i][k].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[i][k].getCouleur() == couleur)
									{
										k = 8;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = 8;
										cpt = 0;
									}
								}
							break;
							case 3 : 
								k = i+1;
								l = j+1;
								while(k < 8 && l < 8)
								{
									if(k > 7 || l > 7)
									{
										k = 8;
										cpt = 0;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleur)
									{
										k = 8;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = 8;
										cpt = 0;
									}
									k++;
									l++;
								}
							break;
							case 4 : 
								for(k=i+1;k<8;k++)
								{
									if(k > 7)
									{
										k = 8;
										cpt = 0;
									}
									else if(plateau.getCases()[k][j].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][j].getCouleur() == couleur)
									{
										k = 8;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = 8;
										cpt = 0;
									}
								}
							break;
							case 5 : 
								k = i+1;
								l = j-1;
								while(k < 8 && l > -1)
								{
									if(k > 7 || l < 0)
									{
										k = 8;
										cpt = 0;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleur)
									{
										k = 8;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = 8;
										cpt = 0;
									}
									k++;
									l--;
								}
							break;
							case 6 : 
								for(k=j-1;k>-1;k--)
								{
									if(k < 0)
									{
										k = -1;
										cpt = 0;
									}
									else if(plateau.getCases()[i][k].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[i][k].getCouleur() == couleur)
									{
										k = -1;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = -1;
										cpt = 0;
									}
								}
							break;
							case 7 : 
								k = i-1;
								l = j-1;
								while(k > -1 && l > -1)
								{
									if(k < 0 || l < 0)
									{
										k = -1;
										cpt = 0;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleurInverse)
									{
										cpt++;
									}
									else if(plateau.getCases()[k][l].getCouleur() == couleur)
									{
										k = -1;
										if(cpt > 0)
										{
											jouable = true;
										}
									}
									else
									{
										k = -1;
										cpt = 0;
									}
									k--;
									l--;
								}
							break;
						}
						if(jouable)
						{
							dir = 8;
							i = 8;
							j = 8;
						}
					}
				}
			}
		}
		return jouable;
	}
	
	public String getAddr()
	{
		return addr.getText();
	}
	
	public String getJoueurType() 
	{
		if(plateau.getJoueurCourant() == 1)
		{
			return information.getTypeJ1();
		}
		else
		{
			return information.getTypeJ2();
		}
	}
	
	public Parser getParser()
	{
		return parser;
	}

	public Plateau getPlateau()
	{
		return plateau;
	}

	public Information getInformation()
	{
		return information;
	}

	public boolean isStarted() 
	{
		return started;
	}

	public void setStarted(boolean started) 
	{
		this.started = started;
	}

	public boolean isChanged() 
	{
		return changed;
	}

	public void setChanged(boolean changed) 
	{
		this.changed = changed;
	}

	public Thread getThread() 
	{
		return thread;
	}

	public void setThread(Thread thread) 
	{
		this.thread = thread;
	}
		
}
