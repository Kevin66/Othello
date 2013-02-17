package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChargerPartie extends JFrame implements ActionListener {

	private static final long serialVersionUID = -150119899501890733L;
	
	private IHM ihm;
	private JPanel panel = new JPanel(new GridLayout(2,2));
	private JPanel panelCharger = new JPanel();
	private JLabel labelCharger = new JLabel("Partie n° :");
	private JTextField id = new JTextField();
	private JButton bCharger = new JButton("Charger");
	private JButton bAnnuler = new JButton("Annuler");
	
	public ChargerPartie(IHM ihm)
	{
		this.ihm = ihm;
		this.setPreferredSize(new Dimension(200,60));
		this.setResizable(false);
		panelCharger.add(labelCharger);
		panel.add(panelCharger);
		panel.add(id);
		panel.add(bCharger);
		panel.add(bAnnuler);
		bCharger.addActionListener(this);
		bAnnuler.addActionListener(this);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == bCharger)
		{
			String idPartie = id.getText();
			ihm.getParser().setURL("index.php?loadgame="+idPartie,ihm.getAddr());
			ihm.getParser().setPlateau(ihm.getPlateau().getCases());
			ihm.getPlateau().setJoueurCourant(ihm.getParser().getMain());
			ihm.getInformation().setBackGroundScore(ihm.getParser().getMain());
			ihm.getInformation().setIdPartie(ihm.getParser().getId());
			ihm.getInformation().setTypeJ1(ihm.getParser().getType(1));
			ihm.getInformation().setTypeJ2(ihm.getParser().getType(2));
			ihm.getInformation().setScoreJ1(ihm.getPlateau().compterScore(1));
			ihm.getInformation().setScoreJ2(ihm.getPlateau().compterScore(2));
			if(!ihm.getInformation().getTypeJ1().equals("Human"))
			{
				ihm.nivJ1 = ihm.getInformation().getTypeJ1().substring(2, 3);
			}
			if(!ihm.getInformation().getTypeJ2().equals("Human"))
			{
				ihm.nivJ2 = ihm.getInformation().getTypeJ2().substring(2, 3);
			}
			
			if(ihm.getPlateau().getJoueurCourant() == 1)
			{
				if(!ihm.typeJ1.equals("Human"))
				{
					System.out.printf("Joueur 1 niveau %s\n",ihm.nivJ1);
					ihm.faireJouerIA(1,ihm.nivJ1);
				}
			}
			else
			{
				if(!ihm.typeJ2.equals("Human"))
				{
					System.out.printf("Joueur 2 niveau %s\n",ihm.nivJ2);
					ihm.faireJouerIA(2,ihm.nivJ2);
				}
			}
			ihm.setThread(new Thread(ihm));
			ihm.getThread().start();
			ihm.setStarted(true);
			this.dispose();
		}
		else if(e.getSource() == bAnnuler)
		{
			this.dispose();
		}
	}
}
