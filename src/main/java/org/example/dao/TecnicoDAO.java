package org.example.dao;

import org.example.Conexao;
import org.example.model.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TecnicoDAO {
    public void inserirTecnico(Tecnico tecnico) {
        String query = "INSERT INTO Tecnico(nome, especialidade) VALUES (?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());
            stmt.executeUpdate();

            System.out.println("Técnico cadastrado com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia (Tecnico tecnico){
        String query = """
                    SELECT nome
                    FROM Tecnico
                    WHERE nome = ?
                    AND especialidade = ?
                """;

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, tecnico.getNome());
            stmt.setString(2, tecnico.getEspecialidade());

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
