package com.example.android.training.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(
    name = "datastore",
    corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { emptyPreferences() }
    ),
)

fun getStringFlow(key: String, defaultValue: String = "", context: Context) =
    context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)] ?: defaultValue
    }

fun getStringSync(key: String, defaultValue: String = "", context: Context): String = runBlocking {
    context.dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue
}

suspend fun getStringSuspendSync(key: String, defaultValue: String = "", context: Context): String = coroutineScope {
    context.dataStore.data.first()[stringPreferencesKey(key)] ?: defaultValue
}

suspend fun setString(key: String, value: String, context: Context) {
    context.dataStore.edit { preferences ->
        preferences[stringPreferencesKey(key)] = value
    }
}
