package tester.clients.resttemplate;

import tester.server.payload.Cat;
import tester.server.payload.Dog;
import tester.server.payload.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class TestRestTemplate implements CommandLineRunner {

    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestRestTemplate.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        EchoClientAdapted client = new EchoClientAdapted(restTemplate, "http://localhost:6000");

        Dog vita = new Dog();
        vita.setName("Vita");

        Cat rox = new Cat();
        rox.setName("Rox");

        List<Pet> pets = Arrays.asList(vita, rox);

        System.out.println("Testing the endpoint");
        String result = client.echoPets(pets);
        System.out.println(result);
    }
}
