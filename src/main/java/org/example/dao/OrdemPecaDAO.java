package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemPeca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdemPecaDAO {
    public void inserirOrdemPeca(OrdemPeca ordemPeca){
        String query = "INSERT INTO OrdemPeca(idOrdem, idPeca, quantidade) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ordemPeca.getIdOrdem());
            stmt.setInt(2, ordemPeca.getIdPeca());
            stmt.setDouble(3, ordemPeca.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Peça associado à Ordem de manutenção com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia(int idOrdem, int idPeca){
        String query = "SELECT idOrdem FROM OrdemPeca WHERE idOrdem = ? AND idPeca = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idPeca);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
