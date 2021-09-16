package jp.co.noraconeco.simplenoteapp.repository

internal interface Repository<Item, Index> {
    suspend fun add(item: Item)
    suspend fun remove(item: Item)
    suspend fun addAll(item: Collection<Item>)
    suspend fun removeAll()
    suspend fun update(item: Item)
    suspend fun get(index: Index): Item?
    suspend fun fetch(selection: Collection<Index>): Collection<Item>
    suspend fun getAll(): Collection<Item>
}

