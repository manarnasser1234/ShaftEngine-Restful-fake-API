package com.API.shaft.TestCases;

import com.API.shaft.base.BaseTest;
import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.Matchers.lessThan;

public class RandomCompaniesTest extends BaseTest {

    @Test
    public void Return_alist_of_RandomCompanies(){
        RestActions ReturnCompanies= DriverFactory.getAPIDriver(baseURL2);
        Response response= ReturnCompanies.buildNewRequest("companies", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        String ResponseData= RestActions.getResponseBody(response);
        response.then().assertThat()
                .time(lessThan(20000L));
        Assert.assertEquals(ResponseData.concat(""),ResponseData);
        response.body().peek();

    }
    @Test
    public void Return_adiff_list_of_RandomCompanies(){
        RestActions ReturndiffCompanies= DriverFactory.getAPIDriver(baseURL2);
        Response response= ReturndiffCompanies.buildNewRequest("companies", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        String ResponseData= RestActions.getResponseBody(response);
        response.then().assertThat()
                .time(lessThan(20000L));
        Assert.assertEquals(ResponseData.concat(""),ResponseData);
        response.body().peek();

    }
    @Test
    public void Verify_That_responsebody_not_return_alist_of_ten_companies(){
        RestActions companies= DriverFactory.getAPIDriver(baseURL2);
        Response response= companies.buildNewRequest("companies", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> gitcompanies = response.jsonPath().getList("$");
        Set<Integer> seenIds=new HashSet<>(); // To save ids (not duplicated)
        boolean duplicateFound = false;
        for (Object company : gitcompanies) {
            int companyId = (Integer) ((Map) company).get("id"); // to return companyId
            if (!seenIds.add(companyId)) {
                duplicateFound = true;
                System.out.println("Duplicate company found with ID: " + companyId);
                break;
            }
        }
        Assert.assertTrue(duplicateFound,"Found duplicated companyid");
        Assert.assertEquals(gitcompanies.size(),11);
        response.body().peek();

    }
    @Test
    public void Verify_That_responsebody_return_alist_of_ten_uniqueCompaniesids(){
        RestActions companies= DriverFactory.getAPIDriver(baseURL2);
        Response response= companies.buildNewRequest("companies", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> responsebody = response.jsonPath().getList("$");
        Set<Integer> gitIds=new HashSet<>(); // To save ids (not duplicated)

        int count=0;   //to save uniqueCompaniesids
        for (Object company : responsebody) {
            int comId = (Integer) ((Map) company).get("id"); //return all companyids
            if (gitIds.add(comId)) {
                count++;
            }
            if (count == 10) {
                break;
            }
        }
        Assert.assertEquals(count, 10,"the responsebody actually include ten company");

    }
    @Test
    public void Verify_how_theAPI_handles_invalid_endpoints(){
        RestActions companies= DriverFactory.getAPIDriver(baseURL2);
        Response response= companies.buildNewRequest("companies/invalid_random", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .performRequest();
        response.body().peek();
        Assert.assertFalse(response.body().asString().contains("not found"), "Response body should contain 'not found'.");
    }

}

