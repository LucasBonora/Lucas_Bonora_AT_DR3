package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UsuarioDTOInput;
import org.example.dto.UsuarioDTOOutput;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

import static spark.Spark.*;

public class UsuarioController {
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper;

    public UsuarioController(UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    public void respostasRequisicoes() {
        get("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                List<UsuarioDTOOutput> usuarios = usuarioService.listarUsuarios();

                String jsonUsuarios = objectMapper.writeValueAsString(usuarios);

                response.type("application/json");

                response.status(200);

                return jsonUsuarios;
            }
        });
        get("/usuarios/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Obtém o ID do parâmetro da URI
                int idUsuario = Integer.parseInt(request.params(":id"));

                // Busca o usuário pelo ID
                UsuarioDTOOutput usuarioDTOOutput = usuarioService.buscarUsuario(idUsuario);

                if (usuarioDTOOutput != null) {
                    // Converte o usuário para JSON
                    String jsonUsuario = objectMapper.writeValueAsString(usuarioDTOOutput);

                    // Define o tipo de conteúdo da resposta como JSON
                    response.type("application/json");

                    // Define o código de resposta como 200 (OK)
                    response.status(200);

                    // Retorna o usuário como JSON
                    return jsonUsuario;
                } else {
                    // Se o usuário não for encontrado, define o código de resposta como 404 (Not Found)
                    response.status(404);
                    return "Usuário não encontrado";
                }
            }
        });
        delete("/usuarios/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int idUsuario = Integer.parseInt(request.params(":id"));

                usuarioService.excluirUsuario(idUsuario);

                response.status(204);

                return "Usuário excluído com sucesso";
            }
        });
        post("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String corpoRequisicao = request.body();

                UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(corpoRequisicao, UsuarioDTOInput.class);

                usuarioService.inserirUsuario(usuarioDTOInput);

                response.status(201);

                return "Usuário inserido com sucesso";
            }
        });
        put("/usuarios/:id", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                int idUsuario = Integer.parseInt(request.params(":id"));

                String corpoRequisicao = request.body();

                UsuarioDTOInput usuarioDTOInput = objectMapper.readValue(corpoRequisicao, UsuarioDTOInput.class);

                usuarioService.alterarUsuario(idUsuario, usuarioDTOInput);

                response.status(200);

                return "Usuário atualizado com sucesso";
            }
        });
    }
}


