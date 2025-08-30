import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwagLabsTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // 1. Ingresar a la página
        driver.get("https://www.saucedemo.com/v1/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    // ---------------------------------------------------------------------------------------------------------

    @Test
    public void checkoutCamposObligatoriosTest() {
        // producto al carrito
        driver.findElement(By.xpath("//button[contains(text(),'ADD TO CART')]")).click();

        // al carrito
        driver.findElement(By.xpath("//*[name()='path' and contains(@fill,'currentCol')]")).click();

        WebElement checkout = new WebDriverWait(driver, Duration.ofSeconds(10)) .until (ExpectedConditions.presenceOfElementLocated (By.xpath("//a[contains(text(),'CHECKOUT')]")));
        checkout.click();

        //  finalizar sin rellenar datos obligatorios
        WebElement submit = new WebDriverWait(driver, Duration.ofSeconds(10)) .until (ExpectedConditions.presenceOfElementLocated (By.xpath(" //input[@value='CONTINUE']")));
        submit.click();

        // Validar que aparezca error
        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        assertTrue(errorMessage.contains("Error"), "El sistema no mostró mensaje de campos requeridos.");
        //DEBE DAR TEST FAILED
    }
    // ---------------------------------------------------------------------------------------------------------

    @Test
    public void verificarSidebar_AllItems() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement menuButton = driver.findElement(By.xpath("//button[normalize-space()='Open Menu']"));
        menuButton.click();

        // Esperar a que se despliegue el sidebar
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("bm-menu")));

        WebElement allItems = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("inventory_sidebar_link")));
        allItems.click();
        Thread.sleep(1000);
        //DEBE DAR TEST SUCCESUFL
    }
    // ---------------------------------------------------------------------------------------------------------
    @Test
    public void verificarAccesoRutaSensiblesSinLogi2n() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Abrir menú
        WebElement menuButton = driver.findElement(By.xpath("//button[text()='Open Menu']"));
        menuButton.click();

        // Esperar a que se despliegue el sidebar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.bm-menu-wrap")));

        //  logout
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logout.click();

        Thread.sleep(1000);

        // Verificar que se redirige al login
        boolean isLoginPage = driver.getCurrentUrl().contains("saucedemo.com/v1/") &&
                driver.findElements(By.id("login-button")).size() > 0;
        assertTrue(isLoginPage, "ERROR: Logout no redirigió al login.");

        // Intentar entrar directo a inventory.html
        driver.get("https://www.saucedemo.com/v1/inventory.html");
        Thread.sleep(1000);
        // Verificar nuevamente que sigue en login
        boolean sigueEnLogin = driver.findElements(By.id("login-button")).size() > 0;
        assertTrue(sigueEnLogin, "ERROR: Se accedió a inventory.html sin autenticación.");
        //DEBE DAR TEST FAILED
    }

    // ---------------------------------------------------------------------------------------------------------
    @Test
    public void verificarCompraVacia() {
        // Ir al carrito vacío
        driver.findElement(By.className("shopping_cart_link")).click();

        //Intentar hacer checkout
        driver.findElement(By.xpath("//a[contains(text(),'CHECKOUT')]")).click();

        // Validar que el carrito sigue vacío
        boolean carritoVacio = driver.findElements(By.className("cart_item")).size() == 0;

        assertTrue(carritoVacio, "ERROR: El sistema permitió avanzar con un carrito vacío.");
        //DEBDE DAR SUCCESFUL
    }
    // ---------------------------------------------------------------------------------------------------------
    @Test
    public void verificarRedesSociales() {
        // Guardar ventana original
        String originalWindow = driver.getWindowHandle();

        // Twitter
        WebElement twitterLink = driver.findElement(By.className("social_twitter"));
        twitterLink.click();
        cambiarPestanaYVerificar("twitter.com/saucelabs");
        driver.switchTo().window(originalWindow);

        // Facebook
        WebElement facebookLink = driver.findElement(By.className("social_facebook"));
        facebookLink.click();
        cambiarPestanaYVerificar("facebook.com/saucelabs");
        driver.switchTo().window(originalWindow);

        // LinkedIn
        WebElement linkedInLink = driver.findElement(By.className("social_linkedin"));
        linkedInLink.click();
        cambiarPestanaYVerificar("linkedin.com/company/sauce-labs");
        driver.switchTo().window(originalWindow);
        //DEBDE DAR FAILED
    }
    //FUNCION AUXILIAR
    private void cambiarPestanaYVerificar(String urlEsperada) {
        // Cambiar a la nueva pestaña abierta
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        // Verificar URL
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains(urlEsperada),
                "La red social no redirigió correctamente. URL actual: " + currentUrl);
        driver.close();
    }

}

