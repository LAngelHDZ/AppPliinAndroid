package com.example.pliin.apppliin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pliin.apppliin.data.database.entities.EmployeeEntity

@Dao
interface EmployeeDao {

    //Comsulta la informacion del empleado
    @Query("SELECT * FROM Employee_table WHERE username = :user")
    suspend fun getDataEmployee(user: String): EmployeeEntity

    //Verifica si existe la informacion del empleado
    @Query("SELECT COUNT(*) FROM Employee_table WHERE username = :user")
    suspend fun queryDataEmployee(user: String): Boolean

    //Inserta la informaciòn del empleado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataEmployee(Employee: EmployeeEntity)
}