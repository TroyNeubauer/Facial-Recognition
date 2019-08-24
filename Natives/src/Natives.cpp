#pragma once

#include "jni.h"
#include <stdio.h>
#include "Natives.h"
#include <string.h>

extern "C" {
	jclass nativeUtils;
	JNIEnv* env;

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_init(JNIEnv* jniEnv, jclass cls)
	{
		nativeUtils = cls;
		env = jniEnv;
	}

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_setTime(JNIEnv* env, jclass cls, jdouble time)
	{
		char c[128];
		getClipboard(c, sizeof(c));
		printf("CB: %s\n", c);
	}

	JNIEXPORT jdouble JNICALL Java_com_mt_face_NativeUtils_getTime(JNIEnv* env, jclass cls)
	{
		return 0.0;
	}


	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_play(JNIEnv* env, jclass cls)
	{
	}

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_pause(JNIEnv* env, jclass cls)
	{
	}


	JNIEXPORT jobject JNICALL Java_com_mt_face_NativeUtils_getKnownPeople(JNIEnv* env, jclass cls)
	{
		return nullptr;
	}

	JNIEXPORT jobject JNICALL Java_com_mt_face_NativeUtils_getUnknownPeople(JNIEnv* env, jclass cls)
	{
		return nullptr;
	}
}

void* getWindowHandle()
{
	return nullptr;
}

jboolean isCopy;

void getClipboard(char* buf, int length)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "getClipboard", "()Ljava/lang/String;");
	jstring string = (jstring)env->CallStaticObjectMethod(nativeUtils, mid);
	const char* result = env->GetStringUTFChars(string, &isCopy);
	strncpy(buf, result, length);
	env->ReleaseStringUTFChars(string, result);
}

void setClipboard(char* clipboard)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "setClipboard", "()Ljava/lang/String;");
	jstring string = env->NewStringUTF(clipboard);

	env->CallStaticVoidMethod(nativeUtils, mid, string);
	env->ReleaseStringChars(string, clipboard);
}

bool isFocused()
{
	return false;
}


bool isMouseButtonDown()
{
	return false;
}

void getCursorPos(double& x, double& y)
{
}

void setCursorPos(double x, double y)
{
}

void getWindowSize(int& x, int& y)
{
}

double getApplicationTime()
{
	return 0.0;
}
