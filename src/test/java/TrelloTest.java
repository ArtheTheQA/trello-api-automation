import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class TrelloTest {

    String propFileName = "config.properties";
    String host = "";
    private static String KEY = "";
    private static String OAUTHSECRET = "";
    private static String PERSONALTOKEN = "";
    private static String FAKETOKEN ="";


    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        properties.load(inputStream);
        host = properties.getProperty("host");
        KEY = properties.getProperty("KEY");
        OAUTHSECRET = properties.getProperty("OAUTHSECRET");
        PERSONALTOKEN = properties.getProperty("PERSONALTOKEN");
        FAKETOKEN = properties.getProperty("FAKETOKEN");
    }

    @Test
    public void getAllBoards() {

        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when().get(host + "/1/members/me/boards")
                .then().statusCode(200);
    }

    @Test
    public void expectedboard401Erro()
    {
        given().auth()
                .oauth(KEY, OAUTHSECRET, FAKETOKEN, "")
                .when().get(host + "/1/members/me/boards")
                .then().statusCode(401);
    }

    @Test
    public void createNewCard()
    {
        String boardName = "BoardWithList";
        String listName = "NewList";
        String cardName = "NewCard";
        String cardDesc = "tapa_na__shuleba";

        ValidatableResponse response = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/boards/?name="+ boardName)
                .then().statusCode(200)
                .and().body("name",equalTo(boardName));

        JSONObject body = new JSONObject(response.extract().body().asString());
        String boardId = body.getString("id");


        ValidatableResponse response2 = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/lists/?name="+listName+"&idBoard="+boardId)
                .then().statusCode(200)
                .and().body("name",equalTo(listName));

        JSONObject body2 = new JSONObject(response2.extract().body().asString());
        String listId = body2.getString("id");

        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/cards?idList=" + listId + "&name=" + cardName + "&desc=" + cardDesc)
                .then().statusCode(200)
                .and().body("name",equalTo(cardName))
                .and().body("desc",equalTo(cardDesc));
    }

    @Test
    public void deleteNewCard()
    {
        String boardName = "BoardWithList";
        String listName = "NewList";
        String cardName = "NewCard";

        ValidatableResponse response = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/boards/?name="+ boardName)
                .then().statusCode(200)
                .and().body("name",equalTo(boardName));

        JSONObject body = new JSONObject(response.extract().body().asString());
        String boardId = body.getString("id");


        ValidatableResponse response2 = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/lists/?name="+listName+"&idBoard="+boardId)
                .then().statusCode(200)
                .and().body("name",equalTo(listName));

        JSONObject body2 = new JSONObject(response2.extract().body().asString());
        String listId = body2.getString("id");

        ValidatableResponse response3 = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/cards?idList=" + listId + "&name=" + cardName)
                .then().statusCode(200)
                .and().body("name",equalTo(cardName));

        JSONObject body3 = new JSONObject(response3.extract().body().asString());
        String cardId = body3.getString("id");

        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .delete(host + "/1/cards/"+ cardId)
                .then().statusCode(200);

        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .get(host + "/1/cards/"+ cardId)
                .then().statusCode(404);
    }

//    @Test
//    public void closeNewCard()
//    {
//        String boardName = "BoardWithList";
//        String listName = "NewList";
//        String cardName = "NewCard";
//
//        ValidatableResponse response = given().auth()
//                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
//                .when()
//                .contentType(ContentType.JSON)
//                .post(host + "/1/boards/?name="+ boardName)
//                .then().statusCode(200)
//                .and().body("name",equalTo(boardName));
//
//        JSONObject body = new JSONObject(response.extract().body().asString());
//        String boardId = body.getString("id");
//
//
//        ValidatableResponse response2 = given().auth()
//                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
//                .when()
//                .contentType(ContentType.JSON)
//                .post(host + "/1/lists/?name="+listName+"&idBoard="+boardId)
//                .then().statusCode(200)
//                .and().body("name",equalTo(listName));
//
//        JSONObject body2 = new JSONObject(response2.extract().body().asString());
//        String listId = body2.getString("id");
//
//        ValidatableResponse response3 = given().auth()
//                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
//                .when()
//                .contentType(ContentType.JSON)
//                .post(host + "/1/cards?idList=" + listId + "&name=" + cardName)
//                .then().statusCode(200)
//                .and().body("name",equalTo(cardName));
//
//        JSONObject body3 = new JSONObject(response3.extract().body().asString());
//        String cardId = body3.getString("id");
//
//        given().auth()
//                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
//                .when()
//                .contentType(ContentType.JSON)
//                .post(host + "/1/cards/"+ cardId + "?closed=true")
//                .then().statusCode(200);
//    }

    @Test
    public void createNewBoard() {
        String boardName = "TestBoard";
        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .given()
                .contentType(ContentType.JSON)
                .post(host + "/1/boards/?name="+ boardName)
                .then().statusCode(200)
                .and().body("name",equalTo(boardName));
    }

    @Test
    public void createNewList() {
        String boardName = "BoardWithList";
        String listName = "NewList";

        ValidatableResponse response = given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/boards/?name="+ boardName)
                .then().statusCode(200)
                .and().body("name",equalTo(boardName));

        JSONObject body = new JSONObject(response.extract().body().asString());
        String boardId = body.getString("id");

        given().auth()
                .oauth(KEY, OAUTHSECRET, PERSONALTOKEN, "")
                .when()
                .contentType(ContentType.JSON)
                .post(host + "/1/lists/?name="+listName+"&idBoard="+boardId)
                .then().statusCode(200)
                .and().body("name",equalTo(listName));
    }
}