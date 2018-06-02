package br.ucdb.pos.engenhariasoftware.testesoftware.repository;

import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("select sum(l.valor) from Lancamento  l where l.tipoLancamento = :tipo")
    BigDecimal somaValoresPorTipo(@Param("tipo")TipoLancamento tipoLancamento);

    @Query("select l from Lancamento l where (upper(l.descricao) like upper( :itemBusca)) " +
            " or (upper(l.tipoLancamento) like upper( :itemBusca)) " +
            " order by l.dataLancamento")
    List<Lancamento> busca(@Param("itemBusca")String itemBusca);

}
