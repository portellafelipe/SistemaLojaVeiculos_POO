import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

public class VendaCrud implements Crud<Venda> {
    public void inserir(Venda v) throws Exception {
        String sql = "INSERT INTO venda (id_cliente, id_funcionario, id_veiculo, data_venda, valor) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sql);
                 PreparedStatement update = con.prepareStatement(
                     "UPDATE veiculo SET situacao='Vendido' WHERE id_veiculo=? AND situacao='Disponível'")) {
                update.setInt(1, v.getVeiculo().getId());
                if (update.executeUpdate() == 0) {
                    throw new SQLException("Veículo não está disponível.");
                }
                ps.setInt(1, v.getCliente().getId());
                ps.setInt(2, v.getFuncionario().getId());
                ps.setInt(3, v.getVeiculo().getId());
                ps.setDate(4, Date.valueOf(v.getData()));
                ps.setBigDecimal(5, v.getValor());
                ps.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Venda> listar() throws Exception {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT v.id_venda, v.data_venda, v.valor, " +
            "c.id_cliente, c.nome AS cliente_nome, c.telefone AS cliente_telefone, c.cpf, c.email, " +
            "f.id_funcionario, f.nome AS func_nome, f.telefone AS func_telefone, f.matricula, f.cargo, " +
            "car.id_veiculo, car.marca, car.modelo, car.ano, car.preco, car.situacao " +
            "FROM venda v JOIN cliente c ON v.id_cliente=c.id_cliente " +
            "JOIN funcionario f ON v.id_funcionario=f.id_funcionario " +
            "JOIN veiculo car ON v.id_veiculo=car.id_veiculo ORDER BY v.id_venda";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente(rs.getInt("id_cliente"), rs.getString("cliente_nome"),
                    rs.getString("cliente_telefone"), rs.getString("cpf"), rs.getString("email"));
                Funcionario f = new Funcionario(rs.getInt("id_funcionario"), rs.getString("func_nome"),
                    rs.getString("func_telefone"), rs.getString("matricula"), rs.getString("cargo"));
                Veiculo car = new Veiculo(rs.getInt("id_veiculo"), rs.getString("marca"),
                    rs.getString("modelo"), rs.getInt("ano"), rs.getBigDecimal("preco"),
                    rs.getString("situacao"));
                lista.add(new Venda(rs.getInt("id_venda"), c, f, car,
                    rs.getDate("data_venda").toLocalDate(), rs.getBigDecimal("valor")));
            }
        }
        return lista;
    }

    public void atualizar(Venda v) throws Exception {
        String sql = "UPDATE venda SET id_cliente=?, id_funcionario=?, id_veiculo=?, data_venda=?, valor=? WHERE id_venda=?";
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, v.getCliente().getId());
            ps.setInt(2, v.getFuncionario().getId());
            ps.setInt(3, v.getVeiculo().getId());
            ps.setDate(4, Date.valueOf(v.getData()));
            ps.setBigDecimal(5, v.getValor());
            ps.setInt(6, v.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws Exception {
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id_veiculo FROM venda WHERE id_venda=?");
                 PreparedStatement del = con.prepareStatement(
                    "DELETE FROM venda WHERE id_venda=?");
                 PreparedStatement upd = con.prepareStatement(
                    "UPDATE veiculo SET situacao='Disponível' WHERE id_veiculo=?")) {
                ps.setInt(1, id);
                int idVeiculo;
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) return;
                    idVeiculo = rs.getInt("id_veiculo");
                }
                del.setInt(1, id);
                del.executeUpdate();
                upd.setInt(1, idVeiculo);
                upd.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
    }
}
