package br.senai.escola.models;

public class Espaco {
    private final String codigo;
    private String nome;
    private String tipo;
    private int capacidade;

    public Espaco(String codigo, String nome, String tipo, int capacidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getCapacidade() { return capacidade; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    @Override public String toString() { return codigo + " - " + nome; }
}
