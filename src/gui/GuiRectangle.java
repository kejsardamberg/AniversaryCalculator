package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GuiRectangle extends JPanel{

	int width = 0;
	int height = 0;
	int numberOfPassed = 0;
	int numberOfFailed = 0;
	int numberOfIgnored = 0;

	public GuiRectangle(int width, int height, int numberOfPassed, int numberOfFailed, int numberOfIgnored){
		this.width = width;
		this.height = height;
		this.numberOfFailed = numberOfFailed;
		this.numberOfPassed = numberOfPassed;
		this.numberOfIgnored = numberOfIgnored;
		this.setPreferredSize(new Dimension(width, height));
	}

	private static final long serialVersionUID = -5790513721221508497L;

	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int numberOfTests = numberOfPassed + numberOfFailed + numberOfIgnored;
		int cellWidth = width/numberOfTests;
		int gap = width - (cellWidth * numberOfTests);
        g.setColor(Color.GREEN);
        g.fillRect(gap/2, 0, cellWidth*numberOfPassed, height);
        g.setColor(Color.YELLOW);
        g.fillRect(cellWidth*numberOfPassed, 0, cellWidth*numberOfIgnored, height);
        g.setColor(Color.RED);
        g.fillRect(cellWidth*numberOfPassed + cellWidth*numberOfIgnored, 0, cellWidth*numberOfFailed, height);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(gap/2, 0, width - gap, height);

        for (int i = cellWidth; i <= width; i += cellWidth) {
            g.drawLine(i, 1, i, height);
        }
	}
}
