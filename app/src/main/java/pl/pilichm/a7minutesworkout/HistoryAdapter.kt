package pl.pilichm.a7minutesworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(val context: Context, private val items: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val llHistoryItemMain: LinearLayout = view.findViewById(R.id.llHistoryItemMain)
        val tvPosition: TextView = view.findViewById(R.id.tvPosition)
        val tvItem: TextView = view.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_history_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = items[position]

        if (position%2==0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}