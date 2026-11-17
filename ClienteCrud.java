import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteCrud implements Crud<Cliente> {
    public void inserir(Cliente c) throws Exception {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Cliente> listar() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id_cliente";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("id_cliente"), rs.getString("nome"),
                    rs.getString("telefone"), rs.getString("cpf"), rs.getString("email")));
            }
        }
        return lista;
    }

    public void atualizar(Cliente c) throws Exception {
        String sql = "UPDATE cliente SET nome=?, cpf=?, telefone=?, email=? WHERE id_cliente=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM cliente WHERE id_cliente=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
