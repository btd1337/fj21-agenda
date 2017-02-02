package br.com.caelum.agenda.teste;

import br.com.caelum.agenda.dao.ContatoDao;
import br.com.caelum.agenda.modelo.Contato;

public class TesteRemoveContato {

	public static void main(String[] args) throws ClassNotFoundException {
		ContatoDao dao = new ContatoDao();
		Contato contato = dao.pesquisar(3); // Vai remover o ID 3
		
		System.out.println("Encontrou o Contato:\n"+contato);
		dao.remove( contato );
		System.out.println("============================");
		System.out.println("Removeu o Contato:\n"+
							contato.getNome()+
							"("+contato.getId()+")");

	}

}
