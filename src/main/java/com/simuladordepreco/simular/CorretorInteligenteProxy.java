package com.simuladordepreco.simular;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import feign.Headers;
import feign.RequestLine;

/**
 * CorretorInteligenteProxy
 */
public interface CorretorInteligenteProxy {
    @RequestLine("POST /acoes/")
    @Headers({
        "Accept: application/json",
        "Content-Type: application/json"
    })
    Movimentacoes registrarAcao(ObservacaoDeAcao obs);
    class ObservacaoDeAcao {
        public String empresa;
        public String precoCompra;
        public String precoVenda;
    }

    class Movimentacoes {
        List<Movimentacao> movimentacoes;
    }

    public static ObservacaoDeAcao criarObs(BigDecimal precoCompra, BigDecimal precoVenda, String empresa) {
		var observacaoDeAcao = new ObservacaoDeAcao();
		observacaoDeAcao.precoCompra = precoCompra.setScale(2, RoundingMode.HALF_EVEN).toString();
		observacaoDeAcao.precoVenda = precoVenda.setScale(2, RoundingMode.HALF_EVEN).toString();
		observacaoDeAcao.empresa = empresa;
		return observacaoDeAcao;
	}
}
