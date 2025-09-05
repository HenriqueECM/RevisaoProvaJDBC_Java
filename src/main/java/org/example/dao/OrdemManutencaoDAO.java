package org.example.dao;

import org.example.Conexao;
import org.example.model.OrdemManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrdemManutencao> listarOrdemManutencaoPendentes (){
        String query = "SELECT id, idMaquina, idTecnico, dataSolicitacao, status FROM OrdemManutencao WHERE status = 'PENDENTE'";

        List<OrdemManutencao> ordemManutencaoList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                int idMaquina = rs.getInt("idMaquina");
                int idTecnico = rs.getInt("idTecnico");
                Date dataSolicitacao = rs.getDate("dataSolicitacao");
                String status = rs.getString("status");

                OrdemManutencao ordemManutencao = new OrdemManutencao(id, idMaquina, idTecnico, dataSolicitacao.toLocalDate(), status);
                ordemManutencaoList.add(ordemManutencao);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  ordemManutencaoList;
    }

    public void atualizarStatus (int id){
        String query = "UPDATE OrdemManutencao SET status = 'EXECUTADA' WHERE id = ?";

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Ordem de manutenção de ID: " + id + " está executada");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
