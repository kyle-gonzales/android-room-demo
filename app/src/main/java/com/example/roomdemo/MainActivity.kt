package com.example.roomdemo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.DialogUpateBinding
import kotlinx.coroutines.flow.firstOrNull
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
                setRecyclerViewList(ArrayList(it), employeeDao)
            }
        }
    }

    private fun setRecyclerViewList(employees : ArrayList<EmployeeEntity>, employeeDao: EmployeeDAO){

        if (employees.isNotEmpty()) {
            itemAdapter = ItemAdapter(employees,
                {updateId -> updateRecord(updateId, employeeDao)},
                {deleteId -> deleteRecord(deleteId, employeeDao)}
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

    private fun addRecord(employeeDao : EmployeeDAO){
        if (isValidInput()) {
            val tempName = binding?.etName?.text.toString()
            val tempEmail = binding?.etEmail?.text.toString()

            lifecycleScope.launch{
                employeeDao.insert(EmployeeEntity(name = tempName, email = tempEmail)) //suspended function
                Toast.makeText(applicationContext, "new record added", Toast.LENGTH_SHORT).show()
                binding?.etName?.text?.clear()
                binding?.etEmail?.text?.clear()
            }
        } else {
            Toast.makeText(applicationContext, "invalid input. make sure to fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isValidInput() : Boolean{
        return !(binding?.etName?.text.toString().isEmpty() || binding?.etEmail?.text.toString().isEmpty())
    }

    private fun updateRecord(id: Int, employeeDao: EmployeeDAO) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val binding = DialogUpateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            employeeDao.getEmployeeById(id).firstOrNull().also { employee ->
                binding.etName.setText(employee?.name)
                binding.etEmail.setText(employee?.email)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()

            if (!(binding.etName.text.toString().isEmpty() || binding.etEmail.text.toString().isEmpty())) {
                lifecycleScope.launch {
                    employeeDao.update(EmployeeEntity(id, name, email))
                    updateDialog.dismiss()
                    Toast.makeText(applicationContext, "employee updated", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "invalid input. make sure to fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancel.setOnClickListener {
            updateDialog.dismiss()
        }

        updateDialog.show()
    }

    private fun deleteRecord(id: Int, employeeDao: EmployeeDAO) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Delete")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Delete")  { dialog, _ ->
                lifecycleScope.launch {
                    employeeDao.delete(EmployeeEntity(id))
                    Toast.makeText(applicationContext, "employee deleted", Toast.LENGTH_SHORT).show()

                }
            }
            .setNegativeButton("Cancel") {
                dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)

        val deleteDialog = builder.create()
        deleteDialog.show()
    }
}