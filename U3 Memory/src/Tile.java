import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class Tile extends JButton {
	
	static final int SIZE = 50;
	
	private String symbol = "";
	private boolean revealed = false;
	private int row = 0;
	private int col = 0;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		Dimension size = new Dimension(SIZE,SIZE);
		setPreferredSize(size);
		reveal(false);
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
		if(revealed) {
			setText(symbol);
		}
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	public boolean hasSymbol() {
		return symbol != "";
	}

	public boolean isRevealed() {
		return this.revealed;
	}
	
	public void reveal(boolean reveal) {
		this.revealed = reveal;
		if(revealed) {
				setBackground(Color.white);
				setText(this.symbol);
		}
		else {
			setBackground(new Color(102, 204, 255));;
			setText("");
		}
		setFocusPainted(reveal);
	}
	
	public boolean sameSymbol(Tile tile) {
		return symbol.equals(tile.getSymbol());
	}
	
	public void reset() {
		symbol = "";
		reveal(false);
	}
	
}
