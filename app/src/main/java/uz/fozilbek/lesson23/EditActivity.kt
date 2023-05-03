package uz.fozilbek.lesson23

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import uz.fozilbek.lesson23.core.CustomerAdapter
import uz.fozilbek.lesson23.core.db.CustomersDb
import uz.fozilbek.lesson23.core.models.CustomerData
import uz.fozilbek.lesson23.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
   private val binding by lazy{
        ActivityEditBinding.inflate(layoutInflater)
    }
    var idEdit:String=""
    val db = CustomersDb.getInstance()
    private val adapter = CustomerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idEdit= intent.getStringExtra("MY_ID").toString()
        loadData()
        setContentView(binding.root)


        binding.saveButton.setOnClickListener {

            val nameUser = binding.name.text.toString()
            val numberUser = binding.phone.text.toString()
            val ageUser = binding.age.text.toString().toInt()
            val cityUser = binding.city.text.toString()

            val data = CustomerData(id = idEdit.toLong(), name = nameUser, city = cityUser, phoneNumber = numberUser, age = ageUser)
            db.editCustomer(data)
            setResult(Activity.RESULT_OK)
            finish()

        }

    }

    private fun loadData() {
        val customerData: CustomerData? =db.getCustomer(idEdit.toInt())
        binding.age.setText(customerData?.age.toString())
        binding.name.setText(customerData?.name.toString())
        binding.phone.setText(customerData?.phoneNumber.toString())
        binding.city.setText(customerData?.city.toString())
    }


}