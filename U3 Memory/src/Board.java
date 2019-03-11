import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Board extends JFrame {

	private static final int GRIDSIZE = 4;
	private JLabel playerLabel;
	private Tile[][] tiles = new Tile[GRIDSIZE][GRIDSIZE];
	private int totalRevealed = 0;
	private boolean isFirstPlayersTurn;
	private Tile selectedTile1;
	private Tile selectedTile2;
	private int scorePlayer1;
	private int scorePlayer2;
	

	
	public Board(){	
		
		setPreferredSize(new Dimension(800,600));

		initGUI();
			
		setTitle("Memory");
		setResizable(false);
		pack(); //pack means pack tightly -- override the sizes 
		setLocationRelativeTo(null); //centers on screen, do this after packing but before visible
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		newGame();

	}

	public void initGUI(){
		
		playerLabel = new JLabel("Player 1 - score 0:0");		
		Font playerFont = new Font (Font.SANS_SERIF , Font.BOLD + Font.ITALIC, 24);
		playerLabel.setFont(playerFont);
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBackground(new Color(204, 0, 102));;
		playerLabel.setOpaque(true);
		
		add(playerLabel,BorderLayout.PAGE_START);
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE,GRIDSIZE));
		add(centerPanel,BorderLayout.CENTER);
		for(int r=0; r<GRIDSIZE; r++) {
			for(int c=0; c<GRIDSIZE; c++) {
				tiles[r][c] = new Tile(r,c);	
				tiles[r][c].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Tile button = (Tile)e.getSource();
						int row = button.getRow();
						int col = button.getCol();
						clickTile(row,col);
						
					}
				});
				centerPanel.add(tiles[r][c]);
			}
		}	
	}
	
	private void updatePlayerLabel() {
		if(isFirstPlayersTurn) {
			playerLabel.setText("Player 1 - " + scorePlayer1 + ":" +  scorePlayer2);
		}
		else {
			playerLabel.setText("Player 2 - " + scorePlayer1 + ":" +  scorePlayer2);
		}
	}
	
	private void clickTile(int row, int col) {
		Tile tile = tiles[row][col];
		if(tile.isRevealed()) {
			return;
		}
		tile.reveal(true);
		if(selectedTile1 == null) {
			selectedTile1 = tile;
			return;
		}
		else {
			selectedTile2 = tile;
			if(selectedTile1.sameSymbol(selectedTile2)) {
				totalRevealed = totalRevealed + 2;
				if(isFirstPlayersTurn) {
					scorePlayer1 = scorePlayer1 + 1;
				}
				else {
					scorePlayer2 = scorePlayer2 + 1;
				}
				if (isGameOver()) {
					updatePlayerLabel();
					if (scorePlayer1 > scorePlayer2) {
						JOptionPane.showMessageDialog(null, "Player 1 won");
					}
					else {
						JOptionPane.showMessageDialog(null, "Player 2 won");
					}
					newGame();
				}
				else {
					JOptionPane.showMessageDialog(null, "Good Job, Pick another one");
					selectedTile1.setSymbol("");
					selectedTile2.setSymbol("");
					selectedTile1 = null;
					selectedTile2 = null;
				}				
			}
			else {
				JOptionPane.showMessageDialog(null, "Sorry, that's wrong");
				isFirstPlayersTurn = !isFirstPlayersTurn;
				showPlayerMessage();
				selectedTile1.reveal(false);
				selectedTile2.reveal(false);
				selectedTile1 = null;
				selectedTile2 = null;
			}			
		}	
		updatePlayerLabel();
	}
	
	private boolean isGameOver() {
		return totalRevealed == GRIDSIZE*GRIDSIZE;
	}
	
	private void newGame() {
		for(int r=0;r<GRIDSIZE;r++) {
			for (int c=0;c<GRIDSIZE;c++) {
				tiles[r][c].reset();
			}
		}
		setSymbols();
		totalRevealed = 0;
		scorePlayer1 = 0;
		scorePlayer2 = 0;
		selectedTile1 = null;
		selectedTile2 = null;
		isFirstPlayersTurn = true;
		updatePlayerLabel();
		showPlayerMessage();	
	}
	
	private void showPlayerMessage() {
		if(isFirstPlayersTurn) {
			JOptionPane.showMessageDialog(null, "Player 1 Turn");
		}
		else {
			JOptionPane.showMessageDialog(null, "Player 2 Turn");
		}
	}

	private void setSymbols() {
		
		Random rand = new Random();
		int numSymbols = GRIDSIZE*GRIDSIZE / 2;
		int pickRow;
		int pickCol;

		for(int i=0; i<numSymbols; i++) {
			for (int j=0; j<2; j++) {
				do {
					pickRow = rand.nextInt(GRIDSIZE);
					pickCol = rand.nextInt(GRIDSIZE);				
				}
				while(tiles[pickRow][pickCol].hasSymbol());
				tiles[pickRow][pickCol].setSymbol(Integer.toString(i));
			}			
		}		
	}
	
	private void showSymbols() {
		for(int r=0;r<GRIDSIZE;r++) {
			for (int c=0;c<GRIDSIZE;c++) {
				tiles[r][c].reveal(true);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch ( Exception e) {}
        
        EventQueue.invokeLater(new Runnable (){
            @Override
            public void run() {
                new Board();
            }   
        });
	}

}
