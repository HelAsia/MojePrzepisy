package com.moje.przepisy.mojeprzepisy.logOut;

import org.junit.Test;
import org.mockito.Mockito;

public class LogOutPresenterTest {

    @Test
    public void onCancelNavigatesToMainActivity() {
        LogOutContract.View logoutView = Mockito.mock(LogOutContract.View.class);
        LogOutPresenter presenter = new LogOutPresenter(logoutView, null);
        presenter.onCancel();
        Mockito.verify(logoutView).navigateToMainLoggedCardsActivity();
    }
}