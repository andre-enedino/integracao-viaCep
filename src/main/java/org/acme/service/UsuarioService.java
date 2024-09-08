package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.entity.Usuario;
import org.acme.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional()
    public void salvar(Usuario usuario){

        usuarioRepository.persist(usuario);

    }
}
