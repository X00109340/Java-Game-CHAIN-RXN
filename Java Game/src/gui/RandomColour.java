package gui;

import java.awt.Color;
import java.util.Random;

public class RandomColour{
	public Color randColor;
	public RandomColour() {
		Color[] colors = new Color[5]; // colour array of size 5.
		
		colors[0] = new Color(255,0,0); // red
		colors[1] = new Color(0,255,0);// green
		colors[2] = new Color(0,0,255); // blue
		colors[3] = new Color(255,255,0); // yellow
		colors[4] = new Color(160,32,240); // purple
		
		// gets a random colour
		randColor = colors[new Random().nextInt(colors.length)];
	}
	
	// returns random colour red component
	public int getR(){
		return randColor.getRed();
	}
	// returns random colour green component
	public int getG(){
		return randColor.getGreen();
	}
	// returns random colour blue component
	public int getB(){
		return randColor.getBlue();
	}
	// returns the random colour instance variable
	public Color getColor(){
		return randColor;
	}
}
