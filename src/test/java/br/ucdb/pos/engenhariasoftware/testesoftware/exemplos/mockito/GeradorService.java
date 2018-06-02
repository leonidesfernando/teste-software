package br.ucdb.pos.engenhariasoftware.testesoftware.exemplos.mockito;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class GeradorService {
    private static final String CAMINHO_ARQUIVOS_GERADOS = "";

    public File xls(List<Usuario> usuarios){
        File arquivo = new File(CAMINHO_ARQUIVOS_GERADOS + "arquivo.xls");
        // código para gerar xls
        arquivo.isDirectory();
        System.out.println("Gerado xls:" + System.getProperty("java.io.tmpdir") + "usuarios.xls");
        arquivo.delete();
        return arquivo;
    }


    public void toXlsx(List<Usuario> usuarios){
        System.out.println("chamou método real, gerando xlsx ...");
    }
    public void toPdf(List<Usuario> usuarios){
        System.out.println("chamou método real, gerando pdf ...");
    }
}
