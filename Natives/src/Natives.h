#pragma once

#include "jni.h"
#include <stdio.h>

extern thread_local JNIEnv* env;

extern "C" {
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_init(JNIEnv* env, jclass cls);
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_render(JNIEnv* env, jclass cls, int width, int height);

	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_onMouseEvent(JNIEnv* env, jclass cls, jint button, jboolean press);
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_onKeyEvent(JNIEnv* env, jclass cls, jint key, jboolean press);
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_onChar(JNIEnv* env, jclass cls, jchar c);
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_onScrollEvent(JNIEnv* env, jclass cls, jdouble x, jdouble y);

	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_setVideoTime(JNIEnv* env, jclass cls, jdouble time);
	JNIEXPORT jdouble JNICALL Java_com_mt_face_natives_NativeUtils_getVideoTime(JNIEnv* env, jclass cls);

	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_play(JNIEnv* env, jclass cls);
	JNIEXPORT void JNICALL Java_com_mt_face_natives_NativeUtils_pause(JNIEnv* env, jclass cls);

	JNIEXPORT jobject JNICALL Java_com_mt_face_natives_NativeUtils_getKnownPeople(JNIEnv* env, jclass cls);
	JNIEXPORT jobject JNICALL Java_com_mt_face_natives_NativeUtils_getUnknownPeople(JNIEnv* env, jclass cls);
}

void ThrowError(const char* message);

void* GetWindowHandle();
void GetClipboard(char* buf, int length);
void SetClipboard(const char* clipboard);
bool IsFocused();
bool IsMouseButtonDown(int button);

void GetCursorPos(double* x, double* y);
void SetCursorPos(double x, double y);

double GetApplicationTime();

template<typename... Types>
void JavaPrint(const char *format, Types... args)
{
	char buf[1024];
	snprintf(buf, sizeof(buf), format, args...);
	
	thread_local jclass clas = env->FindClass("com/mt/face/natives/NativeUtils");
	if (clas == nullptr) ThrowError("[JavaPrint] Unable to find native utils method");

	thread_local jmethodID mid = env->GetStaticMethodID(clas, "javaPrint", "(J)V");
	if (mid == nullptr) ThrowError("[JavaPrint] Unable to find NativeUtils::javaPrint method");

	env->CallStaticVoidMethod(clas, mid, buf);

}

