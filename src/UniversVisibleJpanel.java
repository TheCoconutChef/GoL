import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class UniversVisibleJpanel extends JPanel {
	UniversVisibleJpanel(int carresHorizontaux, int carresVerticaux){
		super();
		Cadrillage cadre = new Cadrillage(carresVerticaux, carresHorizontaux);
		BarreCritere bc = new BarreCritere(cadre);
		BarreOption bo = new BarreOption(cadre, bc);
		JPanel bouttoniere = new JPanel();
		JButton commencer = new JButton("Que l'univers soit!");
		JButton pause = new JButton("L'univers prend une pause");
		JButton apocalypse = new JButton("Apocalypse");
		GestionDebutFin gdf = new GestionDebutFin(cadre, bc);

		cadre.setPreferredSize(new Dimension(700, 350));
		bc.setPreferredSize(new Dimension(100,70));
		bouttoniere.add(commencer);
		bouttoniere.add(pause);
		bouttoniere.add(apocalypse);
		apocalypse.addActionListener(gdf);
		commencer.addActionListener(gdf);
		pause.addActionListener(gdf);
		setLayout(new BorderLayout());
		add(cadre, BorderLayout.CENTER);
		add(bouttoniere, BorderLayout.SOUTH);
		add(bc, BorderLayout.NORTH);
		add(bo, BorderLayout.EAST);
	}
}
