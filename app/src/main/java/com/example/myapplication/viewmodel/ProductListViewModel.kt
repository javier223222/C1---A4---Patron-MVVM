package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Product

import com.example.myapplication.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList()) // Estado de la lista de productos
    val products: StateFlow<List<Product>> = _products


    fun getProducts() {
        viewModelScope.launch {
            try {
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                println("Error al obtener productos: ${e.message}")
            }
        }
    }


    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                // ⚠️ ACTUALIZAR LA LISTA MANUALMENTE ANTES DE LLAMAR `getProducts()`
                val updatedList = _products.value.filter { it.id != productId }
                _products.value = updatedList
                repository.deleteProduct(productId)



                getProducts()
            } catch (e: Exception) {
                println("Error al eliminar producto: ${e.message}")
            }
        }
    }


    fun updateProduct(productId: Int, newName: String) {
        viewModelScope.launch {
            try {


                repository.updateProduct(productId, Product(productId,newName,"",0,"","",""))
                getProducts() // Recargar lista después de actualizar

            } catch (e: Exception) {
                println("Error al actualizar producto: ${e.message}")
            }
        }
    }


    fun addProduct(name:String,description:String,image:String,price:Int) {
        viewModelScope.launch {
            try {

               val newProduct= repository.createProduct(name,description,image,price)
                getProducts() // Recargar lista después de agregar
            } catch (e: Exception) {
                println("Error al agregar producto: ${e.message}")
            }
        }
    }
}
