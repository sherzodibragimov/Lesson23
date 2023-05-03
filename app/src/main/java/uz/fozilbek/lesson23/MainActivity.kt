package uz.fozilbek.lesson23

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AbsListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import uz.fozilbek.lesson23.core.CustomerAdapter
import uz.fozilbek.lesson23.core.db.CustomersDb
import uz.fozilbek.lesson23.core.models.CustomerData
import uz.fozilbek.lesson23.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val adapter = CustomerAdapter()
    val customersDb = CustomersDb.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.customersList.adapter = adapter
        adapter.setData(customersDb.getAllCustomers())

        adapter.onItemDelete = {
            val n = customersDb.removeCustomer(it.id)
            if (n > 0) {
                adapter.setData(customersDb.getAllCustomers())
            }
        }
        adapter.onItemEdit = {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("MY_ID",it.id.toString())
            previewRequest.launch(intent)

        }

        binding.addData.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            previewRequest.launch(intent)
        }

        binding.searchEdit.addTextChangedListener{ char->
          val dates: ArrayList<CustomerData> =customersDb.getAllCustomers()
            val newData=ArrayList<CustomerData>()
            for (i in 0 until dates.size){
                if (dates[i].name.toString().contains(char.toString(),ignoreCase = true)){
                    newData.add(dates[i])
                }
            }
            adapter.setData(newData)

        }
        binding.customersList.setOnScrollListener(object : AbsListView.OnScrollListener {
            // var VisibleItem: Int = 0
            override fun onScroll(p0: AbsListView?, FirstVisibleItem: Int, i2: Int, i3: Int) {
            }

            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
                if (p1 == 1) {
                    binding.addData.hide()
                } else {
                    binding.addData.show()
                }
            }

        })
    }


    val previewRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                adapter.setData(customersDb.getAllCustomers())
            }
        }
}