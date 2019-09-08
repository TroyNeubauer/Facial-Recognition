#pragma once

#include "jni.h"
#include "Natives.h"
#include <string.h>
#include <tuple>

#include "imgui.h"
#include "imgui_impl_glfw.h"
#include "Renderer.h"

thread_local JNIEnv* env = nullptr;

extern "C" {

	void Java_com_mt_face_natives_NativeUtils_init(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
		Renderer::Init();
		JavaPrint("Native Initalization Complete");
	}

	void Java_com_mt_face_natives_NativeUtils_render(JNIEnv* jniEnv, jclass cls, int width, int height)
	{
		env = jniEnv;
		Renderer::Render(width, height);
	}

	void Java_com_mt_face_natives_NativeUtils_onMouseEvent(JNIEnv* jniEnv, jclass cls, jint button, jboolean press)
	{
		env = jniEnv;
		ImGui_ImplGlfw_MouseButtonCallback(button, press);
	}
	
	void Java_com_mt_face_natives_NativeUtils_onKeyEvent(JNIEnv* jniEnv, jclass cls, jint key, jboolean press)
	{
		env = jniEnv;
		ImGui_ImplGlfw_KeyCallback(key, press);
	}
	
	void Java_com_mt_face_natives_NativeUtils_onChar(JNIEnv* jniEnv, jclass cls, jchar c)
	{
		env = jniEnv;
		ImGui_ImplGlfw_CharCallback(c);
	}

	void Java_com_mt_face_natives_NativeUtils_onScrollEvent(JNIEnv* jniEnv, jclass cls, jdouble x, jdouble y)
	{
		env = jniEnv;
		ImGui_ImplGlfw_ScrollCallback(x, y);
	}

	void Java_com_mt_face_natives_NativeUtils_setVideoTime(JNIEnv* jniEnv, jclass cls, jdouble time)
	{
		env = jniEnv;
	}

	jdouble Java_com_mt_face_natives_NativeUtils_getVideoTime(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
		return 0.0;
	}


	void Java_com_mt_face_natives_NativeUtils_play(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
	}

	void Java_com_mt_face_natives_NativeUtils_pause(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
	}


	jobject Java_com_mt_face_natives_NativeUtils_getKnownPeople(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
		return nullptr;
	}

	jobject Java_com_mt_face_natives_NativeUtils_getUnknownPeople(JNIEnv* jniEnv, jclass cls)
	{
		env = jniEnv;
		return nullptr;
	}
}

std::tuple<jclass, jmethodID> Lookup(const char* className, const char* methodName, const char* methodDesc, bool isStatic)
{
	jclass clas = env->FindClass(className);
	if (clas == nullptr)
	{
		JavaPrint("Failed to find class! %s", className);
		env->FatalError("Failed to find class");
		return {nullptr, nullptr};
	}
#ifdef DEBUG
	JavaPrint("Got class for method %s, class: %p", methodName, clas);
#endif
	jmethodID method = (isStatic) ? env->GetStaticMethodID(clas, methodName, methodDesc) : env->GetMethodID(clas, methodName, methodDesc);
	if (method == nullptr)
	{
		JavaPrint("Failed to find method! %s, desc: %s, isStatic: %s", methodName, methodDesc, isStatic ? "true" : "false");
		env->FatalError("Failed to find method");
		return { clas, nullptr };
	}
#ifdef DEBUG
	JavaPrint("Got method %s, %p", methodName, method);
#endif
	return { clas, method };
}

void ThrowError(const char* message)
{
	env->ThrowNew(env->FindClass("java/lang/Exception"), message);
}

void* GetWindowHandle()
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "getWindowHandle", "()J", true);
	return (void*) env->CallStaticLongMethod(clas, mid);
}

jboolean isCopy;

void GetClipboard(char* buf, int length)
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "getClipboard", "()Ljava/lang/String;", true);
	jstring string = (jstring) env->CallStaticObjectMethod(clas, mid);
	const char* result = env->GetStringUTFChars(string, &isCopy);
	strncpy(buf, result, length);
	env->ReleaseStringUTFChars(string, result);
}

void SetClipboard(const char* clipboard)
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "setClipboard", "(J)V", true);
	env->CallStaticVoidMethod(clas, mid, (jlong) clipboard);
}

bool IsFocused()
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "isFocused", "()Z", true);
	return env->CallStaticBooleanMethod(clas, mid);
}


bool IsMouseButtonDown(int button)
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "isMouseButtonDown", "(I)Z", true);
	return env->CallStaticBooleanMethod(clas, mid, button);
}

void GetCursorPos(double* x, double* y)
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "getCursorPos", "(JJ)V", true);
	env->CallStaticVoidMethod(clas, mid, (jlong)((void*) x), (jlong)((void*) y));
}

void SetCursorPos(double x, double y)
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "setCursorPos", "(DD)V", true);
	env->CallStaticVoidMethod(clas, mid, x, y);
}

double GetApplicationTime()
{
	const auto[clas, mid] = Lookup("com/mt/face/natives/NativeUtils", "getApplicationTime", "()D", true);
	return env->CallStaticDoubleMethod(clas, mid);
}

