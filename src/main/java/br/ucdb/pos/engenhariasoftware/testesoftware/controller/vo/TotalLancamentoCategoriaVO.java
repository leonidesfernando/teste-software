package br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo;

import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(of = {"tipo", "total", "categoria"})
public class TotalLancamentoCategoriaVO implements Serializable {

    @Getter
    public TipoLancamento tipo;

    @Getter
    public BigDecimal total;

    @Getter
    public Categoria categoria;

    public TotalLancamentoCategoriaVO(BigDecimal total, TipoLancamento tipo, Categoria categoria) {
        this.tipo = tipo;
        this.total = total;
        this.categoria = categoria;

        if(isSaida()){
            this.total = total.negate();
        }
    }

    public boolean isSaida(){
        return tipo == TipoLancamento.SAIDA;
    }
}
