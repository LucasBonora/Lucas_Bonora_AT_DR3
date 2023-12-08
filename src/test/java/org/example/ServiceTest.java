package org.example;

import org.example.dto.UsuarioDTOInput;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class ServiceTest {

    @Test
    public void testInserirUsuario() {
        UsuarioService usuarioService = new UsuarioService();

        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("Novo Usuário");
        usuarioDTOInput.setSenha("novaSenha123");

        usuarioService.inserirUsuario(usuarioDTOInput);

        int tamanhoLista = usuarioService.listarUsuarios().size();

        Assertions.assertEquals(1, tamanhoLista);
    }
}
