package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails;

public interface CommentEditContract {

    interface View {
        void setToolbar();
        void setComment();
        void setCommentId();
        void setRecipeId();
        void setCommentEditText();
        String getCommentAfterEdit();
        void goToMainDetailsTabActivity();
        void setListeners();
        void setErrorTextView(String message);
    }

    interface Presenter {
        void setFirstScreen();
        void sendCommentToServer(int commentId, String comment);
    }
}
