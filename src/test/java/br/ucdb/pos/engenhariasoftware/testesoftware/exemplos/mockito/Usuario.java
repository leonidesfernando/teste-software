package br.ucdb.pos.engenhariasoftware.testesoftware.exemplos.mockito;

import java.time.LocalDateTime;

public class Usuario {

    public Usuario(String nome, String email, String cpf, LocalDateTime ultimoAcesso, Perfil perfil){}

    enum Perfil{
        OPERADOR,
        GERENTE,
        ADMIN;
    }
}
