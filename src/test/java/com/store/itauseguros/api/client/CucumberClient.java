package com.store.itauseguros.api.client;

import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import retrofit2.Call;
import retrofit2.http.*;


public interface CucumberClient {

    @GET("products")
    Call<PageableProducts> productsGet(
            @Query("id") String id,
            @Query("category") String category,
            @Query("name") String name,
            @Query("_limit") Integer limit,
            @Query("_offset") Integer offset,
            @Query("_sort") String sort
    );

    @POST("products")
    Call<Product> productsPost(
            @Body ProductRequestDTO productRequestDTO
    );

    @PUT("products/{product_id}")
    Call<Product> productsProductIdPut(
            @Path("product_id") String productId,
            @Body ProductRequestDTO productRequestDTO
    );
}
