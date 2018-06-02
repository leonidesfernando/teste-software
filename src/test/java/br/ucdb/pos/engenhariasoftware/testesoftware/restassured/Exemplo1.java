package br.ucdb.pos.engenhariasoftware.testesoftware.restassured;

import br.ucdb.pos.engenhariasoftware.testesoftware.converter.StringToMoneyConverter;
import br.ucdb.pos.engenhariasoftware.testesoftware.modelo.Lancamento;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

public class Exemplo1 {

    @Test
    public void exemplo1() {

        get("http://services.groupkt.com/country/get/all")
                .then()
                .body("RestResponse.result.name", hasItems("Brazil"));
    }


}


