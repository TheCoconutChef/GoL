import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GestionDebutFin implements ActionListener
{
	Cadrillage cadre;
	BarreCritere bc;
	Thread threadUnivers;
	
	GestionDebutFin(Cadrillage m_cadre, BarreCritere m_bc){cadre = m_cadre; bc = m_bc;}
	
	public void actionPerformed(ActionEvent e){
		String action = e.getActionCommand();
		if(action == "Que l'univers soit!"){
			if(!cadre.getEnMarche()){
				threadUnivers = new Thread(cadre);
				threadUnivers.start();
			}
			bc.setCriteresDatumBc();
			cadre.setEnMarche(true);
		}
		else if(action == "L'univers prend une pause"){
			cadre.setEnMarche(false);
		}
		else{
			cadre.nettoyer();
		}
	}
}
