package com.simuladordepreco.simular;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import feign.gson.GsonDecoder;
import feign.Feign;

public class SimularApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimularApp.class, args);
	}

	public void run(String... args) {
		var urlCorretorInteligente = extrairUrlOuPadrao(args);
		var iteracoes = 100;

		var proxy = Feign.builder()
                         .decoder(new GsonDecoder())
                         .target(CorretorInteligenteProxy.class, urlCorretorInteligente);

		imprimirLinhaComLabels();
		for (int i = 0; i < iteracoes; i++) {
			var precoCompra = gerarAleatorio();
			var precoVenda = gerarAleatorio();
			var empresa = "Intel";

			var observacao = criarObs(precoCompra, precoVenda, empresa);
			var movimentacoes = proxy.registrarAcao(observacao);
			for (Movimentacao mov : movimentacoes) {
				imprimirEmTela(mov);
			}
			
			imprimirLinhasDeEspera();
		}
	}

	private void imprimirLinhasDeEspera() {
		System.out.println("-----------------------------------------");
		System.out.println("-----------------------------------------");
	}

	private void imprimirLinhaComLabels() {
		System.out.println(" TIPO  EMPRESA   QTDE   VLR  CONTA  DATA");
	}

	private void imprimirEmTela(Movimentacao mov) {
		var tipo = mov.tipo;
		var empresa = mov.empresa;
		var qtde = mov.quantidadeDeAcoes.toString();
		var vlr = mov.valorMovimentado.toString();
		var conta = mov.idConta;
		var data = mov.data.toString();
		System.out.println(" " +tipo + "  " + empresa + "  " +qtde + "  " + vlr + " " +conta + " " + data);
	}

	static Random gerador = new Random();
	private static BigDecimal gerarAleatorio() {
		var valorAleatorio = gerador.nextDouble();
		return new BigDecimal(10+(1*valorAleatorio));
	}

	private static String extrairUrlOuPadrao(String[] args) {
		var url = "http://localhost:8080";
		if(args != null && args.length > 0 && Strings.isNotBlank(args[0])) {
			url = args[0];
		}

		return url;
	}

	private static ObservacaoDeAcao criarObs(BigDecimal precoCompra, BigDecimal precoVenda, String empresa) {
		var observacaoDeAcao = new ObservacaoDeAcao();
		observacaoDeAcao.precoCompra = precoCompra;
		observacaoDeAcao.precoVenda = precoVenda;
		observacaoDeAcao.empresa = empresa;
		return observacaoDeAcao;
	}

}
