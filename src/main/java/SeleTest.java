import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        SeleTest seleTest = new SeleTest();
        System.setProperty("webdriver.gecko.driver", "E:\\soft\\geckodriver/geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        seleTest.getProject(driver);

        driver.close();
    }

    private void tapc(WebDriver driver) throws InterruptedException {

        driver.get("https://www.tapd.cn/51894879/bugtrace/bugreports/my_view");
        Thread.sleep(20000);


    }

    private void getProject(WebDriver driver) throws IOException {
        // dev test prod
        String env = "prod";
        Map<String, Relation> projectInfo = getProjectInfo();

        driver.get("https://dolphin-"+ env +".kedacom.com/dashboard/#/service/dashboard");

        // type is error or slow
        getInfo(driver, "error", projectInfo, env);
    }

    private Map<String, Relation> getProjectInfo() throws IOException {
        Map<String, Relation> relationMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("D:\\learn\\seleniumTest\\src\\main\\java\\info.txt")));
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

    private void getInfo(WebDriver driver, String type, Map<String, Relation> project, String env) throws IOException {
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
        for (WebElement element : elements) {
            // enter detail page
            WebElement detailPage = element.findElement(By.cssSelector("span.can-click"));
            //使用显示等待，等待掩盖的div消失
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(detailPage));

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
            String[] split = s.split(";");
            Relation relation = project.get(split[1]);
            System.out.println("【"+ env + "环境】"+ split[0] +" + "+ split[1] +" + 有错误");
            System.out.println("处理人 " + relation.getOwnerName());
            System.out.println("项目标识 " + split[0]);
            System.out.println("所属部门 " + relation.getDept());
            System.out.println("业务范围 " + relation.getBelong());
            System.out.println("问题链接 " + split[2]);
            System.out.println();
        }
    }

    private File captureElement(File screenshot, WebElement element, WebDriver driver){
        try {
            // wait to get all info
            Thread.sleep(3000);
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
