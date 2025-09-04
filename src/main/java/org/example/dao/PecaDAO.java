package org.example.dao;

import org.example.Conexao;
import org.example.model.Peca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {
    public void inserirPeca(Peca peca){
        String query = "INSERT INTO Peca (nome, estoque) VALUES(?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, peca.getNome());
            stmt.setDouble(2, peca.getEstoque());
            stmt.executeUpdate();

            System.out.println("Peça cadastrada com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia(Peca peca){
        String query = """
                    SELECT nome
                    FROM Peca
                    WHERE nome = ?
                """;

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, peca.getNome());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Peca> listarTodasPecas (){
        String query = "SELECT id, nome, estoque FROM Peca";

        List<Peca> pecaList = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                Peca peca = new Peca(id, nome, estoque);
                pecaList.add(peca);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pecaList;
    }
}
