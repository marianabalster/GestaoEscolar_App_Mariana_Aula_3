package br.senai.escola.models;

public class Professor {
    private final String registro;
    private String nome;

    public Professor(String registro, String nome) {
        this.registro = registro;
        this.nome = nome;
    }

    public String getRegistro() { return registro; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
