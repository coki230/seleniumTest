package tapd;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//        Thread.sleep(2000);
//        Clipboard systemSelection = Toolkit.getDefaultToolkit().getSystemSelection();
//        System.out.println(systemSelection);
//        DeleteFileUtil.deleteDirectory("C:\\Users\\Administrator\\Desktop\\tmp\\test");

        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("234");
        list.add("345");
        list.add("567");
        list.add("677");
        Optional<String> first = list.stream().findFirst();
        System.out.println(first);
    }
}
