package me.kofesst.lovemood.database

internal object DatabaseTables {
    object Profiles {
        const val TABLE_NAME = "profiles"

        const val ID_COLUMN = "id_profile"
        const val USERNAME_COLUMN = "username"
        const val DATE_OF_BIRTH_COLUMN = "date_of_birth"
        const val AVATAR_COLUMN = "avatar_content"
    }

    object Relationships {
        const val TABLE_NAME = "relationships"

        const val ID_COLUMN = "id_relationship"
        const val USER_ID_COLUMN = "user_profile_id"
        const val PARTNER_ID_COLUMN = "partner_profile_id"
        const val START_DATE_COLUMN = "start_date"
    }

    object PhotoMemories {
        const val TABLE_NAME = "photo_memories"

        const val ID_COLUMN = "id_memory"
        const val CONTENT_COLUMN = "photo_content"
        const val ADDED_DATE_TIME_COLUMN = "added_at"
        const val ASSOCIATED_DATE_COLUMN = "associated_date"
    }
}