#pragma once
#include "jni.h"


extern "C" {
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_init(JNIEnv* env, jclass cls);
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_setTime(JNIEnv* env, jclass cls, jdouble time);
	JNIEXPORT jdouble JNICALL Java_com_mt_face_NativeUtils_getTime(JNIEnv* env, jclass cls);

	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_play(JNIEnv* env, jclass cls);
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_pause(JNIEnv* env, jclass cls);

	JNIEXPORT jobject JNICALL Java_com_mt_face_NativeUtils_getKnownPeople(JNIEnv* env, jclass cls);
	JNIEXPORT jobject JNICALL Java_com_mt_face_NativeUtils_getUnknownPeople(JNIEnv* env, jclass cls);
}

void* getWindowHandle();
void getClipboard(char* buf, int length);
void setClipboard(char* clipboard);
bool isFocused();
bool isMouseButtonDown();

void getCursorPos(double& x, double& y);
void setCursorPos(double x, double y);

void getWindowSize(int& x, int& y);
double getApplicationTime();
