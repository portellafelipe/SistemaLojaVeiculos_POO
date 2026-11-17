import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioCrud implements Crud<Funcionario> {
    public void inserir(Funcionario f) throws Exception {
        String sql = "INSERT INTO funcionario (nome, matricula, cargo, telefone) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getMatricula());
            ps.setString(3, f.getCargo());
            ps.setString(4, f.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Funcionario> listar() throws Exception {
        List<Funcionario> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM funcionario ORDER BY id_funcionario");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Funcionario(rs.getInt("id_funcionario"), rs.getString("nome"),
                    rs.getString("telefone"), rs.getString("matricula"), rs.getString("cargo")));
            }
        }
        return lista;
    }

    public void atualizar(Funcionario f) throws Exception {
        String sql = "UPDATE funcionario SET nome=?, matricula=?, cargo=?, telefone=? WHERE id_funcionario=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getMatricula());
            ps.setString(3, f.getCargo());
            ps.setString(4, f.getTelefone());
            ps.setInt(5, f.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM funcionario WHERE id_funcionario=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
