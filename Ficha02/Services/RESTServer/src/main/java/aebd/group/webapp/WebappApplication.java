package aebd.group.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


@SpringBootApplication
public class WebappApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(WebappApplication.class, args);

	}

}
