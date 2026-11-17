import java.math.BigDecimal;
import java.time.LocalDate;

public class Venda implements Identificavel {
    private int id;
    private Cliente cliente;
    private Funcionario funcionario;
    private Veiculo veiculo;
    private LocalDate data;
    private BigDecimal valor;

    public Venda(int id, Cliente cliente, Funcionario funcionario,
                 Veiculo veiculo, LocalDate data, BigDecimal valor) {
        this.id = id;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.veiculo = veiculo;
        this.data = data;
        this.valor = valor;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Funcionario getFuncionario() { return funcionario; }
    public Veiculo getVeiculo() { return veiculo; }
    public LocalDate getData() { return data; }
    public BigDecimal getValor() { return valor; }

    @Override
    public String getDescricao() {
        return "Venda " + id + " - " + veiculo.getDescricao();
    }
}
