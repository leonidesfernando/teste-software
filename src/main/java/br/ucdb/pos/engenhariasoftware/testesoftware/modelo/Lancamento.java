package br.ucdb.pos.engenhariasoftware.testesoftware.modelo;

import br.ucdb.pos.engenhariasoftware.testesoftware.converter.MoneyDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.DD_MM_YYYY;

@NamedQueries(value = {
		@NamedQuery(name = "lancamento.maisRecentes", query = "select l from Lancamento l order by l.dataLancamento"),
		@NamedQuery(name = "lancamento.totalLancamentosPorPeriodo",
				query = "select new br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoVO(sum(l.valor), l.tipoLancamento) " +
						" from Lancamento l where l.dataLancamento between :dataInicial and :dataFinal group by l.tipoLancamento"),
		@NamedQuery(name = "lancamento.totalLancamentosPorPeriodoPorCategoria",
				query = "select new br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoCategoriaVO(sum(l.valor), l.tipoLancamento, l.categoria) " +
						" from Lancamento l where l.dataLancamento between :dataInicial and :dataFinal group by l.tipoLancamento, l.categoria order by l.tipoLancamento"),
		@NamedQuery(name = "lancamento.busca", query = "select l from Lancamento l where (upper(l.descricao) like upper( :itemBusca)) " +
				"  or (upper(l.tipoLancamento) like upper( :itemBusca)) or (upper(l.categoria) like upper( :itemBusca))" +
				"  order by l.dataLancamento ")
})

@Entity
@EqualsAndHashCode(of = {"id"})
public class Lancamento {


	@Id
	@GeneratedValue
	@Getter @Setter
	private long id;

	@NotBlank(message = "A descrição deve ser informada")
	@Getter @Setter
    private String descricao;

	@JsonDeserialize(using = MoneyDeserialize.class)
	@NotNull(message = "O valor deve ser informado")
	@Min(message = "O valor deve ser maior que zero", value = 0)
	@Getter @Setter
    private BigDecimal valor;

	@JsonFormat(pattern = DD_MM_YYYY)
	@NotNull(message = "A data deve ser informada")
	@Getter @Setter
    private Date dataLancamento;

	@Getter @Setter
	@Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento = TipoLancamento.SAIDA;

	@Getter @Setter
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
}