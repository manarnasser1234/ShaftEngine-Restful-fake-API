package com.API.shaft.base;

import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected String baseURL;
    protected String baseURL2;
    @BeforeMethod
    public void setBaseURL(){
        baseURL= "https://dummy-json.mock.beeceptor.com/";
    }
    @BeforeMethod
    public void setBaseURL2(){
        baseURL2= "https://fake-json-api.mock.beeceptor.com/";
    }




}
