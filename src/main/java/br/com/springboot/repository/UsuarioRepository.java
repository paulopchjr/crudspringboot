package br.com.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.springboot.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
	
	
	@Query(value = "SELECT u FROM Usuario u where upper(trim(u.nome)) like %?1%")
	List<Usuario> buscarPorNome(String nome);
	
	@Query(value = "SELECT u FROM Usuario u where upper(trim(u.nome)) like %?1% and u.idade = ?2")
	List<Usuario> filtroUsuario(String nome, int idade);

}
