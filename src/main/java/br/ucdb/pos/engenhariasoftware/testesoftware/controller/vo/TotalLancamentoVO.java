package br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo;

import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(of = {"tipo", "total"})
public class TotalLancamentoVO implements Serializable {

    @Getter
    public TipoLancamento tipo;

    @Getter
    public BigDecimal total;

    public TotalLancamentoVO(BigDecimal total, TipoLancamento tipo) {
        this.tipo = tipo;
        this.total = total;
    }
}
