package utils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotHelper {

  public void sendKeys(CharSequence text) {
    try {
      char[] chars = text.toString().toCharArray();
      Robot robot = new Robot();
      for (char _char : chars) {
        robot.keyPress(_char);
        robot.keyRelease(_char);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  public void ctrl_A() {
    try {
      Robot robot = new Robot();
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_A);
      robot.keyRelease(KeyEvent.VK_A);
      robot.keyRelease(KeyEvent.VK_CONTROL);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  public void click(int deltaX,int deltaY) {
    try {
      Robot robot = new Robot();
      robot.mouseMove(deltaX,deltaY);
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
