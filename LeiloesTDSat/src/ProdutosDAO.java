import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try (Connection conn = new conectaDAO().connectDB(); // Usando try-with-resources
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        // Definindo os parâmetros da consulta
        stmt.setString(1, produto.getNome());   // Nome do produto
        stmt.setInt(2, produto.getValor());     // Valor do produto
        stmt.setString(3, produto.getStatus()); // Status do produto
        
        // Executando a inserção no banco
        stmt.executeUpdate();
        
    } catch (SQLException e) {
        // Exibe mensagem de erro se algo der errado
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
    }
        
}
    
    public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    try (Connection conn = new conectaDAO().connectDB(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);  // Definindo o ID do produto para atualização

        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado ou já vendido.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";  // Busca apenas produtos vendidos
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    try (Connection conn = new conectaDAO().connectDB();
         PreparedStatement prep = conn.prepareStatement(sql);
         ResultSet resultset = prep.executeQuery()) {

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);  // Adiciona à lista
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
    }

    return listagem;
}
    
    public ArrayList<ProdutosDTO> listarProdutos() {
    String sql = "SELECT * FROM produtos"; // Consulta para buscar todos os produtos
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    try (Connection conn = new conectaDAO().connectDB();  // Usando try-with-resources
         PreparedStatement prep = conn.prepareStatement(sql);
         ResultSet resultset = prep.executeQuery()) {
        
        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));  // Mantendo Integer para id
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));  // Mantendo Integer para valor
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);  // Adiciona o produto à lista
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
    }

    return listagem;
}
       
}