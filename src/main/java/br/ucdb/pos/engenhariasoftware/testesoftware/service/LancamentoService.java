package br.ucdb.pos.engenhariasoftware.testesoftware.service;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.LancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.ResultadoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoCategoriaVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.TotalLancamentoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

	public List<Lancamento> buscaTodos(int pagina){

		return entityManager.createNamedQuery("lancamento.maisRecentes", Lancamento.class)
				.setFirstResult(((pagina-1) * MAXIMO_LANCAMENTOS))
				.setMaxResults(MAXIMO_LANCAMENTOS).getResultList();
	}

	public int tamanhoPagina(){
		return MAXIMO_LANCAMENTOS;
	}

	public int calculaNumeroPaginas(int totalRegistros){
		int numero = (totalRegistros / tamanhoPagina());
		if((totalRegistros > 0) && ( (totalRegistros % tamanhoPagina()) > 0 )){
			numero++;
		}
		return numero;
	}

	/**
	 * Foi criado apenas para construir a paginacao com o Thymeleaf
	 * @param totalRegistros
	 * @return
	 */
	public List<Integer> getPaginas(int totalRegistros){

		int numero = calculaNumeroPaginas(totalRegistros);
		List<Integer> paginas = new ArrayList<>(numero);

		for(int i = 1; i <= numero; i++){
			paginas.add(i);
		}
		return paginas;
	}

	public long conta(String itemBusca){

		String sql = "select count(*) from Lancamento l ";
		if(StringUtils.hasText(itemBusca)){
			String where = " where (upper(l.descricao) like upper( :itemBusca)) " +
					"  or (upper(l.tipoLancamento) like upper( :itemBusca)) " +
					" or (upper(l.categoria) like upper( :itemBusca))";
			sql += where.replaceAll(":itemBusca", "'%"+itemBusca+"%'");
		}
		TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
		return query.getSingleResult();
	}

	public List<Lancamento> busca(String itemBusca){
		return entityManager.createNamedQuery("lancamento.busca", Lancamento.class)
				.setParameter("itemBusca", "%"+itemBusca+"%")
				.setMaxResults(MAXIMO_LANCAMENTOS)
				.getResultList();
	}

	public ResultadoVO buscaAjax(String itemBusca){
		final List<Lancamento> resultado = busca(itemBusca);
		int tamanhoPagina = resultado.size();
		long totalRegistros = conta(itemBusca);
		return  getResultadoVO(resultado, tamanhoPagina, totalRegistros);
	}

	protected ResultadoVO getResultadoVO(final List<Lancamento> resultado, int tamanhoPagina, long totalRegistros) {
		List<LancamentoVO> lancamentos = new ArrayList<>(resultado.size());
		DecimalFormat df = new DecimalFormat("#,###,##0.00");
		DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		resultado.stream().forEach(r -> {
			String categoria = "";
			if(r.getCategoria() != null){
				categoria = r.getCategoria().getNome();
			}
			lancamentos.add(
					new LancamentoVO(r.getId(),
							r.getDescricao(),
							df.format(r.getValor()),
							dateFormat.format(r.getDataLancamento()),
							r.getTipoLancamento().getTipo(),
							categoria)

					);
				}
		);

		return new ResultadoVO(df.format(getTotalSaida(resultado)),
				df.format(getTotalEntrada(resultado)),
				lancamentos, tamanhoPagina, totalRegistros);
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

	public List<TotalLancamentoCategoriaVO> getTotalPorPeriodoPorCategoria(Date dataInicial, Date dataFinal){
		return entityManager.createNamedQuery("lancamento.totalLancamentosPorPeriodoPorCategoria", TotalLancamentoCategoriaVO.class)
				.setParameter("dataInicial", dataInicial)
				.setParameter("dataFinal", dataFinal)
				.getResultList();
	}
}
