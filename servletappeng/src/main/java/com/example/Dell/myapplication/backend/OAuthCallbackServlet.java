package com.example.Dell.myapplication.backend;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.api.client.util.store.DataStore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Dell on 3/08/2016.
 */
public class OAuthCallbackServlet extends AbstractAppEngineAuthorizationCodeCallbackServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        return OAuthUtils.initializeFlow();
    }

    @Override
    protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
        String userId=getUserId(req);
        return OAuthUtils.getRedirectUri(req);
    }

    @Override
    protected void onSuccess(HttpServletRequest req, HttpServletResponse resp,
                             Credential credential) throws ServletException, IOException {
        System.out.println("in onsuccess");
        StoredCredential storeCredential = new StoredCredential(credential);
        DataStore<StoredCredential> newDataStore = OAuthUtils.DATA_STORE_FACTORY .getDataStore("credentials");
        //Update with actual username
        newDataStore.set("heyyou", storeCredential);
        resp.setStatus(HttpServletResponse.SC_OK);
        //Have some nice confirmation page
        resp.sendRedirect("http://127.0.0.1:8080/newindex.html");
      //  resp.sendRedirect(OAuthUtils.MAIN_SERVLET_PATH);
        OAuthUtils.getDataFromApi(credential);
    }

    @Override
    protected void onError(HttpServletRequest req, HttpServletResponse resp,
                           AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
        System.out.println("Error from oauthcalllback!");
        String nickname="";
        // String nickname = UserServiceFactory.getUserService().getCurrentUser().getNickname();
        resp.getWriter().print(
                "<h3>I am sorry" + getUserId(req)+ ", an internal server error occured. Try it later.</h1>");
        resp.setStatus(500);
        resp.addHeader("Content-Type", "text/html");
        return;
    }

}