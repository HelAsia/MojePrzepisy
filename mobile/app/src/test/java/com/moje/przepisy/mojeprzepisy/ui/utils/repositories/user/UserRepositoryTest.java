package com.moje.przepisy.mojeprzepisy.ui.utils.repositories.user;

import com.moje.przepisy.mojeprzepisy.data.network.UserAPI;
import com.moje.przepisy.mojeprzepisy.data.ui.utils.repositories.user.UserRepositoryInterface.OnLoginFinishedListener;
import java.io.IOException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserRepositoryTest {
  private MockWebServer mockWebServer;

  @Mock
  OnLoginFinishedListener listener;
  UserAPI userAPI;

  @Before
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    mockWebServer = new MockWebServer();

  }

  @Test
  public void loginIsCorrectTest() throws InterruptedException, IOException {
/*    mockWebServer.enqueue(new MockResponse().setBody("{'status':200}"));
    mockWebServer.start();
    HttpUrl loginURL = mockWebServer.url("/login");

    String body = loginURL.toString();
    mockWebServer.

    assertEquals("{'status':200}", body);*/

 //   userAPI.login(new User("test", "test"));

//    RecordedRequest request = mockWebServer.takeRequest();
//    assertEquals("POST /login HTTP/1.1", request.getRequestLine());

  }
}
