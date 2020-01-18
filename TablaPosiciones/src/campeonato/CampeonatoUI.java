package campeonato;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class CampeonatoUI extends JFrame implements ActionListener{
	JButton nuevo = new JButton("Nuevo Campeonato");
	JButton cargar = new JButton("Cargar Campeonato");
	
	
	CampeonatoUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Campeonato");
		this.pack();
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.add(nuevo);
		this.add(cargar);
		
	}
	
	public static void main(String[] args) {
		
	}
}
