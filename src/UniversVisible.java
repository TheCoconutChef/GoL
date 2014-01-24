import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
//TODO 	(1) Que la taille des carrés s'adaptent au nombre de carré
//		(2) Double buffer?
public class UniversVisible extends JFrame 
{
	/*Constructeur*/
	UniversVisible(int carresHorizontaux, int carresVerticaux)
	{
		super("Marche partiellement!");
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	/*main*/
	public static void main(String[] args)
	{
		UniversVisible uv = new UniversVisible(70,140);
		uv.setVisible(true);
	}
}
