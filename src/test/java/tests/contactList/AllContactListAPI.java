package tests.contactList;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.ContactListTestBase;
import utility.ContactListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AllContactListAPI extends ContactListTestBase {

    String id;

    @Test (priority = 1)
    public void addContactTest(){

        Map<String, Object> contactInfo = new HashMap<>();
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
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        body(contactInfo).
                        when().
                        post("/contacts").
                        then().
                        statusCode(201).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response().jsonPath();

        String firstName = jsonPath.getString("firstName");
        Assert.assertEquals(firstName, "Eric");

        String lastName = jsonPath.getString("lastName");
        Assert.assertEquals(lastName, "Johnson");

       id = jsonPath.getString("_id");
       System.out.println("id" + id);

       Assert.assertTrue(id != null);

    }

    @Test (priority = 2)
    public void getContactListTest(){

        JsonPath jsonPath =
                given().
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        when().
                        get("/contacts").
                        then().
                        assertThat().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().jsonPath();

        List contacts = jsonPath.getList("");
        System.out.println("Number of Contacts: " + contacts.size());

        List contactNames = jsonPath.getList("firstName");
        System.out.println("Contact first names: " + contactNames);

        Assert.assertTrue(contactNames.contains("Eric"));

        List ids = jsonPath.getList("_id");
        Assert.assertTrue(ids.contains(id));

    }


    @Test (priority =3)
    public void updateContactTest(){

        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("firstName", "Tom");
        contactInfo.put("lastName", "Doe");
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
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        pathParam("contactId", id).
                        body(contactInfo).
                        when().
                        put("/contacts/{contactId}").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response().jsonPath();

        String firstName = jsonPath.getString("firstName");
        Assert.assertEquals(firstName, "Tom");
    }

    @Test (priority = 4)
    public void deleteContactTest(){
        Response response =
        given().
                spec(ContactListUtils.setRequestSpecification(ContentType.HTML)).
                pathParam("contactId","663c111fc81cd90013c5fd99" ).
                when().
                delete("/contacts/{contactId}").
                then().
                statusCode(200).
                contentType(ContentType.HTML).
                log().all().
                extract().response();

        String message = response.body().asString();
        System.out.println("message:" + message);

        Assert.assertEquals(message, "Contact deleted");

    }



}
