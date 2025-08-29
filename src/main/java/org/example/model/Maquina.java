package org.example.model;

public class Maquina {
    private String nome, setor, status;
    private int id;

    public Maquina(int id, String nome, String setor, String status){
        this.setor = setor;
        this.nome = nome;
        this.status = status;
        this.id = id;
    }

    public Maquina(String nome, String setor, String status){
        this.setor = setor;
        this.nome = nome;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
