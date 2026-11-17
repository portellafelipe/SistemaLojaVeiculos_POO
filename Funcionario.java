public class Funcionario extends Pessoa {
    private String matricula;
    private String cargo;

    public Funcionario(int id, String nome, String telefone, String matricula, String cargo) {
        super(id, nome, telefone);
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public String getMatricula() { return matricula; }
    public String getCargo() { return cargo; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    @Override
    public String getTipoPessoa() { return "Funcionário"; }

    @Override
    public String getDescricao() { return getNome() + " - " + matricula; }
}
