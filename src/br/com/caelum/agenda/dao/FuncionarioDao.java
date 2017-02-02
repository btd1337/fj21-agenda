package br.com.caelum.agenda.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.caelum.agenda.ConnectionFactory;
import br.com.caelum.agenda.modelo.Funcionario;

public class FuncionarioDao {
	
	// a conexão com o banco de dados
	private Connection connection;
	 
	public FuncionarioDao() throws ClassNotFoundException {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Funcionario funcionario){
		String sql =	"INSERT INTO funcionarios " +
						"(nome, usuario, senha)" +
						" VALUES (?,?,?)";
		try
		{
			// prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			// seta os valores
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getUsuario());
			stmt.setString(3, funcionario.getSenha());
			
			// Executa
			stmt.execute();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public List<Funcionario> getLista()
	{
		try
		{
			List<Funcionario> funcionarios = new ArrayList<Funcionario>();
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement("SELECT * FROM funcionarios");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				// Cria o objeto Contato
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getLong("id"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setUsuario(rs.getString("usuario"));
				funcionario.setSenha(rs.getString("senha"));
				
				// adicionando o objeto à lista
				funcionarios.add(funcionario);
			}
			rs.close();
			stmt.close();
			return funcionarios;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}	
	}
	
	public Funcionario pesquisar(int id)
	{
		
		try
		{
			Funcionario funcionario = new Funcionario();
			String sql = "SELECT * FROM funcionarios WHERE id=?";
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setMaxRows(1);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				// setta os valores de contato
				funcionario.setId(rs.getLong("id"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setUsuario(rs.getString("usuario"));
				funcionario.setSenha(rs.getString("senha"));
			}
			else{
				funcionario = null;
			}
			return funcionario;
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Funcionario funcionario) {
	     String sql = "UPDATE funcionarios SET nome=?, usuario=?,"+
	             "senha=? WHERE id=?";
	     
	     try {
	         PreparedStatement stmt = (PreparedStatement) connection.
	        		 prepareStatement(sql);
	         stmt.setString(1, funcionario.getNome());
	         stmt.setString(2, funcionario.getUsuario());
	         stmt.setString(3, funcionario.getSenha());
	         stmt.setLong(4, funcionario.getId());
	         stmt.execute();
	         stmt.close();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
	
	public void remove(Funcionario funcionario) {
	     try {
	         PreparedStatement stmt = (PreparedStatement) connection.
	        		 prepareStatement("DELETE FROM funcionarios WHERE id=?");
	         stmt.setLong(1, funcionario.getId());
	         stmt.execute();
	         stmt.close();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
}
