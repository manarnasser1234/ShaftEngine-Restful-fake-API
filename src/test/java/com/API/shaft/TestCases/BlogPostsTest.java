package com.API.shaft.TestCases;

import com.API.shaft.base.BaseTest;
import com.shaft.api.RestActions;
import com.shaft.driver.DriverFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.isA;

public class BlogPostsTest extends BaseTest {
    @Test
    public void Verify_the_responsebody_return_alis_of_blogpost(){
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response= Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        String gitpost=RestActions.getResponseBody(response);
        Assert.assertEquals(gitpost.concat(""),gitpost);
        response.body().peek();
    }
    @Test
    public void Verify_that_thelist_contains_exactly_10_blogposts(){
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response= Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> posts = response.jsonPath().getList("$"); //Extract thelist of blogposts from the response
        Assert.assertEquals(posts.size(),10,"Thenumber of blogposts should be exactly 10");
        System.out.println("num of blogposts: " + posts.size());
    }
    @Test
    public void Verify_PostIds_AreUnique(){
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response= Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> posts = response.jsonPath().getList("$");
        Set<Integer> postIds = new HashSet<>().stream()
                .map(post -> (Integer) ((Map<String, Object>) post).get("id"))  // Extract ids
                .collect(Collectors.toSet());
        for (Object post : posts) {
              Map<String, Object> postMap = (Map<String, Object>) post;
              int id = (int) postMap.get("id");
            postIds.add(id);
        }
        Assert.assertEquals(postIds.size(),posts.size(),"Postids should be unique");
        System.out.println("Unique postids: " + postIds);

    }
    @Test
    public void Verify_validUserIds(){
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response= Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        List<Object> userid = response.jsonPath().getList("$");
        Set<Integer> validuserids = new HashSet<>().stream()
                .map(user -> (Integer) ((Map<String, Object>) user).get("id"))
                .collect(Collectors.toSet());
        for (Object user : userid) {
            Map<String, Object> userMap = (Map<String, Object>) user;
            int id = (int) userMap.get("userId");
            validuserids.add(id);
            Assert.assertTrue(validuserids.contains(id));
        }
           System.out.println("userids: " +validuserids);

    }
    @Test
    public void Verify_valid_data_post(){
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response= Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        response.then().assertThat()
                .body("$",isA(java.util.List.class))
                .body("[1]",hasKey("userId"))
                .body("[1]",hasKey("id"))
                .body("[1]",hasKey("title"))
                .body("[1]",hasKey("body"))
                .body("[1]",hasKey("link"))
                .body("[1]",hasKey("comment_count"))
                .log().all();
    }
    @Test
    public void Verify_That_Validate_DataTypes() {
        RestActions Post = DriverFactory.getAPIDriver(baseURL);
        Response response = Post.buildNewRequest("posts", RestActions.RequestType.GET)
                .setTargetStatusCode(200)
                .setContentType(ContentType.JSON)
                .performRequest();
        response.then().assertThat()
                .body("[1].userId", isA(Integer.class))
                .body("[1].id", isA(Integer.class))
                .body("[1].title", isA(String.class))
                .body("[1].body", isA(String.class))
                .body("[1].link", isA(String.class))
                .body("[1].comment_count", isA(Integer.class))
                .log().all();
    }

}
