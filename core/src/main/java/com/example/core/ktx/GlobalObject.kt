package com.example.core.ktx

import kotlinx.serialization.json.Json

val globalGson = Json{
    encodeDefaults = true
    ignoreUnknownKeys = true
}