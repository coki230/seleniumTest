import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
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
    private static String picPath;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        SeleTest seleTest = new SeleTest();
        String os = System.getenv().get("OS");
        if (os.toUpperCase().contains("WINDOWS")) {
            System.setProperty("webdriver.gecko.driver", "E:\\soft\\geckodriver/geckodriver.exe");
            SeleTest.picPath = "C:\\Users\\Administrator\\Desktop\\tmp\\";
        } else {
            System.setProperty("webdriver.gecko.driver", "/Users/xiao230/Desktop/soft/Firefox-driver/geckodriver");
            SeleTest.picPath = "/Users/xiao230/Desktop/tmp/";
        }

        // 新建一个firefox浏览器实例,并设置headless,不显示浏览器的情况下运行程序
//        FirefoxBinary firefoxBinary = new FirefoxBinary();
//        firefoxBinary.addCommandLineOptions("--headless");
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setBinary(firefoxBinary);
//        WebDriver driver = new FirefoxDriver(firefoxOptions);


        WebDriver driver = new FirefoxDriver();
        // dev test prod
//        String[] envs = {"test","dev","prod"};
//        for (String env : envs) {
//            seleTest.getErrorAndCreateTapd(driver, env);
//        }

        seleTest.getErrorAndCreateTapd(driver, "prod");

//        seleTest.getCookie(driver);
    }

    private void getErrorAndCreateTapd(WebDriver driver, String env) {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            // type is error or slow
            String type = "error";
            SeleTest.picPath = SeleTest.picPath + env + "\\" + type + "\\";
            List<Tapd> tapds = getProject(driver, env, type);

            tapc(driver, tapds, env);

            System.out.println("=================================================================");
            System.out.println("【"+ env +"环境】");
            tapds.forEach(tapd -> {
                if (tapd.getTapdUrl() != null && !tapd.getTapdUrl().contains("add")) {
                    System.out.println("空间：" + tapd.getNamespace());
                    System.out.println("服务：" + tapd.getMark());
                    System.out.println("责任人：@" + tapd.getDealMan());
                    System.out.println("问题简述:  有错误");
                    System.out.println("tapd地址：" + tapd.getTapdUrl());
                    System.out.println();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            System.exit(0);
        }
    }

    private void getCookie(WebDriver driver) throws InterruptedException, IOException {
        driver.get("https://www.tapd.cn/51894879/bugtrace/bugreports/my_view");
        driver.manage().deleteAllCookies();
        Thread.sleep(20000);
        writeCookie(driver);
    }

    private void tapc(WebDriver driver, List<Tapd> tapds, String env) throws InterruptedException, IOException, AWTException {

        driver.get("https://www.tapd.cn/51894879/bugtrace/bugreports/my_view");

        setCookie(driver);
        driver.navigate().refresh();
        Thread.sleep(2000);
        createTapd(driver,tapds, env);
    }

    private void hoverAndClick(WebDriver driver, WebElement webElement) throws InterruptedException {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
        Thread.sleep(500);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).clickAndHold().click().perform();
    }

    private void createTapd(WebDriver driver, List<Tapd> tapds, String env) throws InterruptedException {
        String mainWindow = driver.getWindowHandle();
        for (Tapd tapd : tapds) {
            // create new button
            driver.findElement(By.id("btn_add_bug")).click();

            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                WebDriver window = driver.switchTo().window(windowHandle);
                Thread.sleep(3000);
                try {
                    if (window.getCurrentUrl().contains("add")) {
                        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                        // set title
                        driver.findElement(By.id("BugTitle")).clear();
                        driver.findElement(By.id("BugTitle")).sendKeys(tapd.getTitle());
                        // set 优先级
                        WebElement bugPriority = driver.findElement(By.xpath("//*[@id=\"BugPriorityDiv\"]/div"));
                        bugPriority.click();
                        hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='中']")));
                        // set 严重程度 *
                        WebElement bugSeverity = driver.findElement(By.xpath("//*[@id=\"BugSeverityDiv\"]/div/div"));
                        bugSeverity.click();
                        hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='一般']")));
                        // set 混合云环境 *
                        WebElement envWebElement = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldThreeDiv\"]/div/div"));
                        envWebElement.click();
                        if (env.equals("prod")) {
                            hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='prod_kl']")));
                        } else {
                            hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='"+ env +"']")));
                        }
                        // set 处理人
                        WebElement bugCurrentOwner = driver.findElement(By.id("BugCurrentOwnerValue"));
                        bugCurrentOwner.click();
                        Thread.sleep(2000);
                        bugCurrentOwner.sendKeys(tapd.getDealMan());
                        Thread.sleep(1000);
                        // we need click some element to fill the input
                        driver.findElement(By.id("BugTitle")).click();
                        // set 所属部门 *
                        WebElement bugCustomFieldOne = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldOneDiv\"]/div/div"));
                        bugCustomFieldOne.click();
                        hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='"+ Util.getTapdDept(tapd.getDept()) +"']")));
                        // set 业务范围 *
                        WebElement bugCustomFieldFive = driver.findElement(By.xpath("//*[@id=\"BugCustomFieldFiveDiv\"]/div/div"));
                        bugCustomFieldFive.click();
                        hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='"+ tapd.getRange() +"']")));
                        // set 项目标识 *
                        driver.findElement(By.id("BugCustomFieldTwo")).sendKeys(tapd.getMark());
                        // set 问题分类 *
                        WebElement BugCustomField6 = driver.findElement(By.xpath("//*[@id=\"BugCustomField6Div\"]/div/div"));
                        BugCustomField6.click();
                        hoverAndClick(driver, driver.findElement(By.xpath("//td[@title='功能缺陷']")));

                        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"editor-BugDescription\"]/div[7]/iframe")));
                        // 实施人员
                        jsExecutor.executeScript("arguments[0].innerHTML = '赵泽'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[1]/div")));
                        // 实施时间
                        Date t = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        jsExecutor.executeScript("arguments[0].innerHTML = '"+ df.format(t) +"'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[2]/div")));
                        // 实施事项
                        jsExecutor.executeScript("arguments[0].innerHTML = '大盘巡检'", driver.findElement(By.xpath("/html/body/div[1]/div[1]/table/tbody/tr[4]/td[3]/div")));
                        // url
                        WebElement url = driver.findElement(By.xpath("/html/body/div[1]/div[2]"));
                        jsExecutor.executeScript("arguments[0].innerHTML = '1、问题截图<br><font color=\"red\"><strong>请修改状态到“待验证”，并指定给创建人。由创建人关闭，否则不要指定给创建人验证。</strong></font><br>"+ tapd.getLink() +"'", url);
                        // 问题截图
                        WebElement pic = driver.findElement(By.xpath("/html/body/div[1]/div[3]"));
                        hoverAndClick(driver, pic);

                        //指定图片路径
                        Image image = ImageIO.read(new File(SeleTest.picPath + tapd.getMark() + ".png"));
                        //把图片路径复制到剪切板
                        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new ClipImage(image), null);

