package com.example;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class CommentApiTest {

    @BeforeEach
     public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void postComment_shouldReturnCorrectData() {
        //RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String requestBody = "{\n" +
                "  \"postId\": 1,\n" +
                "  \"name\": \"Test User\",\n" +
                "  \"email\": \"test@example.com\",\n" +
                "  \"body\": \"This is a test comment.\"\n" +
                "}";

        given()
            .log().all()
            .header("Content-Type", "application/json")
            .body(requestBody)                                  //set up the body to be sent
                                                                //along with info in its headers 
        .when()
            .post("/comments")                      
        .then()                                                 //Assert That....
            .log().all()    
            .statusCode(201)          
            .body("name", equalTo("Test User"))
            .body("email", endsWith("@example.com"))
            .body("postId", equalTo(1));
    }


    @Test
    public void getComment_shouldReturnResource() {
        //RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
            .log().all()
        .when()
            .get("/posts/12")                              //get the post with ID 12
        .then()
            .log().body()
            .statusCode(200)
            .body("id", equalTo(12))
            .body("userId", equalTo(2))
            .body("title", not(emptyOrNullString()));
    }
    //some creative ways of asserting within test scenario:
    //equalTo, not, endsWith, emptyOrNullString
    //presence and null check: notNullValue(), not(emptyOrNullString())
    //substring and pattern matching: containsString(".com"), matchesPattern("regex")
    //   But depends on the body content 



}



