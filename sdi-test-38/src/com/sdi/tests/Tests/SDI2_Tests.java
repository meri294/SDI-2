package com.sdi.tests.Tests;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sdi.tests.pageobjects.PO_LoginForm;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class SDI2_Tests {

	WebDriver driver; 
	List<WebElement> elementos = null;
	
	public SDI2_Tests()
	{
	}

	@Before
	public void run()
	{
		//Creamos el driver para Firefox. Recomendable usar Firefox.
		driver = new FirefoxDriver();
		//Vamos a la página principal de mi aplicación
		driver.get("http://localhost:8280/sdi2-38");			
	}
	@After
	public void end()
	{
		//Cerramos el navegador
		driver.quit();
	}

	//PRUEBAS
	
	//	1.	[RegVal] Registro de Usuario con datos válidos.
	@Test	
    public void t01_RegVal() {
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		new PO_LoginForm().rellenaFormulario(driver, "usuario1", "usuario1");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "bienvenido", 10);
		SeleniumUtils.textoPresentePagina(driver, "Fernando");
    
    }
	//	2.	[RegInval] Registro de Usuario con datos inválidos (contraseñas diferentes).
    @Test
    public void t02_RegInval() {
    	SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		new PO_LoginForm().rellenaFormulario(driver, "usuario1", "incorrecto");
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 10);
		SeleniumUtils.textoPresentePagina(driver, "La contraseña no es correcta");
    }
	//	3.	[IdVal] Identificación de Usuario registrado con datos válidos.
   /* @Test
    public void t03_IdVal() {
    
    }
	//	4.	[IdInval] Identificación de usuario registrado con datos inválidos.
    @Test
    public void t04_IdInval() {
    
    }
	//	5.	[AccInval] Intento de acceso con URL desde un usuario no público (no identificado). Intento de acceso a vistas de acceso privado. 
    @Test
    public void t05_AccInval() {
    
    }
	//	6.	[RegViajeVal] Registro de un viaje nuevo con datos válidos.
    @Test
    public void t06_RegViajeVal() {
    
    }
	//	7.	[RegViajeInVal] Registro de un viaje nuevo con datos inválidos.
    @Test
    public void t07_RegViajeInVal() {
    
    }
	//	8.	[EditViajeVal] Edición de viaje existente con datos válidos.
    @Test
    public void t08_EditViajeVal() {
    
    }
	//	9.	[EditViajeInVal] Edición de viaje existente con datos inválidos.
    @Test
    public void t09_EditViajeInVal() {
    
    }
	//	10.	[CancelViajeVal] Cancelación de un viaje existente por un promotor.
    @Test
    public void t10_CancelViajeVal() {
    
    }
	//	11.	[CancelMulViajeVal] Cancelación de múltiples viajes existentes por un promotor.
    @Test
    public void t11_CancelMulViajeVal() {
    
    }
	//	12.	[Ins1ViajeAceptVal] Inscribir en un viaje un solo usuario y ser admitido por el promotor.
    @Test
    public void t12_Ins1ViajeAceptVal() {
    
    }
	//	13.	[Ins2ViajeAceptVal] Inscribir en un viaje dos usuarios y ser admitidos los dos por el promotor.
    @Test
    public void t13_Ins2ViajeAceptVal() {
    
    }
	//	14.	[Ins3ViajeAceptInval] Inscribir en un viaje (2 plazas máximo) dos usuarios y ser admitidos los dos y que un tercero intente inscribirse en ese mismo viaje pero ya no pueda por falta de plazas.
    @Test
    public void t14_Ins3ViajeAceptInval() {
    
    }
	//	15.	[CancelNoPromotorVal] Un usuario no promotor Cancela plaza.
    @Test
    public void t15_CancelNoPromotorVal() {
    
    }
	//	16.	[Rech1ViajeVal] Inscribir en un viaje un usuario que será admitido y después rechazarlo por el promotor.
    @Test
    public void t16_Rech1ViajeVal() {
    
    }
	//	17.	[i18N1] Cambio del idioma por defecto a un segundo idioma. (Probar algunas vistas)
    @Test
    public void t17_i18N1() {
    
    }
	//	18.	[i18N2] Cambio del idioma por defecto a un segundo idioma y vuelta al idioma por defecto. (Probar algunas vistas)
    @Test
    public void t18_i18N2() {
    
    }
	//	19.	[OpFiltrado] Prueba para el filtrado opcional.
    @Test
    public void t19_OpFiltrado() {
    
    }
	//	20.	[OpOrden] Prueba para la ordenación opcional.
    @Test
    public void t20_OpOrden() {
    
    }
	//	21.	[OpPag] Prueba para la paginación opcional.
    @Test
    public void t21_OpPag() {
    
    }
	//	22.	[OpMante] Prueba del mantenimiento programado opcional.

    @Test
    public void t22_OpMante() {
    
    }*/
}