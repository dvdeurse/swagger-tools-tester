package tester.clients.resttemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tester.clients.resttemplate.client.BaseClient;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class EchoClientAdapted extends BaseClient {
    public EchoClientAdapted(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public EchoClientAdapted(RestTemplate restTemplate, String basePath) {
        super(restTemplate, basePath);
    }

    public EchoClientAdapted(RestTemplate restTemplate, String basePath,
                      Map<String, List<String>> headers) {
        super(restTemplate, basePath, headers);
    }

    public String echoPets(List<tester.server.payload.Pet> requestBody) {
        ParameterizedTypeReference<String> responseTypeRef = new ParameterizedTypeReference<>(){};
        ParameterizedTypeReference<List<tester.server.payload.Pet>> requestTypeRef = new ParameterizedTypeReference<>(){};
        ResponseEntity<String> response = invokeAPI("/echo/pets", "POST", createUrlVariables(), createQueryParameters(), requestBody, requestTypeRef, responseTypeRef);
        return response.getBody();
    }

    private <T,U> ResponseEntity<T> invokeAPI(String path, String method, Map<String, String> urlVariables, MultiValueMap<String, String> queryParams, U body, ParameterizedTypeReference<U> requestType, ParameterizedTypeReference<T> returnType) {
        URI baseUrl = restTemplate.getUriTemplateHandler().expand(basePath);
        URI uri = UriComponentsBuilder
                .fromUri(baseUrl)
                .path(path)
                .queryParams(queryParams)
                .buildAndExpand(urlVariables)
                .toUri();
        RequestEntity.BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.resolve(method), uri);
        customizeRequest(requestBuilder);
        RequestEntity<Object> requestEntity = requestBuilder.body(body, requestType.getType());
        return restTemplate.exchange(requestEntity, returnType);
    }

}
