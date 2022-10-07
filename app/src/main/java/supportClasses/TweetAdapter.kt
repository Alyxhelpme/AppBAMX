package supportClasses

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.bamx.R
import org.w3c.dom.Text
import java.util.concurrent.Executors

class TweetAdapter(var tweetUserNames : ArrayList<String>,
                   var tweetTexts : ArrayList<String>,
                   var tweetUrls : ArrayList<String>) :
    RecyclerView.Adapter<TweetAdapter.TweetViewHolder>(){

    class TweetViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView){

        var tweetUserName : TextView
        var tweetText : TextView
        var tweetUrl : String

        init {
            tweetUserName = itemView.findViewById(R.id.userDisplayName)
            tweetText = itemView.findViewById(R.id.tweetText)
            tweetUrl = ""

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tweet, parent, false)
        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.tweetUserName.text = tweetUserNames[position]
        holder.tweetText.text = tweetTexts[position]
        holder.tweetUrl = tweetUrls[position]

    }

    override fun getItemCount(): Int {
        return tweetUserNames.size
    }
}
