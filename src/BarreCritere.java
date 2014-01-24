import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class BarreCritere extends JPanel{
	/*VAR*/
	private Cadrillage 	cadre;
	private CritereP[] 	tabCritereP;
	private CritereNum	critereN;
	private CritereNum	critereR;
	/*SET*/
	public void setCriteresDatumBc(){
		for(int i = 0; i<8; i++){
			tabCritereP[i].setModCritP();
			cadre.setCritereP(tabCritereP[i].isActive() ? tabCritereP[i].getIdCritere() : -1, i);
			cadre.setModCritP(tabCritereP[i].isActive() ? tabCritereP[i].getModCrit() : "O", i);
			if(tabCritereP[i].isActive()){
				System.out.println("Crit : " +tabCritereP[i].getIdCritere()
						+ " ModCrit : " +tabCritereP[i].getModCrit());
			}
		}
		for(int i = 0; i<9; i++){
			cadre.setCritereN(critereN.isActive() && critereN.isOn(i%3,(int)i/3) ? i+1:-1, i+1);
			cadre.setCritereR(critereR.isActive() && critereR.isOn(i%3,(int)i/3) ? i+1:-1, i+1);
		}
	}
	/*CONSTRUCTEUR*/
	BarreCritere(Cadrillage m_cadre){
		//TODO GridBag Layout pour séparer les crit numériques des crit patron
		super();
		cadre = m_cadre;
		//JPanel lesMoities = new JPanel();
		//lesMoities.setLayout(new BoxLayout(lesMoities, BoxLayout.Y_AXIS));
		//JPanel moitierSup = new JPanel();
		//JPanel moitierInf = new JPanel();
		JPanel lesNum = new JPanel();
		lesNum.setLayout(new BoxLayout(lesNum, BoxLayout.Y_AXIS));
		critereN = new CritereNum("N");
		critereR = new CritereNum("R");
		tabCritereP = new CritereP[8];
		for(int i = 0; i<8; i++){
			tabCritereP[i] = new CritereP();
			tabCritereP[i].resetCritere();
			add(tabCritereP[i]);
		}
		//lesMoities.add(moitierSup);
		//lesMoities.add(moitierInf);
		lesNum.add(critereN);
		lesNum.add(critereR);
		//add(lesMoities);
		//add(lesNum);
		add(critereN);
		add(critereR);
	}
	/*CLASS INTERNE : CRITERE, CRITEREP, CRITERENUM*/
	public abstract class Critere extends JPanel{
		/*VAR*/
		protected int 						tailleCase = 10;
		protected boolean					active = false;
		protected boolean[][] 				grilleDatum; 
		/*IS*/
		public boolean isActive()			{return active;}
		public boolean isOn(int i, int j)	{return grilleDatum[i][j];}
		/*SET*/
		public void setEtatCase(int i, int j){
			grilleDatum[i][j] = !(grilleDatum[i][j]);
			repaint();
		}
		public void resetCritere(){
			for(int i = 0; i<3; i++)
				for(int j = 0; j<3; j++){
					grilleDatum[i][j] = false;
				}
		}
		/*CONSTRUCTEUR*/
		Critere(){
			super();
			grilleDatum = new boolean[3][3];
			for(int i = 0; i<3; i++)
				for(int j = 0; j<3; j++){
					grilleDatum[i][j] = false;
				}
			repaint();
		}
		/*CLASSE INTERNE SENTINELLE*/
		/*
		 * \brief MouseListener pour changer l'état des cases
		 */
		public class SentinelleEtat implements MouseListener{
			public void mouseClicked(MouseEvent event){
				if(!cadre.getEnMarche() && isActive()){
					int i = (int)event.getX()/tailleCase;
					int j = (int)event.getY()/tailleCase;
					setEtatCase(i,j);
					System.out.println("i = " + i + " j = " + j);
				}
			};
			/*Implémantation vide*/
			public void mouseReleased(MouseEvent event){}
			public void mousePressed(MouseEvent event){}
			public void mouseEntered(MouseEvent event){}
			public void mouseExited(MouseEvent event){}
		}
		public class SentinelleEtatCheck implements ItemListener{
			public void itemStateChanged(ItemEvent event) {
				active = event.getStateChange() == ItemEvent.SELECTED ? true : false;
			}
		}
		/*CLASSE INTERNE PSEUDO-PAIR*/
		public class pair<L,R>{
			/*VAR*/
			private L premier;
			private R second;
			/*GET*/
			public L first() {return premier;}
			public R second(){return second;}
			/*SET*/
			public void setFirst(L f){premier = f;}
			public void setSecond(R s){second = s;}
			/*CONSTRUCTEUR*/
			pair(L m_premier, R m_second){
				premier = m_premier;
				second = m_second;
			}
		}
	}
	public class CritereP extends Critere{
		/*VAR*/
		private Rectangle2D.Float[][] 	grille;
		private CanvasCritereP 			canvas;
		private pair<Integer, String>	critereEtat;
		private final int[][] 			factPremier = {{5,7,13},{3,23,17},{2,11,19}};
		private final JPanel			conteneurBouton = new JPanel();
		private final ButtonGroup		mvBg = new ButtonGroup();
		private final JRadioButton		V = new JRadioButton("V");
		private final JRadioButton 		M = new JRadioButton("M"); 
		/*GET*/
		public int getIdCritere()		{return critereEtat.premier;}
		public String getModCrit()		{return critereEtat.second;}
		/*SET*/
		public void setModCritP(){if(active){critereEtat.setSecond((String)mvBg.getSelection().getActionCommand());}}
		public void setCritereEtat(){
			int intCritere = 1;
			for(int i = 0; i<3; i++)
				for(int j = 0; j<3; j++){
					intCritere *= (grilleDatum[i][j] == true) ?  factPremier[i][j]: 1;
				}
			critereEtat.setFirst(intCritere);
		}
		public void resetCritere(){
			super.resetCritere();
			critereEtat.setFirst(-1);
			critereEtat.setSecond("O");
		}
		/*CONSTRUCTEUR*/
		CritereP(){
			super();
			
			canvas = new CanvasCritereP();
			critereEtat = new pair<Integer, String>(1,"N");
			canvas.addMouseListener(new SentinelleEtatCritereP());
			canvas.setPreferredSize(new Dimension(tailleCase*3, tailleCase*3));
			V.addActionListener(new SentinelleModCrit()); V.setActionCommand("V"); 
			M.addActionListener(new SentinelleModCrit()); M.setActionCommand("M"); 
			mvBg.add(V);mvBg.add(M);
			conteneurBouton.setLayout(new BoxLayout(conteneurBouton, BoxLayout.Y_AXIS));
			conteneurBouton.add(V);conteneurBouton.add(M);
			grille = new Rectangle2D.Float[3][3];
			for(int i = 0; i<3; i++)
				for(int j = 0; j<3; j++){
					grille[i][j] = new Rectangle2D.Float(i*tailleCase, j*tailleCase, tailleCase, tailleCase);
				}

			add(canvas);			
			add(conteneurBouton);
		}
		/*CLASSE INTERNE CANVAS*/
		public class CanvasCritereP extends JPanel{
			/*CONSTRUCTEUR*/
			CanvasCritereP(){
				super();
			}
			/*PAINT COMPONENT OVERRIDE*/
			public void paintComponent(Graphics g){
				Graphics2D g2d = (Graphics2D) g;
				super.paintComponent(g);
				for(int i = 0; i<3; i++)
					for(int j = 0; j<3; j++){
						g2d.setColor(grilleDatum[i][j]==false ? Color.GRAY : Color.YELLOW);
						g2d.fill(grille[i][j]);
						g2d.setColor(Color.WHITE);
						g2d.draw(grille[i][j]);
					}
				g2d.setColor(Color.WHITE);
				g2d.draw(grille[1][1]);
			}
		}
		/*SOUS CLASSE SENTINELLE ETAT*/
		public class SentinelleEtatCritereP extends SentinelleEtat{
			public void mouseClicked(MouseEvent event){
				super.mouseClicked(event);
				if(!cadre.getEnMarche() && isActive()){
						setCritereEtat();
					}
				}
			}
		/*SOUS CLASSE SENTINELLE MODCRIT*/
		public class SentinelleModCrit implements ActionListener{
			public void actionPerformed(ActionEvent event){
				if(event.getActionCommand() == critereEtat.second){
					mvBg.clearSelection();
					critereEtat.setSecond("O");
					active = false;
				}
				else{
					critereEtat.setSecond(event.getActionCommand());
					active = true;
				}
			}
		}
	}
	public class CritereNum extends Critere{
		private JCheckBox				activeBox;;
		private Rectangle2D.Float[][] 	grille;
		private CanvasCritereNum		canvas;
		private final int[][] 			nombres = {{1,4,7},{2,5,8},{3,6,9}};
		/*CONSTRUCTEUR*/
		CritereNum(String nomCheck){
			super();
			//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			grille = new Rectangle2D.Float[3][3];
			for(int i = 0; i<3; i++)
				for(int j = 0; j<3; j++){
					grille[i][j] = new Rectangle2D.Float(i*tailleCase, j*tailleCase, tailleCase, tailleCase);
				}
			activeBox = new JCheckBox(nomCheck);
			canvas = new CanvasCritereNum();
			canvas.addMouseListener(new SentinelleEtat());
			activeBox.addItemListener(new SentinelleEtatCheck());
			add(canvas);
			add(activeBox);
		}
		/**/
		public class CanvasCritereNum extends JPanel{
			CanvasCritereNum(){
				super();
				this.setPreferredSize(new Dimension(tailleCase*3, tailleCase*3));
			}
			/*PAINT COMPONENT OVERRIDE*/
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				for(int i = 0; i<3; i++)
					for(int j = 0; j<3; j++){
						g2d.setColor(grilleDatum[i][j]==true? Color.BLUE : Color.GREEN);
						g2d.fill(grille[i][j]);
						g2d.setColor(Color.WHITE);
						g2d.draw(grille[i][j]);
					}
			}
		}
	}
}