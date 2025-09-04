package org.example.model;

public class OrdemPeca {
    private int idOrdem, idPeca;
    private double quantidade;

    public OrdemPeca (int idOrdem, int idPeca, double quantidade){
        this.quantidade = quantidade;
        this.idPeca = idPeca;
        this.idOrdem = idOrdem;
    }

    public int getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;
    }

    public int getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(int idPeca) {
        this.idPeca = idPeca;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }
}
