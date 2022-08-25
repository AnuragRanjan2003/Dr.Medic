package com.example.hackfest

import Models.Form
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackfest.Adapters.FormAdapter
import com.example.hackfest.databinding.ActivitySurveyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class SurveyActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var dataRef: DatabaseReference
    private lateinit var fUser:FirebaseUser
    private lateinit var binding: ActivitySurveyBinding
    private lateinit var formList:ArrayList<Form>
    private lateinit var formAdapter: FormAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= FirebaseDatabase.getInstance()
        dataRef=FirebaseDatabase.getInstance().getReference("forms")
        fUser= FirebaseAuth.getInstance().currentUser!!
        val symptomList=resources.getStringArray(R.array.symptom_array)
        val symptomAdapter=ArrayAdapter(this,R.layout.item_vm,symptomList)
        formList= ArrayList()
        binding.symp.setAdapter(symptomAdapter)
        binding.qesRec.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.qesRec.hasFixedSize()
        binding.symp.addTextChangedListener{object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                loadForm(s.toString(),formList)
            }

        }



        }
        binding.btnSubmit.setOnClickListener {
            val format=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            val date=LocalDateTime.now()
            val dref= database.getReference("Responses").child(fUser.uid).child(date.format(format))
            for ( rep in formAdapter.getResponseList()){
                dref.child(rep.Title).setValue(rep)
            }

        }

    }
    private fun loadForm(s:String,list:ArrayList<Form>){
        dataRef.child(s).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( snap in snapshot.children){
                    val form=snap.getValue(Form::class.java)
                    list.add(form!!)
                }
                formAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        formAdapter= FormAdapter(list,this@SurveyActivity)
        binding.qesRec.adapter=formAdapter

    }
}