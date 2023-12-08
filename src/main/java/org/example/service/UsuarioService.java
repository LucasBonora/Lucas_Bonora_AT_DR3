package org.example.service;


import org.example.dto.UsuarioDTOInput;
import org.example.dto.UsuarioDTOOutput;
import org.example.model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {
    private List<Usuario> listaUsuarios;
    private ModelMapper modelMapper;

    public UsuarioService() {
        this.listaUsuarios = listaUsuarios;
        this.modelMapper = modelMapper;
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput) {
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public List<UsuarioDTOOutput> listarUsuarios() {
        return listaUsuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .collect(Collectors.toList());
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return listaUsuarios.stream()
                .filter(usuario -> usuario.getId() == id)
                .findFirst();
    }
    public void alterarUsuario(int id, UsuarioDTOInput usuarioDTOInput) {
        Optional<Usuario> usuarioExistente = buscarUsuarioPorId(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtualizado = modelMapper.map(usuarioDTOInput, Usuario.class);
            usuarioExistente.get().setNome(usuarioAtualizado.getNome());
            usuarioExistente.get().setSenha(usuarioAtualizado.getSenha());
        }
    }

    public UsuarioDTOOutput buscarUsuario(int id) {
        Optional<Usuario> usuarioExistente = buscarUsuarioPorId(id);

        return usuarioExistente.map(usuario -> modelMapper.map(usuario, UsuarioDTOOutput.class))
                .orElse(null);
    }

    public void excluirUsuario(int id) {
        Iterator<Usuario> iterator = listaUsuarios.iterator();

        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getId() == id) {
                iterator.remove();
                break;
            }
        }
    }

}
