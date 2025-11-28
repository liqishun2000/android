package com.example.android.training.xml

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R
import com.example.android.databinding.ActivityRecycleViewBinding
import com.example.core.ktx.log

/**
 * RecycleView可以设置SnapHelper做吸附，
 * 通过SnapHelper可以获取吸附后的position
 * GridLayoutManager.SpanSizeLookup 可以自定义行的跨度
 * 外层传入unspecified的测量模式，RecycleView的高度就不受自己控制了，
 * RecycleView会全部展开。只要外层是NestedScrollView，NestedScrollView是mathParent
 * 或者wrapContent都一样
 * 在NestedScrollView中，只有嵌套一层ViewGroup后，RecycleView设置固定高度才不会全部展开
 * */
class RecycleViewTraining: ComponentActivity() {

    private var _binding: ActivityRecycleViewBinding? = null
    private val mBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityRecycleViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.rvContent.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_content, parent, false)
                return object : RecyclerView.ViewHolder(inflate) {}
            }

            @SuppressLint("SetTextI18n")
            override fun onBindViewHolder(
                holder: RecyclerView.ViewHolder,
                position: Int
            ) {
                log("RecycleViewTraining>>>onBindViewHolder>>>position:$position")
                holder.itemView.findViewById<TextView>(R.id.tvContent).text = "test: $position"
            }

            override fun getItemCount(): Int {
                return 20
            }

        }
    }
}