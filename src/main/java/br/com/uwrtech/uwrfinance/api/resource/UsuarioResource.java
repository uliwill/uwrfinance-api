package br.com.uwrtech.uwrfinance.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uwrtech.uwrfinance.api.dto.UsuarioDTO;
import br.com.uwrtech.uwrfinance.exceptions.ErroAutenticacao;
import br.com.uwrtech.uwrfinance.exceptions.RegraNegocioException;
import br.com.uwrtech.uwrfinance.model.entity.Usuario;
import br.com.uwrtech.uwrfinance.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {

	private UsuarioService service;
	
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		} catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
		
		try {
			Usuario usuarioSalvo = service.salvarusuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
}
