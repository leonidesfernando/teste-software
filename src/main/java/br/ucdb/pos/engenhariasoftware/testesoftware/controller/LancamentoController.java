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

	private int paginaCorrente;

	@PostMapping("/buscaLancamentos")
    public ResponseEntity<ResultadoVO> buscaAjax(@RequestBody String itemBusca){
        ResultadoVO resultadoVO = lancamentoService.buscaAjax(itemBusca);
	    return ResponseEntity.ok(resultadoVO);
    }

    @GetMapping("/lancamentos")
    public ModelAndView lancamentos(){
        return geraRetornoLancamentos(1);
    }

    @GetMapping("/lancamentos/{p}")
    public ModelAndView buscar(@PathVariable("p") Integer pagina){
        return geraRetornoLancamentos(pagina);
    }

    private ModelAndView geraRetornoLancamentos(int pagina){
        final List<Lancamento> lancamentos = lancamentoService.buscaTodos(pagina);
        Long totalRegistros = lancamentoService.conta(null);
        List<Integer> paginas = lancamentoService.getPaginas(totalRegistros.intValue());
        int tamanhoPagina = lancamentos.size() >= lancamentoService.tamanhoPagina() ? lancamentoService.tamanhoPagina() : lancamentos.size();
        paginaCorrente = pagina;

        final ModelAndView mv = new ModelAndView("lancamentos");
        mv.addObject("p", pagina);
        mv.addObject("tamanhoPagina", tamanhoPagina);
        mv.addObject("totalRegistros", totalRegistros);
        mv.addObject("paginas", paginas);
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
        ajustaPaginaCorrente();
        return new RedirectView("/lancamentos/" + paginaCorrente);
    }

    private void ajustaPaginaCorrente() {
        Long totalRegistros = lancamentoService.conta(null);
        int numeroPaginas = lancamentoService.calculaNumeroPaginas(totalRegistros.intValue());
        if(paginaCorrente < 1){
            paginaCorrente = 1;

        }else if(paginaCorrente > numeroPaginas){
            paginaCorrente = numeroPaginas;
        }
    }

    @GetMapping("/editar/{id}")
    public ModelAndView edtiar(@PathVariable("id") Long id){

	    return lancamento(lancamentoService.buscaPorId(id));
    }

    @GetMapping("/remover/{id}")
    public RedirectView remover(@PathVariable("id") Long id){

        lancamentoService.remover(id);
        ajustaPaginaCorrente();
        return new RedirectView("/lancamentos/" + paginaCorrente);
    }
}
