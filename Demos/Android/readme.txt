==========================================================
 TeeChart for Java version 3

 Copyright (c) 2006-2013
 by Steema Software SL
 All Rights Reserved.

 http://support.steema.com
 http://www.steema.com 
 email: general: info@steema.com
 support forum: http://www.teechart.net/support/index.php

==========================================================
Working with the TeeChart for Android mobile devices demo 
project in Eclipse and compiling from Eclipse or Ant.
==========================================================

Sections
1. Working with TeeChart for Android in Eclipse
2. Working with Ant


1. Working with TeeChart for Android in Eclipse

Open the Eclipse environment. We assume here that you have already installed 
the Android SDK, ADT Plugin for Eclipse. If you have not yet done so you can find 
information here about how to install it: 
http://developer.android.com/sdk/eclipse-adt.html

- Opening the TeeChart demo project
With the Android ADT Plugin for Eclipse installed, from the Eclipse menu 
select 'File' and 'New' and 'Project'. From the 'Select a Wizard' list, select
'Android' and 'Android Project'. From here you can create the project from 
existing source by navigating to the src folder of the TeeChart demo.

- Reference to TeeChart.Android.Jar
Please note that the demo project includes references to the Android.Jar of the
API level in which to work and to the TeeChart.Android.Jar which is the complete
TeeChart Library for Android. You will need to check that the references to these 
two libraries are correct in your newly created project.

- Compiling the TeeChart demo project
Once compiling correctly in your environment, to create an apk to upload to your
mobile, select the right mousebutton menu on the TeeChart Eclipse project and then
the 'Android Tools' menu. From here you can select to create a signed or unsigned
apk (mobile compatible exported file format). If you choose to sign you need to 
create your own keyfile.

2. Working with Ant

We recommend that you use the editor environment of your choice, eg. Eclipse, to 
work with the TeeChart demo but for automated builds of this or your own projects
Ant (http://ant.apache.org/) is well suited.

- Configuring Ant with Android
Install ant and add the the Ant bin directory to the machine path:

eg. to path: ....\apache-ant-1.8.2-bin\apache-ant-1.8.2\bin

Set the ANT_HOME environment variable to 
"[yourfolder]....\apache-ant-1.8.2-bin\apache-ant-1.8.2\"

Set the Android sdk location in the environment. Advisable are these sets:

ANDROID_SDK_ROOT=C:\Program Files\Android\android-sdk
JAVA_HOME=C:\Program files\Java\jdk1.6.0_23

- Compiling the TeeChart demo with Ant
The TeeChart demo project installation includes the configuration files used by
Ant to build the project. Please note that you will need to add the 
build.properties file to include you own key information as Steema's own key has
been removed from the source.

If you don't have keys you can create your own keys in Eclipse by selecting the 
right mousebutton menu on the TeeChart Eclipse project and then the 'Android Tools' 
menu. If you select 'Export Signed Application Package' the  you will be prompted 
to create a keyfile.

----------------
build.properties
----------------
Set the following content in build.properties (here with example key entries set):

key.store = "./keystore/mykeystorefile"

key.alias=my_alias

key.store.password=my_password

key.alias.password=my_password

----------------

If you leave the password entries out of the files then you will be prompted at 
compile time for them.

----------------
AndroidManifest.xml
----------------
Please note that the debuggable option (android:debuggable="false") should be set 
to false when publish-releasing an apk.


libs folder
-----------
The ant compile looks to the libs folder as default for any supporting libraries.
Copy the TeeChart.Android.Jar to this folder.

To build your application and prepare the Application Package (.apk file) go to the 
demo project folder and run:

ant release

---
be ready to type in the password if it's not included in the build.properties files

TeeChart-release.apk will be output to the bin folder.