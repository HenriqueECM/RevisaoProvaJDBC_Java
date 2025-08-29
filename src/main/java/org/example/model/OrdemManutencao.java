package org.example.model;

import java.time.LocalDate;

public class OrdemManutencao {
    private int id, idMaquina, idTecnico;
    private LocalDate dataSolicitacao;
    private String status;

    public OrdemManutencao(int id, int idMaquina, int idTecnico, LocalDate dataSolicitacao, String status){
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
        this.id = id;
    }

    public OrdemManutencao(int idMaquina, int idTecnico, LocalDate dataSolicitacao, String status){
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
        this.idMaquina = idMaquina;
        this.idTecnico = idTecnico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
