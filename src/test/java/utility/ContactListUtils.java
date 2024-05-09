package utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ContactListUtils extends ContactListTestBase{

    public static String getToken(String email, String password) {

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("password", password);

        JsonPath jsonPath =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(userInfo).
                        when().
                        post("/users/login").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
//                        log().all().
                        extract().response().jsonPath();

        String token = jsonPath.getString("token");

        return "Bearer " + token;
    }

    public static RequestSpecification setRequestSpecification(ContentType contentType){

        RequestSpecification requestSpecification = new RequestSpecBuilder().
                addHeader("Authorization", ContactListUtils.getToken("kool@coders.com", "1234567")).
                setContentType(ContentType.JSON).log(LogDetail.ALL).
                setAccept(contentType).
                build();

        return requestSpecification;
    }


}
