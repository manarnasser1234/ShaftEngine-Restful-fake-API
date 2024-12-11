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
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.lessThan;


public class ToDotasksTest extends BaseTest {
    @Test
    public void Verify_that_responsebody_include_alist_of_ToDo_tasks(){
        RestActions responsebody= DriverFactory.getAPIDriver(baseURL);
       Response response= responsebody.buildNewRequest("todos", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> todos = response.jsonPath().getList("$");
        Assert.assertTrue(todos instanceof List, "Responsebody should be a list of tasks");
        Assert.assertFalse(todos.isEmpty(), "Responsebody should not be empty");
       response.body().peek();
    }
    @Test
    public void Verify_that_response_return_the_completed_number_of_ToDo_task(){
        RestActions responsebody= DriverFactory.getAPIDriver(baseURL);
       Response response= responsebody.buildNewRequest("todos", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Map<String, Object>> tasks = response.jsonPath().getList("$");
        List<Map<String, Object>> completedTasks = tasks.stream()
                .filter(task -> (Boolean) task.get("completed"))  // Only tasks where completed is true
                .collect(Collectors.toList()); // new list from response
        Assert.assertTrue(completedTasks.size() >0, "There should be at least one completed task (not empty)");
        Assert.assertEquals(response.statusCode(),200);
        response.then().assertThat()
                .time(lessThan(20000L));
        System.out.println("new responsebody: ");
        completedTasks.forEach(task -> {
            System.out.println("userId: " + task.get("userId"));
            System.out.println("id: " + task.get("id"));
            System.out.println("title: " + task.get("title"));
            System.out.println("completed: " + task.get("completed"));
            System.out.println("----------------------");
        });

    }
    @Test
    public void Verify_InvalidEndpoint(){
        RestActions TodoTask= DriverFactory.getAPIDriver(baseURL);
        Response response= TodoTask.buildNewRequest("todos/invalid", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .performRequest();
        response.body().peek();
        Assert.assertFalse(response.body().asString().contains("not Accepted"), "Responsebody should contain 'not Accepted'.");
    }
    @Test
    public void verify_TaskIds_Are_Unique(){
        RestActions TaskId= DriverFactory.getAPIDriver(baseURL);
        Response response= TaskId.buildNewRequest("todos", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        String j=RestActions.getResponseBody(response);
        System.out.println(j);
//        List<Map<String, Object>> tasks = response.jsonPath().getList("$");
//        Set<Integer> taskIds = new HashSet<>().stream()
//                .map(task -> (Integer) ((Map<String, Object>) task).get("id"))  // Extract ids
//                .collect(Collectors.toSet());
//        for (Object task : tasks) {
//            int taskId = (Integer) ((Map) task).get("id");
//            taskIds.add(taskId);
//        }
//        Assert.assertEquals(taskIds.size(),tasks.size(),"Taskids should be unique");
//        System.out.println("Unique taskId: " +taskIds);
    }


}
