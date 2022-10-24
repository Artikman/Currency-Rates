package com.example.currencyconverter.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.network.DataState
import com.example.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var adapter: CurrencyConverterRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding?.btSortByAlphabet?.setOnClickListener {
            adapter.differ.submitList(adapter.differ.currentList.sortedBy { it.currency })
        }

        binding?.btSortByDescendingAlphabet?.setOnClickListener {
            adapter.differ.submitList(adapter.differ.currentList.sortedByDescending { it.currency })
        }

        binding?.btSortByValue?.setOnClickListener {
            adapter.differ.submitList(adapter.differ.currentList.sortedBy { it.amount })
        }

        binding?.btSortByDescendingValue?.setOnClickListener {
            adapter.differ.submitList(adapter.differ.currentList.sortedByDescending { it.amount })
        }

        initRecyclerView()
        observeSupportedCurrencies()
        observeConversions()
        observeSourceCurrencyChange()
    }

    private fun observeSourceCurrencyChange() {
        binding!!.currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    convertCurrency()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun observeConversions() {
        viewModel.conversions.observe(this) {
            when (it) {
                is DataState.Failed -> {
                    it.message
                }
                is DataState.Loaded -> {
                    adapter.differ.submitList(it.data)
                    showProgress(false)
                }
                is DataState.Loading -> {
                    showProgress(true)
                }
            }
        }
    }

    private fun observeSupportedCurrencies() {
        viewModel.supportedCurrencies.observe(this) {
            when (it) {
                is DataState.Failed -> {
                    it.message
                }
                is DataState.Loaded -> {
                    val arrayAdapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        it.data
                    )
                    binding!!.currencySpinner.adapter = arrayAdapter
                }
                is DataState.Loading -> {
                }
            }
        }
    }

    private fun convertCurrency() {
        val selectedCurrency = binding!!.currencySpinner.selectedItem as Currency?
        if (selectedCurrency == null) {
            adapter.differ.submitList(listOf())
        } else {
            viewModel.convert(selectedCurrency.currency)
        }
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            binding!!.progressBar.visibility = View.VISIBLE
            binding!!.convertedRecyclerView.visibility = View.GONE
        } else {
            binding!!.convertedRecyclerView.visibility = View.VISIBLE
            binding!!.progressBar.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding!!.convertedRecyclerView.apply {
            adapter = this@MainActivity.adapter
        }
    }

}