package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.interceptador.LogInterceptador;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER) //opcional
// @TransactionManagement(TransactionManagementType.CONTAINER) //outra forma na mão Beans user transaction
//@Interceptors(LogInterceptador.class)
public class AutorDao {

	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct //callback
	void aposCriacao() {
		System.out.println("AutorDAO foi criado");
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY) //opcional
	public void salva(Autor autor) {
		System.out.println("Salvando autor " + autor.getNome());
		/*try {
			Thread.sleep(20000); //20seg
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		manager.persist(autor);
		System.out.println("Salvou autor " + autor.getNome());
		
		//throw new RuntimeException("Serviço externo deu erro");
	}
	
	public List<Autor> todosAutores() {
		return manager.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.manager.find(Autor.class, autorId);
		return autor;
	}
	
}
