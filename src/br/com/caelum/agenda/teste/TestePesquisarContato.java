package br.com.caelum.agenda.teste;

import br.com.caelum.agenda.dao.ContatoDao;
import br.com.caelum.agenda.modelo.Contato;

public class TestePesquisarContato {

	public static void main(String[] args) throws ClassNotFoundException {
		ContatoDao dao = new ContatoDao();
		Contato contato = dao.pesquisar(2);
		
		if (contato == null) 
			System.out.println("Pesquisa retornou Null");
		else
			System.out.println(contato);
		
	}

}
