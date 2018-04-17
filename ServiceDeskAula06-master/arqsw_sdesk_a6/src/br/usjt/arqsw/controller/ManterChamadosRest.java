package br.usjt.arqsw.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;
import br.usjt.arqsw.service.FilaService;
/**
 * 
 * @author Vinicius Regina 816124344
 *
 */
@RestController
public class ManterChamadosRest {

	private ChamadoService chamadoService;
	private FilaService filaService;

	@Autowired
	public ManterChamadosRest(ChamadoService cs, FilaService fs) {
		chamadoService = cs;
		filaService = fs;
	}
    /**
     * Lista todos os chamados de uma determinada fila
     * @param id Recebe o id da fila como par‚metro
     * @return um JSON Array com todos os chamados da fila
     */
	@RequestMapping(method = RequestMethod.GET, value = "rest/chamados/{id}")
	public @ResponseBody List<Chamado> listarChamados(
			@PathVariable("id") Long id) {
		List<Chamado> chamados = null;
		try {
			Fila fila = filaService.carregar(id.intValue());
			chamados = chamadoService.listarChamados(fila);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chamados;
	}

	/**
     * Lista todos os chamados abertos de uma fila
     * @param id Recebe o id da fila como par‚metro
     * @return Array com os chamados abertos da fila
     */
	@RequestMapping(method = RequestMethod.GET, value = "rest/chamados/abertos/{id}")
	public @ResponseBody List<Chamado> listarChamadosAbertos(
			@PathVariable("id") Long id) {
		List<Chamado> chamados = null;
		try {
			Fila fila = filaService.carregar(id.intValue());
			chamados = chamadoService.listarChamadosAbertos(fila);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chamados;
	}

	/**
	 * Inclui um novo chamado
	 * @param chamado, com descri√ß√£o e fila preenchidos
	 * @return um JSON do chamado com o id criado pelo autoincrement do banco, a data e o status
	 * @throws ParseException 
	 */
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "rest/chamados")
	public ResponseEntity<Chamado> criarChamado(@RequestBody Chamado chamado) throws ParseException {
		try {
			System.out.println(chamado);
			chamadoService.novoChamado(chamado);
			return new ResponseEntity<Chamado>(chamado, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Chamado>(chamado,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}