package com.via.boleto.grafica;
/**
 * Created by Matheus Silva on 09/04/2018.
 */
import com.google.gson.JsonObject;
import com.via.boleto.grafica.model.AutenticacaoTO;
import com.via.boleto.grafica.model.AutenticarPostTO;
import com.via.boleto.grafica.model.ProdutoTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface iRetrofit {
    @GET("Produtos/")
    Call<List<ProdutoTO>> getProduto();

    @POST("/Api/Usuario/")
    Call<AutenticacaoTO> getAutenticacao(@Body AutenticarPostTO autenticarPostTO);

//    @GET("Dinheiro")
//    Call<CreditoTo> getDinheiro();
//
//    @GET("Dinheiro")
//    Call<DebitoTo> getDinheiro();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://sggfit.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
