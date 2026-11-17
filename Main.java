import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        try {
            Conexao.conectar().close();
            SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Não foi possível conectar ao MySQL.\nConfira o banco, usuário, senha e driver.\n\n" + e.getMessage(),
                "Erro de conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
}
