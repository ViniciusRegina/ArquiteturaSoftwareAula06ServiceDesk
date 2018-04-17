package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.FilaService;
/**
 * 
 * @author Vinicius Regina 816124344
 *
 */
@Transactional
@Controller("/fila")
public class ManterFilasController {
	private FilaService filaService;

	@Autowired
	public ManterFilasController(FilaService fs) {
		filaService = fs;
	}
	
	@Autowired
	private ServletContext servletContext;

	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String inicio() {
		return "index";
	}

	private List<Fila> listarFilas() throws IOException {
		return filaService.listarFilas();
	}

	/**
	 * 
	 * @param model Acesso à request http
	 * @return JSP de Listar Chamados
	 */
	@RequestMapping("/listar_filas")
	public String listarFilasExibir(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "FilaListar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}

	@RequestMapping("/novaFila")
	public String novoChamado(Model model) {
		return "NovaFila";
	}

	@RequestMapping("/salvarFila")
	public String salvarChamado(Fila fila, BindingResult result, Model model) {

		try {
			System.out.println(fila.getNome());
			filaService.novaFila(fila);
			model.addAttribute("filas", listarFilas());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "FilaListar";
	}
	
	@RequestMapping("/incluir_imagem")
	public String incluirImagem(@Valid Fila fila, BindingResult result, Model model, 
			@RequestParam("file") MultipartFile file) throws IOException{
		
		//filaService.criar(fila);
		filaService.gravarImagem(servletContext, fila, file);
		
				return "erro";
		
	}
	
	@RequestMapping("atualizar_imagem")
	public String atualizar(Fila fila, Model model, @RequestParam("file") MultipartFile file) {
		try {
			filaService.gravarImagem(servletContext, fila, file);
			return "redirect:listar_locais";
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("erro", e);
		}
		return "erro";
	}
	
	@RequestMapping("editar_fila")
	public String formAlterar(Model model, Fila fila) {
		try {
			List<Fila> filas = filaService.listarFilas();
			model.addAttribute("fila", fila);
			return "Editarfila";
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("erro", e);
		}
		return "erro";
	}
	
	@RequestMapping("/excluir_fila")
	public String excluirChamado(int id, Model model) {
		try {
			Fila fila = new Fila();
			fila.setId(id);
			filaService.excluirFila(fila);
			model.addAttribute("filas", listarFilas());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "FilaListar"; 
	}
	
	
}
