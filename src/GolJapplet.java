import javax.swing.JApplet;

public class GolJapplet extends JApplet {
	public void init(){
		try{
			creerIG();
		}catch(Exception e){
			System.err.println("Échec de la création de l'IG");
		}
	}
	public void start(){
		
	}
	public void stop(){
		
	}
	public void destroy(){
		
	}
	
	private void creerIG(){
		UniversVisibleJpanel uv = new UniversVisibleJpanel(70,140);
		uv.setOpaque(true);
		setContentPane(uv);
	}
}
