@file:Suppress("DEPRECATION")

package io.github.arwazkhan189.socialapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.arwazkhan189.socialapp.models.Post


class PostAdapter(
    options: FirestoreRecyclerOptions<Post>,
    @Suppress("DEPRECATION") private val listener: IPostAdapter
) :
    FirestoreRecyclerAdapter<Post, PostAdapter.PostViewHolder>(options) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPostTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvCreatedAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
        val tvLikeCount: TextView = itemView.findViewById(R.id.tvLikeCount)
        val imgUser: ImageView = itemView.findViewById(R.id.imgUser)
        val btnLike: ImageView = itemView.findViewById(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
        viewHolder.btnLike.setOnClickListener {
            listener.onLikeClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Post) {
        holder.tvPostTitle.text = model.text
        holder.tvUsername.text = model.createdBy.displayName
        Glide.with(holder.imgUser.context).load(model.createdBy.imageUrl).circleCrop()
            .into(holder.imgUser)
        holder.tvLikeCount.text = model.likedBy.size.toString()
        holder.tvCreatedAt.text = Utils.getTimeAgo(model.createdAt)

        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid

        val isLiked = model.likedBy.contains(currentUserId)
        if (isLiked) {
            holder.btnLike.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.btnLike.context,
                    R.drawable.ic_liked
                )
            )
        } else {
            holder.btnLike.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.btnLike.context,
                    R.drawable.ic_unliked
                )
            )
        }
    }

}

interface IPostAdapter {
    fun onLikeClicked(postId: String)
}