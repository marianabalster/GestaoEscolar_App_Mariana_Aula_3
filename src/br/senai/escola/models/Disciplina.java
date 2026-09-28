package br.senai.escola.models;

public class Disciplina {
    private final String codigo;
    private String nome;
    private Curso curso;

    public Disciplina(String codigo, String nome, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public Curso getCurso() { return curso; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCurso(Curso curso) { this.curso = curso; }
}
