package br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(of = {"totalSaida", "totalEntrada"})
public class ResultadoVO implements Serializable {

    @Getter
    private String totalSaida;
    @Getter
    private String totalEntrada;
    @Getter
    private List<LancamentoVO> lancamentos;

    public ResultadoVO(String totalSaida, String totalEntrada, List<LancamentoVO> lancamentos) {
        this.totalSaida = totalSaida;
        this.totalEntrada = totalEntrada;
        this.lancamentos = lancamentos;
    }
}
