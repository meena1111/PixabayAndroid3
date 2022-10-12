package com.example.pixabayandroid3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pixabayandroid3.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var adapter = PixabayAdapter(arrayListOf())
    var page = 1
    var perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerWord.adapter = adapter
        initClicker()
    }

    private fun initClicker() {
        with(binding) {
            btnStartSearching.setOnClickListener {
                doRequest()
            }
            btnChangeWord.setOnClickListener {
                ++page
                doRequest()
            }
            btnAddWord.setOnClickListener {
                doRequest()
            }
        }
    }

    private fun doRequest() {
        App.api.getImages(binding.etSearchingWord.text.toString(), page = page, perPage = perPage)
            .enqueue(object :
                Callback<PixabayModel> {
                override fun onResponse(
                    call: Call<PixabayModel>,
                    response: Response<PixabayModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.hits?.forEach {
                            adapter.addImage(it)
                        }
                    }
                }

                override fun onFailure(call: Call<PixabayModel>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Ой что то пошло не так!\n Повторите попытку позже",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }
}