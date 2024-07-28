package com.store.itauseguros.api;

import com.itauseguros.model.PageableProducts;
import com.itauseguros.model.Product;
import com.itauseguros.model.ProductRequestDTO;
import com.store.itauseguros.api.client.CucumberClient;
import com.store.itauseguros.domain.TariffCalculator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import retrofit2.Call;
import retrofit2.Response;


import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class CucumberFeatures {

    private final CucumberClient cucumberClient;
    private Response<Product> response;
    private Response<PageableProducts> pageableProductsResponse;
    private final ProductRequestDTO productRequestDTO = new ProductRequestDTO();

    // Atributos para GET
    private String id;
    private String category;
    private String name;
    private Integer limit;
    private Integer offset;
    private String sort;

    public CucumberFeatures(CucumberClient cucumberClient) {
        this.cucumberClient = cucumberClient;
    }

    // Post
    @Given("be informed of the base price category and name")
    public void beInformedOfTheBasePriceCategoryAndName() {
        productRequestDTO.setBasePrice(100.00);
        productRequestDTO.setCategory("VIDA");
        productRequestDTO.setName("SEGURO DE VIDA");
    }

    @When("make the post request")
    public void makeThePostRequest() throws Exception {
        Call<Product> call = cucumberClient.productsPost(productRequestDTO);
        response = call.execute();
    }

    @Then("must return on screen the registered values in the base plus the tariffed price")
    public void mustReturnOnScreenTheRegisteredValuesInTheBasePlusTheTariffedPrice() {
        Product productResponse = response.body();
        assertThat(productResponse).isNotNull();
        assert productResponse != null;
        assertThat(productResponse.getBasePrice()).isEqualTo(100.00);
        assertThat(productResponse.getCategory()).isEqualTo("VIDA");
        assertThat(productResponse.getName()).isEqualTo("SEGURO DE VIDA");
        assertThat(productResponse.getTariffPrice()).isEqualTo(TariffCalculator.calculateTariffPrice(productResponse.getBasePrice(), productResponse.getCategory()));
    }

    // Get
    @Given("iven I set the search parameters null")
    public void ivenISetTheSearchParametersNull() {
        this.id = null;
        this.category = null;
        this.name = null;
        this.limit = null;
        this.offset = null;
        this.sort = null;
    }

    @When("I call the API with the search parameters")
    public void iCallTheApiWithTheSearchParameters() throws IOException {
        productRequestDTO.setBasePrice(100.00);
        productRequestDTO.setCategory("VIDA");
        productRequestDTO.setName("SEGURO DE VIDA");
        Call<Product> call = cucumberClient.productsPost(productRequestDTO);
        response = call.execute();
        pageableProductsResponse = callGetProducts(id, category, name, limit, offset, sort);
    }

    @Then("the response status code should be {int} if you do not have recorded records")
    public void theResponseStatusCodeShouldBeIfYouDoNotHaveRecordedRecords(int expectedStatusCode) {
        assertThat(pageableProductsResponse).isNotNull();
        assertThat(pageableProductsResponse.code()).isEqualTo(expectedStatusCode);
    }


    private Response<PageableProducts> callGetProducts(String id, String category, String name, Integer limit, Integer offset, String sort) throws IOException {
        Call<PageableProducts> call = cucumberClient.productsGet(id, category, name, limit, offset, sort);
        return call.execute();
    }


    //Put
    @Given("there is a product registered with the following category {string} basePrice {double} name {string}")
    public void thereIsAProductRegisteredWithTheFollowingCategoryBasePriceName(String arg0, double arg1, String arg3) {
        productRequestDTO.setName(arg3);
        productRequestDTO.setCategory(arg0);
        productRequestDTO.setBasePrice(arg1);
    }

    @When("update basePrice {double}")
    public void updateBasePrice(double arg0) throws IOException {
        Call<Product> call = cucumberClient.productsPost(productRequestDTO);
        response = call.execute();
        productRequestDTO.setBasePrice(arg0);
        assert response.body() != null;
        Call<Product> updateCall = cucumberClient.productsProductIdPut(response.body().getId(), productRequestDTO);
        response = updateCall.execute();
    }


    @Then("must return on screen the registered values in the base plus the tariffed price to updated")
    public void mustReturnOnScreenTheRegisteredValuesInTheBasePlusTheTariffedPriceToUpdated() {
        Product productResponse = response.body();
        assertThat(productResponse).isNotNull();
        assert productResponse != null;
        assertThat(productResponse.getBasePrice()).isEqualTo(productResponse.getBasePrice());
        assertThat(productResponse.getCategory()).isEqualTo(productResponse.getCategory());
        assertThat(productResponse.getName()).isEqualTo(productResponse.getName());
        assertThat(productResponse.getTariffPrice()).isEqualTo(TariffCalculator.calculateTariffPrice(productResponse.getBasePrice(), productResponse.getCategory()));
    }

}