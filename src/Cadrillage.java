import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

class Cadrillage extends JPanel implements Runnable
{	
	/*VAR*/
	private UniversDatum 			universDatum;
	private Rectangle2D.Float[][] 	universDatumRec;
	private int 					tailleCellule;
	private boolean 				enMarche = false;
	private int						vitesse = 200;
	/*SET*/
	public void 	setEtatRec		(int j, int i) 				{universDatum.setEtatCellule(j, i); repaint();}
	public void 	setEnMarche		(boolean b) 				{enMarche = b;}
	public void 	setModCritP		(String mod, 	int index)  {universDatum.setModCritP(mod, index);}
	public void 	setCritereP		(int idCritere, int index) 	{universDatum.setCritereP(idCritere, index);}
	public void 	setCritereN		(int critN, 	int index)	{universDatum.setCritereN(critN, index);}
	public void 	setCritereR		(int critR,		int index)	{universDatum.setCritereR(critR, index);}
	public void 	setVitesse		(int m_vitesse)				{vitesse = m_vitesse;}
	/*GET*/
	public boolean 	getEnMarche		() 							{return enMarche;}
	public int 		getTailleCellule()							{return tailleCellule;}
	/*CONSTRUCTEUR*/
	Cadrillage(int carresVerticaux, int carresHorizontaux){
		universDatum = new UniversDatum(carresVerticaux, carresHorizontaux);
		universDatumRec = new Rectangle2D.Float[carresVerticaux][carresHorizontaux];
		tailleCellule = 5; //temporaire
		setSize(carresHorizontaux*tailleCellule, carresVerticaux*tailleCellule);

		SentinelleEtatCellule sec = new SentinelleEtatCellule(this);
		addMouseListener(sec);
		
		for(int i = 0; i<universDatum.getLargeur(); i++)
			for(int j = 0; j<universDatum.getHauteur(); j++)
				universDatumRec[i][j] = new Rectangle2D.Float(i*tailleCellule,j*tailleCellule,tailleCellule,tailleCellule);	
	}
	/*GENERATION*/
	public void generation(){universDatum.generation();repaint();}
	/*NETTOYAGE*/
	public void nettoyer(){universDatum.nettoyer(); repaint();}
	/*PAINT COMPONENT OVERRIDE*/
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(int i = 0; i<universDatum.getLargeur(); i++)
			for(int j = 0; j<universDatum.getHauteur(); j++){
				Rectangle2D.Float laCellule = universDatumRec[i][j];
				UniversDatum.ETAT etatDeLaCellule = universDatum.getEtatCellule(i, j);
				
				g2d.setColor(etatDeLaCellule == UniversDatum.ETAT.ON ? Color.YELLOW : Color.DARK_GRAY);
				g2d.fill(laCellule);
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.draw(laCellule);
				//TODO séparation des cellule par de minces ligne grise ou blanchardes
			}
	}
	/*RUN*/
	public synchronized void  run() {
		while(true){
			generation();
			try{
				Thread.sleep(vitesse);
				
				synchronized(this){
					while(!enMarche){
						wait();
					}
				}
			}catch(InterruptedException ie){
				break;
			}	
		}
	}
}