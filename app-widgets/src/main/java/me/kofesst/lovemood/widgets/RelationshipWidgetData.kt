package me.kofesst.lovemood.widgets

@Suppress("ArrayInDataClass")
data class RelationshipWidgetData(
    val relationshipId: Int,
    val userUsername: String,
    val userAvatar: ByteArray,
    val partnerUsername: String,
    val partnerAvatar: ByteArray,
    val loveDuration: String
)