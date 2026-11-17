import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private final ClienteCrud clientes = new ClienteCrud();
    private final FuncionarioCrud funcionarios = new FuncionarioCrud();
    private final VeiculoCrud veiculos = new VeiculoCrud();
    private final VendaCrud vendas = new VendaCrud();

    private final Color AZUL = new Color(35, 75, 120);
    private final Color FUNDO = new Color(240, 243, 247);

    public TelaPrincipal() {
        setTitle("AutoCar - Sistema da Concessionária");
        setSize(1050, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Clientes", criarPainelClientes());
        abas.addTab("Funcionários", criarPainelFuncionarios());
        abas.addTab("Veículos", criarPainelVeiculos());
        abas.addTab("Vendas", criarPainelVendas());
        add(abas);
    }

    private JPanel painelBase() {
        JPanel painel = new JPanel(new BorderLayout(10, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painel.setBackground(FUNDO);
        return painel;
    }

    private JTable criarTabela(String... colunas) {
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setGridColor(new Color(220, 225, 232));
        tabela.setSelectionBackground(new Color(210, 225, 242));
        tabela.setSelectionForeground(Color.BLACK);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(AZUL);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);
        return tabela;
    }

    private JPanel painelCampos(String[] titulos, JTextField[] campos) {
        JPanel painel = new JPanel(new GridLayout(2, titulos.length, 8, 6));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo);
            label.setFont(new Font("Arial", Font.BOLD, 13));
            painel.add(label);
        }
        for (JTextField campo : campos) {
            campo.setFont(new Font("Arial", Font.PLAIN, 13));
            painel.add(campo);
        }
        return painel;
    }

    private JButton botao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setFocusPainted(false);
        return botao;
    }

    private JPanel painelBotoes(JButton... botoes) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        painel.setBackground(FUNDO);
        for (JButton botao : botoes) painel.add(botao);
        return painel;
    }

    private void adicionarTabela(JPanel painel, JTable tabela) {
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 220)));
        painel.add(rolagem, BorderLayout.CENTER);
    }

    private JPanel criarPainelClientes() {
        JPanel painel = painelBase();
        JTextField id = new JTextField(), nome = new JTextField(), cpf = new JTextField();
        JTextField telefone = new JTextField(), email = new JTextField();
        id.setEditable(false);
        JTextField[] campos = {id, nome, cpf, telefone, email};
        painel.add(painelCampos(new String[]{"ID", "Nome", "CPF", "Telefone", "E-mail"}, campos), BorderLayout.NORTH);

        JTable tabela = criarTabela("ID", "Nome", "CPF", "Telefone", "E-mail");
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        List<Cliente> registros = new ArrayList<>();

        JButton cadastrar = botao("Cadastrar"), atualizar = botao("Atualizar");
        JButton excluir = botao("Excluir"), limpar = botao("Limpar"), recarregar = botao("Recarregar");

        Runnable carregar = () -> {
            modelo.setRowCount(0);
            registros.clear();
            try {
                registros.addAll(clientes.listar());
                for (Cliente c : registros) {
                    modelo.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail()});
                }
            } catch (Exception e) { erro(e); }
        };

        cadastrar.addActionListener(e -> executar(() ->
                clientes.inserir(new Cliente(0, nome.getText(), telefone.getText(), cpf.getText(), email.getText())), carregar));
        atualizar.addActionListener(e -> executar(() ->
                clientes.atualizar(new Cliente(Integer.parseInt(id.getText()), nome.getText(), telefone.getText(), cpf.getText(), email.getText())), carregar));
        excluir.addActionListener(e -> executar(() -> clientes.excluir(Integer.parseInt(id.getText())), carregar));
        limpar.addActionListener(e -> limparCampos(campos));
        recarregar.addActionListener(e -> carregar.run());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() >= 0) {
                Cliente c = registros.get(tabela.getSelectedRow());
                id.setText(String.valueOf(c.getId()));
                nome.setText(c.getNome());
                cpf.setText(c.getCpf());
                telefone.setText(c.getTelefone());
                email.setText(c.getEmail());
            }
        });

        painel.add(painelBotoes(cadastrar, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);
        adicionarTabela(painel, tabela);
        carregar.run();
        return painel;
    }

    private JPanel criarPainelFuncionarios() {
        JPanel painel = painelBase();
        JTextField id = new JTextField(), nome = new JTextField(), matricula = new JTextField();
        JTextField cargo = new JTextField(), telefone = new JTextField();
        id.setEditable(false);
        JTextField[] campos = {id, nome, matricula, cargo, telefone};
        painel.add(painelCampos(new String[]{"ID", "Nome", "Matrícula", "Cargo", "Telefone"}, campos), BorderLayout.NORTH);

        JTable tabela = criarTabela("ID", "Nome", "Matrícula", "Cargo", "Telefone");
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        List<Funcionario> registros = new ArrayList<>();
        JButton cadastrar = botao("Cadastrar"), atualizar = botao("Atualizar");
        JButton excluir = botao("Excluir"), limpar = botao("Limpar"), recarregar = botao("Recarregar");

        Runnable carregar = () -> {
            modelo.setRowCount(0);
            registros.clear();
            try {
                registros.addAll(funcionarios.listar());
                for (Funcionario f : registros) {
                    modelo.addRow(new Object[]{f.getId(), f.getNome(), f.getMatricula(), f.getCargo(), f.getTelefone()});
                }
            } catch (Exception e) { erro(e); }
        };

        cadastrar.addActionListener(e -> executar(() ->
                funcionarios.inserir(new Funcionario(0, nome.getText(), telefone.getText(), matricula.getText(), cargo.getText())), carregar));
        atualizar.addActionListener(e -> executar(() ->
                funcionarios.atualizar(new Funcionario(Integer.parseInt(id.getText()), nome.getText(), telefone.getText(), matricula.getText(), cargo.getText())), carregar));
        excluir.addActionListener(e -> executar(() -> funcionarios.excluir(Integer.parseInt(id.getText())), carregar));
        limpar.addActionListener(e -> limparCampos(campos));
        recarregar.addActionListener(e -> carregar.run());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() >= 0) {
                Funcionario f = registros.get(tabela.getSelectedRow());
                id.setText(String.valueOf(f.getId()));
                nome.setText(f.getNome());
                matricula.setText(f.getMatricula());
                cargo.setText(f.getCargo());
                telefone.setText(f.getTelefone());
            }
        });

        painel.add(painelBotoes(cadastrar, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);
        adicionarTabela(painel, tabela);
        carregar.run();
        return painel;
    }

    private JPanel criarPainelVeiculos() {
        JPanel painel = painelBase();
        JTextField id = new JTextField(), marca = new JTextField(), modeloVeiculo = new JTextField();
        JTextField ano = new JTextField(), preco = new JTextField(), situacao = new JTextField("Disponível");
        id.setEditable(false);
        JTextField[] campos = {id, marca, modeloVeiculo, ano, preco, situacao};
        painel.add(painelCampos(new String[]{"ID", "Marca", "Modelo", "Ano", "Preço", "Situação"}, campos), BorderLayout.NORTH);

        JTable tabela = criarTabela("ID", "Marca", "Modelo", "Ano", "Preço", "Situação");
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        List<Veiculo> registros = new ArrayList<>();
        JButton cadastrar = botao("Cadastrar"), atualizar = botao("Atualizar");
        JButton excluir = botao("Excluir"), limpar = botao("Limpar"), recarregar = botao("Recarregar");

        Runnable carregar = () -> {
            modelo.setRowCount(0);
            registros.clear();
            try {
                registros.addAll(veiculos.listar());
                for (Veiculo v : registros) {
                    modelo.addRow(new Object[]{v.getId(), v.getMarca(), v.getModelo(), v.getAno(), v.getPreco(), v.getSituacao()});
                }
            } catch (Exception e) { erro(e); }
        };

        cadastrar.addActionListener(e -> executar(() ->
                veiculos.inserir(new Veiculo(0, marca.getText(), modeloVeiculo.getText(), Integer.parseInt(ano.getText()),
                        new BigDecimal(preco.getText().replace(",", ".")), situacao.getText())), carregar));
        atualizar.addActionListener(e -> executar(() ->
                veiculos.atualizar(new Veiculo(Integer.parseInt(id.getText()), marca.getText(), modeloVeiculo.getText(), Integer.parseInt(ano.getText()),
                        new BigDecimal(preco.getText().replace(",", ".")), situacao.getText())), carregar));
        excluir.addActionListener(e -> executar(() -> veiculos.excluir(Integer.parseInt(id.getText())), carregar));
        limpar.addActionListener(e -> { limparCampos(campos); situacao.setText("Disponível"); });
        recarregar.addActionListener(e -> carregar.run());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() >= 0) {
                Veiculo v = registros.get(tabela.getSelectedRow());
                id.setText(String.valueOf(v.getId()));
                marca.setText(v.getMarca());
                modeloVeiculo.setText(v.getModelo());
                ano.setText(String.valueOf(v.getAno()));
                preco.setText(v.getPreco().toString());
                situacao.setText(v.getSituacao());
            }
        });

        painel.add(painelBotoes(cadastrar, atualizar, excluir, limpar, recarregar), BorderLayout.SOUTH);
        adicionarTabela(painel, tabela);
        carregar.run();
        return painel;
    }

    private JPanel criarPainelVendas() {
        JPanel painel = painelBase();
        JTextField clienteId = new JTextField(), funcionarioId = new JTextField();
        JTextField veiculoId = new JTextField(), valor = new JTextField();
        JTextField[] campos = {clienteId, funcionarioId, veiculoId, valor};
        painel.add(painelCampos(new String[]{"ID do cliente", "ID do funcionário", "ID do veículo", "Valor da venda"}, campos), BorderLayout.NORTH);

        JTable tabela = criarTabela("ID da venda", "Cliente", "Veículo", "Data", "Valor");
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        JButton registrar = botao("Registrar venda"), recarregar = botao("Recarregar vendas");

        Runnable carregar = () -> {
            modelo.setRowCount(0);
            try {
                for (Venda v : vendas.listar()) {
                    modelo.addRow(new Object[]{v.getId(), v.getCliente().getNome(), v.getVeiculo().getDescricao(), v.getData(), v.getValor()});
                }
            } catch (Exception e) { erro(e); }
        };

        registrar.addActionListener(e -> executar(() -> {
            int idCliente = Integer.parseInt(clienteId.getText());
            int idFuncionario = Integer.parseInt(funcionarioId.getText());
            int idVeiculo = Integer.parseInt(veiculoId.getText());
            Cliente cliente = buscarCliente(idCliente);
            Funcionario funcionario = buscarFuncionario(idFuncionario);
            Veiculo veiculo = buscarVeiculo(idVeiculo);

            if (!"Disponível".equalsIgnoreCase(veiculo.getSituacao())) {
                throw new Exception("Veículo não está disponível.");
            }

            vendas.inserir(new Venda(0, cliente, funcionario, veiculo, LocalDate.now(),
                    new BigDecimal(valor.getText().replace(",", "."))));
        }, carregar));

        recarregar.addActionListener(e -> carregar.run());
        painel.add(painelBotoes(registrar, recarregar), BorderLayout.SOUTH);
        adicionarTabela(painel, tabela);
        carregar.run();
        return painel;
    }

    private Cliente buscarCliente(int id) throws Exception {
        for (Cliente c : clientes.listar()) if (c.getId() == id) return c;
        throw new Exception("Cliente não encontrado.");
    }

    private Funcionario buscarFuncionario(int id) throws Exception {
        for (Funcionario f : funcionarios.listar()) if (f.getId() == id) return f;
        throw new Exception("Funcionário não encontrado.");
    }

    private Veiculo buscarVeiculo(int id) throws Exception {
        for (Veiculo v : veiculos.listar()) if (v.getId() == id) return v;
        throw new Exception("Veículo não encontrado.");
    }

    private void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) campo.setText("");
    }

    private void executar(Acao acao, Runnable depois) {
        try {
            acao.executar();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
            depois.run();
        } catch (Exception e) { erro(e); }
    }

    private void erro(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private interface Acao {
        void executar() throws Exception;
    }
}
