package com.API.shaft.TestCases;

import com.API.shaft.base.BaseTest;
import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.Matchers.*;


public class ReturnUserTest extends BaseTest {
     @Test
     public void Returns_a_list_of_ten_users() {
        RestActions Return_User= DriverFactory.getAPIDriver(baseURL2);
         Response response=  Return_User.buildNewRequest("users", RestActions.RequestType.GET)
                 .performRequest();
         String ResponseData= RestActions.getResponseBody(response);
         System.out.println(ResponseData);

     }
     @Test
     public void Returns_a_list_of_diff_ten_Users(){
         RestActions Return_diff_User= DriverFactory.getAPIDriver(baseURL2);
        Response response= Return_diff_User.buildNewRequest("users", RestActions.RequestType.GET)
                 .performRequest();
         String ResponseData_diff= RestActions.getResponseBody(response);
         System.out.println(ResponseData_diff);
     }
     @Test
     public void Check_That_Response_include_data_of_users(){
         RestActions ValidResponse= DriverFactory.getAPIDriver(baseURL2);
       Response response= ValidResponse.buildNewRequest("users", RestActions.RequestType.GET)
                 .setTargetStatusCode(200)
                 .setContentType(ContentType.JSON)
                 .performRequest();
        String includeData= RestActions.getResponseBody(response);
         assertFalse("Responsebody should not be blank", includeData.isBlank());
         System.out.println("ResponseBody: " + includeData);

     }
     @Test
    public void Verify_that_each_user_have_required_field(){
         RestActions ValidResponse= DriverFactory.getAPIDriver(baseURL2);
         Response response= ValidResponse.buildNewRequest("users", RestActions.RequestType.GET)
                 .setTargetStatusCode(200)
                 .setContentType(ContentType.JSON)
                 .performRequest();
         response.then().assertThat().body("$",isA(java.util.List.class))
                  .body("[2]",hasKey("id"))
                  .body("[2]",hasKey("name"))
                  .body("[2]",hasKey("company"))
                  .body("[2]",hasKey("username"))
                  .body("[2]",hasKey("email"))
                  .body("[2]",hasKey("address"))
                  .body("[2]",hasKey("zip"))
                  .body("[2]",hasKey("state"))
                  .body("[2]",hasKey("country"))
                  .body("[2]",hasKey("phone"))
                  .body("[2]",hasKey("photo"));
         response.body().peek();
     }
     @Test
    public void Check_That_Validate_DataTypes(){
         RestActions ValidResponse= DriverFactory.getAPIDriver(baseURL2);
         Response response= ValidResponse.buildNewRequest("users", RestActions.RequestType.GET)
                 .setTargetStatusCode(200)
                 .setContentType(ContentType.JSON)
                 .performRequest();
         response.then().assertThat()
                 .body("[1].id", isA(Integer.class))
                 .body("[1].name",isA(String.class))
                 .body("[1].company",isA(String.class))
                 .body("[1].username",isA(String.class))
                 .body("[1].email",isA(String.class))
                 .body("[1].address",isA(String.class))
                 .body("[1].zip",isA(String.class))
                 .body("[1].state",isA(String.class))
                 .body("[1].country",isA(String.class))
                 .body("[1].phone",isA(String.class))
                 .body("[1].photo",isA(String.class));
         response.body().peek();
     }


 }


