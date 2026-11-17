import java.util.List;

public interface Crud<T> {
    void inserir(T objeto) throws Exception;
    List<T> listar() throws Exception;
    void atualizar(T objeto) throws Exception;
    void excluir(int id) throws Exception;
}
