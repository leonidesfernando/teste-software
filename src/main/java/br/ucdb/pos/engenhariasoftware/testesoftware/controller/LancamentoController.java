package br.ucdb.pos.engenhariasoftware.testesoftware.controller;

import br.ucdb.pos.engenhariasoftware.testesoftware.controller.vo.ResultadoVO;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;

	@PostMapping("/buscaLancamentos")
    public ResponseEntity<ResultadoVO> buscaAjax(@RequestBody String itemBusca){

        ResultadoVO resultadoVO = lancamentoService.buscaAjax(itemBusca);
	    return ResponseEntity.ok(resultadoVO);
    }

    @GetMapping("/lancamentos")
    public ModelAndView buscar(){

        final ModelAndView mv = new ModelAndView("lancamentos");

        final List<Lancamento> lancamentos = lancamentoService.buscaTodos();
        mv.addObject("lancamentos", lancamentos);
        mv.addObject("totalEntrada", lancamentoService.getTotalEntrada(lancamentos));
        return mv.addObject("totalSaida", lancamentoService.getTotalSaida(lancamentos));
    }

    @GetMapping("/lancamento")
    public ModelAndView lancamento(Lancamento lancamento){

        final Map<String, Object> map = new HashMap<>();
        map.put("lancamento", lancamento);
        map.put("tiposLancamento", TipoLancamento.values());
        return new ModelAndView("cadastra-lancamento", map);
    }

    @PostMapping("/salvar")
    public Object salvar(@Valid Lancamento lancamento, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return lancamento(lancamento);
        }
        lancamentoService.salvar(lancamento);
        return new RedirectView("/lancamentos");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edtiar(@PathVariable("id") Long id){

	    return lancamento(lancamentoService.buscaPorId(id));
    }

    @GetMapping("/remover/{id}")
    public RedirectView remover(@PathVariable("id") Long id){

        lancamentoService.remover(id);
        return new RedirectView("/lancamentos");
    }
}
