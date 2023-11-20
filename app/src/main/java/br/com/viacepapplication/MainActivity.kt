package br.com.viacepapplication

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import br.com.viacepapplication.api.apiService
import br.com.viacepapplication.model.AddressResponse
import br.com.viacepapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var etCepInput : EditText
    private lateinit var searchButton : Button
    private lateinit var tvResultCep: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCepInput = findViewById(R.id.editTextCep)
        tvResultCep = findViewById(R.id.textAddress)
        searchButton = findViewById(R.id.button)

        searchButton.setOnClickListener{
            var cep : String = etCepInput.text.toString()
            getAddressByCep(cep)

            // Ocultar o teclado
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun getAddressByCep(cep: String) {
        val retrofitClient = RetrofitClient.getRetrofitInstance("https://viacep.com.br/ws/")
        val endpoint = retrofitClient.create(apiService::class.java)

        endpoint.getAddress(cep).enqueue(object : retrofit2.Callback<AddressResponse>{
            override fun onResponse(call: Call<AddressResponse>,
                                    response: Response<AddressResponse>) {

                var data = response.body()

                if (data != null && data?.logradouro != null) {
                    tvResultCep.text =
                        "${data?.logradouro}, ${data?.bairro}, ${data?.localidade} - ${data?.uf}"
                } else {
                    tvResultCep.text = "Digite um CEP válido"
                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                println("Erro")
            }
        })
    }
}