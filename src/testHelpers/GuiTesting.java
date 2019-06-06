package testHelpers;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

public class GuiTesting {
	Robot bot = getRobot();

	public Robot getRobot(){
		Robot bot = null;
		try {
			bot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return bot;
	}
	
	public void Click(Object object){
		Point point = GetClickablePoint(object); 
		if (point.x != -1 && point.y != -1){
			bot.mouseMove(point.x, point.y);
			bot.mousePress(InputEvent.BUTTON1_MASK);
			bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else {
			System.out.println("Could not press " + object.toString() + " since no clickable point was found.");
		}
		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
		//try{Thread.sleep(100);}catch(InterruptedException e){}
	}
	
	private static Point GetClickablePoint(Object object){
		int x = -1;
		int y = -1;
		switch (object.getClass().toString()){
			case "class javax.swing.JButton":
				JButton theButtonObject = (JButton) object;
				x = theButtonObject.getLocationOnScreen().x + (theButtonObject.getWidth()/2);
				y = theButtonObject.getLocationOnScreen().y + (theButtonObject.getWidth()/2);
				break;
			case "class javax.swing.JTextField":
				JTextField theTextField = (JTextField) object;
				x = theTextField.getLocationOnScreen().x + theTextField.getWidth()/2;
				y = theTextField.getLocationOnScreen().y + theTextField.getHeight()/2;
				break;
			default:
				System.out.println(object.getClass().toString());
				break;
		}
		return new Point(x, y);
	}
	
	public void SetText(Object object, String string){
		JTextField textField = (JTextField)object;
		textField.setText(string);
		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void Write(String string) {
		for (char c : string.toCharArray()){
			bot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));
			//try{Thread.sleep(50);}catch(InterruptedException e){}
			bot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c));
		}
		try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
}
