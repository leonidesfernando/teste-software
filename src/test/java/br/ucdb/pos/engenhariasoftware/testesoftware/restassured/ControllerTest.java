package br.ucdb.pos.engenhariasoftware.testesoftware.restassured;

import br.ucdb.pos.engenhariasoftware.testesoftware.converter.StringToMoneyConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class ControllerTest {

    @Test
    public void editarTest(){

        Response response = given()
                .pathParam("id", 8).when().get("/editar/{id}");

        final String html = response.body().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        String titulo = xmlPath.getString("html.body.div.div.h4");
        assertEquals(titulo, "Cadastro de Lançamento");
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void removeTest(){
        Response response = given().pathParam("id", 131)
                .when().get("/remover/{id}");
        assertEquals(response.getStatusCode(), 200);
        assertEquals("Lançamentos",response.body().htmlPath()
                .getString("html.body.div.div.div.div.h4").toString());
    }


    @Test
    public void salvarTest(){
        Response response = given().when()
                .formParam("descricao", "Assured Rest")
                .formParam("valor", "50.00")
                .formParam("dataLancamento", "03/04/2018")
                .formParam("tipoLancamento", "SAIDA")
                .header("Content-Type", "application/x-www-form-urlencoded") // opcional
                .post("/salvar");

        assertEquals(response.getStatusCode(), 200);
    }


    @Test
    public void buscandoComPostTest() {
        Response response = given()
                .when()
                .body("jmeter")
                .post("/lancamentos");

        assertEquals(response.getStatusCode(), 200);

        InputStream in = response.asInputStream();
        List<Lancamento> list = JsonPath.with(in)
                .getList("lancamentos", Lancamento.class);
        assertEquals(list.size(), 4);
    }

}
