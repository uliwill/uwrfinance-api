package br.com.uwrtech.uwrfinance.service;

import java.util.Optional;

import br.com.uwrtech.uwrfinance.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarusuario(Usuario usuario);

	void validarEmail(String email);
	
	Optional<Usuario> obterporId(Long id);
}
