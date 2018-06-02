package br.ucdb.pos.engenhariasoftware.testesoftware.service;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.LancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.ResultadoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.ucdb.pos.engenhariasoftware.testesoftware.util.Constantes.DD_MM_YYYY;

@Service
public class LancamentoService {

	@PersistenceContext
	private EntityManager entityManager;

	private static final int MAXIMO_LANCAMENTOS = 10;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Transactional
	public Lancamento salvar(Lancamento lancamento){

		return lancamentoRepository.save(lancamento);
	}

	@Transactional
	public void remover(long id){
		lancamentoRepository.delete(id);
	}

	public List<Lancamento> buscaTodos(){

		return entityManager.createNamedQuery("lancamento.maisRecentes", Lancamento.class).setMaxResults(MAXIMO_LANCAMENTOS).getResultList();
	}

	public List<Lancamento> busca(String itemBusca){
		return lancamentoRepository.busca("%"+itemBusca+"%");
	}

	public ResultadoVO buscaAjax(String itemBusca){
		return  getResultadoVO(busca(itemBusca));
	}

	protected ResultadoVO getResultadoVO(final List<Lancamento> resultado) {
		List<LancamentoVO> lancamentos = new ArrayList<>(resultado.size());
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		resultado.stream().forEach(r ->
				lancamentos.add(
						new LancamentoVO(r.getId(),
								r.getDescricao(),
								df.format(r.getValor()),
								dateFormat.format(r.getDataLancamento()),
								r.getTipoLancamento().getTipo())
				)
		);
		return new ResultadoVO(df.format(getTotalSaida(resultado)),
				df.format(getTotalEntrada(resultado)),
				lancamentos);
	}

	protected DecimalFormat getDecimalFormat(){
		return new DecimalFormat("#,###,##0.00");
	}

	protected SimpleDateFormat getDateFormat(){
		return new SimpleDateFormat(DD_MM_YYYY);
	}

	public Lancamento buscaPorId(Long id){
		return lancamentoRepository.findOne(id);
	}

	public BigDecimal getTotalEntrada(final List<Lancamento> lancamentos){
		return somaValoresPorTipo(lancamentos, TipoLancamento.ENTRADA);
	}

	public BigDecimal getTotalSaida(final List<Lancamento> lancamentos){
		return somaValoresPorTipo(lancamentos, TipoLancamento.SAIDA);
	}

	protected BigDecimal somaValoresPorTipo(List<Lancamento> lancamentos, TipoLancamento tipo){
		return lancamentos.stream()
				.filter(l -> l.getTipoLancamento() == tipo)
				.map(Lancamento::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<TotalLancamentoVO> getTotalPorPeriodo(Date dataInicial, Date dataFinal){
		return entityManager.createNamedQuery("lancamento.totalLancamentosPorPeriodo", TotalLancamentoVO.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();
	}
}
