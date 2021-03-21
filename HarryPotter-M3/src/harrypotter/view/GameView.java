package harrypotter.view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import sun.audio.*;
import harrypotter.exceptions.InCooldownException;
import harrypotter.exceptions.InvalidTargetCellException;
import harrypotter.exceptions.NotEnoughIPException;
import harrypotter.exceptions.OutOfBordersException;
import harrypotter.exceptions.OutOfRangeException;
import harrypotter.model.character.Champion;
import harrypotter.model.character.GryffindorWizard;
import harrypotter.model.character.HufflepuffWizard;
import harrypotter.model.character.RavenclawWizard;
import harrypotter.model.character.SlytherinWizard;
import harrypotter.model.character.Wizard;
import harrypotter.model.magic.Collectible;
import harrypotter.model.magic.DamagingSpell;
import harrypotter.model.magic.HealingSpell;
import harrypotter.model.magic.Potion;
import harrypotter.model.magic.RelocatingSpell;
import harrypotter.model.magic.Spell;
import harrypotter.model.tournament.FirstTask;
import harrypotter.model.tournament.SecondTask;
import harrypotter.model.tournament.Task;
import harrypotter.model.tournament.ThirdTask;
import harrypotter.model.tournament.Tournament;
import harrypotter.model.world.Cell;
import harrypotter.model.world.ChampionCell;
import harrypotter.model.world.CollectibleCell;
import harrypotter.model.world.CupCell;
import harrypotter.model.world.Direction;
import harrypotter.model.world.EmptyCell;
import harrypotter.model.world.Merperson;
import harrypotter.model.world.ObstacleCell;
import harrypotter.model.world.TreasureCell;
import harrypotter.model.world.WallCell;
import javax.swing.AbstractButton;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameView extends JFrame implements ActionListener, KeyListener {
	private Tournament tournment;
	private JPanel map = new JPanel();
	private JPanel choose;
	private JFrame window;
	private JComboBox school1;
	private JComboBox school2;
	private JComboBox school3;
	private JComboBox school4;
	private JTextField name1;
	private JTextField name2;
	private JTextField name3;
	private JTextField name4;
	private JComboBox a1;// spells
	private JComboBox a2;// spells
	private JComboBox a3;// spells
	private JComboBox b1;// spells
	private JComboBox b2;// spells
	private JComboBox b3;// spells
	private JComboBox c1;// spells
	private JComboBox c2;// spells
	private JComboBox c3;// spells
	private JComboBox d1;// spells
	private JComboBox d2;// spells
	private JComboBox d3;//spells
	private ArrayList spells1;
	private ArrayList spells2;
	private ArrayList spells3;
	private ArrayList spells4;
	private JPanel startbuttonpanel;
	private boolean markcells = false;//fire
	ArrayList<Point> marked;//fire
	private Clip clip;
public void congrats(){

	setBounds(50, 50,1920, 1080);
	JPanel gameover = new JPanel();
	JLabel x = new JLabel();
	x.setIcon(new ImageIcon("congrats1.jpg"));
	gameover.setVisible(true);
	window.dispose();
	window = new JFrame();
	window.setVisible(true);
	window.addKeyListener(this);
	JButton startadv = new JButton();
	startadv.setText("Restart Game");
	startadv.setFont(new Font("Chiller", Font.BOLD, 25));
	startadv.addActionListener(this);
	JButton endadv = new JButton();
	endadv.setText("Quit Game");
	endadv.setBackground(Color.red);
	endadv.setFont(new Font("Chiller", Font.BOLD, 25));
	endadv.addActionListener(this);
	gameover.add(startadv, BorderLayout.SOUTH);
	gameover.add(endadv);
	window.add(gameover);
	gameover.setBackground(Color.black);
	startadv.setBackground(Color.red);
	gameover.add(x);
	
	window.setExtendedState(JFrame.MAXIMIZED_BOTH);     
	revalidate();
}

	public void gameover() {

		setBounds(50, 50, 1920, 1080);
		JPanel gameover = new JPanel();
		JLabel x = new JLabel();
		x.setIcon(new ImageIcon("gameover2.png"));
		gameover.setVisible(true);
		window.dispose();
		window = new JFrame();
		window.setVisible(true);
		window.addKeyListener(this);
		JButton startadv = new JButton();
		startadv.setText("Restart Game");
		startadv.setFont(new Font("Chiller", Font.BOLD, 25));
		startadv.addActionListener(this);
		JButton endadv = new JButton();
		endadv.setText("Quit Game");
		endadv.setBackground(Color.red);
		endadv.setFont(new Font("Chiller", Font.BOLD, 25));
        
		endadv.addActionListener(this);
		gameover.add(startadv, BorderLayout.SOUTH);
		gameover.add(endadv);
		window.add(gameover);
		gameover.setBackground(Color.black);
		startadv.setBackground(Color.red);
		gameover.add(x);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		revalidate();

	}

	public static boolean member(int i,int j, ArrayList<Point> l){
		//fire
		boolean f = false;
		for(int k = 0;k<l.size();k++){
			if(l.get(k).x==i&&l.get(k).y ==j){
				f = true;
			}
		}
		return f;
	}
	public void map() {
		if (tournment.getLevel().getChampions().size() == 0) 
		{
			if(tournment.getLevel() instanceof FirstTask)
			{
				FirstTask f = (FirstTask)tournment.getLevel();
				if(f.getWinners().size()==0)
				{
					gameover();
				}
			}
			if(tournment.getLevel() instanceof SecondTask)
			{
				SecondTask f = (SecondTask)tournment.getLevel();
				if(f.getWinners().size()==0)
				{
					gameover();
				}
			}
			if(tournment.getLevel() instanceof ThirdTask)
			{
			if(tournment.winner!=null)
				congrats();
			else
				gameover();
			}
	
		}
	
		else{
			window.setVisible(true);
			map.removeAll();
			map.setLayout(new GridLayout(10, 10));
			Cell[][] map1 = tournment.getLevel().getMap();
			if (tournment.getLevel() instanceof FirstTask) {
				
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (map1[i][j] instanceof EmptyCell) {
							if (i == 4 && j == 4) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("egg1.jpg"));
								map.add(y);
							} else {
								if( markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("sand.jpg"));
								map.add(y);
							}
							}
						}
						if (map1[i][j] instanceof ObstacleCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("obstacle.jpg"));
							y.setToolTipText(""+ ((ObstacleCell) map1[i][j]).getObstacle().getHp());
							map.add(y);
						}
						if (map1[i][j] instanceof CollectibleCell) {
							if (((CollectibleCell) map1[i][j]).getCollectible() instanceof Potion) {
								if(markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("potion1.jpg"));
								map.add(y);
							}
							}
						}

						if (map1[i][j] instanceof ChampionCell) {
							if(((ChampionCell) map1[i][j]).getChamp() == tournment.getLevel().getCurrentChamp())
							{
								if(((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard){
									if(markcells ==true&&member(i,j,marked)==true){
										JLabel y = new JLabel();
										y.setIcon(new ImageIcon("fire1.jpg"));
										
										map.add(y);
									}
									else{
			
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentgryf.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
							}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard){
									if(markcells ==true&&member(i,j,marked)==true){
										JLabel y = new JLabel();
										y.setIcon(new ImageIcon("fire1.jpg"));
										map.add(y);
									}
									else{
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentsly.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard){
									if(markcells ==true&&member(i,j,marked)==true){
										JLabel y = new JLabel();
										y.setIcon(new ImageIcon("fire1.jpg"));
										map.add(y);
									}
									else{
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currenthuff.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard){
									if(markcells ==true&&member(i,j,marked)==true){
										JLabel y = new JLabel();
										y.setIcon(new ImageIcon("fire1.jpg"));
										map.add(y);
									}
									else{
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentrav.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								}
							}
							else{
							if (((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard) {
								if(markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Gryfindor11.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard) {
								if(markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Sylythirn11.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard) {
								if(markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Hufflepuff11.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard) {
								if(markcells ==true&&member(i,j,marked)==true){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("fire1.jpg"));
									map.add(y);
								}
								else{
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("ravenclaw11.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							}
						}
						}
						
					}
					
				}
			}
			if (tournment.getLevel() instanceof SecondTask) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (map1[i][j] instanceof EmptyCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("lake.jpg"));
							map.add(y);
						}
						
						if (map1[i][j] instanceof ChampionCell) {
							if(((ChampionCell) map1[i][j]).getChamp() == tournment.getLevel().getCurrentChamp())
							{
								if(((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentgryf.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentsly.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currenthuff.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentrav.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
							}
							else{
							if (((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Gryfindor12.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Sylythirn12.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Hufflepuff12.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("ravenclaw12.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
						}
						}
						if( map1[i][j] instanceof TreasureCell){
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("lake.jpg"));
							map.add(y);
						}
						if (map1[i][j] instanceof CollectibleCell) {
							if (((CollectibleCell) map1[i][j]).getCollectible() instanceof Potion) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("potion2.jpg"));
								map.add(y);
							}
						}
						
						if (map1[i][j] instanceof ObstacleCell) {
							if (((ObstacleCell) map1[i][j]).getObstacle() instanceof Merperson) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("merperson22.png"));
								map.add(y);
							}

						}

					}
				}
			}
			if (tournment.getLevel() instanceof ThirdTask) {
				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						if (map1[i][j] instanceof EmptyCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("maze.png"));
							map.add(y);
						}
						if (map1[i][j] instanceof WallCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("wall.jpg"));
							map.add(y);
						}
						if (map1[i][j] instanceof ChampionCell) {//hena khaletha 3 bardo 3ashan ba3d el deadline el background han3'yaro
							if(((ChampionCell) map1[i][j]).getChamp() == tournment.getLevel().getCurrentChamp())
							{
								if(((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentgryf.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentsly.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currenthuff.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
								if(((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard){
									JLabel y = new JLabel();
									y.setIcon(new ImageIcon("currentrav.jpg"));
									Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
									y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
									map.add(y);
								}
							}
							else{
							if (((ChampionCell) map1[i][j]).getChamp() instanceof GryffindorWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Gryfindor1.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof SlytherinWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Sylythirn1.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof HufflepuffWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("Hufflepuff3.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
							if (((ChampionCell) map1[i][j]).getChamp() instanceof RavenclawWizard) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("ravenclaw1.jpg"));
								Wizard w = (Wizard) ( (ChampionCell) map1[i][j]).getChamp();
								y.setToolTipText("HP: "+ w.getHp()+"   Name: "+ w.getName()+"  IP:  "+ w.getIp()+" TraitCooldown:  "+ w.getTraitCooldown());
								map.add(y);
							}
						}
						}
						if (map1[i][j] instanceof CollectibleCell) {
							if (((CollectibleCell) map1[i][j]).getCollectible() instanceof Potion) {
								JLabel y = new JLabel();
								y.setIcon(new ImageIcon("potion.jpg"));
								map.add(y);
							}

						}
						if (map1[i][j] instanceof CupCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("cup.png"));
							map.add(y);
						}
						if (map1[i][j] instanceof ObstacleCell) {
							JLabel y = new JLabel();
							y.setIcon(new ImageIcon("obstacle.jpg"));
							y.setToolTipText(""+ ((ObstacleCell) map1[i][j]).getObstacle().getHp());
							map.add(y);
						}

					}
				}
			}
			
			if (tournment.getLevel() instanceof FirstTask ) 
			{
				JPanel y1 = new JPanel();
				int i=0;
				int added=0;
				for(;i<tournment.getLevel().getChampions().size();i++)
				{
					Wizard w = (Wizard) tournment.getLevel().getChampions().get(i);
					JTextArea y2 = new JTextArea();
					y2.setEditable(false);
					y2.setFont(new Font("Arial", Font.BOLD, 15));
					//y2.setBackground(Color.red);
					y2.addKeyListener(this);	
					y1.setPreferredSize(new Dimension(400, 100));
					//y1.setBackground(Color.red);
					JLabel y = new JLabel();
					y1.add(y2);
					y1.add(y);
					window.add(y1, BorderLayout.EAST);
					y1.setVisible(true);
					if(i==tournment.getLevel().getChampions().size()-1)
					{
						y.setIcon(new ImageIcon("dragon1.png"));
					}
					added++;
				}
				y1.repaint();
				y1.validate();
		       
	
			}
			if (tournment.getLevel() instanceof SecondTask) {
				JPanel y1 = new JPanel();
				int i=0;
				int added=0;
				for(;i<tournment.getLevel().getChampions().size();i++)
				{
					Wizard w = (Wizard) tournment.getLevel().getChampions().get(i);
					JTextArea y2 = new JTextArea();
					y2.setEditable(false);
					y2.setFont(new Font("Chiller", Font.BOLD, 15));
					//y2.setBackground(Color.red);
					y2.addKeyListener(this);	
					y1.setPreferredSize(new Dimension(400, 100));
					//y1.setBackground(Color.red);
					JLabel y = new JLabel();
					y1.add(y2);
					y1.add(y);
					window.add(y1, BorderLayout.EAST);
					y1.setVisible(true);
					if(i==tournment.getLevel().getChampions().size()-1)
					{
						y.setIcon(new ImageIcon("merperson3.png"));
					}
					added++;
				}
				y1.validate();
	
			}
			if (tournment.getLevel() instanceof ThirdTask){
				JPanel y1 = new JPanel();
				int i=0;
				int added=0;
				for(;i<tournment.getLevel().getChampions().size();i++)
				{
					Wizard w = (Wizard) tournment.getLevel().getChampions().get(i);
					JTextArea y2 = new JTextArea();
					y2.setEditable(false);
					y2.setFont(new Font("Chiller", Font.BOLD, 15));
					//y2.setBackground(Color.red);
					y2.addKeyListener(this);	
					y1.setPreferredSize(new Dimension(400, 100));
					//y1.setBackground(Color.red);
					JLabel y = new JLabel();
					y1.add(y2);
					y1.add(y);
					window.add(y1, BorderLayout.EAST);
					y1.setVisible(true);
					if(i==tournment.getLevel().getChampions().size()-1)
					{
						y.setIcon(new ImageIcon("voldomort.png"));
					}
					added++;
				}
				y1.validate();
			}
			
			revalidate();
			
			map.setVisible(true);
			window.add(map, BorderLayout.CENTER);
			
			map.repaint();
			map.revalidate();
			
			map.setVisible(true);
			window.add(map, BorderLayout.CENTER);
			
			map.repaint();
			map.revalidate();

		}
	}

	public GameView() {
		setBounds(50, 50, 1920, 1080);
		JPanel start = new JPanel();
		start.setBackground(new Color(3,20,12));
		start.setVisible(true);
		JLabel x = new JLabel();
		x.setIcon(new ImageIcon("Tricupwizard.gif"));
		window = new JFrame();
		window.setVisible(true);
		window.addKeyListener(this);
		JButton startadv = new JButton();
		startadv.setText("START");
		startadv.setFont(new Font("Chiller", Font.BOLD, 85));
		startadv.setSize(100,100);
		startadv.setOpaque(true);
		startadv.setBackground(new Color(3,20,12));
		startadv.setForeground(new Color(150,200,200));
		startadv.setBorderPainted(false);
		startadv.addActionListener(this);
		
//        JButton controls = new JButton();
//        controls.setText("Game Controls");
//        controls.setFont(new Font("Chiller", Font.BOLD, 50));
//        controls.setSize(100,100);
//        controls.setOpaque(true);
//        controls.setBackground(new Color(3,20,12));
//        controls.setForeground(new Color(150,200,200));
//        controls.setBorderPainted(false);
//        controls.addActionListener(this);
//        start.add(controls);
//		window.add(start);
//		 JButton gameinfo = new JButton();
//		 gameinfo.setText("Game Tasks");
//		 gameinfo.setFont(new Font("Chiller", Font.BOLD, 50));
//		 gameinfo.setSize(100,100);
//		 gameinfo.setOpaque(true);
//		 gameinfo.setBackground(new Color(3,20,12));
//		 gameinfo.setForeground(new Color(150,200,200));
//		 gameinfo.setBorderPainted(false);
//		 gameinfo.addActionListener(this);
//	     window.add(gameinfo,BorderLayout.EAST);
//	     window.add(controls,BorderLayout.WEST);
			window.add(start);
		window.add(startadv,BorderLayout.SOUTH);
		start.add(x);
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		try 
		{
			File soundfile = new File("music.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(clip.LOOP_CONTINUOUSLY);

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void choose() throws IOException {
		
		window.setVisible(false);

		setTitle("The TriWizard Tournament");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setBounds(50, 50, 1920, 1080);

		tournment = new Tournament();
		choose = new JPanel();
		choose.setLayout(null);
		name1 = new JTextField("name 1");
		name2 = new JTextField("name 2");
		name3 = new JTextField("name 3");
		name4 = new JTextField("name 4");
		name1.setFont(new Font("Chiller", Font.BOLD, 25));
		name2.setFont(new Font("Chiller", Font.BOLD, 25));
		name3.setFont(new Font("Chiller", Font.BOLD, 25));
		name4.setFont(new Font("Chiller", Font.BOLD, 25));
		name1.setEditable(true);
		name2.setEditable(true);
		name3.setEditable(true);
		name4.setEditable(true);
		
		
		name1.setOpaque( false );
		name1.setBorder(null);
		JLabel label1 = new JLabel( new ImageIcon("/Users/MohamedAshraf/Desktop/rsz_harrypotter.png") );
		label1.setOpaque(true);
		label1.setLayout( new BorderLayout() );
		label1.add( name1,BorderLayout.CENTER );
		
		name2.setOpaque( false );
		name2.setBorder(null);
		JLabel label2 = new JLabel( new ImageIcon("/Users/MohamedAshraf/Desktop/rsz_2.png") );
		label2.setLayout( new BorderLayout() );
		label2.add( name2 );
		
		name3.setOpaque( false );
		name3.setBorder(null);
		JLabel label3 = new JLabel( new ImageIcon("/Users/MohamedAshraf/Desktop/rsz_14.png") );
		label3.setLayout( new BorderLayout() );
		label3.add( name3 );
		
		name4.setOpaque( false );
		name4.setBorder(null);
		JLabel label4 = new JLabel( new ImageIcon("/Users/MohamedAshraf/Desktop/rsz_13.png") );
		label4.setLayout( new BorderLayout() );
		label4.add( name4 );
		
		choose.add(label1);
		choose.add(label2);
		choose.add(label3);
		choose.add(label4);
		String[] champs = { "Gryfindor", "Sylythirin", "Hufflepuff","Ravenclaw" };
		
		school1 = new JComboBox(champs);
		school2 = new JComboBox(champs);
		school3 = new JComboBox(champs);
		school4 = new JComboBox(champs);
		
		school1.setBackground(Color.red);
		school2.setBackground(Color.green);
		school3.setBackground(Color.blue);
		school4.setBackground(Color.orange);
		
		choose.add(school1);
		choose.add(school2);
		choose.add(school3);
		choose.add(school4);
		
		ArrayList champions = new ArrayList();

		JButton button = new JButton();
		button.setText("Start Game");
		button.setFont(new Font("Chiller", Font.BOLD, 25));
		button.addActionListener(this);
		window = new JFrame();
		window.setVisible(true);

		String[] d_spells = { "Sectumsempra", "Reducto",
				"Piertotum Locomotor", "Oppugno", "Incendio",
				"Expulso", "Bombarda", "Avada Kedavra",
				"Crucio", "Igni", "Kamehameha"};
		String[] h_spells={
				"Cheering Charm", "Expecto Patronum", "Ferula",
				"Protego Horribilis", "Rennervate", "Quen"};
		String[] r_spells={
				"Accio", "Imperio", "Wingardium Leviosa",
				"Axii"};

		a1 = new JComboBox(d_spells);
		a2 = new JComboBox(h_spells);
		a3 = new JComboBox(r_spells);

		a1.setBackground(Color.red);
		a2.setBackground(Color.red);
		a3.setBackground(Color.red);

		b1 = new JComboBox(d_spells);
		b2 = new JComboBox(h_spells);
		b3 = new JComboBox(r_spells);

		b1.setBackground(Color.green);
		b2.setBackground(Color.green);
		b3.setBackground(Color.green);

		c1 = new JComboBox(d_spells);
		c2 = new JComboBox(h_spells);
		c3 = new JComboBox(r_spells);

		c1.setBackground(Color.blue);
		c2.setBackground(Color.blue);
		c3.setBackground(Color.blue);

		d1 = new JComboBox(d_spells);
		d2 = new JComboBox(h_spells);
		d3 = new JComboBox(r_spells);

		d1.setBackground(Color.orange);
		d2.setBackground(Color.orange);
		d3.setBackground(Color.orange);

		a1.setSize(50, 50);

		b1.setBounds(20, 20, 20, 20);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		choose.add(a1);
		choose.add(b1);
		choose.add(c1);
		choose.add(d1);
		choose.add(a2);
		choose.add(b2);
		choose.add(c2);
		choose.add(d2);
		choose.add(a3);
		choose.add(b3);
		choose.add(c3);
		choose.add(d3);

		// choose.add(button);
		startbuttonpanel = new JPanel();
		startbuttonpanel.setLayout(new BorderLayout());
		choose.setLayout(new GridLayout(5, 4));
		window.add(choose);
		// button.setBounds(0, 0, g.getWidth(), g.getHeight());
		startbuttonpanel.add(button, BorderLayout.CENTER);
		startbuttonpanel.setVisible(true);
		// window.add(button,BorderLayout.SOUTH);
		window.add(startbuttonpanel, BorderLayout.SOUTH);
		// choose.add(button, BorderLayout.CENTER);
		// choose.add(x, BorderLayout.SOUTH);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		

		validate();

	}


	public void actionPerformed(ActionEvent e) 
	{
		if(((JButton) e.getSource()).getText().compareTo("Game Controls") == 0){
			
		}

		JFrame errorwindow = new JFrame();
		if (((JButton) e.getSource()).getText().compareTo("START") == 0|| ((JButton) e.getSource()).getText().compareTo("Restart Game") == 0) {
			try 
			{
				clip.start();
				choose();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		if (((JButton) e.getSource()).getText().compareTo("Quit Game") == 0) 
		{
			clip.stop();
			window.dispose();
		}
		if (((JButton) e.getSource()).getText().compareTo("Start Game") == 0) 
		{
			try 
			{
				startbuttonpanel.setVisible(false);
				ArrayList<String> x = new ArrayList<String>();
				x.add(((String) school1.getSelectedItem()));
				x.add(((String) school2.getSelectedItem()));
				x.add(((String) school3.getSelectedItem()));
				x.add(((String) school4.getSelectedItem()));

				for (int i = 0; i < x.size(); i++) 
				{
					if (x.get(i).equals("Gryfindor")) 
					{
						Wizard a =new GryffindorWizard("");
						if(i==0)
						 a = new GryffindorWizard("" + name1.getText());
						if(i==1)
						 a = new GryffindorWizard("" + name2.getText());
						if(i==2)
						 a = new GryffindorWizard("" + name3.getText());
						if(i==3)
						 a = new GryffindorWizard("" + name4.getText());
						tournment.addChampion((Champion) a);
					}
					if (x.get(i).equals("Sylythirin")) 
					{
						Wizard a =new SlytherinWizard("");
						if(i==0)
						 a = new SlytherinWizard("" + name1.getText());
						if(i==1)
						 a = new SlytherinWizard("" + name2.getText());
						if(i==2)
						 a = new SlytherinWizard("" + name3.getText());
						if(i==3)
						 a = new SlytherinWizard("" + name4.getText());
						tournment.addChampion((Champion) a);
					}
					if (x.get(i).equals("Hufflepuff")) 
					{
						Wizard a =new HufflepuffWizard("");
						if(i==0)
						 a = new HufflepuffWizard("" + name1.getText());
						if(i==1)
						 a = new HufflepuffWizard("" + name2.getText());
						if(i==2)
						 a = new HufflepuffWizard("" + name3.getText());
						if(i==3)
						 a = new HufflepuffWizard("" + name4.getText());
						tournment.addChampion((Champion) a);
					}
					if (x.get(i).equals("Ravenclaw")) 
					{
						Wizard a =new RavenclawWizard("");
						if(i==0)
						 a = new RavenclawWizard("" + name1.getText());
						if(i==1)
						 a = new RavenclawWizard("" + name2.getText());
						if(i==2)
						 a = new RavenclawWizard("" + name3.getText());
						if(i==3)
						 a = new RavenclawWizard("" + name4.getText());
						tournment.addChampion((Champion) a);
					}
				}
				switch((String)a1.getSelectedItem())
				{
					case "Sectumsempra":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Sectumsempra",150,5,300));break;
					case "Reducto":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Reducto",100,2,100));break;
					case "Piertotum Locomotor":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Piertotum Locomotor",400,1,200));break;
					case "Oppugno":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Oppugno",50,2,100));break;
					case "Incendio":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Incendio",150,4,250));break;
					case "Expulso":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Expulso",200,5,300));break;
					case "Bombarda":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Bombarda",300,3,350));break;
					case "Avada Kedavra":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Avada Kedavra",500,10,650));break;
					case "Crucio":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Crucio",400,6,500));break;
					case "Igni":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Igni",300,2,300));break;
					case "Kamehameha":((Wizard)tournment.getChampions().get(0)).getSpells().add(new DamagingSpell("Kamehameha",200,7,400));break;
				}
				switch((String)a3.getSelectedItem())
				{
					case "Accio":((Wizard)tournment.getChampions().get(0)).getSpells().add(new RelocatingSpell("Accio",100,1,1));break;
					case "Imperio":((Wizard)tournment.getChampions().get(0)).getSpells().add(new RelocatingSpell("Imperio",400,10,10));break;
					case "Wingardium Leviosa":((Wizard)tournment.getChampions().get(0)).getSpells().add(new RelocatingSpell("Wingardium Leviosa",300,5,5));break;
					case "Axii":((Wizard)tournment.getChampions().get(0)).getSpells().add(new RelocatingSpell("Axii",200,3,3));break;
				}
				switch((String)a2.getSelectedItem())
				{
					case "Cheering Charm":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Cheering Charm",50,2,100));break;
					case "Expecto Patronum":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Expecto Patronum",150,8,550));break;
					case "Ferula":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Ferula",200,4,200));break;
					case "Protego Horribilis":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Protego Horribilis",300,1,100));break;
					case "Rennervate":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Rennervate",100,3,200));break;
					case "Quen":((Wizard)tournment.getChampions().get(0)).getSpells().add(new HealingSpell("Quen",50,1,50));break;
				}
				
				switch((String)b1.getSelectedItem())
				{
					case "Sectumsempra":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Sectumsempra",150,5,300));break;
					case "Reducto":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Reducto",100,2,100));break;
					case "Piertotum Locomotor":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Piertotum Locomotor",400,1,200));break;
					case "Oppugno":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Oppugno",50,2,100));break;
					case "Incendio":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Incendio",150,4,250));break;
					case "Expulso":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Expulso",200,5,300));break;
					case "Bombarda":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Bombarda",300,3,350));break;
					case "Avada Kedavra":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Avada Kedavra",500,10,650));break;
					case "Crucio":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Crucio",400,6,500));break;
					case "Igni":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Igni",300,2,300));break;
					case "Kamehameha":((Wizard)tournment.getChampions().get(1)).getSpells().add(new DamagingSpell("Kamehameha",200,7,400));break;
				}
				switch((String)b3.getSelectedItem())
				{
					case "Accio":((Wizard)tournment.getChampions().get(1)).getSpells().add(new RelocatingSpell("Accio",100,1,1));break;
					case "Imperio":((Wizard)tournment.getChampions().get(1)).getSpells().add(new RelocatingSpell("Imperio",400,10,10));break;
					case "Wingardium Leviosa":((Wizard)tournment.getChampions().get(1)).getSpells().add(new RelocatingSpell("Wingardium Leviosa",300,5,5));break;
					case "Axii":((Wizard)tournment.getChampions().get(1)).getSpells().add(new RelocatingSpell("Axii",200,3,3));break;
				}
				switch((String)b2.getSelectedItem())
				{
					case "Cheering Charm":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Cheering Charm",50,2,100));break;
					case "Expecto Patronum":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Expecto Patronum",150,8,550));break;
					case "Ferula":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Ferula",200,4,200));break;
					case "Protego Horribilis":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Protego Horribilis",300,1,100));break;
					case "Rennervate":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Rennervate",100,3,200));break;
					case "Quen":((Wizard)tournment.getChampions().get(1)).getSpells().add(new HealingSpell("Quen",50,1,50));break;
				}
				switch((String)c1.getSelectedItem())
				{
					case "Sectumsempra":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Sectumsempra",150,5,300));break;
					case "Reducto":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Reducto",100,2,100));break;
					case "Piertotum Locomotor":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Piertotum Locomotor",400,1,200));break;
					case "Oppugno":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Oppugno",50,2,100));break;
					case "Incendio":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Incendio",150,4,250));break;
					case "Expulso":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Expulso",200,5,300));break;
					case "Bombarda":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Bombarda",300,3,350));break;
					case "Avada Kedavra":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Avada Kedavra",500,10,650));break;
					case "Crucio":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Crucio",400,6,500));break;
					case "Igni":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Igni",300,2,300));break;
					case "Kamehameha":((Wizard)tournment.getChampions().get(2)).getSpells().add(new DamagingSpell("Kamehameha",200,7,400));break;
				}
				switch((String)c3.getSelectedItem())
				{
					case "Accio":((Wizard)tournment.getChampions().get(2)).getSpells().add(new RelocatingSpell("Accio",100,1,1));break;
					case "Imperio":((Wizard)tournment.getChampions().get(2)).getSpells().add(new RelocatingSpell("Imperio",400,10,10));break;
					case "Wingardium Leviosa":((Wizard)tournment.getChampions().get(2)).getSpells().add(new RelocatingSpell("Wingardium Leviosa",300,5,5));break;
					case "Axii":((Wizard)tournment.getChampions().get(2)).getSpells().add(new RelocatingSpell("Axii",200,3,3));break;
				}
				switch((String)c2.getSelectedItem())
				{
					case "Cheering Charm":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Cheering Charm",50,2,100));break;
					case "Expecto Patronum":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Expecto Patronum",150,8,550));break;
					case "Ferula":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Ferula",200,4,200));break;
					case "Protego Horribilis":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Protego Horribilis",300,1,100));break;
					case "Rennervate":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Rennervate",100,3,200));break;
					case "Quen":((Wizard)tournment.getChampions().get(2)).getSpells().add(new HealingSpell("Quen",50,1,50));break;
				}
				switch((String)d1.getSelectedItem())
				{
					case "Sectumsempra":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Sectumsempra",150,5,300));break;
					case "Reducto":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Reducto",100,2,100));break;
					case "Piertotum Locomotor":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Piertotum Locomotor",400,1,200));break;
					case "Oppugno":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Oppugno",50,2,100));break;
					case "Incendio":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Incendio",150,4,250));break;
					case "Expulso":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Expulso",200,5,300));break;
					case "Bombarda":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Bombarda",300,3,350));break;
					case "Avada Kedavra":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Avada Kedavra",500,10,650));break;
					case "Crucio":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Crucio",400,6,500));break;
					case "Igni":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Igni",300,2,300));break;
					case "Kamehameha":((Wizard)tournment.getChampions().get(3)).getSpells().add(new DamagingSpell("Kamehameha",200,7,400));break;
				}
				switch((String)d3.getSelectedItem())
				{
					case "Accio":((Wizard)tournment.getChampions().get(3)).getSpells().add(new RelocatingSpell("Accio",100,1,1));break;
					case "Imperio":((Wizard)tournment.getChampions().get(3)).getSpells().add(new RelocatingSpell("Imperio",400,10,10));break;
					case "Wingardium Leviosa":((Wizard)tournment.getChampions().get(3)).getSpells().add(new RelocatingSpell("Wingardium Leviosa",300,5,5));break;
					case "Axii":((Wizard)tournment.getChampions().get(3)).getSpells().add(new RelocatingSpell("Axii",200,3,3));break;
				}
				switch((String)d2.getSelectedItem())
				{
					case "Cheering Charm":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Cheering Charm",50,2,100));break;
					case "Expecto Patronum":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Expecto Patronum",150,8,550));break;
					case "Ferula":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Ferula",200,4,200));break;
					case "Protego Horribilis":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Protego Horribilis",300,1,100));break;
					case "Rennervate":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Rennervate",100,3,200));break;
					case "Quen":((Wizard)tournment.getChampions().get(3)).getSpells().add(new HealingSpell("Quen",50,1,50));break;
				}
				tournment.beginTournament();
			} 
			catch (IOException e1) 
			{	
				e1.printStackTrace();
			}
			choose.setVisible(false);
			window.remove(choose);
			map();
			revalidate();
		}
	}


	public void keyPressed(KeyEvent e) {
		int res = e.getKeyCode();
		JFrame ErrorWindow = new JFrame();
		if (res == KeyEvent.VK_LEFT) {
			try {
                if(tournment.getLevel() instanceof FirstTask){
                	//fire
                	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
                	markcells = true;
                }
				tournment.getLevel().moveLeft();
				map();
				repaint();
				revalidate();
				markcells = true; //fire
				if(tournment.getLevel() instanceof FirstTask){
				map();
				repaint();
				revalidate();
				markcells = false;
				marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
				}
//				if(tournment.getLevel() instanceof SecondTask){
//					Cell[][] map1 = tournment.getLevel().getMap();
//					Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//					if(map1[p.x+1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson ){
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x-1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson ){
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y-1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson ){
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//				}

			} catch (OutOfBordersException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (InvalidTargetCellException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}
		if (res == KeyEvent.VK_RIGHT) {
			try {

				  if(tournment.getLevel() instanceof FirstTask){
					  //fire
	                	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
	                	markcells = true;
	                }
					tournment.getLevel().moveRight();
					map();
					repaint();
					revalidate();
					markcells = true;
					if(tournment.getLevel() instanceof FirstTask){
						//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
//					if(tournment.getLevel() instanceof SecondTask){
//						Cell[][] map1 = tournment.getLevel().getMap();
//						Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//						if(map1[p.x+1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x-1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y-1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//					}

			} catch (OutOfBordersException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (InvalidTargetCellException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}

		if (res == KeyEvent.VK_UP) {
			try {

				  if(tournment.getLevel() instanceof FirstTask){
					  //fire
	                	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
	                }
					tournment.getLevel().moveForward();
					map();
					repaint();
					revalidate();
					markcells = true; //fire
					if(tournment.getLevel() instanceof FirstTask){
					//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
//					if(tournment.getLevel() instanceof SecondTask){
//						Cell[][] map1 = tournment.getLevel().getMap();
//						Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//						if(map1[p.x+1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x-1][p.y] instanceof ObstacleCell && ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y-1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson ){
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//					}

			} catch (OutOfBordersException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (InvalidTargetCellException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}

		if (res == KeyEvent.VK_DOWN) {
			try {

				  if(tournment.getLevel() instanceof FirstTask){
					//fire
	                	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
	                	markcells = true;
	                }
					tournment.getLevel().moveBackward();
					map();
					repaint();
					revalidate();
					markcells = true;//fire
					if(tournment.getLevel() instanceof FirstTask){
						//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
//					if(tournment.getLevel() instanceof SecondTask){
//						Cell[][] map1 = tournment.getLevel().getMap();
//						Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//						if(map1[p.x+1][p.y] instanceof ObstacleCell ){
//							if(((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson )
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x-1][p.y] instanceof ObstacleCell ){
//							if( ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson )
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//							if( ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson)
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//						if(map1[p.x][p.y-1] instanceof ObstacleCell ){
//							if(((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson )
//							JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//						}
//					}

			} catch (OutOfBordersException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (InvalidTargetCellException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}
		if (res == KeyEvent.VK_D) 
		{
			  if(tournment.getLevel() instanceof FirstTask){
				//fire
              	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
              	markcells = true;
              	
              }
				
				
				
			try {
				Spell s=null;
				for(int i=0;i<((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().size();i++)
				{
					if(((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i) instanceof DamagingSpell)
					{
						s=((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i);
						break;
					}
				}
				
				Direction d=null;
				switch(JOptionPane.showInputDialog("please enter damaging direction  up  , down , right , left"))
				{
				
					
					case "up":d=Direction.FORWARD;System.out.println("yesss");break;
					case "down":d=Direction.BACKWARD;break;
					case "right":d=Direction.RIGHT;break;
					case "left":d=Direction.LEFT;break;
				}
				if(d==null){
					
					JOptionPane.showMessageDialog(ErrorWindow, "Please enter a valid direction");
					}
				try 
				{
					tournment.getLevel().castDamagingSpell((DamagingSpell) s,d);
				} 
				catch (InCooldownException e1) 
				{
					JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
					e1.printStackTrace();
				} catch (NotEnoughIPException e1) 
				{
					JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
					e1.printStackTrace();
				}
				map();
				repaint();
				revalidate();
				if(tournment.getLevel() instanceof FirstTask){
					//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
				
				

			} 
			catch (OutOfBordersException  | InvalidTargetCellException | IOException e1) //see if you catch correctly or no 
			{
				System.out.println("three");
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			} 	
		}
		if (res == KeyEvent.VK_H) 
		{
			if(tournment.getLevel() instanceof FirstTask){
				//fire
              	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
              	markcells = true;
              }
			try
			{
				
				Spell s=null;
				for(int i=0;i<((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().size();i++)
				{
					if(((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i) instanceof HealingSpell)
					{
						s=((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i);
						break;
					}
				}
				try 
				{
					tournment.getLevel().castHealingSpell((HealingSpell)s);
				} 
				catch (InCooldownException e1) 
				{
					JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
				} catch (NotEnoughIPException e1) 
				{
					JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
				}
				map();
				repaint();
				revalidate();
				if(tournment.getLevel() instanceof FirstTask){
					//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
//				if(tournment.getLevel() instanceof SecondTask){
//					Cell[][] map1 = tournment.getLevel().getMap();
//					Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//					if(map1[p.x+1][p.y] instanceof ObstacleCell ){
//						if(((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x-1][p.y] instanceof ObstacleCell ){
//						if( ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//						if( ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson)
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y-1] instanceof ObstacleCell ){
//						if(((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//				}
			}
			catch (IOException e1) //see if you catch correctly or no 
			{
				System.out.println("three");
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}
		if (res == KeyEvent.VK_R)
		{
			if(tournment.getLevel() instanceof FirstTask){
				//fire
              	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
              	markcells = true;
              }
			
			try
			{
				Spell s=null;
				for(int i=0;i<((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().size();i++)
				{
					if(((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i) instanceof RelocatingSpell)
					{
						s=((Wizard)tournment.getLevel().getCurrentChamp()).getSpells().get(i);
						break;
					}
				}
				Direction d=null;
				switch(JOptionPane.showInputDialog("please enter desired direction of object up  , down , right , left"))
				{
				
					
					case "up":d=Direction.FORWARD;break;
					case "down":d=Direction.BACKWARD;break;
					case "right":d=Direction.RIGHT;break;
					case "left":d=Direction.LEFT;break;
				}
				if(d==null){
				
					JOptionPane.showMessageDialog(ErrorWindow, "Please enter a valid direction");
					}
				Direction x=null;
				switch(JOptionPane.showInputDialog("please enter desired direction of relocation up  , down , right , left"))
				{
				
					
					case "up":x=Direction.FORWARD;System.out.println("yesss");break;
					case "down":x=Direction.BACKWARD;break;
					case "right":x=Direction.RIGHT;break;
					case "left":x=Direction.LEFT;break;
				}
				if(x==null){
					JFrame ErrorWindow1 = new JFrame();
					JOptionPane.showMessageDialog(ErrorWindow, "Please enter a valid direction");
					}
				int r=Integer.parseInt(JOptionPane.showInputDialog("please in the range"));
				try 
				{
					try {
						tournment.getLevel().castRelocatingSpell((RelocatingSpell) s,d,x,r);
					} catch (NotEnoughIPException e1) {
						JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
					} catch (InvalidTargetCellException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
					} catch (OutOfRangeException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
					}
				} 
				catch(OutOfBordersException|InCooldownException g )
				{
					JOptionPane.showMessageDialog(ErrorWindow, g.getMessage());
				}
				map();
				repaint();
				revalidate();
				if(tournment.getLevel() instanceof FirstTask){
					//fire
					map();
					repaint();
					revalidate();
					markcells = false;
					marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
					}
//				if(tournment.getLevel() instanceof SecondTask){
//					//merperson
//					Cell[][] map1 = tournment.getLevel().getMap();
//					Point p = ((Wizard) tournment.getLevel().getCurrentChamp()).getLocation();
//					if(map1[p.x+1][p.y] instanceof ObstacleCell ){
//						if(((ObstacleCell) map1[p.x+1][p.y]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x-1][p.y] instanceof ObstacleCell ){
//						if( ((ObstacleCell) map1[p.x-1][p.y]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y+1] instanceof ObstacleCell && ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson ){
//						if( ((ObstacleCell) map1[p.x][p.y+1]).getObstacle() instanceof Merperson)
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//					if(map1[p.x][p.y-1] instanceof ObstacleCell ){
//						if(((ObstacleCell) map1[p.x][p.y-1]).getObstacle() instanceof Merperson )
//						JOptionPane.showMessageDialog(ErrorWindow, "You were attacked by the Merperson");
//					}
//				}
			}
			
			catch(IOException e1)
			{
				JOptionPane.showMessageDialog(ErrorWindow, e1.getMessage());
			}
		}
		
		if (res == KeyEvent.VK_T) 
		{
			if(tournment.getLevel() instanceof FirstTask){
				//fire
              	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
              	markcells = true;
              }
			try
			{
				Champion current=tournment.getLevel().getCurrentChamp();
				if((Wizard) current instanceof GryffindorWizard)
				{
					tournment.getLevel().onGryffindorTrait();
				}
				if((Wizard) current instanceof SlytherinWizard)
				{
					Direction d=null;
					boolean done=false;
					while(!done)
					{
					switch(JOptionPane.showInputDialog("please type in up , down , right, left"))
					{
						case "up":d=Direction.FORWARD;System.out.println("yesss");break;
						case "down":d=Direction.BACKWARD;break;
						case "r":d=Direction.RIGHT;break;
						case "l":d=Direction.LEFT;break;
					}
					if(d==null)
					{
						JOptionPane.showMessageDialog(ErrorWindow, "Invalid input");
					}
					else
					{
						done=true;
					}
					}
					tournment.getLevel().onSlytherinTrait(d);
				}
				if((Wizard) current instanceof HufflepuffWizard)
				{
					tournment.getLevel().onHufflepuffTrait();
				}
				if((Wizard) current instanceof RavenclawWizard)
				{
					ArrayList p=new ArrayList();
					String s="";
					if(tournment.getLevel() instanceof FirstTask)
					{
						p=(ArrayList<Point>)tournment.getLevel().onRavenclawTrait();
						for(int i=0;i< p.size();i++)
						{
							s=s+"Marked Cell "+(i+1)+" "+p.get(i)+"\n";
						}
					}
					if(tournment.getLevel() instanceof SecondTask)
					{
						p=(ArrayList<Direction>)tournment.getLevel().onRavenclawTrait();
						for(int i=0;i< p.size();i++)
						{
							s=s+"Direction "+(i+1)+" "+p.get(i)+"\n";
						}
					}
					if(tournment.getLevel() instanceof ThirdTask)
					{
						p=(ArrayList<Direction>)tournment.getLevel().onRavenclawTrait();
						for(int i=0;i< p.size();i++)
						{
							s=s+"Direction "+(i+1)+" "+p.get(i)+"\n";
						}
					}
					JOptionPane.showMessageDialog(ErrorWindow, s);
				}
				map();
				revalidate();
				
			}
			catch(InCooldownException | OutOfBordersException | InvalidTargetCellException | IOException e2)
			{
				JOptionPane.showMessageDialog(ErrorWindow, e2.getMessage());
			}
		}
		if(res == KeyEvent.VK_U)
		{
			if(tournment.getLevel() instanceof FirstTask){
				//fire
              	marked = ((FirstTask) tournment.getLevel()).getMarkedCells();
              	markcells = true;
              }
				Champion a =tournment.getLevel().getCurrentChamp();
				ArrayList<Collectible> pot=((Wizard)a).getInventory();
				String[] list=new String[pot.size()];
				for(int i=0;i<pot.size();i++)
				{
					list[i]=pot.get(i).getName();
				}
				JComboBox jcb = new JComboBox(list);
				jcb.setEditable(true);
				JOptionPane.showMessageDialog( null, jcb, "select or type a value", JOptionPane.QUESTION_MESSAGE);
				String choosen=(String) jcb.getSelectedItem();
				for(int i=0;i<pot.size();i++)
				{
					if(choosen.equals(pot.get(i).getName()))
						{
							((Task) tournment.getLevel()).usePotion((Potion)pot.get(i));
						}
				}
				jcb.removeAll();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
      
	public static void main(String args[]) 
	{
		GameView game = new GameView();
	}
}
