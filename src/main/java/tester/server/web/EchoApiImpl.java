package tester.server.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tester.server.api.EchoApi;
import tester.server.payload.Pet;

import javax.validation.Valid;
import java.util.List;

@Component
public class EchoApiImpl implements EchoApi {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Mono<String> echoPets(@Valid List<Pet> requestBody) {
        return Mono.just(objectMapper.writerFor(new TypeReference<List<Pet>>(){}).writeValueAsString(requestBody));
    }

}
