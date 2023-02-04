package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var adapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        adapter = ItemAdapter()
        binding?.rvRecords?.adapter = adapter

        binding?.btnAddRecord?.setOnClickListener {
            if (isValidInput()) {
                var tempName = binding?.etName?.text.toString()
                var tempEmail = binding?.etEmail?.text.toString()
                // addRecord()
            }
        }

    }

    private fun isValidInput() : Boolean{
        return !(binding?.etName?.text.toString().isEmpty() || binding?.etEmail?.text.toString().isEmpty())
    }
}