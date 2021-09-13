package jp.co.noraconeco.simplenoteapp.repository

internal interface Repository<Item, Index> {
    fun add(item: Item)
    fun remove(item: Item)
    fun addAll(item: Collection<Item>)
    fun removeAll()
    fun get(index: Index): Item?
    fun fetch(selection: Collection<Index>): Collection<Item>
    fun getAll(): Collection<Item>
}

