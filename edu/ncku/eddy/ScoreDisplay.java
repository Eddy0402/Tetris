package edu.ncku.eddy;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.text.NumberFormat;

import javax.imageio.ImageIO;

public class ScoreDisplay extends Canvas {

	private String time = "00:00.000";
	private int pieceCount = 0;
	private int clearedLine = 0;
	private double pps;
	private double ppm;
	private double lpm;
	private String time40L = "";

	public ScoreDisplay(GameEngine gameEngine) {
		setSize(640, 480);

	}

	public void update(String time, int pieceCount, int clearedLine, double pps, double ppm, double lpm, String time40L) {
	    
		this.time = time;
		this.pieceCount = pieceCount;
		this.clearedLine = clearedLine;
		this.pps = pps;
		this.ppm = ppm;
		this.lpm = lpm;
		this.time40L = time40L;
		
		Graphics offgc;
		Image offscreen = null;
		Dimension d = getSize();
		
	    offscreen = createImage(d.width, d.height);
	    offgc = offscreen.getGraphics();

	    paint(offgc);
	    
		this.getGraphics().drawImage(offscreen, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {



		drawBackground(g.create());

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.translate(0, 0);
		g2d.setColor(Color.ORANGE);
		g2d.setFont(new Font("SansSerif", Font.BOLD, 18));

		g2d.drawString("Time:", 50, 100);
		g2d.drawString("Piece:", 50, 150);
		g2d.drawString("Line:", 50, 200);
		g2d.drawString("Pieces Per Second:", 50, 250);
		g2d.drawString("Pieces Per Minute:", 50, 300);
		g2d.drawString("Lines Per Minute:", 50, 350);

		NumberFormat nfFormat = NumberFormat.getInstance();
		nfFormat.setMinimumFractionDigits(3);
		nfFormat.setMaximumFractionDigits(3);

		g2d.setColor(Color.LIGHT_GRAY);

		g2d.drawString(time, 50, 125);
		g2d.drawString("" + pieceCount, 50, 175);
		g2d.drawString("" + clearedLine, 50, 225);
		g2d.drawString(nfFormat.format(pps), 50, 275);
		g2d.drawString(nfFormat.format(ppm), 50, 325);
		g2d.drawString(nfFormat.format(lpm), 50, 375);
		g2d.drawString("Cleared 40 Lines in :" + time40L, 50, 400);
		

	}

	private void drawBackground(Graphics g) {

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.translate(0, 0);
		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("res/back1.png"));
		} catch (Exception ex) {
		}

		g2d.drawImage(bgImage, null, null);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9020789431082872086L;

}
