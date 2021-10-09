package ru.gb.moviesearcher.ui.main.view

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.main_content.*
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.ContactsFragmentBinding

const val REQUEST_CODE = 42

class ContactsFragment : Fragment() {

    private var _binding: ContactsFragmentBinding? = null
    private val binding get() = _binding!!


    private val requestPermissionLauncher  =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> getContacts()
                !ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_CONTACTS) ->{
                    Toast.makeText(context,
                        "Go to settings and allow permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    Toast.makeText(context,
                        "You need to allow access to contacts",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                }

            }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermission() {
        context?.let { context ->
            when {
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(context)
                        .setTitle("Access to contacts")
                        .setMessage("Explanation ...")
                        .setPositiveButton("OK") { _, _ ->
                            requestPermission()
                        }
                        .create()
                        .show()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

//    private fun requestPermission() {
//        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)
//    }


    private fun requestPermission (){
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun getContacts() {
        context?.let { context ->
            val contentResolver: ContentResolver = context.contentResolver
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(context, name)
                    }
                }
            }
            cursorWithContacts?.close()
        }


    }

    private fun addView(context: Context, textToShow: String) {
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.main_container_text_size)
        })
    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            REQUEST_CODE -> {
//                if ((grantResults.isNotEmpty() &&
//                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                ) {
//                    getContacts()
//                } else {
//
//                    context?.let {
//                        AlertDialog.Builder(it)
//                            .setTitle("Access to contacts")
//                            .setMessage("Explanation")
//                            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
//                            .create()
//                            .show()
//                    }
//                }
//                return
//            }
//        }
//
//
//    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}