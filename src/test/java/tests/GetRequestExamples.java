package tests;




import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class GetRequestExamples {

    @Test
    public void testGetList(){
        given().
                log().all().
                get("https://reqres.in/api/users").
                then().
                statusCode(200).
                and().
                body("data[0].first_name", equalTo("George")).
                body("data[0].last_name", equalTo("Bluth")).
                body("data.first_name", hasItem("Charles")).
                body("data.last_name",hasItems("Weaver","Bluth")).log().all();
    }

    @Test
    public void testGetListWithHeaders(){

        given().
//                log().all().
                accept(JSON).
                header("Connection","keep-alive").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                statusCode(200).
                and().
                body("data[0].first_name", equalTo("George")).
                body("data[0].last_name", equalTo("Bluth")).
                body("data.first_name", hasItem("Charles")).
                body("data.last_name",hasItems("Weaver","Bluth"));
//                log().all();
    }


    @Test
    public void testGetListWithPathParams(){

        String email =
        given().
                log().all().
                accept(JSON).
                header("Connection","keep-alive").
                pathParam("id",2).
                when().
                get("https://reqres.in/api/users/{id}").
                then().
                statusCode(200).
                and().
                assertThat().
                contentType(JSON).
                body("data.first_name", equalTo("Janet")).
                log().all().
                extract().path("data.email");

        System.out.println("Email: " + email);

        Assert.assertEquals(email, "janet.weaver@reqres.in");

    }


    @Test
    public void testGetListWithAllResponse(){

        Response response =
                given().
//                        log().all().
                        accept(JSON).
                        header("Connection","keep-alive").
                        pathParam("id",2).
                        when().
                        get("https://reqres.in/api/users/{id}").
                        then().
                        statusCode(200).
                        and().
                        assertThat().
                        contentType(JSON).
//                        log().all().
                        extract().response();


        String email = response.path("data.email");
        System.out.println("Email: " + email);
        Assert.assertEquals(email, "janet.weaver@reqres.in");

        String firstName = response.path("data.first_name");
        System.out.println("First Name: " + firstName);
        Assert.assertEquals(firstName, "Janet");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains(JSON.toString()));

    }

    @Test
    public void testGetListWithQueryParams(){

        Response response =
        given().
                log().all().
                queryParam("page","3").
                queryParam("per_page", "5").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(JSON).
                log().all().
                extract().
                response();

        String name = response.path("data[0].first_name");
        System.out.println(name);
        Assert.assertEquals(name, "George");

        String expectedSupportUrl = "https://reqres.in/#support-heading";
        String actualSupportUrl = response.path("support.url");

        Assert.assertEquals(expectedSupportUrl, actualSupportUrl);

        // Validate the per_page is 5

        Integer expectedPerPage = 5;
        Integer actualPerPage  = response.path("per_page");
        Assert.assertEquals(expectedPerPage, actualPerPage);

        Assert.assertEquals((Integer) response.path("per_page"), 5);
        Assert.assertEquals(Integer.parseInt(response.path("per_page").toString()), 5);

        // Validate in the API response support.text field contains "ReqRes" text

        String expectedField = "ReqRes";
        String actualField = response.path("support.text");
        Assert.assertTrue(actualField.contains(expectedField));






    }




}
