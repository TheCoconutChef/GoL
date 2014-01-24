import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SentinelleEtatCellule implements MouseListener
{
	private Cadrillage cadre;
	
	SentinelleEtatCellule(Cadrillage m_cadre){cadre = m_cadre;}
	
	public void mouseClicked(MouseEvent event)
	{
		int x = (int)event.getX()/cadre.getTailleCellule();
		int y = (int)event.getY()/cadre.getTailleCellule();
		cadre.setEtatRec(x, y);
		//cadre.lesAdjacents(x, y);
		//System.out.println("x = " + x + " y = " + y);
	}
	//Implémantation vide
	public void mouseReleased(MouseEvent event){}
	public void mousePressed(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
}