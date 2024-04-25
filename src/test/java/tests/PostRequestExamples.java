package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequestExamples {

    @Test
    public void testPostRequest() throws JsonProcessingException {

//        {
//            "name": "Adam",
//            "job": "Software Engineer"
//        }

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Adam");
        requestMap.put("job", "Software Engineer");

        System.out.println(requestMap);

        System.out.println("=====================");

        // Rest Assured use Jackson Library to convert json to java / java to json

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestMap);

        System.out.println(jsonRequest); // compact print

        System.out.println("=====================");

        jsonRequest = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestMap);

        System.out.println(jsonRequest); // pretty print

//        Content Type is used to specify content type of the Request Body.

        RestAssured.baseURI = "https://reqres.in";

        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/api/users").
                then().
                statusCode(201).
                and().
                assertThat().
                body("name", equalTo("Adam")).
                body("job", equalTo("Software Engineer"));


    }


    @Test
    public void testPostRequestUsingPath() throws JsonProcessingException {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Adam");
        requestMap.put("job", "Software Engineer");

        System.out.println(requestMap);

        System.out.println("=====================");

        // Rest Assured use Jackson Library to convert json to java / java to json

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestMap);

        System.out.println(jsonRequest); // compact print

        System.out.println("=====================");

        RestAssured.baseURI = "https://reqres.in";

        Response response = given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/api/users").
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.path("name"), "Adam");
        Assert.assertEquals(response.path("job"), "Software Engineer");


    }


    @Test
    public void testPostRequestUsingMap() throws JsonProcessingException {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Adam");
        requestMap.put("job", "Software Engineer");

        System.out.println(requestMap);

        System.out.println("=====================");

        // Convert JAVA to JSON => Serialization

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestMap);

        System.out.println(jsonRequest); // compact print

        System.out.println("=====================");

        RestAssured.baseURI = "https://reqres.in";

        Response response = given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/api/users").
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);

        // Convert JSON to JAVA is called => Deserialization

        Map<String, String> responseMap = objectMapper.readValue(response.asString(), Map.class);
        System.out.println(responseMap);

        Assert.assertEquals(responseMap.get("name"), "Adam");
        Assert.assertEquals(responseMap.get("job"), "Software Engineer");


    }


}
