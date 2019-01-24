package com.moje.przepisy.mojeprzepisy.recipeDetails.commentDetails.commentEdit;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.repositories.recipe.RecipeRepository;
import com.moje.przepisy.mojeprzepisy.recipeDetails.recipeDisplay.MainDetailsTabActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentEditActivity extends AppCompatActivity
        implements CommentEditContract.View{
    @BindView(R.id.toolbar_comment_edit) Toolbar toolbar;
    @BindView(R.id.commentEditText) EditText commentEditText;
    @BindView(R.id.updateCommentButton) Button updateCommentButton;
    @BindView(R.id.cancelCommentButton) Button cancelCommentButton;
    @BindView(R.id.errorEditCommentTextView) TextView errorEditCommentTextView;
    private CommentEditContract.Presenter presenter;
    private String comment;
    private int commentId;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_edit);

        ButterKnife.bind(this);

        presenter = new CommentEditPresenter(this, new RecipeRepository(this));
        presenter.setFirstScreen();

    }

    @Override
    public void setComment(){
        if(getIntent().getExtras() != null){
            comment = getIntent().getExtras().getString("comment");
        }
    }

    @Override
    public void setCommentId(){
        if(getIntent().getExtras() != null){
            commentId = getIntent().getExtras().getInt("commentId");
        }
    }

    @Override
    public void setRecipeId(){
        if(getIntent().getExtras() != null){
            recipeId = getIntent().getExtras().getInt("recipeId");
        }
    }

    public void setCommentEditText(){
        commentEditText.setText(comment);
    }

    public String getCommentAfterEdit(){
        return commentEditText.getText().toString();
    }

    public void goToMainDetailsTabActivity(){
        Intent intent = new Intent(this, MainDetailsTabActivity.class);
        Bundle data = new Bundle();
        data.putBoolean("isLogged", true);
        data.putInt("id", recipeId);
        data.putInt("position", 3);
        intent.putExtras(data);
        startActivity(intent);
        CommentEditActivity.this.finish();
    }

    @Override
    public void setListeners() {
        updateCommentButton.setOnClickListener(view ->
                presenter.sendCommentToServer(commentId, getCommentAfterEdit()));
        cancelCommentButton.setOnClickListener(view ->
                goToMainDetailsTabActivity());
    }

    @Override
    public void setErrorTextView(String message) {
        errorEditCommentTextView.setVisibility(View.VISIBLE);
        errorEditCommentTextView.setText(message);
    }

    public void setToolbar() {
        toolbar.setSubtitle(R.string.edit_comment);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        goToMainDetailsTabActivity();
        return true;
    }

    @Override
    public void onBackPressed() {
        goToMainDetailsTabActivity();
    }
}
