package org.acme.externo.endereco.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.externo.endereco.dto.EnderecoResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class ViaCepService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(ViaCepService.class);

    @ConfigProperty(name = "viacep.api.url")
    String viaCepApiUrl;

    public ViaCepService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public EnderecoResponse buscarPorCep(String cep) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(viaCepApiUrl + cep + "/json/"))
                .GET()
                .build();

        LOG.info("Iniciando busca de endereço por cep");
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 400) {
            throw new IllegalArgumentException("Formato de CEP inválido");
        }
        LOG.info("Serviço completo");
        EnderecoResponse enderecoResponse = objectMapper.readValue(response.body(), EnderecoResponse.class);

        return enderecoResponse;
    }
}
