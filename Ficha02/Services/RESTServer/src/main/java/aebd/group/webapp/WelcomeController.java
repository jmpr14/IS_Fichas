package aebd.group.webapp;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

@RestController
public class WelcomeController {


	@PostMapping(
			value = "/services")
	public void recebeJson(@RequestBody Map<String, Object> payload) {

		System.out.println("Recebi dados");

		String telefone = (String) payload.get("telefone");
		String siglaExame = (String) payload.get("siglaExame");
		String numUtente = (String) payload.get("numUtente");
		String numPedido = (String) payload.get("num_Pedido");
		String nome = (String) payload.get("nome");
		String relatorio = (String) payload.get("relatorio");
		String id_exame = (String) payload.get("id_exame");
		String num_Episodio = (String) payload.get("num_Episodio");
		String id_doente = (String) payload.get("id_doente");
		String morada = (String) payload.get("morada");
		String descricao = (String) payload.get("descricao");


		Pedido pedido = new Pedido(telefone,siglaExame,numUtente,numPedido,nome,relatorio,
				id_exame,num_Episodio,id_doente,morada,descricao);

		RegistaPedido.guardaPedido(pedido);

		System.out.println(payload);

		EscreveFicheiro.escrever(payload.toString(),numPedido);

	}





}
