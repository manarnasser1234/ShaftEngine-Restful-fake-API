package com.API.shaft.TestCases;

import com.API.shaft.base.BaseTest;
import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ContinentsInformationTest extends BaseTest {
    @Test
    public void Verify_that_responsebody_return_informationdata(){
        RestActions data= DriverFactory.getAPIDriver(baseURL);
        Response response=data.buildNewRequest("continents", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Map<String, Object>> continents = response.jsonPath().getList("$");
        for (Map<String, Object> continent : continents) {
            String continentName = (String) continent.get("name");
            String continentCode = (String) continent.get("code");
            Number areaNumber = (Number) continent.get("areaSqKm"); //you can handle both Integer and Long
            long area = areaNumber.longValue();      //The longValue() method works on Number and will return the value as a long.
            Number populationNumber = (Number) continent.get("population");
            long population = populationNumber.longValue();
            List<String> lines = (List<String>) continent.get("lines");
            int countries = (int) continent.get("countries");
            List<String> oceans = (List<String>) continent.get("oceans");
            List<String> developedCountries = (List<String>) continent.get("developedCountries");

            System.out.println("Continent: " + continentName);
            System.out.println("Code: " + continentCode);
            System.out.println("Area (sq km): " + area);
            System.out.println("Population: " + population);
            System.out.println("Lines: " + lines);
            System.out.println("Countries: " + countries);
            System.out.println("Oceans: " + oceans);
            System.out.println("Developed Countries: " + developedCountries);
            System.out.println("*******************");
        }
    }
    @Test
    public void Verify_validate_AllContinentData(){
        RestActions data= DriverFactory.getAPIDriver(baseURL);
        Response response=data.buildNewRequest("continents", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Map<String, Object>> continents = response.jsonPath().getList("$");
        for (Map<String, Object> continent : continents) {
            String continentName = (String) continent.get("name");
            String continentCode = (String) continent.get("code");
            Number areaNumber = (Number) continent.get("areaSqKm"); //you can handle both Integer and Long
            long area = areaNumber.longValue();      //The longValue() method works on Number and will return the value as a long.
            Number populationNumber = (Number) continent.get("population");
            long population = populationNumber.longValue();

            switch (continentName) {
                case "Africa":
                    Assert.assertEquals(continentCode, "AF");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "Antarctica":
                    Assert.assertEquals(continentCode, "AN");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "Asia":
                    Assert.assertEquals(continentCode, "AS");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "Europe":
                    Assert.assertEquals(continentCode, "EU");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "North America":
                    Assert.assertEquals(continentCode, "NA");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "Oceania":
                    Assert.assertEquals(continentCode, "OC");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                case "South America":
                    Assert.assertEquals(continentCode, "SA");
                    Assert.assertTrue(area > 0);
                    Assert.assertTrue(population > 0);
                    break;
                default:
                    Assert.fail("Unexpected continent: " + continentName);
            }

    }   }



}
