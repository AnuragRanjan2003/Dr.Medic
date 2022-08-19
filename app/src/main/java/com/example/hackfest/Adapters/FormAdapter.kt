package com.example.hackfest.Adapters

import Models.Form

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.hackfest.R
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial


class FormAdapter(list: ArrayList<Form>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list1 = list
    private val context1 = context
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
        } else if (holder is Item2ViewHolder) {
            holder.Text.text = form.text
            holder.Title.text = form.text
            holder.ChipGroup.isSingleSelection = true
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

    class Item1VewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title = itemView.findViewById<TextView>(R.id.vm1_title)
        var Text = itemView.findViewById<TextView>(R.id.vm1_text)
        val switch = itemView.findViewById<SwitchMaterial>(R.id.vm1_switch)


    }

    class Item2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title = itemView.findViewById<TextView>(R.id.vm2_title)
        val Text = itemView.findViewById<TextView>(R.id.vm2_text)
        val ChipGroup = itemView.findViewById<ChipGroup>(R.id.vm2_chip_group)
    }
}