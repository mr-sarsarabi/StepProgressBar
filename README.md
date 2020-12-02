![blob](https://jitpack.io/#mr-sarsarabi/StepProgressBar/v1.0)

This Library is totally free! feel free to use it...

+I need help with handling sizes to make it all automatic.


![Screenshot of the library outcome.](screenshot.png)

#### Gradle:
**Step 1.** Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
**Step 2.** Add the dependency

	dependencies {
	        implementation 'com.github.mr-sarsarabi:StepProgressBar:v1.0'
	}

#### Maven:


**Step 1.** Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

**Step 2.** Add the dependency

	<dependency>
	    <groupId>com.github.mr-sarsarabi</groupId>
	    <artifactId>StepProgressBar</artifactId>
	    <version>v1.0</version>
	</dependency>


## Usage:

**Xml:**

    <com.miragestudios.stepprogressbar.StepProgressBar
        android:id="@+id/step_progress_bar"
        android:layout_width="match_parent"
        android:layout_height="32dp"
        android:layout_margin="16dp"
        app:backgroundBarColor="#493A0D"
        app:failureStepColor="#B92926"
        app:innerPadding="6dp"
        app:successfulStepColor="#FFC107" />

**Java:**

    StepProgressBar stepProgressBar = findViewById(R.id.step_progress_bar);
    
    stepProgressBar.setStepSates(new Boolean[]{true, false, true, true, true});
    
    stepProgressBar.setStepCount(5);
    stepProgressBar.setSuccessfulStepColor(0xffffff);
    stepProgressBar.setFailureStepColor(0xffffff);
    stepProgressBar.setBackgroundColor(0xffffff);
    stepProgressBar.setInnerPadding(0xffffff);