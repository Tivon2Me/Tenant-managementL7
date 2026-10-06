package com.example.tenantmanagement

data class Tenant(
    val name: String,
    val phone: String,
    val houseNumber: String
) {
    fun summary(): String {
        return "Tenant: $name, House: $houseNumber, Phone: $phone"
    }
}