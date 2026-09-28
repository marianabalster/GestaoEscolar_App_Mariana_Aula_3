package br.senai.escola.models;

public class Aluno {
    private final String matricula;
    private String nome;

    public Aluno(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
