package Xml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.input.BOMInputStream;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import IHM.Case;

public class Parser {
	private Document doc, doc2;
	private SAXBuilder sx;
	private Element racine;
	private List<Element> nodes;
	private XPathExpression<Element> xpaE;
	private XPathFactory xpaF;
	private URL theFile;
	
	public Parser(){
		doc = new Document();
		sx = new SAXBuilder();
	}
	
	public int getMain(){
		Element emt;
		int idMain;
		xpaE = xpaF.compile("othello/partie", Filters.element());
		emt = xpaE.evaluateFirst(doc);
		idMain = Integer.parseInt(emt.getAttributeValue("main"));
		return idMain;
	}
	
	public String getId(){
		Element emt;
		String id;
		xpaE = xpaF.compile("othello/partie", Filters.element());
		emt = xpaE.evaluateFirst(doc);
		id = emt.getAttributeValue("id");
		return id;
	}
	
	public String getType(int i){
		Element emt;
		String type;
		xpaE = xpaF.compile("othello/partie", Filters.element());
		emt = xpaE.evaluateFirst(doc);
		type = emt.getAttributeValue("type"+i);
		return type;
	}
	
	public void setPlateau(Case tab[][]) {
		List<Element> lignes;
		xpaE = xpaF.compile("othello/plateau/*", Filters.element());
		lignes = xpaE.evaluate(doc);
		for (Element l : lignes) {
			if (l.getName() == "ligne") {
				List<Element> colones;
				colones = l.getChildren();
				for(Element c : colones)
				{
					int couleur = Integer.parseInt(c.getTextTrim());
					int i =  Integer.parseInt(l.getAttributeValue("num"));
					int j =  Integer.parseInt(c.getAttributeValue("num"));
					//System.out.printf("%d %d couleur : %d\n",i,j,couleur);
					tab[i][j].setCouleur(couleur);
				}
			}
			
		}
	}
	
	public int setURL(String URL, String addr){
		int ok = 0;
		BOMInputStream bomIn;
		try{
			//theFile = new File("res/Exemple.xml");
			theFile = new URL("http://"+addr+"/othello/"+URL);
			System.out.println(theFile);
			bomIn = new BOMInputStream(theFile.openStream(), false);
			doc = sx.build(bomIn);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			return ok;
		} catch (IOException e) {
			e.printStackTrace();
			//setURL(URL);
			return ok;
		} catch (JDOMException e) {
			e.printStackTrace();
			return ok;
		}
		
		setRacine(doc.getRootElement());
		xpaF = XPathFactory.instance();
		ok = 1;
		return ok;
	}
	
	public Document getDoc2() {
		return doc2;
	}
	
	public void setDoc2(Document doc2) {
		this.doc2 = doc2;
	}
	
	public Element getRacine() {
		return racine;
	}
	
	public void setRacine(Element racine) {
		this.racine = racine;
	}
	
	public List<Element> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<Element> nodes) {
		this.nodes = nodes;
	}
}
