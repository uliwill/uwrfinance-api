package br.com.uwrtech.uwrfinance.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import br.com.uwrtech.uwrfinance.model.enums.StatusLancamento;
import br.com.uwrtech.uwrfinance.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lan_id")
	private Long id;

	@Column(name = "lan_descricao")
	private String descricao;

	@Column(name = "lan_mes")
	private Integer mes;

	@Column(name = "lan_ano")
	private Integer ano;

	@Column(name = "lan_valor")
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "lan_id_usuario")
	private Usuario usuario;

	@Column(name = "lan_data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate data_cadastro;

	@Column(name = "lan_tipo")
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;

	@Column(name = "lan_status")
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento status;

}
