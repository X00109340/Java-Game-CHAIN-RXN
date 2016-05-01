package game;

import java.awt.*;

/**
 * A rectangular container box, containing the bouncing ball.
 */
public class ContainerBox {

	// Box's bounds
	int minX;
	int maxX;
	int minY;
	int maxY;
	
	// Box's filled color (background)
	private Color colorFilled; 
	
	// Box's border color
	private Color colorBorder; 
	
	private static final Color DEFAULT_COLOR_FILLED = Color.BLACK;
	private static final Color DEFAULT_COLOR_BORDER = Color.YELLOW;

	// Constructor
	public ContainerBox(int x, int y, int width, int height, Color colorFilled, Color colorBorder) {
		minX = x;
		minY = y;
		maxX = x + width - 1;
		maxY = y + height - 1;
		this.colorFilled = colorFilled;
		this.colorBorder = colorBorder;
	}

	// Constructor with the default color
	public ContainerBox(int x, int y, int width, int height) {
		this(x, y, width, height, DEFAULT_COLOR_FILLED, DEFAULT_COLOR_BORDER);
	}

	// Set or reset the boundaries of the box.
	public void set(int x, int y, int width, int height) {
		minX = x;
		minY = y;
		maxX = (x + width - 1);
		maxY = (y + height - 1);
	}

	// Draw itself using the given graphic context.
	public void draw(Graphics g) {
		g.setColor(colorFilled);
		g.fillRect(minX, minY, maxX - minX - 1, maxY - minY - 1);
		g.setColor(colorBorder);
		g.drawRect(minX, minY, maxX - minX - 1, maxY - minY - 1);
	}

	// Getter and setter methods below for each of the variables defined above

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public Color getColorFilled() {
		return colorFilled;
	}

	public void setColorFilled(Color colorFilled) {
		this.colorFilled = colorFilled;
	}

	public Color getColorBorder() {
		return colorBorder;
	}

	public void setColorBorder(Color colorBorder) {
		this.colorBorder = colorBorder;
	}

	public static Color getDefaultColorFilled() {
		return DEFAULT_COLOR_FILLED;
	}

	public static Color getDefaultColorBorder() {
		return DEFAULT_COLOR_BORDER;
	}

}
