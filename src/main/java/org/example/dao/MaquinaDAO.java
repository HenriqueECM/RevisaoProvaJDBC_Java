package org.example.dao;

import org.example.Conexao;
import org.example.model.Maquina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaquinaDAO {
    public void inserirMaquina(Maquina maquina){
        String query = "INSERT INTO Maquina (nome, setor, status) VALUES (?,?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());
            stmt.setString(3, maquina.getStatus());

            stmt.executeUpdate();

            System.out.println("Máquina cadastrada com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean buscarExistencia(Maquina maquina){
        String query = "SELECT nome FROM Maquina WHERE nome = ? AND setor = ?";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, maquina.getNome());
            stmt.setString(2, maquina.getSetor());

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
