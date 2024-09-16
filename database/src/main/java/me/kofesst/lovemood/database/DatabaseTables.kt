package me.kofesst.lovemood.database

internal object DatabaseTables {
    object Profiles {
        const val TABLE_NAME = "profiles"

        const val ID_COLUMN = "id_profile"
        const val USERNAME_COLUMN = "username"
        const val GENDER_COLUMN = "gender_name"
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

    object RelationshipEvents {
        const val TABLE_NAME = "relationship_events"

        const val ID_COLUMN = "id_event"
        const val NAME_COLUMN = "raw_name"
        const val DAY_OF_MONTH_COLUMN = "day_of_month"
        const val MONTH_COLUMN = "month_value"
        const val RELATIONSHIP_ID_COLUMN = "relationship_id"
    }

    object PhotoMemories {
        const val TABLE_NAME = "photo_memories"

        const val ID_COLUMN = "id_memory"
        const val CONTENT_COLUMN = "photo_content"
        const val ADDED_DATE_TIME_COLUMN = "added_at"
        const val ASSOCIATED_DATE_COLUMN = "associated_date"
    }
}