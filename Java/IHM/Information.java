package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Information extends JPanel {

	private static final long serialVersionUID = -963089868687887073L;
	
	private JPanel panelScore;
	private JPanel panelType;
	private JPanel panelNOIR;
	private JPanel panelBLANC;
	private JLabel labelNOIR;
	private JLabel labelBLANC;
	private JLabel labelPartie;
	private JPanel panelTypeJ1;
	private JPanel panelTypeJ2;
	private String typeJ1;
	private String typeJ2;
	private JLabel labelType1;
	private JLabel labelType2;
	private int scoreJ1;
	private int scoreJ2;
	private JPanel panelScore1;
	private JPanel panelScore2;
	private JLabel labelScore1;
	private JLabel labelScore2;
	
	public Information()
	{
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(200,445));
		this.setBorder(new LineBorder(Color.BLACK));
		this.setBackground(new Color(180,180,180));
		
		initialiserPanelScore();
		this.labelPartie = new JLabel("",JLabel.CENTER);
		this.labelPartie.setPreferredSize(new Dimension(200,30));
		initialiserPanelType();
		JPanel Haut = new JPanel(new BorderLayout());
		Haut.setPreferredSize(new Dimension(200,90));
		Haut.add(panelScore, BorderLayout.NORTH);
		Haut.add(labelPartie, BorderLayout.CENTER);
		this.add(Haut,BorderLayout.NORTH);
		this.add(this.panelType,BorderLayout.CENTER);
		JPanel Vide = new JPanel();
		Vide.setPreferredSize(new Dimension(200,300));
		this.add(Vide,BorderLayout.SOUTH);
	}
	public void initialiserPanelScore()
	{
		this.panelScore = new JPanel(new GridLayout(2,2)); 
		this.panelScore.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		this.panelScore.setPreferredSize(new Dimension(200,60));
		this.panelNOIR = new JPanel();
		this.labelNOIR = new JLabel("NOIR");
		this.panelNOIR.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		this.panelBLANC = new JPanel();
		this.labelBLANC = new JLabel("BLANC");
		this.panelNOIR.add(labelNOIR);
		this.panelBLANC.add(labelBLANC);
		this.panelBLANC.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		this.panelScore.add(this.panelNOIR);
		this.panelScore.add(this.panelBLANC);
		this.scoreJ1 = 0;
		this.scoreJ2 = 0;
		this.panelScore1 = new JPanel();
		this.labelScore1 = new JLabel(""+this.scoreJ1);
		this.panelScore1.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		this.panelScore1.add(this.labelScore1);
		this.panelScore2 = new JPanel();
		this.labelScore2 = new JLabel(""+this.scoreJ1);
		this.panelScore2.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		this.panelScore2.add(this.labelScore2);
		this.panelScore.add(this.panelScore1);
		this.panelScore.add(this.panelScore2);
	}
	
	public void setBackGroundScore(int joueur)
	{
		if(joueur == 1)
		{
			panelNOIR.setBackground(new Color(150,100,100));
			panelBLANC.setBackground(null);
			
		}
		else
		{
			panelBLANC.setBackground(new Color(150,100,100));
			panelNOIR.setBackground(null);
		}
	}
	public void initialiserPanelType()
	{
		this.panelType = new JPanel(new GridLayout(0,3)); 
		this.panelType.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		this.panelType.setPreferredSize(new Dimension(200,30));
		this.panelTypeJ1 = new JPanel();
		this.panelTypeJ2 = new JPanel();
		this.typeJ1 = "";
		this.typeJ2 = "";
		labelType1 = new JLabel(this.typeJ1);
		panelTypeJ1.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		labelType2 = new JLabel(this.typeJ2);
		panelTypeJ2.setBorder(BorderFactory.createLineBorder(new Color(100,100,100)));
		this.panelTypeJ1.add(labelType1);
		this.panelTypeJ2.add(labelType2);
		this.panelType.add(panelTypeJ1);
		JPanel panelVs = new JPanel();
		panelVs.add(new JLabel("VS"));
		this.panelType.add(panelVs);
		this.panelType.add(panelTypeJ2);
	}
	
	public void setIdPartie(String id)
	{
		this.labelPartie.setText(id);
	}
	public String getIdPartie()
	{

		return this.labelPartie.getText();
	}
	public String getTypeJ1()
	{
		return typeJ1;
	}
	
	public void setTypeJ1(String typeJ1)
	{
		this.typeJ1 = typeJ1;
		this.labelType1.setText(""+this.typeJ1);
	}
	
	public String getTypeJ2()
	{
		return typeJ2;
	}
	
	public void setTypeJ2(String typeJ2)
	{
		this.typeJ2 = typeJ2;
		this.labelType2.setText(""+this.typeJ2);
	}
	
	public int getScoreJ1()
	{
		return scoreJ1;
	}
	
	public void setScoreJ1(int scoreJ1)
	{
		this.scoreJ1 = scoreJ1;
		this.labelScore1.setText(""+this.scoreJ1);
	}
	
	public int getScoreJ2()
	{
		return scoreJ2;
	}
	
	public void setScoreJ2(int scoreJ2)
	{
		this.scoreJ2 = scoreJ2;
		this.labelScore2.setText(""+this.scoreJ2);
	}
}
