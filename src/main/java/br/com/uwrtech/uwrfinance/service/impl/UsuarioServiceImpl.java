package br.com.uwrtech.uwrfinance.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.uwrtech.uwrfinance.exceptions.ErroAutenticacao;
import br.com.uwrtech.uwrfinance.exceptions.RegraNegocioException;
import br.com.uwrtech.uwrfinance.model.entity.Usuario;
import br.com.uwrtech.uwrfinance.model.repository.UsuarioRepository;
import br.com.uwrtech.uwrfinance.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarusuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		
		// seta data atual no banco
		usuario.setData_cadastro(LocalDate.now());
		
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if (existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
	}

	@Override
	public Optional<Usuario> obterporId(Long id) {
		return repository.findById(id);
	}

}
