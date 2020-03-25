package br.com.uwrtech.uwrfinance.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.uwrtech.uwrfinance.model.entity.Usuario;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	// deve verificar se o e-mail existe na base de dados
	public void verificaEmail() {
		// cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// ação
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificação
		Assertions.assertThat(result).isTrue();
	}

	@Test
	// deve retornar falso quando não houver usuário cadastrado com o e-mail
	public void verificaEmailCadastrado() {
		// cenário

		// ação
		boolean result = repository.existsByEmail("usuario@email.com");

		// verificação
		Assertions.assertThat(result).isFalse();
	}

	@Test
	// deve persistir um usuário na base de dados
	public void persistirUsuario() {
		// cenário
		Usuario usuario = criarUsuario();

		// ação
		Usuario usuarioSalvo = repository.save(usuario);

		// verificação
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}

	@Test
	// deve buscar um usuário por e-mail
	public void buscarUsuario() {
		// cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);

		// verificação
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isTrue();
	}

	@Test
	// deve retornar vazio ao buscar usuário por e-mail quando não existe na base
	public void usuarioInexistente() {

		// verificação
		Optional<Usuario> result = repository.findByEmail("usuario@email.com");

		Assertions.assertThat(result.isPresent()).isFalse();
	}

	public static Usuario criarUsuario() {
		return Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
	}

}
