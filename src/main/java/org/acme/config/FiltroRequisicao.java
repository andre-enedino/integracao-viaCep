package org.acme.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class FiltroRequisicao implements ContainerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(FiltroRequisicao.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        LOG.info("Filtrando requisição = "+ containerRequestContext.getUriInfo().getRequestUri());

    }
}
