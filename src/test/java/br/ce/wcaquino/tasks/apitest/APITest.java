package br.ce.wcaquino.tasks.apitest;

import java.net.http.HttpResponse.BodyHandler;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://192.168.200.127:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
		.get("/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefas() {
		RestAssured.given()
		.body("{ \"task\":\"Teste via API\", \"dueDate\":\"2021-12-30\" }")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoAdicionarTarefaInvalida() {
		RestAssured.given()
		.body("{ \"task\":\"Teste via API\", \"dueDate\":\"2020-12-30\" }")
		.contentType(ContentType.JSON)
		.when()
		.post("/todo")
		.then()
		.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}

}
