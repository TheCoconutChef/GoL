
public class UniversDatum 
{	
	/*Construction de l'univers*/
	public UniversDatum(int m_largeur, int m_hauteur){
		modCritP = new String[8];
		lesCriteresP = new int[8];
		lesCriteresN = new int[10];
		lesCriteresR = new int[10];
		for(int i = 0; i<10; i++){
			modCritP[i%8] = "O";
			lesCriteresP[i%8] = -1;
			lesCriteresN[i] = -1;
			lesCriteresR[i] = -1;
		}
		univers = new ETAT[m_largeur][m_hauteur];
		universFutur = new ETAT[m_largeur][m_hauteur];
		hauteur = m_hauteur;
		largeur = m_largeur;
		nettoyer();
	}
	/*Chaque nouvelle generation*/
	public void generation(){
		for(int i = 0; i<largeur; i++)
			for(int j = 0; j<hauteur; j++){
				universFutur[i][j] = applicationRegle(i,j);
			}
		passageVersLeFutur();
	}
	/*Verification pour une cellulle individuelle*/
	private ETAT applicationRegle(int x, int y){
		ETAT reponse = ETAT.OFF;
		int xOuest, xEst, yNord, ySud;
		int[] voisinnage = new int[8];
		int voisinON = 0;
		int critere = 1;
		int luiMeme = univers[x][y] == ETAT.ON ? 1 : 0;
		/*Bouclage de l'univers*/
		xEst 	= 	(x==largeur-1) 	? 0 : (x+1);
		xOuest 	= 	(x==0)			? largeur-1 : (x-1);
		ySud	=	(y==hauteur-1)? 0 : (y+1);
		yNord 	= 	(y==0) ? hauteur-1 : (y-1);
		/*Sondage du voisinnage*/
		voisinnage[0] = univers[xEst][yNord].ordinal();
		voisinnage[1] = univers[x][yNord].ordinal();
		voisinnage[2] = univers[xOuest][yNord].ordinal();
		voisinnage[3] = univers[xOuest][y].ordinal();
		voisinnage[4] = univers[xEst][y].ordinal();
		voisinnage[5] = univers[xOuest][ySud].ordinal();
		voisinnage[6] = univers[x][ySud].ordinal();
		voisinnage[7] = univers[xEst][ySud].ordinal();
		/*Calcul voisinnage + critere*/
		for(int i = 0; i<8; i++){
			voisinON += voisinnage[i];
			critere *= (voisinnage[i]==1) ? factPremier[i] : 1;
		}
		critere *= luiMeme == 1 ? 23 : 1;
		/*Condition*/
		return  seraOn(critere, voisinON, luiMeme) ? ETAT.ON : ETAT.OFF; 
	}
	//TODO adapter la méthode pour les conditions R/N
	public boolean seraOn(int critere, int voisinON, int luiMeme){
		//Dans cette conception, les criteres de patron on priorité sur les numériques
		for(int i = 0; i<8; i++){
			if(lesCriteresP[i] == critere){
				return modCritP[i] == "V" ? true : false; 
			}
		}
		for(int i = 0; i<9; i++){
			if(voisinON == lesCriteresN[i] || (lesCriteresR[i] == voisinON && luiMeme == 1)){
				return true;
			}
		}
		return false;
	}
	/*Nouvel Etat*/
	private void passageVersLeFutur(){
		for(int x = 0; x<largeur; x++)
			for(int y = 0; y<hauteur; y++)
				univers[x][y] = universFutur[x][y];
	}
	/*Nettoyage*/
	public void nettoyer(){
		for(int x = 0; x<largeur; x++)
			for(int y = 0; y<hauteur; y++)
				univers[x][y] = ETAT.OFF;
	}
	/*GET*/
	public ETAT getEtatCellule(int y, int x)	{return univers[y][x];}
	public int 	getHauteur()					{return hauteur;}
	public int 	getLargeur()					{return largeur;}
	/*SET*/
	public void setCritereP(int critere, int index){lesCriteresP[index] = critere;}
	public void setModCritP(String mod, int index){modCritP[index] = mod;}
	public void setCritereN(int critN, int index){lesCriteresN[index] = critN;}
	public void setCritereR(int critR, int index){lesCriteresR[index] = critR;}
	public void setEtatCellule(int y, int x){
		if(univers[y][x]==ETAT.OFF){univers[y][x]=ETAT.ON;}
		else{univers[y][x]=ETAT.OFF;}
	}
	/*VAR*/
	public enum ETAT{OFF, ON};
	private 	ETAT[][] 	univers;
	private		ETAT[][]	universFutur;
	private 	int[]		lesCriteresP;
	private 	String[]	modCritP;
	private 	int[]		lesCriteresN;
	private 	int[]		lesCriteresR;
	private 	int 		hauteur;
	private 	int 		largeur;
	
	private final int[]		factPremier = {2,3,5,7,11,13,17,19};

}