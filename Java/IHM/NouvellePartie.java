package IHM;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NouvellePartie extends JFrame implements ActionListener {

	private static final long serialVersionUID = -603370551162693710L;
	
	private IHM ihm;
	private JPanel panelChoix = new JPanel(new GridLayout(2,3));
	private JPanel panelBouton = new JPanel(new GridLayout(1,2));
	private JLabel labelJ1 = new JLabel("Joueur 1 :",JLabel.CENTER);
	private JLabel labelJ2 = new JLabel("Joueur 2 :",JLabel.CENTER);
	private JButton bCreer = new JButton("Creer");
	private JButton bAnnuler = new JButton("Annuler");
	private JComboBox<String> comboTypeJ1 = new JComboBox<String>();
	private JComboBox<String> comboTypeJ2 = new JComboBox<String>();
	private JComboBox<String> comboNiveauJ1 = new JComboBox<String>();
	private JComboBox<String> comboNiveauJ2 = new JComboBox<String>();
	
	public NouvellePartie(final IHM ihm)
	{	
		this.ihm = ihm;
		this.setTitle("Nouvelle Partie");
		this.setPreferredSize(new Dimension(300,90));
		this.setResizable(false);
		comboTypeJ1.addItem("Human");
		comboTypeJ2.addItem("Human");
		comboTypeJ1.addItem("IA");
		comboTypeJ2.addItem("IA");
		for(int i=1;i<3;i++)
		{
			comboNiveauJ1.addItem(""+i);
			comboNiveauJ2.addItem(""+i);
		}
		comboNiveauJ1.setVisible(false);
		comboNiveauJ2.setVisible(false);
		panelChoix.add(labelJ1);
		panelChoix.add(comboTypeJ1);
		panelChoix.add(comboNiveauJ1);
		panelChoix.add(labelJ2);
		panelChoix.add(comboTypeJ2);
		panelChoix.add(comboNiveauJ2);
		panelBouton.add(bCreer);
		panelBouton.add(bAnnuler);
		this.getContentPane().add(panelChoix, BorderLayout.CENTER);
		this.getContentPane().add(panelBouton, BorderLayout.SOUTH);
		comboTypeJ1.addActionListener(this);
		comboTypeJ2.addActionListener(this);
		bCreer.addActionListener(this);
		bAnnuler.addActionListener(this);
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == comboTypeJ1)
		{
			if(comboTypeJ1.getSelectedItem().equals("IA"))
			{
				comboNiveauJ1.setVisible(true);
			}
			else
			{
				comboNiveauJ1.setVisible(false);
			}
		}
		else if(e.getSource() == comboTypeJ2)
		{
			if(comboTypeJ2.getSelectedItem().equals("IA"))
			{
				comboNiveauJ2.setVisible(true);
			}
			else
			{
				comboNiveauJ2.setVisible(false);
			}
		}
		else if(e.getSource() == bCreer)
		{
			ihm.typeJ1 = (String) comboTypeJ1.getSelectedItem();
			ihm.typeJ2 = (String) comboTypeJ2.getSelectedItem();
			ihm.nivJ1 = "";
			ihm.nivJ2 = "";
			String urlJ1 = "";
			String urlJ2 = "";
			urlJ1 = "jNOIR="+ ihm.typeJ1;
			urlJ2 = "jBLANC="+ ihm.typeJ2;
			if(comboTypeJ1.getSelectedItem().equals("IA"))
			{
				ihm.nivJ1 = (String) comboNiveauJ1.getSelectedItem();
				urlJ1 = urlJ1+"&niv1="+ihm.nivJ1;
			}
			if(comboTypeJ2.getSelectedItem().equals("IA"))
			{
				ihm.nivJ2 = (String) comboNiveauJ2.getSelectedItem();
				urlJ2 = urlJ2+"&niv2="+ihm.nivJ2;
			}
			ihm.getParser().setURL("index.php?newgame=1&"+urlJ1+"&"+urlJ2, ihm.getAddr());
			ihm.getParser().setPlateau(ihm.getPlateau().getCases());
			ihm.getPlateau().setJoueurCourant(1);
			ihm.getInformation().setBackGroundScore(1);
			ihm.getInformation().setIdPartie(ihm.getParser().getId());
			ihm.getInformation().setTypeJ1(ihm.typeJ1 +ihm.nivJ1);
			ihm.getInformation().setTypeJ2(ihm.typeJ2 +ihm.nivJ2);				
			ihm.getInformation().setScoreJ1(ihm.getPlateau().compterScore(1));
			ihm.getInformation().setScoreJ2(ihm.getPlateau().compterScore(2));
			
			ihm.setChanged(false);
			ihm.getPlateau().setNbJoueurBloque(0);
			ihm.getPlateau().setFini(false);

			ihm.setThread(new Thread(ihm));
			ihm.getThread().start();
			
			dispose();
			if(!ihm.typeJ1.equals("Human"))
			{
				System.out.printf("Joueur 1 niveau %s\n",ihm.nivJ1);
				ihm.faireJouerIA(1,ihm.nivJ1);
			}
			ihm.setStarted(true);
		}
		else if(e.getSource() == bAnnuler)
		{
			dispose();
		}	
	}
}
