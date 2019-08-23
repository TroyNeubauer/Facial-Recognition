#pragma once

#include "jni.h"
#include <stdio.h>

extern "C" {
	JNIEXPORT void JNICALL Java_com_mt_face_NativeUtils_setTime(JNIEnv* env, jclass cls, jdouble time)
	{
		printf("Native code!\n");
	}

}
