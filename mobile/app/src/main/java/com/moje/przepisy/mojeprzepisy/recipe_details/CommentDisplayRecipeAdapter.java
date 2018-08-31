package com.moje.przepisy.mojeprzepisy.recipe_details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.moje.przepisy.mojeprzepisy.R;
import com.moje.przepisy.mojeprzepisy.data.model.Comment;
import java.sql.Timestamp;
import java.util.List;

public class CommentDisplayRecipeAdapter extends RecyclerView.Adapter<CommentDisplayRecipeAdapter.ViewHolder> {
  public Context context;
  private List<Comment> commentList;

  CommentDisplayRecipeAdapter(Context context, List<Comment> commentList){
    this.context = context;
    this.commentList = commentList;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public CommentDisplayRecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View v = layoutInflater.inflate(R.layout.one_comment_display_layout, parent, false);
    return new CommentDisplayRecipeAdapter.ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull CommentDisplayRecipeAdapter.ViewHolder viewHolder, int position) {
    viewHolder.bind(commentList.get(position));
  }

  @Override
  public int getItemCount() {
    return commentList.size();
  }

  @Override
  public long getItemId(int position) {
    return commentList.get(position).getCommentId();
  }


  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.userNameTextView) TextView userNameTextView;
    @BindView(R.id.createTimeTextView) TextView createTimeTextView;
    @BindView(R.id.commentTextView) TextView commentTextView;

    ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }

    void bind(Comment comment) {
      String authorName = comment.getAuthorName();
      Timestamp createTime = comment.getCreatedDate();
      String commentText = comment.getComment();

      userNameTextView.setText(authorName);
      createTimeTextView.setText(createTime.toString());
      commentTextView.setText(commentText);
    }
  }
}
