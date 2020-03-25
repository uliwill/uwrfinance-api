package br.com.uwrtech.uwrfinance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uwrtech.uwrfinance.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
