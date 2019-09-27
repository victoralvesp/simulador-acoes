package com.simuladordepreco.simular;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import feign.RequestLine;

/**
 * CorretorInteligenteProxy
 */
public interface CorretorInteligenteProxy {
    @RequestLine("POST /acoes/")
    Collection<Movimentacao> registrarAcao(ObservacaoDeAcao acao);
    
}
