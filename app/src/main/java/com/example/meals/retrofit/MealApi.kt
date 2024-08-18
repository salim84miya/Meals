package com.example.meals.retrofit

import com.example.meals.pojo.CategoryList
import com.example.meals.pojo.MealsByCategoryList
import com.example.meals.pojo.RandomMealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
   suspend fun getSingleRandomMeal():Response<RandomMealList>

   @GET("lookup.php")
   suspend fun getRandomMealDetail(@Query("i") id:String):Response<RandomMealList>

   @GET("filter.php")
   suspend fun getMealsByCategory(@Query("c") category:String):Response<MealsByCategoryList>

   @GET("categories.php")
   suspend fun getMealCategories():Response<CategoryList>

}