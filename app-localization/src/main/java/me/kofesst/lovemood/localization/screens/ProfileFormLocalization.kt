package me.kofesst.lovemood.localization.screens

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

interface ProfileFormLocalization {
    val selectingPictureStageTitle: TextBuilder
    val selectingPictureStageSubtitle: TextBuilder
    val selectingPictureStageLabel: TextBuilder

    val enteringDataStageTitle: TextBuilder
    val enteringDataStageSubtitle: TextBuilder
    val enteringDataStageLabel: TextBuilder

    val selectAvatarLabel: TextBuilder
    val removeAvatarLabel: TextBuilder

    val usernameLabel: TextBuilder
    val usernamePlaceholder: TextBuilder

    val dateOfBirthLabel: TextBuilder
}

@Singleton
class UserProfileFormLocalization @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileFormLocalization {
    override val selectingPictureStageTitle: TextBuilder =
        context buildResource R.string.profile_form__selecting_picture_title

    override val selectingPictureStageSubtitle: TextBuilder =
        context buildResource R.string.profile_form__selecting_picture_subtitle

    override val selectingPictureStageLabel: TextBuilder =
        context buildResource R.string.profile_form__selecting_picture_label

    override val enteringDataStageTitle: TextBuilder =
        context buildResource R.string.profile_form__entering_data_title

    override val enteringDataStageSubtitle: TextBuilder =
        context buildResource R.string.profile_form__entering_data_subtitle

    override val enteringDataStageLabel: TextBuilder =
        context buildResource R.string.profile_form__entering_data_label

    override val selectAvatarLabel: TextBuilder =
        context buildResource R.string.profile_form__select_avatar_label

    override val removeAvatarLabel: TextBuilder =
        context buildResource R.string.profile_form__remove_avatar_label

    override val usernameLabel: TextBuilder =
        context buildResource R.string.profile_form__username_label

    override val usernamePlaceholder: TextBuilder =
        context buildResource R.string.profile_form__username_placeholder

    override val dateOfBirthLabel: TextBuilder =
        context buildResource R.string.profile_form__date_of_birth_label
}

@Singleton
class PartnerProfileFormLocalization @Inject constructor(
    @ApplicationContext private val context: Context
) : ProfileFormLocalization {
    override val selectingPictureStageTitle: TextBuilder =
        context buildResource R.string.profile_partner_form__selecting_picture_title

    override val selectingPictureStageSubtitle: TextBuilder =
        context buildResource R.string.profile_partner_form__selecting_picture_subtitle

    override val selectingPictureStageLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__selecting_picture_label

    override val enteringDataStageTitle: TextBuilder =
        context buildResource R.string.profile_partner_form__entering_data_title

    override val enteringDataStageSubtitle: TextBuilder =
        context buildResource R.string.profile_partner_form__entering_data_subtitle

    override val enteringDataStageLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__entering_data_label

    override val selectAvatarLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__select_avatar_label

    override val removeAvatarLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__remove_avatar_label

    override val usernameLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__username_label

    override val usernamePlaceholder: TextBuilder =
        context buildResource R.string.profile_partner_form__username_placeholder

    override val dateOfBirthLabel: TextBuilder =
        context buildResource R.string.profile_partner_form__date_of_birth_label
}