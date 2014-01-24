import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BarreOption extends JPanel{
	//Comment faire le lien entre cette classe, ses évènements, et ce qui se passe
	//dans la barre de critères?
	private static final int MIN_VITESSE = 300;
	private static final int MAX_VITESSE = 50;
	private Cadrillage cadre;
	private BarreCritere barreCritere;
	/*CONSTRUCTEUR*/
	BarreOption(Cadrillage m_cadre, BarreCritere m_barreCritere){
		super();
		cadre = m_cadre;
		barreCritere = m_barreCritere;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel		conteneurVitesse	= 	new JPanel();
		conteneurVitesse.setLayout(new BoxLayout(conteneurVitesse, BoxLayout.Y_AXIS));
		JLabel 		vitesse 			= 	new JLabel("Vitesse");
		JSlider 	vitesseSlider 		= 	new JSlider(JSlider.HORIZONTAL, MIN_VITESSE, MAX_VITESSE);
		conteneurVitesse.add(vitesse);
		conteneurVitesse.add(vitesseSlider);
		
		JPanel 		conteneurOptionCBox		= new JPanel();
		conteneurOptionCBox.setMaximumSize(new Dimension(1000,40)); //temporaire
		BoxLayout bl = new BoxLayout(conteneurOptionCBox, BoxLayout.Y_AXIS);
		conteneurOptionCBox.setLayout(bl);
		String[] 	optionQtyCelluleCBox 	= 	{"Peu", "Moyen", "Beaucoup"};
		JLabel 		qtyCellules 			= 	new JLabel("Quantité de cellules");
		JComboBox<String> 	qtyCellulesCBox = 	new JComboBox<String>(optionQtyCelluleCBox);
		conteneurOptionCBox.add(qtyCellules);
		conteneurOptionCBox.add(qtyCellulesCBox);

		//TODO Les preConfig parce que y'en a au dessus de 100
		/*
		JLabel preConfig = new JLabel("Pre-Config");
		JComboBox preConfigCBox = new JComboBox();*/
		
		vitesseSlider.addChangeListener(new SentinelleSlider());
		add(conteneurVitesse);
		add(conteneurOptionCBox);
	}
	public class SentinelleSlider implements ChangeListener{
		public void stateChanged(ChangeEvent event) {
			JSlider source = (JSlider)event.getSource();
			if(!source.getValueIsAdjusting()){
				cadre.setVitesse((int)source.getValue());
			}
		}
	}
}