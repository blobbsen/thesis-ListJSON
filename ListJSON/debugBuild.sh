#!/bin/bash
#
# This is an easy debug build script,
# so all developers enable the debuggable mode
# in the AndroidManifest-xml, build the debug apk 
# and sign the debug apk with the same keystore,
# which is stored inside the repository.
#
# (PLEASE DO NOT PROVIDE THE RELEASE KEYSTORE HERE)
#
# @author: andre@blobb.me
# @date: 20.12.2015
#
# Please define the following parameters...
#
APPNAME="MainActivity"
KEYSTORE="ListJSONdebug.keystore"
ALIAS="blobb"
STOREPASS="password"
MANIFEST_PATH="AndroidManifest.xml"
################################################################################
set -e

setDebuggableToTrue() {
	sed -i 's/debuggable *= *"false"/debuggable="true"/;' $MANIFEST_PATH
	
	# checking changes
	check="true"
	
	debuggable_test=`grep debuggable $MANIFEST_PATH | sed 's/.*debuggable="//;s/".*//'`
	
	# checking debuggable flag 
	if [ "$debuggable_test" != "true" ]; then
			echo "ERROR: changing the AndroidManifest.xml"
			exit 1
	fi

	echo "1) changed debuggable attribute to \"true\" in ${MANIFEST_PATH}"

}

# check if manpage was asked
if  [ $# == 1 ] && [ "$1" == "-help" ]; then

	echo -e "\nThis is an easy debug build script,
so all developers enable the debuggable mode
in the AndroidManifest.xml, build the debug apk 
and sign the debug apk with the same keystore,
which is stored inside the repository.\n
Just invoke this script without any parameter,
or pass one random parameter for verbose mode.\n"

	exit
fi

buildLang=`ls | grep build.xml`

if [ $# == 0 ]; then
	echo -e "\n########## building Android debug version ##########\n"
	setDebuggableToTrue 

	if [ ${#buildLang} -gt 0 ]; then
		echo "2) building Android apk with ANT"
		invis=`ant debug` 
	else
		echo "2) building Android apk with GRADLE"
		invis=`./gradlew assembleDebug`
	fi

	echo -e "3) signing apk with ${KEYSTORE}\n"
	invis=`jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ${KEYSTORE} -storepass ${STOREPASS} bin/${APPNAME}-debug.apk ${ALIAS}`

	read  -p "Escape (CTRL+C) or press enter to install debug apk on device/emulator..."
	adb install -r bin/${APPNAME}-debug.apk

else
	echo -e "\n########## building Android debug version ##########"
	setDebuggableToTrue

	if [ ${#buildLang} -gt 0 ]; then
		echo -e "\n########## using ANT to build debug apk ##########\n"
		ant debug 
	else
		echo -e "\n########## using GRADLE to build debug apk #########\n"
		./gradlew assembleDebug 
	fi

	echo -e "\n########## signing apk with debugStore ##########\n"
	jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ${KEYSTORE} -storepass ${STOREPASS} bin/${APPNAME}-debug.apk ${ALIAS} 
	echo -e "\n########## debug build done ##########\n"

	if [ "$1" != "jenkins" ]; then
		read  -p "Escape (CTRL+C) or press enter to install debug apk on device/emulator."
		adb install -r bin/${APPNAME}-debug.apk
	fi
fi
