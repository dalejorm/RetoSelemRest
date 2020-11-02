package TestSteps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class stepsWong {

    WebDriver driver = null;

    @Before
    public void ejecutarStep(){
//        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
//        driver = new FirefoxDriver();
        driver = new ChromeDriver();
        //properties
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Given("Que el usuario quiere comprar un producto")
    public void que_el_usuario_quiere_comprar_un_producto() {
        // Write code here that turns the phrase above into concrete actions
        driver.get("https://www.wong.pe/especiales/cyberwong");
        WebElement firstResult = new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='abarrotes']")));

    }

    @When("el busca el {string} de interes:")
    public void el_busca_el_de_interes(String producto) {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("search-autocomplete-input")).sendKeys(producto);
        driver.findElement(By.id("search-autocomplete-input")).sendKeys(Keys.ENTER);
    }

    @When("el agrega el {string} al carrito de compras")
    public void el_agrega_el_producto_al_carrito_de_compras(String producto) {
        // Write code here that turns the phrase above into concrete actions
        WebElement firstResult = new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='"+producto+"' and @class='product-item__name']")));
        WebElement p = driver.findElement(By.xpath("//a[@title='"+producto+"' and @class='product-item__name']"));
        Actions act = new Actions(driver);
        act.moveToElement(p).click().build().perform();
        driver.findElement(By.xpath("//span[contains(text(),'Agregar al carrito')]")).click();
    }

    @Then("el deberia observar el {string} en el carrito de productos")
    public void el_deberia_observar_el_producto_en_el_carrito_de_productos(String producto) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        WebElement secondResult = new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.className("shipping-preference-overlay__content")));
        if(driver.findElements(By.className("shipping-preference-overlay__content")).size() !=0){
            System.out.println("Ventana notificación de producto agregado correcta");
            WebElement alert = driver.findElement(By.className("shipping-preference-overlay__content"));
            WebDriverWait wait = new WebDriverWait( driver , 30);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@type='button' and @class='modal-address__close font-icn cross']")));
            alert.findElement(By.xpath("//button[@type='button' and @class='modal-address__close font-icn cross']")).click();

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='navigation wrapper']")));
            WebElement buyCar = driver.findElement(By.xpath("//div[@class='navigation wrapper']"));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[@type='button' and @class='btn red minicart__action--toggle-open food-site']")));
            buyCar.findElement(By.xpath("//button[@type='button' and @class='btn red minicart__action--toggle-open food-site']")).click();

            WebElement menu = driver.findElement(By.id("minicart-navigation"));
            if(menu.findElements(By.xpath("//a[text()='"+producto+"']")).size() != 0){
                System.out.println("Existe en producto en el carrito de compras");
            }else{
                System.out.printf("No existe el producto en el carrito de compras");
                throw new Exception("");
            }
        }else{
            System.out.println("No hay ventana de notificación");
            throw new Exception("");
        }
    }

    @After
    public void finStep(){
        driver.close();
    }

}
