package com.davi.atividade1.data.model

import android.os.Parcelable
import com.davi.atividade1.util.FireBaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task (
    var id: String = "",
    var description: String = "",

    // Cria a tarefa como TODO
    var status: Status = Status.TODO
): Parcelable{
    init {
        this.id = FireBaseHelper.getDatabase().push().key ?: ""
    }
}