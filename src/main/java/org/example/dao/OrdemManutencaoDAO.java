package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemManutencao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdemManutencaoDAO {
    public void inserirOrdemM(OrdemManutencao ordemM){
        String query = "INSERT INTO OrdemManutencao (idMaquina, idTecnico, dataSolicitacao, status) VALUES (?,?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, ordemM.getIdMaquina());
            stmt.setInt(2, ordemM.getIdTecnico());
            stmt.setDate(3, Date.valueOf(ordemM.getDataSolicitacao()));
            stmt.setString(4, ordemM.getStatus());
            stmt.executeUpdate();

            System.out.println("Ordem de manutenção criado com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
