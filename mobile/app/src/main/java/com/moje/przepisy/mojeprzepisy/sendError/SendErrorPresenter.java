package com.moje.przepisy.mojeprzepisy.sendError;

public class SendErrorPresenter implements SendErrorContract.Presenter{
    private SendErrorContract.View sendErrorView;

    SendErrorPresenter(SendErrorContract.View sendErrorView){
        this.sendErrorView = sendErrorView;
    }
    @Override
    public void setFirstScreen() {
        sendErrorView.setToolbar();
        sendErrorView.setButtonListener();
    }
}
