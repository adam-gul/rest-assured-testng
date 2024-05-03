package tests.contactList;

import org.testng.annotations.Test;
import utility.ContactListTestBase;
import utility.ContactListUtils;

import static io.restassured.RestAssured.given;

public class DeleteContactAPI extends ContactListTestBase {

    @Test
    public void deleteContactTest(){

        given().
        header("Authorization", ContactListUtils.getToken("kool@coders.com", "1234567")).
        pathParam("contactId","6632dfabcad2be001343498d" ).
        when().
        delete("/contacts/{contactId}").
        then().
        statusCode(200).
        log().all();

    }
}
