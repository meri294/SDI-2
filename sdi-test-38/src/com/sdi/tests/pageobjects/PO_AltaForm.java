package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_AltaForm {

	public void rellenaFormulario(WebDriver driver, String pId, String pnombre,
			String papellidos, String pemail,
			String ppass, String prepass) {
		WebElement id = driver.findElement(By.id("registro:login"));
		id.click();
		id.clear();
		id.sendKeys(pId);
		WebElement nombre = driver.findElement(By.id("registro:nombre"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(pnombre);
		WebElement apellidos = driver.findElement(By.id("registro:apellidos"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(papellidos);
		WebElement idcorreo = driver.findElement(By.id("registro:email"));
		idcorreo.click();
		idcorreo.clear();
		idcorreo.sendKeys(pemail);
		WebElement password = driver.findElement(By.id("registro:pass"));
		password.click();
		password.clear();
		password.sendKeys(ppass);
		WebElement repassword = driver
				.findElement(By.id("registro:repitePass"));
		repassword.click();
		repassword.clear();
		repassword.sendKeys(prepass);
		// Pulsar el boton de Alta.
		By boton = By.id("registro:registrarse");
		driver.findElement(boton).click();
	}

}
