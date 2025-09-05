package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemPeca;
import org.example.model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemPecaDAO {
    public void inserirOrdemPeca(OrdemPeca ordemPeca) {
        String query = "INSERT INTO OrdemPeca(idOrdem, idPeca, quantidade) VALUES (?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ordemPeca.getIdOrdem());
            stmt.setInt(2, ordemPeca.getIdPeca());
            stmt.setDouble(3, ordemPeca.getQuantidade());
            stmt.executeUpdate();

            System.out.println("Peça associado à Ordem de manutenção com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia(int idOrdem, int idPeca) {
        String query = "SELECT idOrdem FROM OrdemPeca WHERE idOrdem = ? AND idPeca = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idPeca);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrdemPeca> buscaOrdemPecaPorId(int idOrdem) {

        String query = "SELECT idOrdem, idPeca, quantidade FROM OrdemPeca WHERE idOrdem = ?";

        List<OrdemPeca> ordemPecaList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idOrdem);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int idOrdemNew = rs.getInt("idOrdem");
                int idOrdemPeca = rs.getInt("idPeca");
                double quantidade = rs.getDouble("quantidade");

                OrdemPeca ordemPeca = new OrdemPeca(idOrdemNew, idOrdemPeca, quantidade);
                ordemPecaList.add(ordemPeca);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ordemPecaList;
    }
}
