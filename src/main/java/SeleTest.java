import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleTest {
    public static void main(String[] args) {
        SeleTest seleTest = new SeleTest();
//        System.setProperty("webdriver.gecko.driver", "E:\\soft\\geckodriver/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", "/Users/xiao230/Desktop/soft/Firefox-driver/geckodriver");

        WebDriver driver = new FirefoxDriver();
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            seleTest.tapc(driver);

            driver.close();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
            driver.quit();
        }
    }

    private void tapc(WebDriver driver) throws InterruptedException, IOException, AWTException {

        driver.get("https://www.tapd.cn/51894879/bugtrace/bugreports/my_view");
        setCookie(driver);
        driver.navigate().refresh();
        Thread.sleep(2000);
        createTapd(driver);

    }

    private void hoverAndClick(WebDriver driver, WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).clickAndHold().click().perform();
    }

    private void createTapd(WebDriver driver) throws InterruptedException, AWTException, IOException {
        // create new button
        driver.findElement(By.id("btn_add_bug")).click();

        String mainWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            WebDriver window = driver.switchTo().window(windowHandle);
            Thread.sleep(200);
            if (window.getCurrentUrl().contains("add")) {
//                // set title
//                driver.findElement(By.id("BugTitle")).clear();
//                driver.findElement(By.id("BugTitle")).sendKeys("aaa");
//                // set 优先级
//                WebElement bugPriority = driver.findElement(By.xpath("//*[@id=\"BugPriorityDiv\"]/div"));
//                bugPriority.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='中']")));
//                // set 严重程度 *
//                WebElement bugSeverity = driver.findElement(By.xpath("//*[@id=\"BugSeverityDiv\"]/div/div"));
//                bugSeverity.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='一般']")));
//                // set 混合云环境 *
//                WebElement env = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldThreeDiv\"]/div/div"));
//                env.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='dev']")));
//                // set 处理人
//                WebElement bugCurrentOwner = driver.findElement(By.id("BugCurrentOwnerValue"));
//                bugCurrentOwner.click();
//                bugCurrentOwner.sendKeys("肖");
//                Thread.sleep(2000);
//                // we need click some element to full the input
//                driver.findElement(By.id("BugTitle")).click();
//                // set 所属部门 *
//                WebElement bugCustomFieldOne = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldOneDiv\"]/div/div"));
//                bugCustomFieldOne.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='行业应用-研发五部']")));
//                // set 业务范围 *
//                WebElement bugCustomFieldFive = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldFiveDiv\"]/div/div"));
//                bugCustomFieldFive.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='共享交换']")));
//                // set 项目标识 *
//                driver.findElement(By.id("BugCustomFieldTwo")).sendKeys("aaa");
//                // set 问题分类 *
//                WebElement BugCustomField6 = driver.findElement(By.xpath("//*[@id=\"BugCustomField6Div\"]/div/div"));
//                BugCustomField6.click();
//                hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='功能缺陷']")));

                driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"editor-BugDescription\"]/div[7]/iframe")));
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                // 实施人员
                jsExecutor.executeScript("arguments[0].innerHTML = '赵泽'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[1]/div")));
                // 实施时间
                Date t = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jsExecutor.executeScript("arguments[0].innerHTML = '"+ df.format(t) +"'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[2]/div")));
                // 实施事项
                jsExecutor.executeScript("arguments[0].innerHTML = '大盘巡检'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[3]/div")));
                // 问题截图
                WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[3]"));
                jsExecutor.executeScript("arguments[0].innerHTML = 'url'", element);
                jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
                hoverAndClick(driver, element);

                //指定图片路径
                Image image = ImageIO.read(new File("/Users/xiao230/Desktop/123.png"));
                //把图片路径复制到剪切板
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new ClipImage(image), null);
                //新建一个Robot类的对象
                Robot robot = new Robot();
                Thread.sleep(1000);
                //按下Ctrl+V
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                //释放Ctrl+V
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(2000);


                driver.switchTo().defaultContent();
                driver.findElement(By.xpath("//*[@id=\"editor-BugDescription\"]/div[6]/span[16]")).click();

                System.out.println(111);


                Thread.sleep(20000);

            }
        }
    }

    class ClipImage implements Transferable {
        private Image image;

        public ClipImage(Image image) {
            this.image = image;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        @NotNull
        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }

    private void setCookie(WebDriver driver) throws IOException {
        driver.manage().deleteAllCookies();
        URL a = SeleTest.class.getClassLoader().getResource("");
        File file1 = new File(a.getPath() + "\\cookie.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file1));
        StringBuffer buffer = new StringBuffer();
        String s = "";
        while ((s = reader.readLine()) != null) {
            buffer.append(s);
        }
        List<Cookie> cookieList = JSON.parseArray(buffer.toString(), Cookie.class);
        for (Cookie cookie : cookieList) {
            driver.manage().addCookie(cookie);
        }
        reader.close();
    }

    private void writeCookie(WebDriver driver) throws IOException {
        System.out.println("start writeCookie");
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies.size());
        for (Cookie cookie : cookies) {
            System.out.println(cookie);
        }
        String s = JSON.toJSONString(cookies);
        saveData(s, "cookie.txt");
    }

    private void saveData(String data, String file) throws IOException {
        URL a = SeleTest.class.getClassLoader().getResource("");
        File file1 = new File(a.getPath() + "\\" + file);
        file1.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
        writer.write(data);
        writer.flush();
        writer.close();
    }

    private void getProject(WebDriver driver) throws IOException, InterruptedException {
        // dev test prod
        String env = "test";
        Map<String, Relation> projectInfo = getProjectInfo();

        driver.get("https://dolphin-"+ env +".kedacom.com/dashboard/#/service/dashboard");

        // type is error or slow
        List<Tapd> tapdList = getInfo(driver, "error", projectInfo, env);
        String s = JSON.toJSONString(tapdList);
        saveData(s, "tapdList.txt");
    }

    private Map<String, Relation> getProjectInfo() throws IOException {
        Map<String, Relation> relationMap = new HashMap<>();
        URL a = SeleTest.class.getClassLoader().getResource("info.txt");
        BufferedReader reader = new BufferedReader(new FileReader(new File(a.getFile())));
        String s = null;
        while ((s = reader.readLine()) != null) {
            Relation relation = new Relation();
            String[] s1 = s.split("\t");
            for (String s2 : s1) {
                relation.setValue(s2);
            }
            relationMap.put(relation.getMark(), relation);
        }
        reader.close();
        return relationMap;
    }

    private List<Tapd> getInfo(WebDriver driver, String type, Map<String, Relation> project, String env) throws IOException, InterruptedException {
        List<Tapd> tapds = new ArrayList<>();
        List<String> errorMarkAndNamespaceList = new ArrayList<>();
        // find apm
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[2]/section/div/ul/li[3]")).click();
        // find error project
        WebElement errorProject;
        if (type.equals("error")) {
            errorProject = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[6]/div/div[3]/div/div/div/div[2]/div[1]/div[2]/div/div/div[3]/table/tbody"));
        } else {
            // slow response page
            errorProject = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[6]/div/div[2]/div/div/div/div[2]/div[1]/div[2]/div/div/div[3]/table/tbody"));
        }
        List<WebElement> elements = errorProject.findElements(By.xpath("./*"));

        String mainWindow = driver.getWindowHandle();
        // click the error project to apm page
        for (int i = 0; i < elements.size(); i++) {
            // enter detail page
            WebElement detailPage = elements.get(i).findElement(By.cssSelector("span.can-click"));

            if (i == 0) {
                Thread.sleep(3000);
                //使用显示等待，等待掩盖的div消失
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.elementToBeClickable(detailPage));
            }

            detailPage.click();

            Set<String> windowHandles = driver.getWindowHandles();
            // print wechar info
            for (String windowHandle : windowHandles) {
                WebDriver window = driver.switchTo().window(windowHandle);
                String currentUrl = window.getCurrentUrl();
                if (currentUrl.contains("resourceType")){
                    // get namespace and project mark
                    WebElement projectInfo = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[2]/section/div/div[2]/div/div[1]/div[2]/div/span/div[2]"));
                    String text = projectInfo.getText();
                    String[] split = text.split("\n")[0].split("/");
                    String namespace = split[0];
                    String mark = split[1];
                    Relation relation = project.get(mark);
                    if (relation != null) {
                        errorMarkAndNamespaceList.add(namespace + ";" + mark + ";" + currentUrl);
                        System.out.println("空间：" + namespace);
                        System.out.println("服务：" + mark);
                        System.out.println("责任人：@" + relation.getOwnerName());
                        System.out.println("问题简述:  有错误");
                        System.out.println("tapd地址：");
                        System.out.println();
                    }
                    // capture the pic
                    WebElement errorPic;
                    if (type.equals("error")) {
                        errorPic = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[3]/div/div[2]/div[2]/div/div[2]/div/div/div"));
                    } else {
                        // slow page
                        errorPic = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[3]/div/div[2]/div[2]/div/div[4]/div/div/div"));
                    }
                    File errorPicFile = new File("C:\\Users\\Administrator\\Desktop\\tmp\\" + type + "\\" + mark + ".png");
                    errorPicFile.createNewFile();
                    captureElement(errorPicFile, errorPic, driver);
                    driver.close();
                }
            }
            driver.switchTo().window(mainWindow);
        }

        System.out.println("=================================================================");

        // print tapd info
        for (String s : errorMarkAndNamespaceList) {
            Tapd tapd = new Tapd();
            String[] split = s.split(";");
            Relation relation = project.get(split[1]);
            System.out.println("【"+ env + "环境】"+ split[0] +" + "+ split[1] +" + 有错误");
            System.out.println("处理人 " + relation.getOwnerName());
            System.out.println("项目标识 " + split[0]);
            System.out.println("所属部门 " + relation.getDept());
            System.out.println("业务范围 " + relation.getBelong());
            System.out.println("问题链接 " + split[2]);
            System.out.println();
            tapd.setDealMan(relation.getOwnerName());
            tapd.setDept(relation.getDept());
            tapd.setLink(split[2]);
            tapd.setMark(split[0]);
            tapd.setRange(relation.getBelong());
            tapd.setTitle("【"+ env + "环境】"+ split[0] +" + "+ split[1] +" + 有错误");
            tapd.setNamespace(split[0]);
            tapds.add(tapd);
        }
        return tapds;
    }

    private File captureElement(File screenshot, WebElement element, WebDriver driver){
        try {
            // wait to get all info
            Thread.sleep(6000);
            File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcfile, screenshot);
            BufferedImage img = ImageIO.read(screenshot);
            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            //获取指定元素的坐标
            Point point = element.getLocation();
            //从元素左上角坐标开始，按照元素的高宽对img进行裁剪为符合需要的图片
            BufferedImage dest = img.getSubimage(point.getX(), point.getY() - 140, width, height);
            ImageIO.write(dest, "png", screenshot);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return screenshot;
    }


}
