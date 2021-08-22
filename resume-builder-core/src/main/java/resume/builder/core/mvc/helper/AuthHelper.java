package resume.builder.core.mvc.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class AuthHelper {
    private static final Logger logger = LoggerFactory.getLogger(AuthHelper.class);

    @Value("${resume.builder.auth.admin.client.id}")
    private String adminClientId;

    @Value("${resume.builder.auth.admin.client.secret}")
    private String adminClientSecret;

    @Value("${resume.builder.auth.admin.token.api}")
    private String adminTokenApi;

    @Value("${resume.builder.auth.client.id}")
    private String clientId;

    @Value("${resume.builder.auth.client.secret}")
    private String clientSecret;

    @Value("${resume.builder.auth.token.api}")
    private String tokenApi;

    @Value("${resume.builder.auth.create.new.user.api}")
    private String authCreateNewUserApi;

    @Value("${resume.builder.auth.find.user.api}")
    private String authFindUserApi;

    @Value("${resume.builder.auth.logout.api}")
    private String authLogoutApi;

    @SneakyThrows
    private CloseableHttpResponse sendGetRequest(String URI, String token)
    {
        HttpGet request = new HttpGet(URI);
        Optional.ofNullable(token).ifPresent(t ->
                request.setHeader("Authorization", "Bearer " + t)
        );

        return HttpClientBuilder.create().build().execute(request);
    }

    @SneakyThrows
    private CloseableHttpResponse sendPostRequest(String URI, HttpEntity entity, String contentType, String token)
    {
        HttpPost request = new HttpPost(URI);
        request.setEntity(entity);
        request.setHeader("Content-Type", contentType);
        Optional.ofNullable(token).ifPresent(t ->
                request.setHeader("Authorization", "Bearer " + t)
        );

        return HttpClientBuilder.create().build().execute(request);
    }

    private CloseableHttpResponse sendPostRequest(String URI, HttpEntity entity, String contentType)
    {
        return this.sendPostRequest(URI, entity, contentType, null);
    }

    @SneakyThrows
    public final Optional<String> getAdminAccessToken()
    {
        logger.info("Getting admin access token from auth server");

        Optional<String> accessToken = Optional.empty();

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("client_id", adminClientId));
        params.add(new BasicNameValuePair("client_secret", adminClientSecret));

        CloseableHttpResponse response = sendPostRequest(adminTokenApi, new UrlEncodedFormEntity(params, StandardCharsets.UTF_8), MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        StatusLine statusLine= response.getStatusLine();
        final int statusCode = statusLine.getStatusCode();
        final String responseBody = EntityUtils.toString(response.getEntity());
        if(statusCode == HttpStatus.SC_OK){
            JsonNode jsonContent = new ObjectMapper().readTree(responseBody);
            accessToken = Optional.of(jsonContent.get("access_token").textValue());
        }
        else{
            logger.error("Failed: {}:{} - {}", statusCode, statusLine.getReasonPhrase(), responseBody);
        }

        return accessToken;
    }

    @SneakyThrows
    public final Boolean createNewUserByAccessTokenAndUsernameAndPassword(String accessToken, String username, String password)
    {
        logger.info("Creating new user at auth server");

        JSONObject credentialJson = new JSONObject();
        JSONArray credentialJsonArray = new JSONArray();
        credentialJson.put("type", "password");
        credentialJson.put("value", password);
        credentialJson.put("temporary", "false");
        credentialJsonArray.add(credentialJson);

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("enabled", "true");
        json.put("credentials", credentialJsonArray);

        CloseableHttpResponse response = sendPostRequest(authCreateNewUserApi, new StringEntity(json.toString()), MediaType.APPLICATION_JSON_VALUE, accessToken);

        StatusLine statusLine= response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_CREATED){
            return true;
        } else{
            logger.error("Failed: {}:{} - {}", statusLine.getStatusCode(), statusLine.getReasonPhrase(), EntityUtils.toString(response.getEntity()));
            return false;
        }
    }

    @SneakyThrows
    public final Optional<String> getAuthUserIdByAccessTokenAndUsername(String accessToken, String username)
    {
        logger.info("Getting user id from auth server");

        Optional<String> authUserId = Optional.empty();

        URIBuilder uri = new URIBuilder(authFindUserApi);
        uri.addParameter("username", username);

        CloseableHttpResponse response = sendGetRequest(uri.toString(), accessToken);

        StatusLine statusLine= response.getStatusLine();
        final int statusCode = statusLine.getStatusCode();
        final String responseBody = EntityUtils.toString(response.getEntity());
        if(statusCode == HttpStatus.SC_OK){
            JsonNode jsonContent = new ObjectMapper().readTree(responseBody);
            authUserId = Optional.of(jsonContent.get(0).get("id").textValue());
        }
        else{
            logger.error("Failed: {}:{} - {}", statusCode, statusLine.getReasonPhrase(), responseBody);
        }

        return authUserId;
    }

    public final Map<String, String> getTokenByUsernameAndPassword(String username, String password)
    {
        logger.info("{} - Getting token from auth server", username);

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("scope", "openid"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        return getUserToken(params);
    }

    public final Map<String, String> getRefreshedToken(String refreshToken)
    {
        logger.info("Getting refreshed token from auth server");

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("refresh_token", refreshToken));

        return getUserToken(params);
    }

    @SneakyThrows
    private Map<String, String> getUserToken(List<BasicNameValuePair> params)
    {
        CloseableHttpResponse response = sendPostRequest(tokenApi, new UrlEncodedFormEntity(params, StandardCharsets.UTF_8), MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        Map<String, String> token = new HashMap<>();
        StatusLine statusLine= response.getStatusLine();
        final int statusCode = statusLine.getStatusCode();
        final String responseBody = EntityUtils.toString(response.getEntity());
        if(statusCode == HttpStatus.SC_OK){
            JsonNode jsonContent = new ObjectMapper().readTree(responseBody);
            token.put("access_token", jsonContent.get("access_token").textValue());
            token.put("refresh_token", jsonContent.get("refresh_token").textValue());
        }
        else{
            logger.error("Failed: {}:{} - {}", statusCode, statusLine.getReasonPhrase(), responseBody);
        }

        return token;
    }

    @SneakyThrows
    public final void logoutByRefreshToken(String refreshToken)
    {
        logger.info("{} - logout", SecurityContextHolder.getContext().getAuthentication().getName());

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("refresh_token", refreshToken));

        CloseableHttpResponse response = sendPostRequest(authLogoutApi, new UrlEncodedFormEntity(params, StandardCharsets.UTF_8), MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        StatusLine statusLine= response.getStatusLine();
        final int statusCode = statusLine.getStatusCode();
        if(statusCode != HttpStatus.SC_NO_CONTENT){
            logger.error("Failed: {}:{} - {}", statusCode, statusLine.getReasonPhrase(), EntityUtils.toString(response.getEntity()));
        }
    }
}
