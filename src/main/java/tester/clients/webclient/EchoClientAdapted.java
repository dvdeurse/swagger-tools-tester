package tester.clients.webclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tester.clients.webclient.client.BaseClient;
import tester.server.payload.Pet;

import java.util.List;
import java.util.Map;

public class EchoClientAdapted extends BaseClient {
    public EchoClientAdapted(WebClient webClient) {
        super(webClient);
    }

    public EchoClientAdapted(WebClient webClient, String basePath) {
        super(webClient, basePath);
    }

    public EchoClientAdapted(WebClient webClient, String basePath, Map<String, List<String>> headers) {
        super(webClient, basePath, headers);
    }

    public Mono<String> echoPets(List<Pet> requestBody) {
        ParameterizedTypeReference<String> responseTypeRef = new ParameterizedTypeReference<>(){};
        ParameterizedTypeReference<List<Pet>> requestTypeRef = new ParameterizedTypeReference<>(){};
        return invokeAPI("/echo/pets", "POST", createUrlVariables(), createQueryParameters(), requestBody, requestTypeRef).flatMap(e -> mapResponse(e, responseTypeRef));
    }

    private <T> Mono<ClientResponse> invokeAPI(String path, String method, Map<String, String> urlVariables, MultiValueMap<String, String> queryParams, T body, ParameterizedTypeReference<T> requestTypeRef) {
        WebClient.RequestBodySpec request = webClient
                .method(HttpMethod.resolve(method))
                .uri(builder -> builder
                        .path(basePath + path)
                        .queryParams(queryParams)
                        .build(urlVariables)
                );
        customizeRequest(request);
        if (body != null) {
            request.body(Mono.just(body), requestTypeRef);
        }
        return request.exchange();
    }

}
