package br.com.caelum.agenda.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.caelum.agenda.ConnectionFactory;
import br.com.caelum.agenda.modelo.Contato;

public class ContatoDao {
	
	// a conexão com o banco de dados
	private Connection connection;
	 
	public ContatoDao() throws ClassNotFoundException {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Contato contato){
		String sql =	"INSERT INTO contatos " +
						"(nome, email, endereco, dataNascimento)" +
						" VALUES (?,?,?,?)";
		try
		{
			// prepared statement para inserção
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(sql);
			
			// seta os valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(
					contato.getDataNascimento().getTimeInMillis()
					));
			
			// Executa
			stmt.execute();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public List<Contato> getLista()
	{
		try
		{
			List<Contato> contatos = new ArrayList<Contato>();
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement("SELECT * FROM contatos");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				// Cria o objeto Contato
				Contato contato = new Contato();
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				
				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				contato.setDataNascimento(data);
				
				// adicionando o objeto à lista
				contatos.add(contato);
			}
			rs.close();
			stmt.close();
			return contatos;
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}	
	}
	
	public Contato pesquisar(int id)
	{
		
		try
		{
			Contato contato = new Contato();
			String sql = "SELECT * FROM contatos WHERE id=?";
			PreparedStatement stmt = (PreparedStatement) this.connection.
					prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setMaxRows(1);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
			{
				// setta os valores de contato
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				
				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				contato.setDataNascimento(data);
			}
			else{
				contato = null;
			}
			return contato;
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Contato contato) {
	     String sql = "UPDATE contatos SET nome=?, email=?,"+
	             "endereco=?, dataNascimento=? WHERE id=?";
	     
	     try {
	         PreparedStatement stmt = (PreparedStatement) connection.
	        		 prepareStatement(sql);
	         stmt.setString(1, contato.getNome());
	         stmt.setString(2, contato.getEmail());
	         stmt.setString(3, contato.getEndereco());
	         stmt.setDate(4, new Date(contato.getDataNascimento()
	                 .getTimeInMillis()));
	         stmt.setLong(5, contato.getId());
	         stmt.execute();
	         stmt.close();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
	
	public void remove(Contato contato) {
	     try {
	         PreparedStatement stmt = (PreparedStatement) connection.
	        		 prepareStatement("DELETE FROM contatos WHERE id=?");
	         stmt.setLong(1, contato.getId());
	         stmt.execute();
	         stmt.close();
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
}
