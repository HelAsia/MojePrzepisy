package com.moje.przepisy.mojeprzepisy.sendError;

import android.content.Context;

class SendErrorContract {

    interface View {
        Context getContext();
        void setToolbar();
        void setButtonListener();
        void setLogged();
    }

    interface Presenter {
        void setFirstScreen();
    }
}
