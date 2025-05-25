package br.com.esig.dao;

import br.com.esig.model.Tarefa;
import br.com.esig.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void inserir(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefa (titulo, descricao, responsavel, prioridade, deadline, situacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getResponsavel());
            stmt.setString(4, tarefa.getPrioridade());
            stmt.setString(5, tarefa.getDeadline());  // Agora String
            stmt.setString(6, tarefa.getSituacao());

            stmt.executeUpdate();
        }
    }

    public List<Tarefa> listarPorSituacao(String situacao) throws SQLException {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefa WHERE situacao = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, situacao);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tarefa t = new Tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                t.setResponsavel(rs.getString("responsavel"));
                t.setPrioridade(rs.getString("prioridade"));
                t.setDeadline(rs.getString("deadline")); // Pega direto como String
                t.setSituacao(rs.getString("situacao"));
                lista.add(t);
            }
        }
        return lista;
    }

    public void atualizar(Tarefa tarefa) throws SQLException {
        String sql = "UPDATE tarefa SET titulo=?, descricao=?, responsavel=?, prioridade=?, deadline=?, situacao=? WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getDescricao());
            stmt.setString(3, tarefa.getResponsavel());
            stmt.setString(4, tarefa.getPrioridade());
            stmt.setString(5, tarefa.getDeadline());  
            stmt.setString(6, tarefa.getSituacao());
            stmt.setInt(7, tarefa.getId());

            stmt.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM tarefa WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void concluir(int id) throws SQLException {
        String sql = "UPDATE tarefa SET situacao='Concluída' WHERE id=?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
