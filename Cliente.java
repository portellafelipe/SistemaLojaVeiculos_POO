public class Cliente extends Pessoa {
    private String cpf;
    private String email;

    public Cliente(int id, String nome, String telefone, String cpf, String email) {
        super(id, nome, telefone);
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getTipoPessoa() { return "Cliente"; }

    @Override
    public String getDescricao() { return getNome() + " - " + cpf; }
}
