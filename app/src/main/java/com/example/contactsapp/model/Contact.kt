package com.example.contactsapp.model

import com.example.contactsapp.R

data class Contact(
    val name: String,
    val phoneNumber: String,
    val imageResId: Int
)

val contacts = listOf(
    Contact("Auntie", "+201012345678", R.drawable.auntie),
    Contact("Brother", "+201123456789", R.drawable.brother),
    Contact("Daughter", "+201234567890", R.drawable.daughter),
    Contact("Friend 1", "+201567890123", R.drawable.friend_1),
    Contact("Friend 2", "+201678901234", R.drawable.friend_2),
    Contact("Grandfather", "+201789012345", R.drawable.grandfather),
    Contact("Granny", "+201890123456", R.drawable.granny),
    Contact("Neighbour", "+201901234567", R.drawable.neigbour),
    Contact("Sister", "+201012345678", R.drawable.sister),
    Contact("Son", "+201123456789", R.drawable.son),
    Contact("Uncle", "+201234567890", R.drawable.uncle)
)