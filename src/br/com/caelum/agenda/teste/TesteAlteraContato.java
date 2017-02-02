package br.com.caelum.agenda.teste;

import br.com.caelum.agenda.dao.ContatoDao;
import br.com.caelum.agenda.modelo.Contato;

public class TesteAlteraContato {

	public static void main(String[] args) throws ClassNotFoundException {
		
		ContatoDao dao = new ContatoDao();
		Contato contato = dao.pesquisar(2);
		
		String nome = contato.getNome();
		System.out.println("Encontrou o Contato:\n"+contato);
		contato.setNome(nome+" 2");
		dao.altera(contato);
		System.out.println("============================");
		System.out.println("Alterou o Contato:\n"+contato);

	}

}
