package com.rsdosev.data.source.local.cache

const val DEFAULT_CACHE_SIZE = 4 * 1024 * 1024 // 4Mb

/**
 * In-memory cache implementation with fixed size using LRU cache
 */
class MemoryCache(cacheSize: Int = DEFAULT_CACHE_SIZE) : Cache<String, Any> {

    private val lruCache: LruCache<String, Any> by lazy { LruCache<String, Any>(cacheSize) }

    @Suppress("UNCHECKED_CAST")
    override fun <T> load(key: String): T? = lruCache[key]?.let {
        it as T
    }

    override fun save(key: String, value: Any) {
        lruCache.put(key, value)
    }
}
