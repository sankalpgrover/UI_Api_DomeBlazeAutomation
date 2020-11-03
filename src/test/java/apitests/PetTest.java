package apitests;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.adidas.utils.EndPoints;
import com.adidas.utils.TestSetup;
import org.testng.annotations.Test;
import com.adidas.utils.RequestMethods;
import io.restassured.response.Response;
import com.google.gson.reflect.TypeToken;
import io.restassured.path.json.JsonPath;
import com.adidas.api.requestmodals.createpets.Pet;


/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/

public class PetTest {
    RequestMethods requestMethods = new RequestMethods();
    Pet pet = new Pet();
    TestSetup testSetup = new TestSetup();
    Gson gson = new Gson();
    String endPoint;
    long petId;

    public PetTest() throws IOException {
    }

    @Test(priority = 2)
    public void createPet() {
        try {
            TestSetup.extentTest = TestSetup.extent.createTest("Create Pet via swagger apitests");
            endPoint = EndPoints.version + EndPoints.pets;
            String petName = "Cat";
            pet.setName(petName);
            pet.setStatus("available");
            Response response = requestMethods.postJsonPayload("POST", endPoint, pet);
            testSetup.assertEquals("Status Code: ", String.valueOf(response.getStatusCode()), "200");
            Pet jsonPojoPet = gson.fromJson(response.getBody().asString(), Pet.class);
            testSetup.assertEquals("PetName: ", jsonPojoPet.getName(), petName);
            testSetup.assertEquals("PetStatus: ", jsonPojoPet.getStatus(), "available");
            petId = jsonPojoPet.getId();
            TestSetup.extent.flush();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }

    @Test(priority = 1)
    public void getPetsByStatus() {
        try {
            TestSetup.extentTest = TestSetup.extent.createTest("Filter Pet basis status via swagger apitests");
            endPoint = EndPoints.version + EndPoints.pets + EndPoints.findPetsByStatus;
            Map<String, String> queryParamMap = new HashMap<String, String>();
            queryParamMap.put("status", "available");
            Response response = requestMethods.getResponse(endPoint, queryParamMap);
            testSetup.assertEquals("Status Code: ", String.valueOf(response.getStatusCode()), "200");
            Type type = new TypeToken<List<Pet>>() {
            }.getType();
            List<Pet> jsonPojoCreatePets = response.getBody().as(type);
            jsonPojoCreatePets.stream().forEach(pet -> {
                testSetup.setTestStatus("INFO", "Pet Id: " + pet.getId());
                testSetup.setTestStatus("INFO", "Pet Name: " + pet.getName());
                testSetup.setTestStatus("INFO", "Pet Status: " + pet.getStatus());
                testSetup.assertEquals("Pet Status: ", pet.getStatus(), "available");
            });
            TestSetup.extent.flush();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }

    @Test(priority = 3)
    public void updatePet() {
        try {
            TestSetup.extentTest = TestSetup.extent.createTest("Update Pet via swagger apitests");
            endPoint = EndPoints.version + EndPoints.pets;
            String petName = "Cat";
            pet.setId(petId);
            pet.setStatus("sold");
            Response response = requestMethods.postJsonPayload("PUT", endPoint, pet);
            testSetup.assertEquals("Status Code: ", String.valueOf(response.getStatusCode()), "200");
            Pet jsonPojoPet = gson.fromJson(response.getBody().asString(), Pet.class);
            testSetup.assertEquals("PetName: ", jsonPojoPet.getName(), petName);
            testSetup.assertEquals("PetStatus: ", jsonPojoPet.getStatus(), "sold");
            TestSetup.extent.flush();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }

    @Test(priority = 4)
    public void deletePet() {
        try {
            JsonPath jsonPathEvaluator;
            TestSetup.extentTest = TestSetup.extent.createTest("Delete pet via swagger apitests");
            endPoint = EndPoints.version + EndPoints.pets + petId;
            Response response = requestMethods.postJsonPayload("DEL", endPoint, pet);
            jsonPathEvaluator = response.jsonPath();
            testSetup.assertEquals("Status Code: ", String.valueOf(response.getStatusCode()), "200");
            testSetup.assertEquals("Message Body: ", jsonPathEvaluator.get("message").toString(), String.valueOf(petId));
            response = requestMethods.getResponse(endPoint, new HashMap<>());
            testSetup.assertEquals("Status Code: ", String.valueOf(response.getStatusCode()), "404");
            jsonPathEvaluator = response.jsonPath();
            testSetup.assertEquals("Message Body: ", jsonPathEvaluator.get("message").toString(), "Pet not found");
            TestSetup.extent.flush();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }
}
