package com.schooldiary.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schooldiary.data.createdata.DataCreatedResponse
import com.schooldiary.data.createdata.DataForCreate
import com.schooldiary.data.grade.ClassAndSubject
import com.schooldiary.data.grade.GradeResponse
import com.schooldiary.data.grade.GradesForTeacherResponse
import com.schooldiary.data.login.LoginResponse
import com.schooldiary.data.login.User
import com.schooldiary.data.schedule.ScheduleResponse
import com.schooldiary.data.schedule.UpdateHomework
import com.schooldiary.data.studentinfo.StudentInfoResponse
import com.schooldiary.data.subject.SubjectsResponse
import com.schooldiary.data.teacherInfo.TeacherInfoResponse
import com.schooldiary.data.users.UserResponse
import com.schooldiary.repository.Repository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val mLoginData = MutableLiveData<LoginResponse>()
    val loginData: LiveData<LoginResponse> = mLoginData

    private val mScheduleData = MutableLiveData<ScheduleResponse>()
    val scheduleData: LiveData<ScheduleResponse> = mScheduleData

    private val mGradesData = MutableLiveData<GradeResponse>()
    val gradesData: LiveData<GradeResponse> = mGradesData

    private val mStudentInfo = MutableLiveData<StudentInfoResponse>()
    val studentInfo: LiveData<StudentInfoResponse> = mStudentInfo

    private val mTeacherInfo = MutableLiveData<TeacherInfoResponse>()
    val teacherInfo: LiveData<TeacherInfoResponse> = mTeacherInfo

    private val mUsersData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> = mUsersData

    var dayForDetails = -1

    var userForDetails = -1

    private val mWeekNumber = MutableLiveData(
        LocalDate.now().get(WeekFields.of(Locale.UK).weekOfYear()) + 17
    )
    val weekNumber: LiveData<Int> = mWeekNumber

    lateinit var userRole: UserRole

    var lessonIdForUpdateHomework: String? = null

    var currentHomeworkForEdit: String = ""

    val homeworkUpdated = MutableLiveData(false)

    private val mDataCreatedResponse = MutableLiveData<DataCreatedResponse>()
    val dataCreatedResponse: LiveData<DataCreatedResponse> = mDataCreatedResponse

    private val mSubjects = MutableLiveData<SubjectsResponse>()
    val subjects: LiveData<SubjectsResponse> = mSubjects

    private val mGradesForTeacher = MutableLiveData<GradesForTeacherResponse>()
    val gradesForTeacher: LiveData<GradesForTeacherResponse> = mGradesForTeacher

    fun login(login: String, password: String) = viewModelScope.launch {
        val userData = User(login, password)
        val loginResponse = repository.loginUser(userData)
        mLoginData.postValue(
            loginResponse ?: LoginResponse(
                "Логин или пароль некорректен", "", "", listOf()
            )
        )
    }

    fun getScheduleForStudent(classId: String) = viewModelScope.launch {
        val scheduleResponse =
            repository.getScheduleForStudent(classId, weekNumber.value.toString())
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }

    fun getScheduleForTeacher(userId: String) = viewModelScope.launch {
        val scheduleResponse = repository.getScheduleForTeacher(userId, weekNumber.value.toString())
        scheduleResponse?.let { mScheduleData.postValue(it) }
    }

    fun getAllGradesForUser(userId: String) = viewModelScope.launch {
        val gradeResponse = repository.getAllGradesForUser(userId)
        gradeResponse?.let { mGradesData.postValue(it) }
    }

    fun plusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! + 1)

    fun minusWeekId() = mWeekNumber.postValue(mWeekNumber.value!! - 1)

    fun updateHomework(newHomework: String) = viewModelScope.launch {
        if (lessonIdForUpdateHomework == null) {
            Log.e(this.javaClass.name, "Field lessonIdForUpdateHomework is null")
            return@launch
        }
        val homeworkData = UpdateHomework(lessonIdForUpdateHomework!!, newHomework)
        repository.updateHomeworkByLessonId(homeworkData)
        currentHomeworkForEdit = newHomework
        homeworkUpdated.postValue(true)
    }

    fun clearMessage() = viewModelScope.launch {
        mLoginData.postValue(LoginResponse("", "", "", listOf()))
    }

    fun getStudentInfo(userId: String) = viewModelScope.launch {
        val studentInfoResponse = repository.getStudentInfo(userId)
        studentInfoResponse?.let { mStudentInfo.postValue(it) }
    }

    fun getTeacherInfo(userId: String) = viewModelScope.launch {
        val teacherInfoResponse = repository.getTeacherInfo(userId)
        teacherInfoResponse?.let { mTeacherInfo.postValue(it) }
    }

    fun createUser(
        fio: String,
        login: String,
        password: String,
        email: String,
        role: String,
        className: String
    ) {
        val nameClass = if (className == "Класс") null else className

        viewModelScope.launch {
            val roles = listOf(role)
            val userData = DataForCreate(
                name = fio,
                login = login,
                password = password,
                email = email,
                roles = roles,
                className = nameClass
            )
            val createdResponse = repository.createUser(userData)
            mDataCreatedResponse.postValue(createdResponse)
            Handler(Looper.getMainLooper()).postDelayed({
                mDataCreatedResponse.postValue(DataCreatedResponse(""))
            }, 2000)
        }
    }

    fun getAllUsers() = viewModelScope.launch {
        val usersResponse = repository.getAllUsers()
        usersResponse?.let { mUsersData.postValue(it) }
    }

    fun deleteUser(userId: String) = viewModelScope.launch {
        repository.deleteUser(userId)
    }

    fun getAllSubjects() = viewModelScope.launch {
        val subjects = repository.getAllSubjects()
        subjects?.let { mSubjects.postValue(it) }
    }

    fun getGradesForTeacher(classId: String, subjectId: String) = viewModelScope.launch {
        val classAndSubject = ClassAndSubject(classId, subjectId)
        val response = repository.getGradesForTeacher(classAndSubject)
        response?.let { mGradesForTeacher.postValue(it) }
    }
}
