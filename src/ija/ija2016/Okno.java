

package ija.ija2016.proj;

import ija.ija2016.*;
import ija.ija2016.homework1.cardpack.*;
import ija.ija2016.homework2.model.board.*;
import ija.ija2016.homework2.model.cards.*;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



	/**
	*
	* @author xwojac00
	*/


public class Okno  implements ActionListener {
	private int cnt;
	private GameGui[] games = new GameGui[4];
	private int focus;
	private JFrame okno;
	AbstractFactorySolitaire factory;
	GameGui fake; 
	
	/**
	*	Konstruktor hlavního okna programu.
	* @throws IOException
	*/	

	public Okno()throws IOException{
		focus = 0;
		factory = new FactoryKlondike();
		cnt = 0;
		okno = new JFrame("Solitare - Master");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);
		createMenu();
		newGame();
	}

	
	

	/**
	*	Získá stav rozehrané hry a uloží do souboru.
	*
	*/

	private void saveGame(){

		String saved = games[focus].getGame().toString();
		Path path = Paths.get("examples/saved.txt").toAbsolutePath();
		try{
	        Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
	        Files.write(path, saved.getBytes(), StandardOpenOption.APPEND);
	    }
	        catch (IOException ex) {
        }
	}
	/**
	*	Načte uloženou hru ze souboru.
	*
	*/

	private void loadGame(){
		try{
		String saved;
		FileReader fileReader = new FileReader(new File("examples/saved.txt"));
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        if((line = br.readLine()) != null){
        	newGame();
        	games[cnt-1].getGame().loadGame(line);
        }}
        catch (IOException e){} 
		try{games[cnt-1].drawGui();}catch(IOException e){}   
	}

	/**
	*	Zajišťuje rozdělení okna na jednotlivé dlaždice podle počtu rozehraných her.
	*	
	*/


	public void resetFrame(){
		try{
			okno.remove(fake);
		}
		catch(Exception e){}
		for(int i = 0; i<4; i++){
				try{okno.getContentPane().remove(games[i]);}
				catch(Exception e){}
			}
		if(cnt == 1){
			okno.setMinimumSize(new Dimension(700, 600));
			okno.setSize(new Dimension(700, 600));
			okno.getContentPane().setLayout(new GridLayout(1,1));
			games[0].setName("0");
			okno.getContentPane().add(games[0]);
		}

		else{

			okno.setMinimumSize(new Dimension(1300, 1100));
			okno.getContentPane().setLayout(new GridLayout(2,2));
			for(int i = 0; i<cnt; i++){
				games[i].setName(Integer.toString(i));
				System.out.println(i);
				okno.getContentPane().add(games[i]);
			}
			if(cnt == 2){
				try{
					fake = new GameGui(-1);
					fake.setVisible(false);
					okno.getContentPane().add(fake);}
				catch(IOException e){}
				
			}
		}
	}

	/**
	*	Spustí novou hru, její GUI a přidá ji do pole games.
	*
	*/	

	public boolean newGame()throws IOException{
		if(cnt == 4)
			return false;
		

		games[cnt] = new GameGui(cnt);



		games[cnt].addMouseListener(new MouseAdapter() { 


			@Override
			public void mouseEntered(MouseEvent e) {
            	Component com = (Component)e.getSource();
          		if (com instanceof GameGui){
          			focus = Integer.parseInt(com.getName());
          		}
       			
    		}
          @Override
          public void mousePressed(MouseEvent e) { 
          	System.out.println(focus);
            Component com = games[focus].findComponentAt(e.getX(), e.getY());
          	if (!(com instanceof JLabel)) return;

            String str[] = com.getName().split(":");  //ze jmena vezme souradnice
            int x = Integer.parseInt(str[0]);
            int y = Integer.parseInt(str[1]);
            Card.Color col = null;
            if(str[3].equals("C"))
	            col = Card.Color.CLUBS;
	        if(str[3].equals("S"))
	            col = Card.Color.SPADES;
	        if(str[3].equals("D"))
	            col = Card.Color.DIAMONDS;
	        if(str[3].equals("H"))
	            col = Card.Color.HEARTS;
	        Card c = null;
	        if(col != null)
            	c = new Karta(col , Integer.parseInt(str[2]));
            games[focus].getGame().select(x,y,c);	// x,y jsou souřadnice, c je karta
          }


          @Override
          public void mouseReleased(MouseEvent e) { 
            Component com = games[focus].findComponentAt(e.getX(), e.getY());
            if (!(com instanceof JLabel)){games[focus].getGame().deselect(); return;}

            String str[] = com.getName().split(":");  //ze jmena vezme souradnice
            int x = Integer.parseInt(str[0]);
            int y = Integer.parseInt(str[1]);
            Card.Color col = null;
            Card c = null;
            if(str[3].equals("C"))
	            col = Card.Color.CLUBS;
	        if(str[3].equals("S"))
	            col = Card.Color.SPADES;
	        if(str[3].equals("D"))
	            col = Card.Color.DIAMONDS;
	        if(str[3].equals("H"))
	            col = Card.Color.HEARTS;
            if(col != null)
            	c = new Karta(col , Integer.parseInt(str[2]));




            games[focus].getGame().transfer(x,y,c);

            try{ games[focus].drawGui();
            }catch(IOException ex){}
            if(games[focus].getGame().checkVictory()){
            	JOptionPane.showMessageDialog(null, "You won!");
            	endGame(focus);
			}
            games[focus].getGame().saveUndo();
          }
      	});


		cnt++;
		resetFrame();
		return true;
	}

	/**
	*	Ukončí rozehranou hru.
	*
	*/	

	public void endGame(int nr){
		if(nr >= cnt)
			return;
		okno.remove(games[nr]);
		for(int i = nr; i < cnt - 1; i++){
			System.out.println("endgame, " + (i+1) + " to " + i);
			//if(i != 3)
				games[i] = games[i+1];
		}
		games[cnt-1] = null;
		cnt--;
		resetFrame();
	}


	/**
	*	Vytvoří menu.
	* @throws IOException
	*/

	private void createMenu()throws IOException{
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;


		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuBar.add(menu);
		menuItem = new JMenuItem("New Game", KeyEvent.VK_T);
		menuItem.setActionCommand("new");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Undo 1", KeyEvent.VK_T);
		menuItem.setActionCommand("undo0");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Undo 2", KeyEvent.VK_T);
		menuItem.setActionCommand("undo1");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Undo 3", KeyEvent.VK_T);
		menuItem.setActionCommand("undo2");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Undo 4", KeyEvent.VK_T);
		menuItem.setActionCommand("undo3");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Save", KeyEvent.VK_T);
		menuItem.setActionCommand("save");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Load", KeyEvent.VK_T);
		menuItem.setActionCommand("load");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit Game 1", KeyEvent.VK_T);
		menuItem.setActionCommand("exit1");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit Game 2", KeyEvent.VK_T);
		menuItem.setActionCommand("exit2");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit Game 3", KeyEvent.VK_T);
		menuItem.setActionCommand("exit3");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit Game 4", KeyEvent.VK_T);
		menuItem.setActionCommand("exit4");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit", KeyEvent.VK_T);
		menuItem.setActionCommand("exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		okno.setJMenuBar(menuBar);
	}

	/**
	*	Určí akce tlačítek v menu.
	*	@param e
	*/

	@Override
	public void actionPerformed(ActionEvent e){
	    if ("new".equals(e.getActionCommand())) {
	    	try{newGame();}catch (IOException x){}
	    }
	    if ("exit".equals(e.getActionCommand()))
	    	System.exit(0);
	    if ("exit1".equals(e.getActionCommand()))
	    	endGame(0);
	    if ("exit2".equals(e.getActionCommand()))
	    	endGame(1);
	    if ("exit3".equals(e.getActionCommand()))
	    	endGame(2);
	    if ("exit4".equals(e.getActionCommand()))
	    	endGame(3);
	    if ("save".equals(e.getActionCommand()))
	    	saveGame();
	    if ("load".equals(e.getActionCommand()))
	    	loadGame();
	    if ("undo0".equals(e.getActionCommand())){
	    	games[0].getGame().undo();
	    	try{games[0].drawGui();}catch(IOException f){}
		}
		if ("undo1".equals(e.getActionCommand())){
	    	games[1].getGame().undo();
	    	try{games[1].drawGui();}catch(IOException f){}
		}
		if ("undo2".equals(e.getActionCommand())){
	    	games[2].getGame().undo();
	    	try{games[2].drawGui();}catch(IOException f){}
		}
		if ("undo3".equals(e.getActionCommand())){
	    	games[3].getGame().undo();
	    	try{games[3].drawGui();}catch(IOException f){}
		}
	} 


}