package com.dannextech.apps.daktari_online;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.dannextech.apps.daktari_online.model.AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TokenGen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_gen);


        GenToken genToken = new GenToken();

        genToken.execute();

    }

    private class GenToken extends AsyncTask<Void, Void, Void> {

        private AccessToken token;
        private String language;
        private String healthServiceUrl;

        private HttpClient httpclient;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.e("Dannex Daniels", "onCreate: getting in");
                diagnosisClient("dannexdaniels@gmail.com","t3B8ScMf9j7RWq64A","https://authservice.priaid.ch/login","en","https://healthservice.priaid.ch");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /// <summary>
        /// DiagnosisClient constructor
        /// </summary>
        /// <param name="username">api user username</param>
        /// <param name="password">api user password</param>
        /// <param name="authServiceUrl">priaid login url (https://authservice.priaid.ch/login)</param>
        /// <param name="language">language</param>
        /// <param name="healthServiceUrl">priaid healthservice url(https://healthservice.priaid.ch)</param>
        public void diagnosisClient(String userName, String password, String authServiceUrl, String language, String healthServiceUrl) throws Exception {

            Log.e("Dannex Daniels", "diagnosisClient: I'm in");
            HandleRequiredArguments(userName, password, authServiceUrl, language, healthServiceUrl);

            httpclient = new DefaultHttpClient();

            this.healthServiceUrl = healthServiceUrl;
            this.language = language;

            LoadToken(userName, password, authServiceUrl);

        }

        private void HandleRequiredArguments(String username, String password, String authServiceUrl, String language, String healthServiceUrl)     {
            if (username == null || username.isEmpty())
                throw new IllegalArgumentException("Argument missing: username");

            if (password == null || password.isEmpty())
                throw new IllegalArgumentException("Argument missing: password");

            if (authServiceUrl == null || authServiceUrl.isEmpty())
                throw new IllegalArgumentException("Argument missing: authServiceUrl");

            if (language == null || language.isEmpty())
                throw new IllegalArgumentException("Argument missing: language");

            if (healthServiceUrl == null || healthServiceUrl.isEmpty())
                throw new IllegalArgumentException("Argument missing: healthServiceUrl");
        }





        private void LoadToken(String username, String password, String url) throws Exception {

            Log.e("Dannex Daniels", "LoadToken: "+url );
            SecretKeySpec keySpec = new SecretKeySpec(
                    password.getBytes(),
                    "HmacMD5");

            String computedHashString = "";
            try {
                Mac mac = Mac.getInstance("HmacMD5");
                mac.init(keySpec);
                byte[] result = mac.doFinal(url.getBytes("UTF-8"));

                //BASE64 encoder = new BASE64();
                computedHashString = Base64.encodeToString(result, Base64.DEFAULT);

                Log.e("Dannex Daniels", "LoadToken: "+computedHashString);

            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (NoSuchAlgorithmException)");
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (InvalidKeyException)");
            }

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Authorization", "Bearer " + username + ":" + computedHashString);

            Log.e("Dannex Daniels", "LoadToken: "+httpPost.getHeaders("Authorization"));
            try {
                HttpResponse response = httpclient.execute(httpPost);

                ObjectMapper objectMapper = new ObjectMapper();
                if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                    RetrieveException(response, objectMapper);
                }
                AccessToken accessToken = objectMapper.readValue(response.getEntity().getContent(), AccessToken.class);
                token = accessToken;

                Log.e("Dannex Daniels", "LoadToken token = "+token);
            }
            catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (ClientProtocolException)");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (IOException)");
            } catch (NetworkOnMainThreadException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw new Exception("Can not create token (NetworkOnMainThreadException)");
            }
        }

        private void RetrieveException(HttpResponse response, ObjectMapper objectMapper) throws Exception {

            Log.e("Dannex Daniels","ObjectMapper = "+objectMapper);
            InputStream res = response.getEntity().getContent();
            String errorMessage = objectMapper.readValue(res, String.class);
            Log.e("Dannex Daniels","Response = "+res);

            Log.e("Dannex Daniels", "RetrieveException: Resposne with status code: " + response.getStatusLine().getStatusCode() + ", error message: " + errorMessage);
            throw new Exception(errorMessage);
        }
    }
}