//                        //新建一个Robot类的对象
//                        Robot robot = new Robot();
//                        Thread.sleep(1000);
//                        // change line
//                        robot.keyPress(KeyEvent.VK_ENTER);
//                        robot.keyRelease(KeyEvent.VK_ENTER);
//                        Thread.sleep(1000);
//                        //按下Ctrl+V
//                        robot.keyPress(KeyEvent.VK_CONTROL);
//                        robot.keyPress(KeyEvent.VK_V);
//                        //释放Ctrl+V
//                        robot.keyRelease(KeyEvent.VK_V);
//                        robot.keyRelease(KeyEvent.VK_CONTROL);
//                        Thread.sleep(1000);

                        Actions actions = new Actions(driver);
                        actions.sendKeys(Keys.ENTER).build().perform();
                        Thread.sleep(500);
                        actions.keyDown(Keys.CONTROL).sendKeys("v").perform();
                        Thread.sleep(500);

                        driver.switchTo().defaultContent();
                        // click the submit button
                        driver.findElement(By.xpath("//*[@id=\"_view\"]")).click();
                        Thread.sleep(2000);

                        String currentUrl = driver.getCurrentUrl();
                        tapd.setTapdUrl(currentUrl);
                        driver.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    driver.close();
                }
            }
            driver.switchTo().window(mainWindow);
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

    private List<Tapd> getProject(WebDriver driver, String env, String type) throws IOException, InterruptedException {
        Map<String, Relation> projectInfo = getProjectInfo();

        driver.get("https://dolphin-"+ env +".kedacom.com/dashboard/#/service/dashboard");

        List<Tapd> tapdList = getInfo(driver, type, projectInfo, env);
        String s = JSON.toJSONString(tapdList);
        saveData(s, "tapdList.txt");
        return tapdList;
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
                    WebElement projectInfo = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[2]/section/div/div[2]/div/div[1]/div[2]/div/span/span/div"));
                    String text = projectInfo.getText();
                    String[] split = text.split("\n")[0].split("/");
                    String namespace = split[0];
                    String mark = split[1];
                    Relation relation = project.get(mark);
                    // capture the pic
                    WebElement errorPic;
                    if (type.equals("error")) {
                        errorPic = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[3]/div/div[2]/div[2]/div/div[2]/div/div/div"));
                    } else {
                        // slow page
                        errorPic = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/section/div/div[3]/div/div[2]/div[2]/div/div[4]/div/div/div"));
                    }
                    File errorPicFile = new File(SeleTest.picPath + mark + ".png");
                    if (!errorPicFile.getParentFile().exists()) {
                        errorPicFile.getParentFile().mkdirs();
                    }
                    errorPicFile.createNewFile();
                    captureElement(errorPicFile, errorPic, driver);
                    driver.close();

                    try {
                        Tapd tapd = new Tapd();
                        System.out.println("【"+ env + "环境】"+ split[0] +" + "+ split[1] +" + 有错误");
                        System.out.println("项目标识 " + split[1]);
                        System.out.println("问题链接 " + currentUrl);
                        System.out.println("所属部门 " + relation.getDept());
                        System.out.println("业务范围 " + relation.getBelong());
                        System.out.println("处理人 " + relation.getOwnerName());
                        System.out.println();
                        tapd.setDealMan(relation.getOwnerName());
                        tapd.setDept(relation.getDept());
                        tapd.setLink(currentUrl);
                        tapd.setMark(split[1]);
                        tapd.setRange(relation.getBelong());
                        tapd.setTitle("【"+ env + "环境】"+ split[0] +" + "+ split[1] +" + 有错误");
                        tapd.setNamespace(split[0]);
                        tapds.add(tapd);
                    } catch (Exception e) {
                        System.out.println(mark);
                        e.printStackTrace();
                    }
                }
            }
            driver.switchTo().window(mainWindow);
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
