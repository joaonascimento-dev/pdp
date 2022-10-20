package br.fatec.pdp;

import java.io.File;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdpApplication {

	public static String APP_PATH;

	public static void main(String[] args) {
		SpringApplication.run(PdpApplication.class, args);
		
		Locale.setDefault(new Locale("pt", "BR"));
        APP_PATH = new File(".").getAbsolutePath();
        APP_PATH = APP_PATH.substring(0, APP_PATH.length() - 1);

        System.out.println("APP_PATH: " + APP_PATH);
	}

}
