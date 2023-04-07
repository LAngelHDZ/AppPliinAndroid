package com.example.pliin.apppliin.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.pliin.apppliin.data.database.entities.ManifestEntity
import com.example.pliin.apppliin.data.database.entities.UserEntity

data class UserWithManifest(
    @Embedded val user:UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "fkemployye"
    )
    val manifest: List<ManifestEntity>
) {
}