#pragma once

#include "jni.h"
#include <stdio.h>
#include "Natives.h"
#include <string.h>

#include "imgui.h"
#include "imgui_impl_glfw.h"

extern "C" {
	jclass nativeUtils;
	JNIEnv* env;

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_init(JNIEnv* jniEnv, jclass cls)
	{
		nativeUtils = cls;
		env = jniEnv;
		char c[128];
		getClipboard(c, sizeof(c));
		printf("CB: %s\n", c);
	}

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_onMouseEvent(JNIEnv* env, jclass cls, jint button, jboolean press)
	{
		ImGui_ImplGlfw_MouseButtonCallback(button, press);
	}
	
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_onKeyEvent(JNIEnv* env, jclass cls, jint key, jboolean press)
	{
		ImGui_ImplGlfw_KeyCallback(key, press);
	}
	
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_onChar(JNIEnv* env, jclass cls, jchar c)
	{
		ImGui_ImplGlfw_CharCallback(c);
	}

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_onScrollEvent(JNIEnv* env, jclass cls, jdouble x, jdouble y)
	{
		ImGui_ImplGlfw_ScrollCallback(x, y);
	}

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_setVideoTime(JNIEnv* env, jclass cls, jdouble time)
	{
		
	}

	JNIEXPORT jdouble JNICALL Java_com_mt_face_NativeUtils_getVideoTime(JNIEnv* env, jclass cls)
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
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "getWindowHandle", "()J");
	return (void*) env->CallStaticLongMethod(nativeUtils, mid);
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
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "setClipboard", "(J)V");
	env->CallStaticVoidMethod(nativeUtils, mid, (jlong) clipboard);
}

bool isFocused()
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "isFocused", "()V");
	return env->CallStaticBooleanMethod(nativeUtils, mid);
}


bool isMouseButtonDown(int button)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "isMouseButtonDown", "(I)V");
	return env->CallStaticBooleanMethod(nativeUtils, mid, button);
}

void getCursorPos(double& x, double& y)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "getCursorPos", "(JJ)V");
	env->CallStaticVoidMethod(nativeUtils, mid, (jlong) &x, (jlong) &y);
}

void setCursorPos(double x, double y)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "setCursorPos", "(DD)V");
	env->CallStaticVoidMethod(nativeUtils, mid, x, y);
}

void getWindowSize(int& x, int& y)
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "getWindowSize", "(JJ)V");
	env->CallStaticVoidMethod(nativeUtils, mid, (jlong)& x, (jlong)& y);
}

double getApplicationTime()
{
	static jmethodID mid = env->GetStaticMethodID(nativeUtils, "getApplicationTime", "()D");
	return env->CallStaticDoubleMethod(nativeUtils, mid);
}
