package org.acme.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.acme.externo.endereco.service.ViaCepService;

import java.io.IOException;

@Path("/enderecos")
public class EnderecoResource {

    private final ViaCepService viaCepService;

    public EnderecoResource(ViaCepService viaCepService) {
        this.viaCepService = viaCepService;
    }

    @GET
    public Response buscarPorCep(@QueryParam("cep") String cep) throws IOException, InterruptedException {
        return Response.status(Response.Status.OK).entity(viaCepService.buscarPorCep(cep)).build();
    }
}
