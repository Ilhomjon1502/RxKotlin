package com.ilhomjon.rxkotlin

import DB.AppDatabase
import Entity.User
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ilhomjon.Adapters.UserAdapter
import com.ilhomjon.rxkotlin.databinding.ActivityFlowableBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.schedulers.Schedulers

class FlowableActivity : AppCompatActivity() {

    lateinit var binding:ActivityFlowableBinding
    lateinit var userAdapter: UserAdapter
    lateinit var appDatabase: AppDatabase

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDatabase = AppDatabase.getInstance(this)
        userAdapter = UserAdapter(object : UserAdapter.OnItemCLickListener {
            override fun onItemClick(user: User) {
                appDatabase.userDao().getUserById(user.id!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Toast.makeText(this@FlowableActivity, "${it.userName} \t${it.password}", Toast.LENGTH_SHORT).show()
                    }
                /*
                Maybe listener xusuiyati bir marta
                 */
            }
        })

        /*
        Flowable o'zgarih bo'lsa ham bo'lmasa ham eshitib ishlab turadi
        Obserwable esa faqat o'zgarish bo'lsa ishlaydi
        */

        appDatabase.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<List<User>>,
                io.reactivex.functions.Consumer<List<User>> {
                override fun accept(t: List<User>?) {
                    userAdapter.submitList(t)
                    binding.progressbar.visibility = View.GONE
                }
            }, object : Consumer<Throwable>, io.reactivex.functions.Consumer<Throwable> {
                override fun accept(t: Throwable?) {

                }
            }, object : Action, io.reactivex.functions.Action {
                override fun run() {
                    Toast.makeText(this@FlowableActivity, "Success", Toast.LENGTH_SHORT).show()
                }
            })
        binding.rv.adapter = userAdapter

        binding.btnSave.setOnClickListener {
            val user = User()
            user.userName = binding.edt1.text.toString()
            user.password = binding.edt2.text.toString()
            appDatabase.userDao().addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t->
                    Log.d("Single", "$t")
                }

            /*
            Single bir marta ishlashini taminlaydi
             */
        }
    }
}