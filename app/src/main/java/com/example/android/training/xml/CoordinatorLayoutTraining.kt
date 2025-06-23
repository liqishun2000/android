package com.example.android.training.xml

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.databinding.ActivityCoordinatorLayoutTrainingBinding
import com.example.android.R

class CoordinatorLayoutTrainingActivity : ComponentActivity() {

    private val mBinding by lazy { ActivityCoordinatorLayoutTrainingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val list = List(20) { it.toString() }
        val adapter = object : RecyclerView.Adapter<ViewHolder>() {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ViewHolder {
                val item = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_content, parent, false)
                return ViewHolder(item)
            }

            override fun getItemCount(): Int {
                return list.size
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.itemView.findViewById<TextView>(R.id.tvContent).text = list[position]
            }

        }
        mBinding.rvContent.adapter = adapter
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
