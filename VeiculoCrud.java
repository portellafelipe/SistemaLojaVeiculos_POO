import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class VeiculoCrud implements Crud<Veiculo> {
    public void inserir(Veiculo v) throws Exception {
        String sql = "INSERT INTO veiculo (marca, modelo, ano, preco, situacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getAno());
            ps.setBigDecimal(4, v.getPreco());
            ps.setString(5, v.getSituacao());
            ps.executeUpdate();
        }
    }

    public List<Veiculo> listar() throws Exception {
        List<Veiculo> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM veiculo ORDER BY id_veiculo");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Veiculo(rs.getInt("id_veiculo"), rs.getString("marca"),
                    rs.getString("modelo"), rs.getInt("ano"), rs.getBigDecimal("preco"),
                    rs.getString("situacao")));
            }
        }
        return lista;
    }

    public void atualizar(Veiculo v) throws Exception {
        String sql = "UPDATE veiculo SET marca=?, modelo=?, ano=?, preco=?, situacao=? WHERE id_veiculo=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getAno());
            ps.setBigDecimal(4, v.getPreco());
            ps.setString(5, v.getSituacao());
            ps.setInt(6, v.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(
                 "DELETE FROM veiculo WHERE id_veiculo=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
