package jp.co.noraconeco.simplenoteapp.repository

import kotlinx.coroutines.flow.Flow

internal interface FlowRepository<Item, Index>: Repository<Item, Index> {
    suspend fun getAllFlow(): Flow<Collection<Item>>
}