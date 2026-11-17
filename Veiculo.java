import java.math.BigDecimal;

public class Veiculo implements Identificavel {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private BigDecimal preco;
    private String situacao;

    public Veiculo(int id, String marca, String modelo, int ano,
                   BigDecimal preco, String situacao) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.situacao = situacao;
    }

    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public BigDecimal getPreco() { return preco; }
    public String getSituacao() { return situacao; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    @Override
    public String getDescricao() {
        return marca + " " + modelo + " (" + ano + ")";
    }
}
