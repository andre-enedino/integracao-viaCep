package org.acme.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalException {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalException.class);

    @Provider
    public static class DuplicacaoDeObjetosExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException e) {
            LOG.info("Detalhes do erro: ", e);
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getErrorMessage(), "Usuário já existe"))
                    .build();
        }
    }

    @Provider
    public static class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

        @Override
        public Response toResponse(IllegalArgumentException e) {
            LOG.error("Detalhes do erro: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage(), "Erro na chamda"))
                    .build();
        }
    }
}
