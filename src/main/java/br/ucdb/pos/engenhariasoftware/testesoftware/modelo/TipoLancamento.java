package br.ucdb.pos.engenhariasoftware.testesoftware.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum TipoLancamento {

	@JsonProperty("Entrada")
	ENTRADA("Entrada"),
	@JsonProperty("Saída")
	SAIDA("Saída");
	
	private TipoLancamento(String tipo){
		this.tipo = tipo;
	}

	@Getter
	private String tipo;
}
