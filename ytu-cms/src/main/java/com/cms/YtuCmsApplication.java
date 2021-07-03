package com.cms;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YtuCmsApplication {
	private int maxUploadSizeInMb = 10 * 1024 * 1024;
	public static final String path = "E://ytu_cms";
	public static final List<String> folders = List.of("", "documents", "images", "tmp");

	public static void main(String[] args) {
		final String uri ="mongodb://localhost:27017/?readPreference=primary&appname=SimpleSolution&ssl=false";
		//"mongodb://localhost:27017/?readPreference=primary&appname=SimpleSolution&ssl=false"; local uri
		//"mongodb+srv://dbAdmin:qssUbEBV8Dh3cnUs@sat.6wtix.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"; server uri
		final String database = "cms";

		SpringApplication.run(YtuCmsApplication.class, args);
		MongoDB.startClient(database, uri);
		folders.forEach(iter -> {
			File f = new File(path + "/" + iter);
			boolean status = f.mkdir();
			if (status)
				System.out.println("FolderControl   | not exist | " + iter + "\t|\n                "
						+ "| create    | done      |");
			else
				System.out.println("FolderControl   | exist     | " + iter + "\t|");

		});
		File f = new File(path + "/documents/" + LocalDate.now().getYear());
		f.mkdir();
		f = new File(path + "/images/users/");
		f.mkdir();
		f = new File(path + "/images/clubs/");
		f.mkdir();
		f = new File(path + "/images/events/");
		f.mkdir();
		System.out.println("Result          | " + path + "\t\t|");
		System.out.println("Result          | checked   | mongodb\t|");
		System.out.println("Result          | checked   | filepath\t|");

	}

	@Bean
	public TomcatServletWebServerFactory containerFactory() {
		return new TomcatServletWebServerFactory() {
			protected void customizeConnector(Connector connector) {
				super.customizeConnector(connector);
				connector.setMaxPostSize(maxUploadSizeInMb);
				connector.setMaxSavePostSize(maxUploadSizeInMb);
				if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {

					((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(maxUploadSizeInMb);
					logger.info("Set MaxSwallowSize " + maxUploadSizeInMb);
				}
			}
		};

	}

}