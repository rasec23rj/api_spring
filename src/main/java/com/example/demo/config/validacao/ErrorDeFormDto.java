package com.example.demo.config.validacao;

public class ErrorDeFormDto {
    public String campo;
    public String erro;

    public ErrorDeFormDto() {
    }

    public ErrorDeFormDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return this.campo;
    }

    public String getErro() {
        return this.erro;
    }

}
