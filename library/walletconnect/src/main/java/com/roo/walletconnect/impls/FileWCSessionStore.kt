package com.mykey.id.walletconnect.impls

import com.mykey.id.walletconnect.nullOnThrow
import com.squareup.moshi.Moshi
import java.io.File
import java.sql.Types
import java.util.concurrent.ConcurrentHashMap

class FileWCSessionStore(private val storageFile: File, moshi: Moshi) : WCSessionStore {
    private val adapter = moshi.adapter<Map<String, WCSessionStore.State>>(
        com.squareup.moshi.Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            WCSessionStore.State::class.java
        )
    )

    private val currentStates: MutableMap<String, WCSessionStore.State> =
        ConcurrentHashMap()

    init {
        val storeContent = storageFile.readText()
        nullOnThrow { adapter.fromJson(storeContent) }?.let {
            currentStates.putAll(it)
        }
    }

    override fun load(id: String): WCSessionStore.State? = currentStates[id]

    override fun store(id: String, state: WCSessionStore.State) {
        currentStates[id] = state
        writeToFile()
    }

    override fun remove(id: String) {
        currentStates.remove(id)
        writeToFile()
    }

    override fun list(): List<WCSessionStore.State> = currentStates.values.toList()

    private fun writeToFile() {
        storageFile.writeText(adapter.toJson(currentStates))
    }

}
