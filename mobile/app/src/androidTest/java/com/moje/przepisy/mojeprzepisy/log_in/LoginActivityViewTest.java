package com.moje.przepisy.mojeprzepisy.log_in;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.moje.przepisy.mojeprzepisy.R;
import org.junit.Test;

public class LoginActivityViewTest extends ActivityInstrumentationTestCase2<LoginActivityView>{
  private static final String EMPTY_STRING = "";
  private static final String GOOD_LOGIN = "A B C 1 2 3 ENTER";
  private static final String GOOD_PASSWORD = "A B C 1 2 3 ENTER";
  TextView errorMessageTextView;
  Button loginButton;
  EditText loginEditText;
  EditText passwordEditText;
  LoginActivityView loginActivityView;

  public LoginActivityViewTest(){
    super(LoginActivityView.class);
  }

  protected void setUp() throws Exception{
    super.setUp();
    loginActivityView = getActivity();
    errorMessageTextView = (TextView) loginActivityView.findViewById(R.id.errorMessageTextView);
    loginButton = (Button) loginActivityView.findViewById(R.id.login_action_button);
    loginEditText = (EditText) loginActivityView.findViewById(R.id.login_editText);
    passwordEditText = (EditText) loginActivityView.findViewById(R.id.password_editText);
  }

  @Test
  public void testPreCondition(){
    String errorText = errorMessageTextView.getText().toString();
    assertEquals(EMPTY_STRING,errorText);

    String loginFirstText = loginEditText.getText().toString();
    String loginFirstHint = loginEditText.getHint().toString();
    assertEquals(EMPTY_STRING,loginFirstText);
    assertEquals(loginActivityView.getResources().getString(R.string.your_login),loginFirstHint);

    String passwordFirstText = passwordEditText.getText().toString();
    String passwordFirstHint = passwordEditText.getHint().toString();
    assertEquals(EMPTY_STRING,passwordFirstText);
    assertEquals(loginActivityView.getResources().getString(R.string.your_password),passwordFirstHint);

    String buttonText = loginButton.getText().toString();
    assertEquals(loginActivityView.getResources().getString(R.string.login),buttonText);
  }

  @Test
  public void testLoginAndPasswordValue(){
    TouchUtils.clickView(this,loginEditText);
    sendKeys(GOOD_LOGIN);

    TouchUtils.clickView(this,passwordEditText);
    sendKeys(GOOD_PASSWORD);

    String loginInputText = loginEditText.getText().toString();
    assertEquals("abc123",loginInputText);

    String passwordInputText = passwordEditText.getText().toString();
    assertEquals("abc123",passwordInputText);
  }
}

