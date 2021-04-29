package aebd.group.webapp;

import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

@RestController
public class WelcomeController {


	@PostMapping("/services")
	public String enviaJson(){
		String json = LerPasta.lerFicheiro();

		return json;
	}


	@PostMapping(
			value = "/medic")
	public void recebeJson(@RequestBody Map<String, Object> payload) {

		System.out.println("Recebi dados");

		String horaExame = (String) payload.get("horaExame");
		String tipo = (String) payload.get("tipo");
		String telefone = (String) payload.get("telefone");
		String siglaExame = (String) payload.get("siglaExame");
		String dataExame = (String) payload.get("dataExame");
		String numUtente = (String) payload.get("numUtente");
		String nome = (String) payload.get("nome");
		String id_exame = (String) payload.get("id_exame");
		String id_pedido = (String) payload.get("id_pedido");
		String id_doente = (String) payload.get("id_doente");
		String morada = (String) payload.get("morada");
		String descricao = (String) payload.get("descricao");

		Pedido pedido = new Pedido(horaExame,tipo,telefone,siglaExame,dataExame,
				numUtente,nome,id_exame,id_pedido,id_doente,morada,descricao);

		RegistaPedido.guardaPedido(pedido);

		System.out.println(payload);

	}





}
