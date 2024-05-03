package tests.contactList;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import utility.ContactListTestBase;
import utility.ContactListUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UpdateContactAPI extends ContactListTestBase {

    @Test
    public void updateContactTest(){

        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("firstName", "Eric");
        contactInfo.put("lastName", "Johnson");
        contactInfo.put("birthdate", "1980-01-05");
        contactInfo.put("email", "eric@fake.com");
        contactInfo.put("phone", "773-999-0000");
        contactInfo.put("street1", "123 Main Street");
        contactInfo.put("street2", "Apartment C");
        contactInfo.put("city", "Chicago");
        contactInfo.put("stateProvince", "IL");
        contactInfo.put("postalCode", 60606);
        contactInfo.put("country", "US");

        JsonPath jsonPath =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        header("Authorization", ContactListUtils.getToken("kool@coders.com", "1234567")).
                        pathParam("contactId", "6632dfabcad2be001343498d").
                        body(contactInfo).
                        when().
                        put("/contacts/{contactId}").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response().jsonPath();

    }
}
