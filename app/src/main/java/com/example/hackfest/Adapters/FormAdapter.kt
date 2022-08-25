package com.example.hackfest.Adapters

import Models.Form
import Models.Response

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hackfest.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial


class FormAdapter(list: ArrayList<Form>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list1 = list
    private val context1 = context
    private val responseList=ArrayList<Response>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                val v = LayoutInflater.from(context1).inflate(R.layout.form_vm1, parent, false)
                Item1VewHolder(v)
            }
            2 -> {
                val v = LayoutInflater.from(context1).inflate(R.layout.form_vm2, parent, false)
                Item2ViewHolder(v)
            }
            else -> {
                val v = LayoutInflater.from(context1).inflate(R.layout.form_vm1, parent, false)
                Item1VewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val form = list1[position]
        if (holder is Item1VewHolder) {
            holder.Text.text = form.text
            holder.Title.text = form.Title
            holder.switch.text = context1.getString(R.string.No)
            responseList[position].Title=form.Title
            responseList[position].Status=0
            holder.switch.setOnClickListener{
                if(holder.switch.isChecked){
                    holder.switch.text="Yes"
                    responseList[position].Status=1
                }else{ holder.switch.text=context1.getText(R.string.No)
                responseList[position].Status=0
                }
            }
        } else if (holder is Item2ViewHolder) {
            holder.Text.text = form.text
            holder.Title.text = form.text
            holder.ChipGroup.isSingleSelection = true
            val resp=responseList[position]
            resp.Title=form.Title
            resp.Status=0
            holder.chipNone.setOnClickListener { resp.Status=0 }
            holder.chipMild.setOnClickListener { resp.Status=1 }
            holder.chipHigh.setOnClickListener { resp.Status=2 }
        }
    }

    override fun getItemCount(): Int {
        return list1.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list1[position].viewType) {
            1 -> R.layout.form_vm1
            2 -> R.layout.form_vm2
            else -> R.layout.form_vm1
        }
    }
    fun getResponseList():ArrayList<Response>{
            return responseList
    }

    class Item1VewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title = itemView.findViewById<TextView>(R.id.vm1_title)
        var Text = itemView.findViewById<TextView>(R.id.vm1_text)
        val switch = itemView.findViewById<SwitchMaterial>(R.id.vm1_switch)


    }

    class Item2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title = itemView.findViewById<TextView>(R.id.vm2_title)
        val Text = itemView.findViewById<TextView>(R.id.vm2_text)
        val ChipGroup = itemView.findViewById<ChipGroup>(R.id.vm2_chip_group)
        val chipNone=itemView.findViewById<Chip>(R.id.vm2_none);
        val chipMild=itemView.findViewById<Chip>(R.id.vm2_mild);
        val chipHigh=itemView.findViewById<Chip>(R.id.vm2_high);
    }

}