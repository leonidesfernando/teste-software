package br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode(of = {"id"})
public class LancamentoVO implements Serializable {

    public LancamentoVO(long id, String descricao, String valor, String dataLancamento, String tipoLancamento, String categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataLancamento = dataLancamento;
        this.tipoLancamento = tipoLancamento;
        this.categoria = categoria;
    }

    @Getter
    private long id;

    @Getter
    private String descricao;

    @Getter
    private String valor;

    @Getter
    private String dataLancamento;

    @Getter
    private String tipoLancamento;

    @Getter
    private String categoria;
}
