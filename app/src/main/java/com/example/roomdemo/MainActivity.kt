package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var itemAdapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val employeeDao = (application as EmployeeApp).db.employeeDao()


        binding?.btnAddRecord?.setOnClickListener {
            addRecord(employeeDao = employeeDao)
        }

        lifecycleScope.launch {
            employeeDao.getAllEmployees().collect() {
                setRecyclerViewList(ArrayList(it), EmployeeDao = employeeDao)
            }
        }
    }

    private fun setRecyclerViewList(employees : ArrayList<EmployeeEntity>, EmployeeDao: EmployeeDAO){

        if (employees.isNotEmpty()) {
            itemAdapter = ItemAdapter(employees

                )
            binding?.rvRecords?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding?.rvRecords?.adapter = itemAdapter
            binding?.rvRecords?.visibility = View.VISIBLE
            binding?.tvNoRecords?.visibility = View.INVISIBLE
        } else {
            binding?.tvNoRecords?.visibility = View.VISIBLE
            binding?.rvRecords?.visibility = View.INVISIBLE
        }
    }

    fun addRecord(employeeDao : EmployeeDAO){
        if (isValidInput()) {
            val tempName = binding?.etName?.text.toString()
            val tempEmail = binding?.etEmail?.text.toString()

            lifecycleScope.launch{
                employeeDao.insert(EmployeeEntity(name = tempName, email = tempEmail)) //suspended function
                Toast.makeText(applicationContext, "${itemAdapter?.itemCount}", Toast.LENGTH_SHORT).show()
                binding?.etName?.text?.clear()
                binding?.etEmail?.text?.clear()

            }
        } else {
            Toast.makeText(applicationContext, "${itemAdapter?.itemCount}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isValidInput() : Boolean{
        return !(binding?.etName?.text.toString().isEmpty() || binding?.etEmail?.text.toString().isEmpty())
    }
}