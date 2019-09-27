package com.simuladordepreco.simular;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.simuladordepreco.simular.CorretorInteligenteProxy.ObservacaoDeAcao;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import feign.Feign;
import feign.Response;

public class SimularApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimularApp.class, args);
	}

	public void run(String... args) throws InterruptedException {
		var urlCorretorInteligente = extrairUrlOuPadrao(args);
		var iteracoes = 100;

		var proxy = Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder())
				.target(CorretorInteligenteProxy.class, urlCorretorInteligente);

		imprimirLinhaComLabels();
		imprimirLinhasDeEspera();
		for (int i = 0; i < iteracoes; i++) {
			var precoCompra = gerarAleatorio();
			var precoVenda = gerarAleatorio();
			var empresa = "Intel";
			var observacao = CorretorInteligenteProxy.criarObs(precoCompra, precoVenda, empresa);
			var response = proxy.registrarAcao(observacao);

			if (response != null && response.movimentacoes.size() > 0) {

				for (Movimentacao mov : response.movimentacoes) {
					imprimirEmTela(mov);
				}

				imprimirLinhasDeEspera();
			}
			TimeUnit.SECONDS.sleep(5);
		}
	}

	private void imprimirObs(ObservacaoDeAcao observacao) {
		System.out.println(observacao.empresa + "|" + observacao.precoCompra + "|" + observacao.precoVenda);
	}

	private void imprimirLinhasDeEspera() {
		System.out.println("-----------------------------------------");
	}

	private void imprimirLinhaComLabels() {
		System.out.println("TIPO || EMPRESA || QTDE ||  VLR  || CONTA  || DATA");
	}

	private void imprimirEmTela(Movimentacao mov) {
		var tipo = mov.tipo;
		var empresa = mov.empresa;
		var qtde = mov.quantidadeDeAcoes.toString();
		var vlr = mov.valorMovimentado.toString();
		var conta = mov.idConta;
		var data = mov.data.toString();
		System.out.println(tipo + " || " + empresa + " || " +qtde + " || " + vlr + " || " +conta + " || " + data);
	}

	static Random gerador = new Random();
	private static BigDecimal gerarAleatorio() {
		var valorAleatorio = 3*gerador.nextDouble() - 1.5;
		return new BigDecimal(10+(1*valorAleatorio));
	}

	private static String extrairUrlOuPadrao(String[] args) {
		var url = "http://localhost:8080";
		if(args != null && args.length > 0 && Strings.isNotBlank(args[0])) {
			url = args[0];
		}

		return url;
	}

	

}
