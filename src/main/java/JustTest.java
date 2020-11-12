import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;

public class JustTest {
    public static void main(String[] args) throws AWTException, InterruptedException {
//        Robot robot = new Robot();
//        robot.mouseMove(200, 200);
//        robot.delay(2000);
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.delay(2000);
//        robot.keyPress('I');
//        robot.keyPress(' ');
//        robot.keyPress('L');
//        robot.keyPress('O');
//        robot.keyPress('V');
//        robot.keyPress('E');

        Thread.sleep(2000);
        Clipboard systemSelection = Toolkit.getDefaultToolkit().getSystemSelection();
        System.out.println(systemSelection);
    }
}
