package br.com.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.model.Usuario;
import br.com.springboot.repository.UsuarioRepository;


@RestController
public class GreetingsController {
	@Autowired
	private UsuarioRepository repository;
	
	
	
    @RequestMapping(value = "/olamundo/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @GetMapping(value = "listatodos")
	@ResponseBody /* Retorna os dados para o corpo da respota [JSON] */
	public ResponseEntity<List<Usuario>> listaUsuarios() {

		List<Usuario> usuarios = repository.findAll(); // executa a consula no banco de dados

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* Retorna a Lista em Json */
	}
    
    
    
    @PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) { // recebe os dados para salvar

		Usuario usuarioSalvo = repository.save(usuario);

		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long idUser) {
		repository.deleteById(idUser);

		return new ResponseEntity<String>("Usuário deletado com Sucesso", HttpStatus.OK);
	}

	@GetMapping(value = "buscarusuarioid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUsuarioPorID(@RequestParam(name = "iduser") Long iduser) {

		Usuario usuario = repository.findById(iduser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody Usuario usuario) {

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("ID não informadao para atualização", HttpStatus.OK);
		}

		Usuario usuarioAtualizado = repository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(usuarioAtualizado, HttpStatus.OK);
	}

	@GetMapping(value = "buscapornome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscaPeloNome(@RequestParam(name = "nome") String nome) {

		List<Usuario> usuarios = repository.buscarPorNome(nome.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}
	
	@GetMapping(value = "filtrousuario")
	@ResponseBody
	public ResponseEntity<List<Usuario>> filtrar(@RequestParam(name = "nome") String nome, @RequestParam(name = "idade") int idade) {

		List<Usuario> usuarios = repository.filtroUsuario(nome.trim().toUpperCase(), idade);

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);

	}
    
    
    

}
