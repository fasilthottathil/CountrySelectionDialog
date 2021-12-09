# CountrySelectionDialog


# Screenshot

 <img src="https://raw.githubusercontent.com/fasilthottathil/CountrySelectionDialog/master/assests/picture.jpeg" width="450" height="650">  

<!-- ![Screen record](../master/assests/video.gif)
 -->




# Step 1:  Add the JitPack repository to your build file
```gradle
allprojects {
  repositories {
	...
	maven { url 'https://jitpack.io' }
  }
```

# Step 2: Add the dependency

```gradle
dependencies {
   implementation 'com.github.fasilthottathil:CountrySelectionDialog:x.x.x'
}
```

```kotlin
 CountrySelectionDialog().create(this)
     .show()?.setOnCountrySelected {
         Log.d(COUNTRY_NAME, it.name)
         Log.d(COUNTRY_CODE, it.code)
         Log.d(COUNTRY_DIAL_CODE, it.dial_code)
     }
```

