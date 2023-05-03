package uz.fozilbek.lesson23

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.fozilbek.lesson23.core.db.CustomersDb
import uz.fozilbek.lesson23.core.models.CustomerData
import uz.fozilbek.lesson23.databinding.ActivityAddCustomerBinding

class AddCustomerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddCustomerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = CustomersDb.getInstance()

        binding.saveButton.setOnClickListener {

            val nameUser = binding.name.text.toString()
            val numberUser = binding.phone.text.toString()
            val ageUser = binding.age.text.toString().toInt()
            val cityUser = binding.city.text.toString()

            val data = CustomerData(id = 0, name = nameUser, city = cityUser, phoneNumber = numberUser, age = ageUser)
            db.addCustomer(data)

            setResult(Activity.RESULT_OK)
            finish()

        }

    }
}