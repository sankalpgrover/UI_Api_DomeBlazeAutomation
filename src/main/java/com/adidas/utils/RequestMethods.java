package com.adidas.utils;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;


/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/

public class RequestMethods {
    TestSetup testSetup = new TestSetup();
    RequestSpecification requestSpecification;

    public RequestMethods() throws IOException {
    }


    public Response postJsonPayload(String requestType,String endPoint, Object payload) throws IOException {
        Response response = null;
        RestAssured.baseURI = testSetup.loadConfigProperties().getProperty("api.uri");
        requestSpecification=given().accept("application/Json");
        try {
            testSetup.setTestStatus("INFO", "Api End Point:" + RestAssured.baseURI + endPoint);

            if(!requestType.toUpperCase().equalsIgnoreCase("")||requestType.toUpperCase() !=null) {

                switch (requestType.toUpperCase()) {
                    case "POST":
                        response =  requestSpecification
                                .contentType(ContentType.JSON)
                                .body(payload).post(endPoint);
                        break;
                    case "PUT":
                        response =  requestSpecification
                                .contentType(ContentType.JSON)
                                .body(payload).post(endPoint);
                        break;
                    case "PATCH":
                        response =  requestSpecification
                                .contentType(ContentType.JSON)
                                .body(payload).post(endPoint);
                        break;
                    case "DEL":
                        response =  requestSpecification.delete(endPoint);
                        break;
                }
            }else{
                testSetup.setTestStatus("INFO","Please check the request type specified in your test case");
            }
            testSetup.setTestStatus("INFO", "Response Status Code: " + response.getStatusCode());
            testSetup.setTestStatus("INFO","***RESPONSE-BODY***" + response.getBody().asString());
            return response;
        } catch (Exception e) {
            testSetup.setTestStatus("FAIL", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return response;
    }


    public Response getResponse(String endPoint, Map<String,String> queryParams) throws IOException {
        Response response = null;
        RestAssured.baseURI = testSetup.loadConfigProperties().getProperty("api.uri");
        requestSpecification=given().accept("application/Json").queryParams(queryParams !=null?queryParams:new HashMap<>());

        try {
            testSetup.setTestStatus("INFO", "Api End Point:" + RestAssured.baseURI + endPoint);
            response = requestSpecification.get(RestAssured.baseURI + endPoint);
            testSetup.setTestStatus("INFO", "Response Status Code: " + response.getStatusCode());
            testSetup.setTestStatus("INFO","***RESPONSE-BODY***" + response.getBody().asString());
        } catch (Exception e) {
            testSetup.setTestStatus("FAIL", e.getLocalizedMessage());
        }
        return response;
    }

}
