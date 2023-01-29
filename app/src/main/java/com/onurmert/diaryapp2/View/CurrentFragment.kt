package com.onurmert.diaryapp2.View

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.onurmert.diaryapp2.Adapter.DiaryAdapter
import com.onurmert.diaryapp2.Model.DiaryModel
import com.onurmert.diaryapp2.R
import com.onurmert.diaryapp2.ViewModel.CurrentVM
import com.onurmert.diaryapp2.databinding.FragmentCurrentBinding
import com.onurmert.retro4fitt.Utils.InternetControl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class CurrentFragment : Fragment() {

    private lateinit var binding: FragmentCurrentBinding

    private lateinit var viewModel: CurrentVM

    private lateinit var toggle: ActionBarDrawerToggle

    private var backTime : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCurrentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(CurrentVM::class.java)

        getDiaryData()
    }

    override fun onResume() {
        super.onResume()

        drawerMenu()

        drawerMenuItemClick(requireView())

        toolBar()

        backPressed()

        swipeRefresh()
    }

    private fun toolBar(){
        binding.toolbarImage.setOnClickListener {
            binding.drawerMenu.open()
        }
    }

    private fun swipeRefresh(){
        binding.swipe.setOnRefreshListener {
            netControl()
            binding.swipe.isRefreshing = false
        }
    }

    private fun drawerMenu(){

        toggle = ActionBarDrawerToggle(requireActivity(), binding.drawerMenu, R.string.open, R.string.close)

        binding.drawerMenu.setDrawerListener(toggle)

        toggle.syncState()
    }

    private fun drawerMenuItemClick(view: View){

        binding.navigationDrawer.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.insertMenu->{
                    directionInsert(view)
                }
                R.id.refreshMenu->{
                    netControl()
                }
                R.id.singOutMenu->{
                    singOut(view)
                }
            }
            true
        }
    }

    private fun singOut(view: View){

        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            this.setMessage("Are you sure you want to quit?")
            this.setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                viewModel.singOut(view)
            })
            this.setCancelable(false)//kapanmaz eğer gerekliyse kullan
            this.show()
        }
    }

    private suspend fun refresh(){
        binding.drawerMenu.close()
        getDiaryData()
        val progresDialog = ProgressDialog(requireContext())
        progresDialog.apply {
            this.setMessage("Resfresh")
            this.show()
            delay(900)
            this.dismiss()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDiaryData(){

        viewModel.getDiary()

        viewModel.diaryList.observe(viewLifecycleOwner, Observer {
            kotlin.run {
                createRecycler(it)
            }
        })
    }

    private fun createRecycler(diaryList : ArrayList<DiaryModel>){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = DiaryAdapter(diaryList)
    }

    private fun netControl(){
        if (InternetControl.connectionControl(requireContext()) == true){
            CoroutineScope(Dispatchers.Main).launch {
                refresh()
            }
        }else{
            Toast.makeText(requireContext(),"Check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun directionInsert(view : View){
        binding.drawerMenu.close()
        val direction = CurrentFragmentDirections.actionCurrentFragmentToInsertFragment2()
        Navigation.findNavController(view).navigate(direction)
    }

    private fun backPressed(){
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if (backTime + 3000 > System.currentTimeMillis()){
                        requireActivity().finish()
                    }else{
                        Toast.makeText(requireContext(), "Click again to exit!", Toast.LENGTH_SHORT).show()
                    }
                    backTime = System.currentTimeMillis()
                }
            })
    }
}