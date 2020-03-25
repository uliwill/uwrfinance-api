package br.com.uwrtech.uwrfinance.service;

import java.util.List;

import br.com.uwrtech.uwrfinance.model.entity.Lancamento;
import br.com.uwrtech.uwrfinance.model.enums.StatusLancamento;

public interface LancamentoService {

	Lancamento salvar(Lancamento lancamento);

	Lancamento atualizar(Lancamento lancamento);

	void deletar(Lancamento lancamento);

	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
}
