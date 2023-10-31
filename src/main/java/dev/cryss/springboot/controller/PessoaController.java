package dev.cryss.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.cryss.springboot.model.Pessoa;
import dev.cryss.springboot.model.Telefone;
import dev.cryss.springboot.repository.PessoaRepository;
import dev.cryss.springboot.repository.TelefoneRepository;
import jakarta.validation.Valid;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			andView.addObject("pessoaobj", pessoa);
			andView.addObject("pessoas", pessoasIt);

			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}

			andView.addObject("msg", msg);
			return andView;
		}

		pessoaRepository.save(pessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", pessoasIt);
		return andView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", pessoasIt);
		return andView;
	}

	@GetMapping("/editar/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");

		andView.addObject("pessoaobj", pessoa.get());

		return andView;
	}

	@GetMapping("/remover/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {

		pessoaRepository.deleteById(idpessoa);

		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoaobj", new Pessoa());
		andView.addObject("pessoas", pessoaRepository.findAll());

		return andView;
	}

	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		andView.addObject("pessoaobj", pessoa.get());

		return andView;
	}

	@PostMapping("**/addfonePessoa/{idpessoa}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("idpessoa") Long idpessoa) {

		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);

		if (telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {

			ModelAndView andView = new ModelAndView("cadastro/telefones");
			andView.addObject("pessoaobj", pessoa.get());
			andView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));

			List<String> msg = new ArrayList<String>();

			if (telefone.getNumero().isEmpty()) {
				msg.add("Numero deve ser informado");
			}

			if (telefone.getTipo().isEmpty()) {
				msg.add("Tipo deve ser informado");
			}

			andView.addObject("msg", msg);
			return andView;
		}

		telefone.setPessoa(pessoa.get());

		telefoneRepository.save(telefone);

		ModelAndView andView = new ModelAndView("cadastro/telefones");

		andView.addObject("pessoaobj", pessoa.get());
		andView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));

		return andView;
	}

	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {

		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();

		telefoneRepository.deleteById(idtelefone);

		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		andView.addObject("pessoaobj", pessoa);

		return andView;
	}

}
