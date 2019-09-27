package com.simuladordepreco.simular;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movimentacao {
    String tipo;
    LocalDateTime data;
    BigDecimal quantidadeDeAcoes;
    BigDecimal valorMovimentado;
    long idConta;
    String empresa;
}