package br.com.viacepapplication.api

import br.com.viacepapplication.model.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface apiService {
    @GET("{cep}/json")
    fun getAddress(
        @Path("cep") cep : String
    ): Call<AddressResponse>
}