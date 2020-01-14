package tester.clients.webclient;

import tester.server.payload.Cat;
import tester.server.payload.Dog;
import tester.server.payload.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class TestWebClient implements CommandLineRunner {

    @Autowired
    WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TestWebClient.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        EchoClientAdapted client = new EchoClientAdapted(webClientBuilder.baseUrl("http://localhost:6000").build(), "");

        Dog vita = new Dog();
        vita.setName("Vita");

        Cat rox = new Cat();
        rox.setName("Rox");

        List<Pet> pets = Arrays.asList(vita, rox);

        System.out.println("Testing the endpoint");
        String result = client.echoPets(pets)
                .doOnError(e -> {
                    String message = e.getMessage();
                    if(e instanceof WebClientResponseException) {
                        message = ((WebClientResponseException) e).getResponseBodyAsString();
                    }
                    System.out.println("There was an error: " + message);
                })
                .block();
        System.out.println(result);
    }
}
