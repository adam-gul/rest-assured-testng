package tests.contactList;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.ContactListTestBase;
import utility.ContactListUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddContactAPI extends ContactListTestBase {

    /*
    {
    "firstName": "Eric",
    "lastName": "Johnson",
    "birthdate": "1970-01-01",
    "email": "jdoe@fake.com",
    "phone": "8005555555",
    "street1": "1 Main St.",
    "street2": "Apartment A",
    "city": "Chicago",
    "stateProvince": "KS",
    "postalCode": "12345",
    "country": "US"
}
     */

    @Test
    public void addContactTest(){

        Map <String, Object> contactInfo = new HashMap<>();
        contactInfo.put("firstName", "Eric");
        contactInfo.put("lastName", "Johnson");
        contactInfo.put("birthdate", "1980-01-05");
        contactInfo.put("email", "eric@fake.com");
        contactInfo.put("phone", "773-999-9090");
        contactInfo.put("street1", "123 Main Street");
        contactInfo.put("street2", "Apartment B");
        contactInfo.put("city", "Chicago");
        contactInfo.put("stateProvince", "IL");
        contactInfo.put("postalCode", 60606);
        contactInfo.put("country", "US");

        JsonPath jsonPath =
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", ContactListUtils.getToken("kool@coders.com", "1234567")).
                body(contactInfo).
                when().
                post("/contacts").
                then().
                statusCode(201).
                contentType(ContentType.JSON).
                log().all().
                extract().response().jsonPath();

        String lastName = jsonPath.getString("lastName");

        Assert.assertEquals(lastName, "Johnson");

        String id = jsonPath.getString("_id");

        Assert.assertTrue(id != null);


    }



}
