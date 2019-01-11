package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails;


import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepositoryInterface;

public class CommentEditPresenter implements CommentEditContract.Presenter {
    private CommentEditContract.View commentEditView;
    private RecipeRepository recipeRepository;

    CommentEditPresenter(CommentEditContract.View commentEditView, RecipeRepository recipeRepository) {
        this.commentEditView = commentEditView;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void setFirstScreen() {
        commentEditView.setToolbar();
        commentEditView.setComment();
        commentEditView.setCommentId();
        commentEditView.setRecipeId();
        commentEditView.setListeners();
        commentEditView.setCommentEditText();
    }

    @Override
    public void sendCommentToServer(int commentId, String comment) {
        recipeRepository.editComment(commentId, comment,
                new RecipeRepositoryInterface.OnEditCommentsDetailsDisplayListener() {
            @Override
            public void onSuccess() {
                commentEditView.goToMainDetailsTabActivity();
            }

            @Override
            public void onError() {
                commentEditView.setErrorTextView("Błąd podczas aktualizacji komentarza!");
            }
        });
    }
}
