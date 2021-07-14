package com.parjanya.samplepermissionapp

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.parjanya.samplepermissionapp.base.BaseActivity
import com.parjanya.samplepermissionapp.databinding.ActivityLiveDataBinding

class LiveDataActivity : BaseActivity<ActivityLiveDataBinding>() {

    override val binding by lazy { ActivityLiveDataBinding.inflate(layoutInflater) }

    private val fruitList = MutableLiveData(listOf("Apple", "Orange", "Banana", "Grape", "Watermelon"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fruitList.observe(this, { fruitList ->
            binding.listTv1.text = fruitList[0]
            binding.listTv2.text = fruitList[1]
            binding.listTv3.text = fruitList[2]
            binding.listTv4.text = fruitList[3]
            binding.listTv5.text = fruitList[4]
        })
        binding.shuffleBtn.setOnClickListener { fruitList.value = fruitList.value?.shuffled() }
    }
}